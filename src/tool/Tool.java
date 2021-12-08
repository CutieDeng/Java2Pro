package tool;

import data.Data;
import util.Holder;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
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

}
