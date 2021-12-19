package tabsupply;

import data.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import service.DataService;
import service.ServiceFactory;
import serviceimplements.HighDataServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static tabsupply.StandTabSupplyTool.getSelectionsBox;

public class LocationPieTabSupplyImpl extends AbstractTabSupplyImpl {

    private static final Supplier<Integer> cntSupplier = new Supplier<Integer>() {
        int number = 1;
        @Override
        synchronized
        public Integer get() {
            return number++;
        }
    };

    final DataService service = new HighDataServiceImpl();

    @Override
    protected Consumer<Void> getBeforeAction() {
        return null;
    }

    @Override
    protected Consumer<Void> getAfterAllAction() {
        return null;
    }

    private String showProperty;

    @Override
    public Tab supply(ServiceFactory factory) {
        // 初始化一个标签页
        super.supply(factory);
        ans.setText("Location Pie Chart " + cntSupplier.get());

        // 设置该标签页的提示信息
        ans.setTooltip(new Tooltip("地区信息饼图"));

        // 设置该标签页内部的页面框架。
        BorderPane viewPane = new BorderPane();
        ans.setContent(viewPane);

        // 设置该标签页右边的相关选项框、搜索框。
        // special: it's a vbox ooo.
        ScrollPane scrollRightPane = new ScrollPane();
        VBox searchPane = getSelectionsBox();
        scrollRightPane.setContent(searchPane);
        viewPane.setRight(scrollRightPane);

        // emmmm, maybe you have no need to set a background for it?
        // 设置搜索、选择页的相关风格
//        searchPane.setStyle("-fx-background-color: #CCFF99;");

        // 设置搜索、选择页的宽度
        searchPane.setPrefWidth(200);

        // 创建饼图
        PieChart pieChart = new PieChart();
        viewPane.setCenter(pieChart);

        Predicate<String> countryFilter = l -> l.startsWith("OWID");
        final List<Data> data = service.getDataList().stream().filter(d -> {
//            Logger.getGlobal().info(d.fetch("iso_code"));
            return countryFilter.test(d.fetch("iso_code"));
        }).collect(Collectors.toList());

//        Logger.getGlobal().info("data list size = " + data.size());

        Map<String, ObservableList<PieChart.Data>> dataMap = new HashMap<>();

        List<String> validColumnNames = service.getColumnNames().stream().filter(StandTabSupplyTool::filterMainColName).collect(Collectors.toList());
        for (Data datum : data) {
            validColumnNames.stream().filter(d -> !"".equals(datum.fetch(d))).forEach(name -> {
                if (!dataMap.containsKey(name)) {
                    dataMap.put(name, FXCollections.observableArrayList());
                }
                ObservableList<PieChart.Data> tmpData = dataMap.get(name);
//                Logger.getGlobal().info(name + "  " + datum + "  " + tmpData);
                tmpData.add(new PieChart.Data(datum.fetch("location"), Double.parseDouble(datum.fetch(name))));
            });
        }

        List<RadioButton> radioButtons = validColumnNames.stream().map(RadioButton::new).collect(Collectors.toList());
        for (RadioButton r : radioButtons) {
            r.setOnAction(e -> {
//                Logger.getGlobal().info(e.toString());
                LocationPieTabSupplyImpl.this.showProperty = r.getText();

                //  创建一下相关的图的信息吧 qwq
                pieChart.setData(dataMap.get(LocationPieTabSupplyImpl.this.showProperty));
            });
        }
        {
            ToggleGroup group = new ToggleGroup();
            group.getToggles().addAll(radioButtons);
        }
        searchPane.getChildren().addAll(radioButtons);

        radioButtons.get(0).fire();

        return ans;
    }
}
