package view2;

import data.Data;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tool.Tool;

import java.nio.file.Paths;
import java.util.*;

public class Launch02 extends Application {

    public static void main(String[] args) {
        Launch02.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AnchorPane pane = new AnchorPane();
        EnclosedObject<KeyFrame> frame = new EnclosedObject<>();
        pane.getChildren().add(method2("total cases",
                Tool.readDataFile(Paths.get("res", "file", "owid-covid-data.csv").toFile()), frame));

        Timeline timeline = new Timeline(frame.object);
        timeline.setCycleCount(Animation.INDEFINITE);

        primaryStage.setScene(new Scene(pane));
        primaryStage.show();

        timeline.play();
    }

    private static class EnclosedObject<T> {
        public T object;
    }

    private static LineChart<String, Number> method2(String valueName, List<Data> dataList, EnclosedObject<KeyFrame> enclosedObject) {
        ObservableList<String> objects = FXCollections.observableArrayList();
        CategoryAxis dates = new CategoryAxis(objects);
        dates.setLabel("时间");

        NumberAxis values = new NumberAxis();
        values.setLabel(valueName);

        LineChart<String, Number> timingChart = new LineChart<>(dates, values);

        final TreeMap<String, ArrayList<Data>> timeStamp = dataList.stream()
                .collect(TreeMap::new, (map, i) -> {
                    if (!map.containsKey(i.fetch("date")))
                        map.put(i.fetch("date"), new ArrayList<>());
                    map.get(i.fetch("date")).add(i);
                },
                        Map::putAll);

        final Map<String, XYChart.Series<String, Number>> countryMap = new HashMap<>();

        Iterator<String> i = timeStamp.keySet().iterator();

        timingChart.setCreateSymbols(false);

        enclosedObject.object = new KeyFrame(Duration.millis(600), e -> {
            if (i.hasNext()) {
                String next = i.next();
                ArrayList<Data> data = timeStamp.get(next);
                objects.add(next);
                data.forEach(d -> {
                    if (!countryMap.containsKey(d.fetch("iso code"))) {
                        Series<String, Number> nS = new Series<>();
                        nS.setName(d.fetch("iso code"));
                        timingChart.getData().add(nS);
                        countryMap.put(d.fetch("iso code"), nS);
                    }
                    if (d.fetch(valueName).length() > 0)
                        countryMap.get(d.fetch("iso code")).getData().add(new XYChart.Data<>(d.fetch("date"),
                            d.fetch(valueName).length() > 0 ? Double.parseDouble(d.fetch(valueName)) : 0));
                });
            }
        });

        return timingChart;
    }

    private static LineChart<String, Number> method(String valueName, List<Data> dataList, EnclosedObject<KeyFrame> enclosedObject) {
        CategoryAxis dates = new CategoryAxis();
        dates.setLabel("时间");
        NumberAxis values = new NumberAxis();
        values.setLabel(valueName);
        LineChart<String, Number> timingChart = new LineChart<>(dates, values);
        final HashMap<String, ArrayList<Data>> res = dataList.stream().collect(HashMap::new,
                (map, d) -> {
            if (!map.containsKey(d.fetch("iso code")))
                map.put(d.fetch("iso code"), new ArrayList<>());
            map.get(d.fetch("iso code")).add(d);
                }, Map::putAll);
        res.forEach((c, l) -> {
            final Series<String, Number> countryInfo = new Series<String, Number>();
            countryInfo.setName(c);
            l.stream().map(d -> new XYChart.Data<>(d.fetch("date"),
                    d.fetch(valueName).length() > 0 ? (Number)Double.parseDouble(d.fetch(valueName)) : 0))
                    .forEach(t -> {
                        countryInfo.getData().add(t);
                    });
            timingChart.getData().add(countryInfo);

        });
        return timingChart;
    }
}
