package tabsupply;

import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import service.DataService;
import service.ServiceFactory;
import serviceimplements.NormalDataServiceImpl;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CovidLineAnimationSupplyImpl extends AbstractTabSupplyImpl {
    @Override
    protected Consumer<Void> getBeforeAction() {
        return null;
    }

    @Override
    protected Consumer<Void> getAfterAllAction() {
        return null;
    }

    private DataService service = new NormalDataServiceImpl();

    @Override
    public Tab supply(ServiceFactory factory) {
        super.supply(factory);

        BorderPane mainPane = new BorderPane();
        ans.setContent(mainPane);

        {
            List<String> dateList = service.getDataList().stream().map(d -> d.fetch("date")).distinct().collect(Collectors.toList());
            ObservableList<String> observableDateList = FXCollections.observableArrayList(dateList);

            Axis<String> x = new CategoryAxis(observableDateList);
            Axis<Number> y = new NumberAxis();

            BorderPane graphPane = new BorderPane();
            mainPane.setCenter(graphPane);

            LineChart<String, Number> lineChart = new LineChart<>(x, y);
            graphPane.setCenter(lineChart);

            ScrollPane scrollPane = new ScrollPane();
            mainPane.setRight(scrollPane);

            VBox box = StandTabSupplyTool.getSelectionsBox();
            scrollPane.setContent(box);

            List<RadioButton> radioButtons = service.getColumnNames().stream().filter(StandTabSupplyTool::filterMainColName)
                    .map(RadioButton::new).collect(Collectors.toList());
            ToggleGroup group = new ToggleGroup();
            group.getToggles().addAll(radioButtons);

            box.getChildren().addAll(radioButtons);


        }


        return ans;
    }
}
