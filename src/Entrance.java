import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import service.ServiceFactory;
import serviceimplements.SimpleFactory;
import tabsupply.BasicInfoTableSupplyImpl;
import tabsupply.BlankTabSupplyImpl;
import tabsupply.CovidInfoTableSupplyImpl;

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
            factory.getMenuBarService().setShowCovidBarOnAction(v -> {
                factory.getTabPaneService().getTabPane().getTabs().add(new BlankTabSupplyImpl().supply(factory));
            });
            factory.getMenuBarService().setShowLocationTableOnAction(v -> {
                Tab newTab = new BasicInfoTableSupplyImpl().supply(factory);
                factory.getTabPaneService().getTabPane().getTabs().add(newTab);
                factory.getTabPaneService().getTabPane().getSelectionModel().select(newTab);
            });
            factory.getMenuBarService().setShowCovidTableOnAction(v -> {
                Tab newTab = new CovidInfoTableSupplyImpl().supply(factory);
                factory.getTabPaneService().getTabPane().getTabs().add(newTab);
                factory.getTabPaneService().getTabPane().getSelectionModel().select(newTab);
            });
        }

        primaryStage.show();
    }
}
