package view2;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OtherTableRow {
    private final StringProperty province = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty date = new SimpleStringProperty();
    private final IntegerProperty confirm = new SimpleIntegerProperty();


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
