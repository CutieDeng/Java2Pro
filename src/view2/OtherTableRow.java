package view2;

import data.Data;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.DateFormat;

public class OtherTableRow {
    private final StringProperty province = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty date = new SimpleStringProperty();
    private final IntegerProperty confirm = new SimpleIntegerProperty();

    public OtherTableRow(Data data) {
        province.set(data.fetch("province"));
        location.set(data.fetch("location"));
        this.date.set(data.fetch("date"));
        confirm.set(Integer.parseInt("0" + data.fetch("confirm")));
    }


    public StringProperty provinceProperty() {
        return province;
    }

    public StringProperty locationProperty() {
        return location;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public IntegerProperty confirmProperty() {
        return confirm;
    }
}
