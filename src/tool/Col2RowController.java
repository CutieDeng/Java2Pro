package tool;

import data.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Col2RowController {

    private List<Data> data = new ArrayList<>();
    private List<String> columnNames = Arrays.stream(new String[]{
            "province", "location", "date", "confirm"
    }).collect(Collectors.toList());

    public List<Data> getDataList() {
        return data;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    private static class SpecialOneColumnData implements Data {

        String province;
        String location;
        String date;
        String confirm;

        public SpecialOneColumnData(String province, String location, String date, String cofirm) {
            this.province = province;
            this.location = location;
            this.date = date;
            this.confirm = cofirm;
        }

        @Override
        public String fetch(String property) {
            if ("province".equals(property))
                return province;
            else if ("location".equals(property))
                return location;
            else if ("date".equals(property))
                return date;
            else if ("confirm".equals(property))
                return confirm;
            return null;
        }
    }

    Col2RowController(File file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
            String[] split = reader.readLine().split(",");
            reader.lines().forEach(d -> {
                String[] split1 = d.split(",");
                String province = split1[0];
                String location = split1[1];
                for (int i = 2; i < split.length; i++) {
                    data.add(new SpecialOneColumnData(province, location, split[i], split1[i]));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
