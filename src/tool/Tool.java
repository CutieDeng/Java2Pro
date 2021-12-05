package tool;

import data.Data;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class Tool {

    public static List<Data> readDataFile(File file) {
        try {
            Class<?> main = Class.forName("Main");
            Method readMethod = main.getDeclaredMethod("read", File.class);
            readMethod.setAccessible(true);
            List<Data> result = (List<Data>) readMethod.invoke(main, file);
            readMethod.setAccessible(false);
            return result.subList(0, 100);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
