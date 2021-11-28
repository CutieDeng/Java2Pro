package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestMenu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane pane = new BorderPane();

        pane.setPrefSize(800, 600);

        {
            Menu file = new Menu("File");
            Menu Edit = new Menu("Edit");
            Menu View = new Menu("View");

            MenuItem click = new MenuItem("Click me!");
            click.setOnAction(event -> System.out.println("Create the event " + event.getEventType() + ". You click me!"));
            click.setDisable(false);
            MenuItem hide = new MenuItem("Hidden option");
            hide.setDisable(true);
            MenuItem hiddenAgain = new MenuItem("View me!");
            hiddenAgain.setDisable(false);
            hiddenAgain.setOnAction(a -> hide.setDisable(false));

            View.getItems().addAll(click, hide, hiddenAgain);
            hide.setOnAction(e -> View.hide());

            MenuItem open = new MenuItem("打开");


            MenuBar mainBar = new MenuBar(file, Edit, View);
//            mainBar.setUseSystemMenuBar(true);
            pane.setTop(mainBar);
        }

        Scene mainScene = new Scene(pane);

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
