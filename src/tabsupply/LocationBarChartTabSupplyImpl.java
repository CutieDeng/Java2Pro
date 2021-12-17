package tabsupply;

import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import service.DataService;
import service.ServiceFactory;
import serviceimplements.HighDataServiceImpl;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
                    .filter(name -> !"iso code".equals(name) && !"location".equals(name) && !"continent".equals(name))
                    .collect(Collectors.toList());

            RadioButton[] buttons = new RadioButton[names.size()];
            ToggleGroup group = new ToggleGroup();
            for (int i = 0; i < buttons.length; i++) {
                buttons[i] = new RadioButton(names.get(i));
            }
            {
                // 做点疯狂的硬编码 qwq
                // 0 号 button 是 population~

            }
            group.getToggles().addAll(buttons);
            verticalBox.getChildren().addAll(buttons);


        }

        return ans;
    }
}
