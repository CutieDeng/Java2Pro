package view2;

import data.Data;
import javafx.beans.property.*;
import tool.Tool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
public class TmpRow implements Tmp{
private static String transfer(String name) {
        return Tool.transfer(name);
    }
public TmpRow(Data data) {
        Class<?> subClass = this.getClass();

        Field[] declaredFields = subClass.getDeclaredFields();

        Arrays.stream(declaredFields).forEach(p -> {
            String fetch = data.fetch(transfer(p.getName()));
            if (p.getType() == IntegerProperty.class) {
                int f = fetch.length() > 0 ? (int) Double.parseDouble(fetch) : 0;
                try {
                    Method set = IntegerProperty.class.getMethod("set", int.class);
                    set.invoke(p.get(this), f);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
                }
            } else if (p.getType() == DoubleProperty.class) {
                int f = fetch.length() > 0 ? (int) Double.parseDouble(fetch) : 0;
                try {
                    Method set = DoubleProperty.class.getMethod("set", double.class);
                    set.invoke(p.get(this), f);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
                }
            } else if (p.getType() == StringProperty.class) {
                try {
                    Method set = StringProperty.class.getMethod("setValue", String.class);
                    set.invoke(p.get(this), fetch);
                } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {
                }
            }
        });
    }

}
