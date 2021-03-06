package RowInfo;

import data.Data;
import javafx.beans.property.*;
import tool.Tool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
@SuppressWarnings("all")
public class TmpRow implements Tmp{
private final StringProperty isoCode = new SimpleStringProperty(); 
private final StringProperty continent = new SimpleStringProperty(); 
private final StringProperty location = new SimpleStringProperty(); 
private final StringProperty date = new SimpleStringProperty(); 
private final StringProperty testsUnits = new SimpleStringProperty(); 
private final StringProperty peopleVaccinated = new SimpleStringProperty(); 
private final StringProperty peopleFullyVaccinated = new SimpleStringProperty(); 
private final StringProperty totalBoosters = new SimpleStringProperty(); 
private final StringProperty stringencyIndex = new SimpleStringProperty(); 
private final StringProperty medianAge = new SimpleStringProperty(); 
private final StringProperty aged65Older = new SimpleStringProperty(); 
private final StringProperty aged70Older = new SimpleStringProperty(); 
private final StringProperty extremePoverty = new SimpleStringProperty(); 
private final StringProperty diabetesPrevalence = new SimpleStringProperty(); 
private final StringProperty femaleSmokers = new SimpleStringProperty(); 
private final StringProperty maleSmokers = new SimpleStringProperty(); 
private final StringProperty handwashingFacilities = new SimpleStringProperty(); 
private final StringProperty lifeExpectancy = new SimpleStringProperty(); 
private final StringProperty humanDevelopmentIndex = new SimpleStringProperty(); 
private final StringProperty excessMortalityCumulativeAbsolute = new SimpleStringProperty(); 
private final StringProperty excessMortalityCumulative = new SimpleStringProperty(); 
private final StringProperty excessMortality = new SimpleStringProperty(); 
private final IntegerProperty totalCases = new SimpleIntegerProperty(); 
private final IntegerProperty newCases = new SimpleIntegerProperty(); 
private final IntegerProperty totalDeaths = new SimpleIntegerProperty(); 
private final IntegerProperty newDeaths = new SimpleIntegerProperty(); 
private final IntegerProperty icuPatients = new SimpleIntegerProperty(); 
private final IntegerProperty hospPatients = new SimpleIntegerProperty(); 
private final IntegerProperty weeklyIcuAdmissions = new SimpleIntegerProperty(); 
private final IntegerProperty weeklyHospAdmissions = new SimpleIntegerProperty(); 
private final IntegerProperty newTests = new SimpleIntegerProperty(); 
private final IntegerProperty totalTests = new SimpleIntegerProperty(); 
private final IntegerProperty totalVaccinations = new SimpleIntegerProperty(); 
private final IntegerProperty newVaccinations = new SimpleIntegerProperty(); 
private final IntegerProperty population = new SimpleIntegerProperty(); 
private final DoubleProperty totalCasesPerMillion = new SimpleDoubleProperty(); 
private final DoubleProperty newCasesPerMillion = new SimpleDoubleProperty(); 
private final DoubleProperty totalDeathsPerMillion = new SimpleDoubleProperty(); 
private final DoubleProperty newDeathsPerMillion = new SimpleDoubleProperty(); 
private final DoubleProperty reproductionRate = new SimpleDoubleProperty(); 
private final DoubleProperty icuPatientsPerMillion = new SimpleDoubleProperty(); 
private final DoubleProperty hospPatientsPerMillion = new SimpleDoubleProperty(); 
private final DoubleProperty weeklyIcuAdmissionsPerMillion = new SimpleDoubleProperty(); 
private final DoubleProperty weeklyHospAdmissionsPerMillion = new SimpleDoubleProperty(); 
private final DoubleProperty totalTestsPerThousand = new SimpleDoubleProperty(); 
private final DoubleProperty newTestsPerThousand = new SimpleDoubleProperty(); 
private final DoubleProperty positiveRate = new SimpleDoubleProperty(); 
private final DoubleProperty testsPerCase = new SimpleDoubleProperty(); 
private final DoubleProperty totalVaccinationsPerHundred = new SimpleDoubleProperty(); 
private final DoubleProperty peopleVaccinatedPerHundred = new SimpleDoubleProperty(); 
private final DoubleProperty peopleFullyVaccinatedPerHundred = new SimpleDoubleProperty(); 
private final DoubleProperty totalBoostersPerHundred = new SimpleDoubleProperty(); 
private final DoubleProperty populationDensity = new SimpleDoubleProperty(); 
private final DoubleProperty gdpPerCapita = new SimpleDoubleProperty(); 
private final DoubleProperty cardiovascDeathRate = new SimpleDoubleProperty(); 
private final DoubleProperty hospitalBedsPerThousand = new SimpleDoubleProperty(); 
private final DoubleProperty excessMortalityCumulativePerMillion = new SimpleDoubleProperty(); 
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

public StringProperty isoCodeProperty() { return isoCode; }
public StringProperty continentProperty() { return continent; }
public StringProperty locationProperty() { return location; }
public StringProperty dateProperty() { return date; }
public StringProperty testsUnitsProperty() { return testsUnits; }
public StringProperty peopleVaccinatedProperty() { return peopleVaccinated; }
public StringProperty peopleFullyVaccinatedProperty() { return peopleFullyVaccinated; }
public StringProperty totalBoostersProperty() { return totalBoosters; }
public StringProperty stringencyIndexProperty() { return stringencyIndex; }
public StringProperty medianAgeProperty() { return medianAge; }
public StringProperty aged65OlderProperty() { return aged65Older; }
public StringProperty aged70OlderProperty() { return aged70Older; }
public StringProperty extremePovertyProperty() { return extremePoverty; }
public StringProperty diabetesPrevalenceProperty() { return diabetesPrevalence; }
public StringProperty femaleSmokersProperty() { return femaleSmokers; }
public StringProperty maleSmokersProperty() { return maleSmokers; }
public StringProperty handwashingFacilitiesProperty() { return handwashingFacilities; }
public StringProperty lifeExpectancyProperty() { return lifeExpectancy; }
public StringProperty humanDevelopmentIndexProperty() { return humanDevelopmentIndex; }
public StringProperty excessMortalityCumulativeAbsoluteProperty() { return excessMortalityCumulativeAbsolute; }
public StringProperty excessMortalityCumulativeProperty() { return excessMortalityCumulative; }
public StringProperty excessMortalityProperty() { return excessMortality; }
public IntegerProperty totalCasesProperty() { return totalCases; }
public IntegerProperty newCasesProperty() { return newCases; }
public IntegerProperty totalDeathsProperty() { return totalDeaths; }
public IntegerProperty newDeathsProperty() { return newDeaths; }
public IntegerProperty icuPatientsProperty() { return icuPatients; }
public IntegerProperty hospPatientsProperty() { return hospPatients; }
public IntegerProperty weeklyIcuAdmissionsProperty() { return weeklyIcuAdmissions; }
public IntegerProperty weeklyHospAdmissionsProperty() { return weeklyHospAdmissions; }
public IntegerProperty newTestsProperty() { return newTests; }
public IntegerProperty totalTestsProperty() { return totalTests; }
public IntegerProperty totalVaccinationsProperty() { return totalVaccinations; }
public IntegerProperty newVaccinationsProperty() { return newVaccinations; }
public IntegerProperty populationProperty() { return population; }
public DoubleProperty totalCasesPerMillionProperty() { return totalCasesPerMillion; }
public DoubleProperty newCasesPerMillionProperty() { return newCasesPerMillion; }
public DoubleProperty totalDeathsPerMillionProperty() { return totalDeathsPerMillion; }
public DoubleProperty newDeathsPerMillionProperty() { return newDeathsPerMillion; }
public DoubleProperty reproductionRateProperty() { return reproductionRate; }
public DoubleProperty icuPatientsPerMillionProperty() { return icuPatientsPerMillion; }
public DoubleProperty hospPatientsPerMillionProperty() { return hospPatientsPerMillion; }
public DoubleProperty weeklyIcuAdmissionsPerMillionProperty() { return weeklyIcuAdmissionsPerMillion; }
public DoubleProperty weeklyHospAdmissionsPerMillionProperty() { return weeklyHospAdmissionsPerMillion; }
public DoubleProperty totalTestsPerThousandProperty() { return totalTestsPerThousand; }
public DoubleProperty newTestsPerThousandProperty() { return newTestsPerThousand; }
public DoubleProperty positiveRateProperty() { return positiveRate; }
public DoubleProperty testsPerCaseProperty() { return testsPerCase; }
public DoubleProperty totalVaccinationsPerHundredProperty() { return totalVaccinationsPerHundred; }
public DoubleProperty peopleVaccinatedPerHundredProperty() { return peopleVaccinatedPerHundred; }
public DoubleProperty peopleFullyVaccinatedPerHundredProperty() { return peopleFullyVaccinatedPerHundred; }
public DoubleProperty totalBoostersPerHundredProperty() { return totalBoostersPerHundred; }
public DoubleProperty populationDensityProperty() { return populationDensity; }
public DoubleProperty gdpPerCapitaProperty() { return gdpPerCapita; }
public DoubleProperty cardiovascDeathRateProperty() { return cardiovascDeathRate; }
public DoubleProperty hospitalBedsPerThousandProperty() { return hospitalBedsPerThousand; }
public DoubleProperty excessMortalityCumulativePerMillionProperty() { return excessMortalityCumulativePerMillion; }
}
