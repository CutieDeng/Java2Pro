import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Hello World!");
        readFile();
    }

    private static class Row{
        static String[] colName;

        String[] row;
        Row(String r) {
            this.row = new String[colName.length];
            String[] split = r.split(",");
            IntStream.range(0, colName.length).forEach(i ->
                    row[i] = split.length > i ? split[i] : "");
        }

        @Override
        public String toString() {
            return String.format("{ROW#(%d)%s}",
                    row.length,
                    IntStream.range(0, row.length).mapToObj(i -> String.format("%s: %s", colName[i], row[i]))
                        .collect(Collectors.joining(", ")));
        }
    }

    private static Map<String, Set<String>> compareIndex(List<Row> rows, int i) {
        return rows.stream().collect(HashMap<String, Set<String>>::new, (p, r) -> {
            if (!p.containsKey(r.row[0]))
                p.put(r.row[0], new HashSet<>());
            p.get(r.row[0]).add(r.row[i]);
        }, HashMap::putAll);
    }

    private static void readFile() throws FileNotFoundException {
        final File toRead = Paths.get("res", "file",
                "owid-covid-data.csv").toFile();

        Scanner input = new Scanner(toRead);

        String head = input.nextLine();
        Row.colName = head.split(",");
        System.out.println("cols.length = " + Row.colName.length);
        System.out.println(Arrays.toString(Row.colName));
        List<Row> rowList = new ArrayList<>();

        while (input.hasNextLine())
            rowList.add(new Row(input.nextLine()));

        final String $1 = "excess_mortality_cumulative_per_million";
        // diabetes_prevalence, female_smokers, male_smokers, handwashing_facilities,
        // hospital_beds_per_thousand, life_expectancy, human_development_index,
        // excess_mortality_cumulative_absolute, excess_mortality_cumulative,
        // excess_mortality, excess_mortality_cumulative_per_million
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        final int index = IntStream.range(0, Row.colName.length)
                .filter(i -> $1.equals(Row.colName[i].toLowerCase(Locale.ROOT)))
                .findAny().getAsInt();

        Map<String, Set<String>> stringSetMap = compareIndex(rowList, index);

        stringSetMap.entrySet().forEach(System.out::println);
        System.out.println(stringSetMap.size());

        System.out.println("Function map: " +
                (stringSetMap.values().stream().anyMatch(s -> s.size() > 1) ?
                        "False" : "True"));

    }

}
