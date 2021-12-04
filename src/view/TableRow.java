package view;

import com.sun.istack.internal.NotNull;
import com.sun.org.apache.bcel.internal.classfile.Code;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

/**
 * 以TableRow存入的数据是已经处理好了的数据，<br>
 * 意思就是说，保证TableRow的每个对象的以下5种属性都不是null值。<br>
 * <p/>
 *
 */


public class TableRow {
    private StringProperty isoCode;
    private StringProperty date;
    private IntegerProperty totalCases;
    private IntegerProperty newCases;
    private IntegerProperty totalDeaths;

    public TableRow(String isoCode, String date, Integer totalCases,
                    Integer newCases, Integer totalDeaths) {
        this.setIsoCode(isoCode);
        this.setDate(date);
        this.setTotalCases(totalCases);
        this.setNewCases(newCases);
        this.setTotalDeaths(totalDeaths);
    }

    public String getIsoCode() {
        return isoCode.get();
    }

    public StringProperty isoCodeProperty() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        if (this.isoCode == null) {
            this.isoCode = new SimpleStringProperty(this, isoCode);
        }
        this.isoCode.set(isoCode);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        if (this.date == null) {
            this.date = new SimpleStringProperty(this, date);
        }
        this.date.set(date);
    }

    public int getTotalCases() {
        return totalCases.get();
    }

    public IntegerProperty totalCasesProperty() {
        return totalCases;
    }

    public void setTotalCases(Integer totalCases) {
        if (this.totalCases == null) {
            this.totalCases = new SimpleIntegerProperty(this, totalCases.toString());
        }
        this.totalCases.set(totalCases);
    }

    public int getNewCases() {
        return newCases.get();
    }

    public IntegerProperty newCasesProperty() {
        return newCases;
    }

    public void setNewCases(Integer newCases) {
        if (this.newCases == null) {
            this.newCases = new SimpleIntegerProperty(this, newCases.toString());
        }
        this.newCases.set(newCases);
    }

    public int getTotalDeaths() {
        return totalDeaths.get();
    }

    public IntegerProperty totalDeathsProperty() {
        return totalDeaths;
    }

    public void setTotalDeaths(Integer totalDeaths) {
        if (this.totalDeaths == null) {
            this.totalDeaths = new SimpleIntegerProperty(this, totalDeaths.toString());
        }
        this.totalDeaths.set(totalDeaths);
    }
}
