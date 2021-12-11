package view;

import data.Data;
import javafx.beans.property.*;
import tool.Tool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 以TableRow存入的数据是已经处理好了的数据，<br>
 * 意思就是说，保证TableRow的每个对象的以下5种属性都不是null值。<br>
 * <p/>
 *
 */


public class TableRow {
    private final StringProperty isoCode = new SimpleStringProperty();
    private final StringProperty date = new SimpleStringProperty();
    private final IntegerProperty totalCases = new SimpleIntegerProperty();
    private final IntegerProperty newCases = new SimpleIntegerProperty();
    private final IntegerProperty totalDeaths = new SimpleIntegerProperty();

    private static String transfer(String name) {
        return Tool.transfer(name);
    }

    public TableRow(Data data) {
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
                // todo: write it later.
            } else if (p.getType() == StringProperty.class) {
                try {
                    Method set = StringProperty.class.getMethod("setValue", String.class);
                    set.invoke(p.get(this), fetch);
                } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {
                }
            }
        });
    }

    public StringProperty isoCodeProperty() {
        return isoCode;
    }

}
