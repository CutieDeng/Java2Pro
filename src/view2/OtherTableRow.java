package view2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OtherTableRow {
    private final StringProperty province_State = new SimpleStringProperty();
    private final StringProperty country_Region = new SimpleStringProperty();
    private final StringProperty date = new SimpleStringProperty();


    public StringProperty province_StateProperty() {
        return province_State;
    }

    public StringProperty country_RegionProperty() {
        return country_Region;
    }

    public StringProperty dateProperty() {
        return date;
    }
}
