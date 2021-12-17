package tabsupply;

import data.Data;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import service.DataService;
import service.ServiceFactory;
import serviceimplements.HighDataServiceImpl;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LocationBarChartTabSupplyImpl extends AbstractTabSupplyImpl{

    @Override
    protected Consumer<Void> getBeforeAction() {
        return null;
    }

    @Override
    protected Consumer<Void> getAfterAllAction() {
        return null;
    }

    private DataService service = new HighDataServiceImpl();

    @Override
    protected Tab tabGenerate() {
        return super.tabGenerate();
    }

    @Override
    public Tab supply(ServiceFactory factory) {
        super.supply(factory);

        BorderPane pane = new BorderPane();
        ans.setContent(pane);

        {
            VBox verticalBox = new VBox();
            verticalBox.setPadding(new Insets(9.2));
            verticalBox.setSpacing(9.2);
            verticalBox.setPrefWidth(215.);
            pane.setRight(verticalBox);

            List<String> names = service.getColumnNames().stream()
                    .filter(name -> !"iso_code".equals(name) && !"location".equals(name) && !"continent".equals(name))
                    .collect(Collectors.toList());

            RadioButton[] buttons = new RadioButton[names.size()];
            ToggleGroup group = new ToggleGroup();
            for (int i = 0; i < buttons.length; i++) {
                buttons[i] = new RadioButton(names.get(i));
            }

            {
                // 创建一个相关的图表信息...
                Axis<String> x = new CategoryAxis();
                Axis<Number> y = new NumberAxis();
                BarChart<String, Number> chart = new BarChart<>(x, y);

                pane.setCenter(chart);

                XYChart.Series<String, Number> allData = new XYChart.Series<>();
                chart.getData().add(allData);
                ObservableList[] infoGroup = new ObservableList[buttons.length];
                for (int i = 0; i < buttons.length; i++) {
                    int finalI = i;
                    infoGroup[finalI] = FXCollections.observableArrayList();
                    service.getDataList().stream().filter(d -> !d.fetch(buttons[finalI].getText()).equals(""))
                            .sorted((o1, o2) -> {
                                double v = Double.parseDouble(o1.fetch(buttons[finalI].getText())) - Double.parseDouble(o2.fetch(buttons[finalI].getText()));
                                if (v < 0)
                                    return 1;
                                else if (v > 0)
                                    return -1;
                                else
                                    return 0;
                            })
                            .limit(21)
                            .forEach(d -> infoGroup[finalI].add(new XYChart.Data<>(d.fetch("location"),
                                    Double.parseDouble(d.fetch(buttons[finalI].getText())))));
                }

                group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                    OptionalInt any = IntStream.range(0, buttons.length).filter(b -> buttons[b].equals(newValue)).findAny();
                    if (any.isPresent()) {
                        chart.getData().get(0).setData(infoGroup[any.getAsInt()]);
                    }
                });
            }

            group.getToggles().addAll(buttons);
            verticalBox.getChildren().addAll(buttons);

        }

        return ans;
    }
}
