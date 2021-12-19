package tabsupply;

import data.Data;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import service.DataService;
import service.ServiceFactory;
import serviceimplements.NormalDataServiceImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CovidLineAnimationSupplyImpl extends AbstractTabSupplyImpl {
    @Override
    protected Consumer<Void> getBeforeAction() {
        return null;
    }

    @Override
    protected Consumer<Void> getAfterAllAction() {
        return null;
    }

    private static final Supplier<Integer> cntSupplier = new Supplier<Integer>() {
        int number = 1;
        @Override
        synchronized
        public Integer get() {
            return number++;
        }
    };

    private final DataService service = new NormalDataServiceImpl();

    private static final int minimum = 739352;
    private static final int maximum = 739536;

    private String propertyName;

    @Override
    public Tab supply(ServiceFactory factory) {
        // 初始化一个标签页
        Tab ans = super.supply(factory);
        ans.setText("Covid Line Chart Animation " + cntSupplier.get());

        // 设置该标签页的提示信息
        ans.setTooltip(new Tooltip("疫情折线动图"));


        BorderPane mainPane = new BorderPane();
        ans.setContent(mainPane);

        {
            List<String> dateList = service.getDataList().stream().map(d -> d.fetch("date")).distinct().sorted().collect(Collectors.toList());
            ObservableList<String> observableDateList = FXCollections.observableArrayList(dateList);

            Axis<String> x = new CategoryAxis(observableDateList);
            Axis<Number> y = new NumberAxis();

            BorderPane graphPane = new BorderPane();
            mainPane.setCenter(graphPane);

            LineChart<String, Number> lineChart = new LineChart<>(x, y);
            graphPane.setCenter(lineChart);

            lineChart.setAnimated(false);
            lineChart.setCreateSymbols(false);

            ScrollBar rollbar = new ScrollBar();
            graphPane.setBottom(rollbar);

            rollbar.setMin(minimum);
            rollbar.setMax(maximum + 1);

            rollbar.setValue(minimum);

            ScrollPane scrollPane = new ScrollPane();
            mainPane.setRight(scrollPane);

            VBox box = StandTabSupplyTool.getSelectionsBox();
            scrollPane.setContent(box);

            List<RadioButton> propertiesButtons = service.getColumnNames().stream().filter(StandTabSupplyTool::filterMainColName)
                    .map(RadioButton::new).collect(Collectors.toList());
            ToggleGroup group = new ToggleGroup();
            group.getToggles().addAll(propertiesButtons);

            box.getChildren().addAll(propertiesButtons);

            List<RadioButton> locations = service.getDataList().stream().map(d -> d.fetch("location")).distinct()
                    .sorted().map(RadioButton::new).collect(Collectors.toList());

            ScrollPane locationScrollPane = new ScrollPane();
            mainPane.setLeft(locationScrollPane);

            VBox locationsBox = new VBox();
            locationScrollPane.setContent(locationsBox);

            Map<String, List<Data>> dateMap = service.getDataList().stream().collect(Collectors.groupingBy(d -> d.fetch("date")));
            Map<String, XYChart.Series<String, Number>> locationMap = locations.stream().map(RadioButton::getText)
                            .distinct().collect(HashMap::new,
                            (map, str) -> map.put(str, new XYChart.Series<>(str, FXCollections.observableArrayList())),
                            Map::putAll);

            rollbar.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (propertyName == null)
                    return ;
                int o = oldValue.intValue();
                int n = newValue.intValue();
                if (o == n)
                    return ;
                locationMap.forEach((s, stringNumberSeries) -> stringNumberSeries.getData().clear());
                Stream<String> validDates = dateList.stream().filter(d -> CovidLineChartTabSupplyImpl.dateTimeTransfer(d) < n);
                validDates.map(dateMap::get).flatMap(Collection::stream)
                        .filter(d -> !"".equals(d.fetch(propertyName)))
                        .forEach(d -> locationMap.get(d.fetch("location")).getData().add(new XYChart.Data<>(d.fetch("date"), Double.parseDouble(d.fetch(propertyName)))));
            });

            Timeline animation = new Timeline(new KeyFrame(Duration.millis(100.), e -> rollbar.increment()));
            animation.setCycleCount(maximum - minimum + 1);

            propertiesButtons.forEach(b -> b.setOnAction(e -> {
                propertyName = b.getText();
                rollbar.setValue(minimum);
                animation.play();
            }));

            boolean[] locationsEnsured = new boolean[locations.size()];
            IntStream.range(0, locations.size()).forEach(i -> locations.get(i).setOnAction(e -> {
                locationsEnsured[i] = ! locationsEnsured[i];
                if (locationsEnsured[i]) {
                    lineChart.getData().add(locationMap.get(locations.get(i).getText()));
                } else {
                    lineChart.getData().remove(locationMap.get(locations.get(i).getText()));
                }
            }));

            locationsBox.getChildren().addAll(locations);

        }


        return ans;
    }
}
