package tool;

import data.Data;
import util.Holder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class FileController {

    private final File dataFile;

    FileController(File dataFile) {
        this.dataFile = dataFile;

        Holder<List<String>> holder = new Holder<>();
        basicList = Tool.readDataFile(dataFile, holder);
        basicListColName = holder.obj;

        higherList = new ArrayList<>();
        // 二次筛选！
        {
            HashSet<String> special = new HashSet<>();
            basicList.stream().forEach(b -> {
                if (special.contains(b.fetch("iso code")))
                    return ;
                special.add(b.fetch("iso code"));
                higherList.add(b);
            });
        }
        if (basicListColName.contains("total_cases")) {
            String[] specialCols = new String[] {
                    "iso_code",
                    "continent",
                    "location",
                    "population",
                    "population_density",
                    "stringency_index",
                    "life_expectancy",
                    "median_age",
                    "human_development_index",
                    "aged_65_older",
                    "aged_70_older",
                    "gdp_per_capita",
                    "extreme_poverty",
                    "handwashing_facilities",
                    "hospital_beds_per_thousand",
                    "cardiovasc_death_rate",
                    "diabetes_prevalence",
                    "female_smokers",
                    "male_smokers"
            };
            higherListColName = Arrays.stream(specialCols).filter(basicListColName::contains).collect(Collectors.toList());
        }
    }

    public List<Data> basicList;
    public List<String> basicListColName;

    public List<Data> higherList;
    public List<String> higherListColName;





}
