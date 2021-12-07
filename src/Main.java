import data.Data;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import tool.Tool;
import view2.Launch;

import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main extends Application{

    public static void main(String[] args) throws FileNotFoundException {
        List<Data> l = Tool.readDataFile(Paths.get("res", "file", "owid-covid-data.csv").toFile());
        l.stream().limit(20).forEach(System.out::println);
        l.stream().map(i -> i.fetch("iso code")).forEach(System.out::println);
        System.out.println("Hello World!");
        launch(Launch.class, args);
        System.exit(0);
//        List<Row> rows = read(Paths.get("res", "file", "owid-covid-data.csv").toFile());
//        graph01();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        {
            NumberAxis x = new NumberAxis("x", 0, 70, 10);
            NumberAxis y = new NumberAxis("y", 0, 15, 1);
            LineChart<Number, Number> lineChart = new LineChart<>(x, y);
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("Series name");

            @SuppressWarnings("unchecked") XYChart.Data<Number, Number>[] data = new XYChart.Data[] {
                    new XYChart.Data<Number, Number>(3, 4),
                    new XYChart.Data<Number, Number>(5, 4),
                    new XYChart.Data<Number, Number>(6, 7),
                    new XYChart.Data<Number, Number>(10, 3),
                    new XYChart.Data<Number, Number>(13, 4),
                    new XYChart.Data<Number, Number>(14, 1),
                    new XYChart.Data<Number, Number>(15, 5),
                    new XYChart.Data<Number, Number>(16, 6),
                    new XYChart.Data<Number, Number>(37, 2),
                    new XYChart.Data<Number, Number>(38, 2),
                    new XYChart.Data<Number, Number>(39, 3),
                    new XYChart.Data<Number, Number>(40, 4),
                    new XYChart.Data<Number, Number>(43, 5),
                    new XYChart.Data<Number, Number>(48, 3),
                    new XYChart.Data<Number, Number>(50, 2),
                    new XYChart.Data<Number, Number>(53, 6),
                    new XYChart.Data<Number, Number>(57, 3),
                    new XYChart.Data<Number, Number>(59, 3),
                    new XYChart.Data<Number, Number>(17, 8),
                    new XYChart.Data<Number, Number>(19, 10),
                    new XYChart.Data<Number, Number>(22, 5),
                    new XYChart.Data<Number, Number>(24, 2),
                    new XYChart.Data<Number, Number>(26, 1),
                    new XYChart.Data<Number, Number>(28, 3),
                    new XYChart.Data<Number, Number>(29, 3),
                    new XYChart.Data<Number, Number>(33, 4),
                    new XYChart.Data<Number, Number>(36, 2),
                    new XYChart.Data<Number, Number>(65, 3),
                    new XYChart.Data<Number, Number>(70, 2),
            };
            series.getData().addAll(data);

            lineChart.getData().add(series);
            series.setName("Line 1");

            final Group root = new Group(lineChart);

            final Scene scene = new Scene(root, 600, 400);

            primaryStage.setTitle("graph!");
            primaryStage.setScene(scene);

            primaryStage.show();
        }
    }

    /**
     * 一个粗糙的信息条目类
     * <p/>
     *
     * 该类的每条实例都对等数据库中一个条目的信息。<br>
     * <p/>
     *
     * 该类实例使用 {@link Row#row} 进行信息的存储，其信息内容类别与 {@link Row#colName} 一一对应。<br>
     * <p/>
     *
     * 该类实例通过一个以 "," （逗号）分割的字符串创建。<br>
     * 特别地，如果创建该类实例的字符串的逗号数目低于期望，那么剩下的列将会被一个空字符串补全，而不是一个空指针。<br>
     * todo: 超过的部分将会被忽略，并记录到日志中。<br>
     * <p/>
     */
    private static class Row implements Data {

        /**
         * 静态列描述，对各列属性作出相应的解释<br>
         * <p/>
         *
         * 请通过 {@link Row#initColName(String)} 方法初始化该变量信息，并保持其不变。<br>
         * <p/>
         *
         * 各信息条目列数不应超过该头部列名列数。<br>
         * <p/>
         *
         */
        static String[] colName;

        /**
         * 成员变量，描述了该实例的具体所蕴含的实际信息内容。<br>
         * <p/>
         */
        String[] row;
        Row(String r) {
            this.row = new String[colName.length];
            String[] split = r.split(",");
            IntStream.range(0, colName.length).forEach(i ->
                    row[i] = split.length > i ? split[i] : "");
        }

        /**
         * 初始化列的信息头，以便于设置对类实例信息的解释。<br>
         * <p/>
         *
         * @param colHead 各列信息头
         */
        static void initColName(String colHead) {
            colName = colHead.split(",");
        }

        static int getIndex(String colName) {
            return IntStream.range(0, Row.colName.length)
                    .filter(i->colName.equals(Row.colName[i]))
                    .findAny().orElseThrow(
                            () -> new RuntimeException(String.format("Cannot find %s! ", colName))
                    );
        }

        @Override
        public String toString() {
            return String.format("{ROW#(%d)%s}",
                    row.length,
                    IntStream.range(0, row.length).mapToObj(i -> String.format("%s: %s", colName[i], row[i]))
                        .collect(Collectors.joining(", ")));
        }

        // IDE tips bug: any.isEmpty() doesn't exist.
        @SuppressWarnings("SimplifyOptionalCallChains")
        @Override
        public String fetch(String property) {
            String match = property.toLowerCase(Locale.ROOT).replace('_', ' ');
            OptionalInt any = IntStream.range(0, colName.length).filter(i -> colName[i].toLowerCase(Locale.ROOT).replace('_', ' ')
                    .equals(match)).findAny();
            if (!any.isPresent()) {
                return null;
            }
            return this.row[any.getAsInt()];
        }
    }

    private static Map<String, Set<String>> compareIndex(List<Row> rows, int i) {
        return rows.stream().collect(HashMap<String, Set<String>>::new, (p, r) -> {
            if (!p.containsKey(r.row[0]))
                p.put(r.row[0], new HashSet<>());
            p.get(r.row[0]).add(r.row[i]);
        }, HashMap::putAll);
    }


    /**
     * 该方法用于分析 reproduction rate. <br>
     * <p/>
     *
     *
     */
    private static void graph01() {
        launch();
    }

    /**
     * 该方法负责确定各信息之间是否存在耦合关系。<br>
     * <p/>
     *
     * 即，（说人话便是）通过属性 a, b, c, 我们能够推导出 d 属性的具体内容，则称 d 能被 a, b, c 属性集表出。<br>
     * <p/>
     *
     * 该方法用于确定各属性列之间的主次关系，以便建立正确的数据层次结构。<br>
     * <p/>
     *
     * 当存在某两种属性互相表出时，优先考虑其上位属性，即具有聚合性质、积分性质、更具可读性的属性为主要属性，另一属性为次要属性。<br>
     * 如果有一个属性不能被任何其他属性表达，则其为主要属性。<br>
     * <p/>
     *
     */
    private static void readFile02() {

        List<Row> read = read(Paths.get("res", "file", "owid-covid-data.csv").toFile());
        System.out.println(Arrays.toString(Row.colName));
        assert read != null;
        final String numerator = "new_cases";
        final String denominator = "population";
        final String sum = "new_cases_per_million";
        final int $0 = Row.getIndex(numerator);
        final int $1 = Row.getIndex(denominator);
        final int $2 = Row.getIndex(sum);

        List<Row> collect = read.stream()
                .filter(
                i -> !"".equals(i.row[$0]) &&
                        !"".equals(i.row[$1]) &&
                        !"".equals(i.row[$2]) &&
                        !new BigDecimal(i.row[$0])
                        .divide(new BigDecimal(i.row[$1]), MathContext.DECIMAL32)
                        .multiply(new BigDecimal("1e6", MathContext.UNLIMITED))
                        .equals(new BigDecimal(i.row[$2]))
                ).collect(Collectors.toList());

        System.out.println(collect.size());

        collect.stream().map(i -> String.format("%s / %s (%s)!= %s", i.row[$0],
                i.row[$1],
                        new BigDecimal(i.row[$0])
                                .divide(new BigDecimal(i.row[$1]), MathContext.DECIMAL32)
                                .multiply(new BigDecimal("1e6", MathContext.UNLIMITED)),
                        i.row[$2]))
                .forEach(System.out::println);
    }

    /**
     * 读取数据集文件<br>
     * <p/>
     *
     * 读取数据集中所有的条目，并汇集成一个列表。<br>
     * <p/>
     *
     * @param file 待读入的文件标识符
     * @return 文件中的所有信息
     */
    private static List<Row> read(File file) {
        final Logger logger = Logger.getGlobal();
        if (!file.exists()) {
            logger.severe(String.format("%s doesn't exist. ", file));
            return null;
        }
        if (file.isDirectory()) {
            logger.severe(String.format("%s is a directory! ", file));
            return null;
        }
        if (!file.canRead()) {
            logger.severe(String.format("%s cannot be read. ", file));
            return null;
        }
        try (BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            Row.initColName(input.readLine());
            return input.lines().filter(Objects::nonNull)
                    .map(Row::new).filter(r -> r.fetch("date").matches("2020-0[1-6]-\\d{2}")).collect(Collectors.toList());
        } catch (IOException e) {
            logger.severe(e.getMessage());
            return null;
        }
    }

    /**
     * 该方法负责确定国家信息与其他信息之间是否存在简单的函数映射关系。<br>
     * <p/>
     *
     * 函数映射关系即对于一个映射 f, 我们发现对于一个国家的任何日期📅，其某个属性都保持不变，我们便认为这个属性与国家之间存在这样一个固定的映射关系。<br>
     * <p/>
     *
     *
     * @throws FileNotFoundException 对应的文件没有找到
     */
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
