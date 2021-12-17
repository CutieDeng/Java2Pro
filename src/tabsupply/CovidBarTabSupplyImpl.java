package tabsupply;

import data.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.DataService;
import service.ServiceFactory;
import serviceimplements.NormalDataServiceImpl;
import tool.Tool;
import util.Holder;
import view2.Tmp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class CovidBarTabSupplyImpl extends AbstractTabSupplyImpl {

    private static final Supplier<Integer> cntSupplier = new Supplier<Integer>() {
        int number = 1;
        @Override
        synchronized
        public Integer get() {
            return number++;
        }
    };



    final DataService service = new NormalDataServiceImpl();

    @Override
    protected Consumer<Void> getBeforeAction() {
        return null;
    }

    @Override
    protected Consumer<Void> getAfterAllAction() {
        return null;
    }

    @Override
    public Tab supply(ServiceFactory factory) {
        // 初始化一个标签页
        Tab ans = super.supply(factory);
        ans.setText("Covid Bar " + cntSupplier.get());

        // 设置该标签页的提示信息
        ans.setTooltip(new Tooltip("疫情信息柱状图"));

        // 设置该标签页内部的页面框架。
        BorderPane viewPane = new BorderPane();
        ans.setContent(viewPane);

        // 设置该标签页右边的相关选项框、搜索框。
        HBox searchPane = new HBox();
        searchPane.setPadding(new Insets(20));
        viewPane.setRight(searchPane);

        // 设置搜索、选择页的相关风格
        searchPane.setStyle("-fx-background-color: #CCFF99;");
        // 设置搜索、选择页的宽度
        searchPane.setPrefWidth(200);

        return ans;

    }
}
