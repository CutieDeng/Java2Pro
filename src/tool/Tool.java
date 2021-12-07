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
        try {
            Class<?> main = Class.forName("Main");
            Method readMethod = main.getDeclaredMethod("read", File.class);
            readMethod.setAccessible(true);
            List<Data> result = (List<Data>) readMethod.invoke(main, file);
            readMethod.setAccessible(false);
            return result;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<Data> readDataFile(File file, Holder<List<String>> cols) {
        List<Data> r = readDataFile(file);
        Class<? extends Data> row = r.get(0).getClass();
        Field colName = null;
        try {
            colName = row.getDeclaredField("colName");
                colName.setAccessible(true);
            cols.obj = (Arrays.asList((String[]) colName.get(row)));
            colName.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Logger.getAnonymousLogger().warning(e.getLocalizedMessage());
        }
        return r;
    }

}
