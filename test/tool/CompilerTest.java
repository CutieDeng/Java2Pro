package tool;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CompilerTest {

    public static void main(String[] args) {
//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
//        Iterable<? extends JavaFileObject> javaFileObjectsFromStrings = manager.getJavaFileObjectsFromStrings(CompilerTest::createClass);
//        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
//            JavaFileObject javaFileObject = manager.makeStringSource(fileName, source);
//            CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
//            if (task.call()) {
//                results = manager.getClassBytes();
//            }
//        }
    }

    private static Iterator<String> createClass() {
        List<String> list = new ArrayList<>();
        list.add("package view2; ");
        list.add("package view;\n" +
                "\n" +
                "import data.Data;\n" +
                "import javafx.beans.property.*;\n" +
                "import tool.Tool;\n" +
                "\n" +
                "import java.lang.reflect.Field;\n" +
                "import java.lang.reflect.InvocationTargetException;\n" +
                "import java.lang.reflect.Method;\n" +
                "import java.util.Arrays;\n" +
                "\n" +
                "/**\n" +
                " * 以TableRow存入的数据是已经处理好了的数据，<br>\n" +
                " * 意思就是说，保证TableRow的每个对象的以下5种属性都不是null值。<br>\n" +
                " * <p/>\n" +
                " *\n" +
                " */\n" +
                "\n" +
                "\n" +
                "public class TableRow {\n" +
                "    private final StringProperty isoCode = new SimpleStringProperty();\n" +
                "    private final StringProperty date = new SimpleStringProperty();\n" +
                "    private final IntegerProperty totalCases = new SimpleIntegerProperty();\n" +
                "    private final IntegerProperty newCases = new SimpleIntegerProperty();\n" +
                "    private final IntegerProperty totalDeaths = new SimpleIntegerProperty();\n" +
                "\n" +
                "    private static String transfer(String name) {\n" +
                "        return Tool.transfer(name);\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     *\n" +
                "     * @param data\n" +
                "     */\n" +
                "    public TableRow(Data data) {\n" +
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
                "                // todo: write it later.\n" +
                "            } else if (p.getType() == StringProperty.class) {\n" +
                "                try {\n" +
                "                    Method set = StringProperty.class.getMethod(\"setValue\", String.class);\n" +
                "                    set.invoke(p.get(this), fetch);\n" +
                "                } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {\n" +
                "                }\n" +
                "            }\n" +
                "        });\n" +
                "    }\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "//        new TableRow(null);\n" +
                "    }\n" +
                "\n" +
                "    public TableRow(String isoCode, String date, Integer totalCases,\n" +
                "                    Integer newCases, Integer totalDeaths) {\n" +
                "        this.setIsoCode(isoCode);\n" +
                "        this.setDate(date);\n" +
                "        this.setTotalCases(totalCases);\n" +
                "        this.setNewCases(newCases);\n" +
                "        this.setTotalDeaths(totalDeaths);\n" +
                "    }\n" +
                "\n" +
                "    public String getIsoCode() {\n" +
                "        return isoCode.get();\n" +
                "    }\n" +
                "\n" +
                "    public StringProperty isoCodeProperty() {\n" +
                "        return isoCode;\n" +
                "    }\n" +
                "\n" +
                "    public void setIsoCode(String isoCode) {\n" +
                "        this.isoCode.set(isoCode);\n" +
                "    }\n" +
                "\n" +
                "    public String getDate() {\n" +
                "        return date.get();\n" +
                "    }\n" +
                "\n" +
                "    public StringProperty dateProperty() {\n" +
                "        return date;\n" +
                "    }\n" +
                "\n" +
                "    public void setDate(String date) {\n" +
                "        this.date.set(date);\n" +
                "    }\n" +
                "\n" +
                "    public int getTotalCases() {\n" +
                "        return totalCases.get();\n" +
                "    }\n" +
                "\n" +
                "    public IntegerProperty totalCasesProperty() {\n" +
                "        return totalCases;\n" +
                "    }\n" +
                "\n" +
                "    public void setTotalCases(Integer totalCases) {\n" +
                "        this.totalCases.set(totalCases);\n" +
                "    }\n" +
                "\n" +
                "    public int getNewCases() {\n" +
                "        return newCases.get();\n" +
                "    }\n" +
                "\n" +
                "    public IntegerProperty newCasesProperty() {\n" +
                "        return newCases;\n" +
                "    }\n" +
                "\n" +
                "    public void setNewCases(Integer newCases) {\n" +
                "        this.newCases.set(newCases);\n" +
                "    }\n" +
                "\n" +
                "    public int getTotalDeaths() {\n" +
                "        return totalDeaths.get();\n" +
                "    }\n" +
                "\n" +
                "    public IntegerProperty totalDeathsProperty() {\n" +
                "        return totalDeaths;\n" +
                "    }\n" +
                "\n" +
                "    public void setTotalDeaths(Integer totalDeaths) {\n" +
                "        this.totalDeaths.set(totalDeaths);\n" +
                "    }\n" +
                "}\n");
        String collect = list.stream().collect(Collectors.joining("%n"));
        ArrayList<String> objects = new ArrayList<>();
        objects.add(collect);
        return objects.iterator();
    }

}
