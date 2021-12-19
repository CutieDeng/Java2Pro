package tabsupply;

import data.Data;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.DataService;
import service.ServiceFactory;
import serviceimplements.NormalDataServiceImpl;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static tabsupply.StandTabSupplyTool.getSelectionsBox;

public class CovidLineChartTabSupplyImpl extends AbstractTabSupplyImpl{

    // 两个没什么用的硬编码嗷嗷
    private static final int minimum = 739352;
    private static final int maximum = 739536;
    private static final int diffMax = 13;

    @Override
    protected Consumer<Void> getBeforeAction() {
        return beforeAction;
    }

    private Consumer<Void> beforeAction;

    private Consumer<Void> afterAll;

    @Override
    protected Consumer<Void> getAfterAllAction() {
        return afterAll;
    }

    private final DataService service = new NormalDataServiceImpl();

    private static final IntSupplier cntSupplier = new IntSupplier() {
        private int cnt = 1;
        @Override
        public int getAsInt() {
            return cnt++;
        }
    };

    @Override
    protected Tab tabGenerate() {
        Tab ans = super.tabGenerate();
        ans.setText("疫情信息折线统计图 " + cntSupplier.getAsInt());
        return ans;
    }

    private String propertyName = "";

    @Override
    public Tab supply(ServiceFactory factory) {
        super.supply(factory);

        BorderPane pane = new BorderPane();
        ans.setContent(pane);

        {
            ObservableList<String> dateCollection = FXCollections.observableArrayList();
            Axis<String> x = new CategoryAxis(dateCollection);
            Axis<Number> y = new NumberAxis();
            BorderPane graphRelatedPane = new BorderPane();

            x.setLabel("日期");

            x.setAnimated(false);
            y.setAnimated(false);

            LineChart chart = new LineChart(x, y);

            beforeAction = v -> {
                factory.getMenuBarService().setExportOnAction(v2 -> {
                    WritableImage tmpSnap = chart.snapshot(new SnapshotParameters(), null);
                    exportAction(tmpSnap);
                });
            };
            afterAll = v -> factory.getMenuBarService().setExportOnAction(null);


            graphRelatedPane.setCenter(chart);

            chart.setAnimated(false);

            pane.setCenter(graphRelatedPane);
            List<String> dateList = service.getDataList().stream().map(d -> d.fetch("date")).filter(Objects::nonNull)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            {
                List<String> names = service.getColumnNames().stream().filter(c -> !"iso_code".equals(c) && !"location".equals(c) && !"continent".equals(c))
                        .filter(c -> !"date".equals(c))
                        .collect(Collectors.toList());

                VBox selections = getSelectionsBox();
                selections.setPrefWidth(284.);

                // 创建个临时页，展示这些数据呜呜
                {
                    ScrollPane tmpPane = new ScrollPane(selections);
                    tmpPane.setFitToWidth(true);
                    pane.setRight(tmpPane);
                }

                RadioButton[] buttons = names.stream().map(RadioButton::new).toArray(RadioButton[]::new);
                ToggleGroup group = new ToggleGroup();

                group.getToggles().addAll(buttons);
                selections.getChildren().addAll(buttons);

                {
                    ScrollPane scrollPane = new ScrollPane();
                    scrollPane.setFitToWidth(true);
                    pane.setLeft(scrollPane);

                    VBox locationBoxes = getSelectionsBox();

                    scrollPane.setContent(locationBoxes);

                    List<String> locations = service.getDataList().stream().map(d -> d.fetch("location")).distinct()
                            .filter(Objects::nonNull)
                            .sorted()
                            .collect(Collectors.toList());
                    List<RadioButton> radioButtons = locations.stream().map(RadioButton::new).collect(Collectors.toList());
                    boolean[][] radioButtonBooleans = new boolean[1][radioButtons.size()];

                    Map<String, XYChart.Series<String, Number>> countryListMap = new HashMap<>();

                    for (int i = 0; i < radioButtons.size(); i++) {
                        int finalI = i;
                        radioButtons.get(i).setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            synchronized
                            public void handle(ActionEvent event) {
                                radioButtonBooleans[0][finalI] = !radioButtonBooleans[0][finalI];
//                                Logger.getGlobal().info("Now You make the radio button " + (radioButtonBooleans[0][finalI] ? "active!" : "sleepy. "));
                                if (radioButtonBooleans[0][finalI]) {
                                    chart.getData().add(countryListMap.get(radioButtons.get(finalI).getText()));
                                } else {
                                    chart.getData().remove(countryListMap.get(radioButtons.get(finalI).getText()));
                                }
                            }
                        });
                    }

                    radioButtons.forEach(b -> {
                        XYChart.Series<String, Number> series = new XYChart.Series<>();
                        series.setName(b.getText());
                        countryListMap.put(b.getText(), series);
                    });

                    Map<String, List<Data>> dateMap = service.getDataList().stream()
                            .collect(HashMap::new, (stringListHashMap, data) -> {
                                if (!stringListHashMap.containsKey(data.fetch("date")))
                                    stringListHashMap.put(data.fetch("date"), new ArrayList<>());
                                stringListHashMap.get(data.fetch("date")).add(data);
                            }, Map::putAll);

                    locationBoxes.getChildren().addAll(radioButtons);

                    ScrollBar scrollBar = new ScrollBar();
                    graphRelatedPane.setBottom(scrollBar);

                    scrollBar.setMin(minimum-1.);
                    scrollBar.setMax(maximum+1.);

                    scrollBar.setValue(minimum);

                    dateList.stream().filter(d -> {
                        double tmp = dateTimeTransfer(d) - scrollBar.getValue();
                        return tmp >= - diffMax && tmp <= diffMax;
                    } ).forEach(dateCollection::add);

                    scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        synchronized
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if (oldValue.intValue() == newValue.intValue())
                                return ;
                            dateCollection.clear();

                            int newV = newValue.intValue();
                            List<String> validDates = dateList.stream().filter(d -> {
                                        int diff = dateTimeTransfer(d) - newV;
                                        return (diff >= -diffMax && diff <= diffMax);
                                    })
                                    .collect(Collectors.toList());
                            dateCollection.addAll(validDates);

                            for (Map.Entry<String, XYChart.Series<String, Number>> entry : countryListMap.entrySet()) {
                                ObservableList<XYChart.Data<String, Number>> datas = entry.getValue().getData();
                                datas.clear();
                            }
                            validDates.stream().map(dateMap::get).flatMap(Collection::stream)
                                    .filter(d -> !"".equals(d.fetch(propertyName)))
                                    .filter(v -> !propertyName.equals(""))
                                    .forEach(d -> countryListMap.get(d.fetch("location")).getData().add(new XYChart.Data<>(d.fetch("date"),
                                            Double.parseDouble(d.fetch(propertyName)))));
                        }
                    });

                    Arrays.stream(buttons).forEach(b -> b.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        synchronized
                        public void handle(ActionEvent event) {
                            propertyName = b.getText();
                            for (Map.Entry<String, XYChart.Series<String, Number>> entry : countryListMap.entrySet()) {
                                ObservableList<XYChart.Data<String, Number>> data = entry.getValue().getData();
                                data.clear();
                            }
                            dateCollection.stream().map(dateMap::get).flatMap(Collection::stream)
                                    .filter(d -> !"".equals(d.fetch(propertyName)))
                                    .forEach(d -> countryListMap.get(d.fetch("location")).getData().add(new XYChart.Data<>(d.fetch("date"),
                                            Double.parseDouble(d.fetch(propertyName)))));
                        }
                    }));

                }

            }

        }

        return ans;
    }


    private void exportAction(WritableImage writableImage) {
        File file = StandTabSupplyTool.getChooseFile(new FileChooser(), "CovidLineChart",
                new FileChooser.ExtensionFilter("image", "*.png"));

        if (file == null) return;

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
        } catch (Exception s) {
            s.printStackTrace();
        }

        try {
            file.createNewFile();//如果保存的文件没有数据，则需要这句话。否则，不需要
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int dateTimeTransfer(String date) {
        char[] chars = date.toCharArray();
        int year = 0;
        int month = -1;
        int day = -1;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                if (day >= 0) {
                    day = 10 * day + chars[i] - '0';
                } else if (month >= 0) {
                    month = 10 * month + chars[i] - '0';
                } else if (year >= 0) {
                    year = 10 * year + chars[i] - '0';
                }
            } else {
                if (year < 0)
                    year = 0;
                else if (month < 0)
                    month = 0;
                else if (day < 0)
                    day = 0;
            }
        }
        return year * 366 + month * 31 + day;
    }
}
