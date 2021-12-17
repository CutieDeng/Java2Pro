package tabsupply;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import service.DataService;
import service.ServiceFactory;
import serviceimplements.NormalDataServiceImpl;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CovidLineTabSupplyImpl extends AbstractTabSupplyImpl {

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
        ans.setText("Covid Line Chart " + cntSupplier.get());

        // 设置该标签页的提示信息
        ans.setTooltip(new Tooltip("疫情信息折线图"));

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
