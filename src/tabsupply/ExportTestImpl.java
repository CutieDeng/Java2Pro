package tabsupply;

import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import service.ServiceFactory;

import java.util.function.Consumer;

public class ExportTestImpl extends AbstractTabSupplyImpl{
    @Override
    protected Consumer<Void> getBeforeAction() {
        return beforeAction;
    }

    @Override
    protected Consumer<Void> getAfterAllAction() {
        return after;
    }

    private Consumer<Void> beforeAction;
    private Consumer<Void> after;

    @Override
    protected Tab tabGenerate() {
        return super.tabGenerate();
    }

    @Override
    public Tab supply(ServiceFactory factory) {
        super.supply(factory);
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(700, 540);
        ans.setContent(pane);

        beforeAction = v -> {
            factory.getMenuBarService()
                    .setExportOnAction(e -> System.out.println("导出我的文件。"));
        };
        after = v -> factory.getMenuBarService().setExportOnAction(null);

        return ans;
    }
}
