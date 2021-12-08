package tool;

import data.Data;
import util.Holder;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

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

}
