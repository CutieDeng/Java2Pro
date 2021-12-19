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
import java.util.function.*;

public class CovidBarTabSupplyImpl extends AbstractTabSupplyImpl {

    private static final IntSupplier cntSupplier = new IntSupplier() {

        private int cnt = 0;

        @Override
        synchronized
        public int getAsInt() {
            return cnt++;
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
        super.supply(factory);
        {
            int i = cntSupplier.getAsInt();
            ans.setText("疫情信息柱状统计图" + (i == 0 ? "" : (" " + i)));
        }

        // 设置该标签页的提示信息
        ans.setTooltip(new Tooltip("疫情信息柱状图"));

        // 设置该标签页内部的页面框架。
        BorderPane viewPane = new BorderPane();
        ans.setContent(viewPane);

        // 设置该标签页右边的相关选项框、搜索框。
        ScrollPane scrollP = new ScrollPane();
        viewPane.setRight(scrollP);

        VBox searchPane = StandTabSupplyTool.getSelectionsBox();
        scrollP.setContent(searchPane);

        // 设置搜索、选择页的宽度
        searchPane.setPrefWidth(200);

        {
            BorderPane graphPane = new BorderPane();
            viewPane.setCenter(graphPane);




        }



        return ans;

    }
}
