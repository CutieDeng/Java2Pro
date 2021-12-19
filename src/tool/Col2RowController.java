package tool;

import data.Data;

import java.io.*;
import java.text.DateFormat;
import java.time.LocalDate;
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
            for (int i = 2+2; i < split.length; i++) {
                char[] tmpChars = split[i].toCharArray();
                int index = 0;
                int month = 0;
                while (index < tmpChars.length) {
                    if (tmpChars[index] < '0' || tmpChars[index] > '9')
                        break;
                    month = (month * 10) + tmpChars[index] - '0';
                    index++;
                }
                index++;
                int day = 0;
                while (index < tmpChars.length) {
                    if (tmpChars[index] < '0' || tmpChars[index] > '9')
                        break;
                    day = day * 10 + tmpChars[index] - '0';
                    index++;
                }
                index++;
                int year = 20;
                while (index < tmpChars.length) {
                    if (tmpChars[index] < '0' || tmpChars[index] > '9')
                        break;
                    year = year * 10 + tmpChars[index] - '0';
                    index++;
                }
                assert (year >= 1000 && year <= 9999);
                StringBuilder builder = new StringBuilder().append(year).append("-");
                if (month < 10)
                    builder.append("0");
                builder.append(month).append("-");
                if (day < 10)
                    builder.append("0");
                builder.append(day);
                split[i] = builder.toString();
            }
            reader.lines().forEach(d -> {
                String[] split1 = d.split(",");
                String province = split1[0];
                String location = split1[1];
                for (int i = 2+2; i < split.length; i++) {
                    try {
                        Integer.parseInt(split1[i]);
                        data.add(new SpecialOneColumnData(province, location, split[i], split1[i]));
                    } catch (RuntimeException e) {
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
