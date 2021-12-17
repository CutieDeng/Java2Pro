package tabsupply;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import service.ServiceFactory;
import service.TabGenerateService;

public class BlankTabSupplyImpl implements TabGenerateService {
    @Override
    public Tab supply(ServiceFactory factory) {
        // 初始化一个标签页
        Tab ans = new Tab("没有名字的可怜蛋");

        // 设置该标签页的提示信息
        ans.setTooltip(new Tooltip("空白页"));

        final BorderPane normalPane = new BorderPane();

        Shape circle = new Circle(30);
        circle.setFill(Color.LIGHTGRAY);
        circle.setStrokeWidth(.8);
        circle.setStroke(Color.WHITE);
        normalPane.setCenter(circle);

        ans.setOnSelectionChanged(e -> {
            System.out.println(ans + ": " + e);

        });

        ans.setContent(normalPane);

        // 监听该 tab 页面是否被放置、移除 tabPane 上 qwq
//        ans.tabPaneProperty().addListener((ObservableValue<? extends TabPane> observable, TabPane oldValue, TabPane newValue) -> {
//            if (newValue != null)
//                newValue.addEventHandler(EventType.ROOT, e -> {
//                  System.out.println("FROM " + ans + ": " + e);
//                });
//            System.out.println("-------");
//        });

//        ans.tabPaneProperty().addListener((observable, oldValue, newValue) -> newValue.selectionModelProperty().addListener((observable1, oldValue1, newValue1) -> {
//            System.out.println("FROM " + ans + ": ");
//            System.out.println(observable1);
//            System.out.println(oldValue1);
//            System.out.println(newValue1);
//        }));

        circle.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> factory.getTipService().setTipMessage("这是一个平凡圆。"));
        circle.addEventHandler(MouseEvent.MOUSE_EXITED, e -> factory.getTipService().setTipMessage("这是一个平面。"));

        return ans;
    }
}
