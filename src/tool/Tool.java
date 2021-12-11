package tool;

import data.Data;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import util.Holder;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class Tool {

    public static List<Data> readDataFile(File file) {
        return readDataFile(file, new Holder<>());
    }

    public static List<Data> readDataFile(File file, Holder<List<String>> cols) {
        List<Data> result = null;
        try {
            Class<?> main = Class.forName("Main");
            Method readMethod = main.getDeclaredMethod("read", File.class);
            readMethod.setAccessible(true);
            //noinspection unchecked
            result = (List<Data>) readMethod.invoke(main, file);
            readMethod.setAccessible(false);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
            result = new ArrayList<>();
        }
        Class<? extends Data> row = result.get(0).getClass();
        try {
            Field colName = row.getDeclaredField("colName");
            colName.setAccessible(true);
            cols.obj = (Arrays.asList((String[]) colName.get(row)));
            colName.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Logger.getAnonymousLogger().warning(e.getLocalizedMessage());
        }
        return result;
    }

    /**
     * 实现了 SHA1 安全哈希算法。<br>
     * <p/>
     *
     *
     * @param file 传入一个文件对象
     * @return 返回该文件对象的哈希值。
     */
    public static String hashFileInSHA1(File file) {
        final String hashAlgorithmType = "SHA-1";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
            final MessageDigest digest = MessageDigest.getInstance(hashAlgorithmType);
            digest.update(reader.lines().collect(Collectors.joining(System.lineSeparator())).getBytes(StandardCharsets.UTF_8));
            byte[] digest1 = digest.digest();
            StringBuilder result = new StringBuilder(160);
            for (byte b : digest1) {
                if ((b & 0xf0) == 0)
                    result.append(0);
                result.append(String.format("%s", Integer.toHexString(0xff & b)));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setEnterKeyForButton(Button button) {
        button.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.ENTER)
                button.fire();
        });
    }

    public static String transfer(String name) {
        char[] chars = name.toCharArray();
        StringBuilder ans = new StringBuilder();
        for (char c : chars) {
            if (c >= 'A' && c <= 'Z')
                ans.append(' ').append((char)(c + 'a' - 'A'));
            else
                ans.append(c);
        }
        return ans.toString();
    }

    public static void main(String[] args) {
        createClass(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public static boolean createClass(List<String> stringProperties, List<String> intProperties, List<String> doubleProperties) {
        final File classFile = Paths.get("src", "view2", "TmpRow.java").toFile();
        try (PrintStream stream = new PrintStream(new BufferedOutputStream(new FileOutputStream(classFile)))) {
            stream.println("package view2;\n" +
                    "\n" +
                    "import data.Data;\n" +
                    "import javafx.beans.property.*;\n" +
                    "import tool.Tool;\n" +
                    "\n" +
                    "import java.lang.reflect.Field;\n" +
                    "import java.lang.reflect.InvocationTargetException;\n" +
                    "import java.lang.reflect.Method;\n" +
                    "import java.util.Arrays;");

            stream.println("public class TmpRow implements Tmp{");
            stringProperties.forEach(s -> stream.format("private final StringProperty %s = new SimpleStringProperty(); ", s));
            intProperties.forEach(i -> stream.format("private final IntegerProperty %s = new IntegerProperty(); ", i));
            doubleProperties.forEach(d -> stream.format("private final DoubleProperty %s = new DoubleProperty(); ", d));

            stream.println("private static String transfer(String name) {\n" +
                    "        return Tool.transfer(name);\n" +
                    "    }");

            stream.println("public TmpRow(Data data) {\n" +
                    "        Class<?> subClass = this.getClass();\n" +
                    "\n" +
                    "        Field[] declaredFields = subClass.getDeclaredFields();\n" +
                    "\n" +
                    "        Arrays.stream(declaredFields).forEach(p -> {\n" +
                    "            String fetch = data.fetch(transfer(p.getName()));\n" +
                    "            if (p.getType() == IntegerProperty.class) {\n" +
                    "                int f = fetch.length() > 0 ? (int) Double.parseDouble(fetch) : 0;\n" +
                    "                try {\n" +
                    "                    Method set = IntegerProperty.class.getMethod(\"set\", int.class);\n" +
                    "                    set.invoke(p.get(this), f);\n" +
                    "                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {\n" +
                    "                }\n" +
                    "            } else if (p.getType() == DoubleProperty.class) {\n" +
                    "                int f = fetch.length() > 0 ? (int) Double.parseDouble(fetch) : 0;\n" +
                    "                try {\n" +
                    "                    Method set = DoubleProperty.class.getMethod(\"set\", double.class);\n" +
                    "                    set.invoke(p.get(this), f);\n" +
                    "                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {\n" +
                    "                }\n" +
                    "            } else if (p.getType() == StringProperty.class) {\n" +
                    "                try {\n" +
                    "                    Method set = StringProperty.class.getMethod(\"setValue\", String.class);\n" +
                    "                    set.invoke(p.get(this), fetch);\n" +
                    "                } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {\n" +
                    "                }\n" +
                    "            }\n" +
                    "        });\n" +
                    "    }\n" +
                    "");

            stringProperties.forEach(s -> stream.format("public StringProperty %sProperty() { return %s; }", s, s));

            stream.println("}");
            stream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, "src/view2/TmpRow.java");
        try {
            Class.forName("view2.TmpRow");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
