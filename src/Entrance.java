import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import service.ServiceFactory;
import serviceimplements.SimpleFactory;
import tabsupply.*;
import tabsupply.CovidTableTabSupplyImpl;
import tabsupply.LocationBarTabSupplyImpl;
import tabsupply.LocationTableTabSupplyImpl;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Entrance extends Application {

    private final ServiceFactory factory = new SimpleFactory();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        final BorderPane pane = new BorderPane();

        pane.setPrefSize(1080, 600);

        final Scene scene = new Scene(pane);
        primaryStage.setScene(scene);

        factory.getMenuBarService().init();
        factory.getTabPaneService().init();
        factory.getTipService().init();

        pane.setTop(factory.getMenuBarService().getMenuBar());
        factory.getMenuBarService().getMenuBar().setUseSystemMenuBar(true);

        pane.setCenter(factory.getTabPaneService().getTabPane());

        pane.setBottom(factory.getTipService().getTip());

        {
            factory.getMenuBarService().setShowLocationTableOnAction(v -> selectNewTab(factory, () -> new LocationTableTabSupplyImpl().supply(factory)).accept(null));
            factory.getMenuBarService().setShowCovidTableOnAction(v -> selectNewTab(factory, () -> new CovidTableTabSupplyImpl().supply(factory)).accept(null));
            factory.getMenuBarService().setShowLocationBarOnAction(v -> selectNewTab(factory, () -> new LocationBarChartTabSupplyImpl().supply(factory)).accept(null));
            factory.getMenuBarService().setCloseOnAction(v -> selectNewTab(factory, () -> new ExportTestImpl().supply(factory)).accept(null));
        }

        primaryStage.show();
    }

    private static Consumer<Void> selectNewTab(ServiceFactory factory, Supplier<Tab> supplier) {
        return v -> {
            Tab ans = supplier.get();
            factory.getTabPaneService().getTabPane().getTabs().add(ans);
            factory.getTabPaneService().getTabPane().getSelectionModel().select(ans);
        };
    }
}
