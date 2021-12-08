package view2;

import javafx.application.Application;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;

public class Launch4Control extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane mainPane = new BorderPane();

        {
            MenuBar bar = new MenuBar();
            bar.setUseSystemMenuBar(true);
            bar.setDisable(false);

            Menu file = new Menu("文件");
            bar.getMenus().add(file);

            MenuItem f1 = new MenuItem("打开");
            f1.addEventHandler(MouseEvent.MOUSE_ENTERED, System.out::println);
            MenuItem f2 = new MenuItem("保存");
            MenuItem f3 = new MenuItem("另存为");
            SeparatorMenuItem item = new SeparatorMenuItem();
            MenuItem f4 = new MenuItem("关闭");

            file.getItems().addAll(f1, f2, f3, item, f4);
            mainPane.setTop(bar);

            bar.addEventFilter(EventType.ROOT, System.out::println);
        }

        mainPane.setPrefSize(800, 600);

        {
            // 3 times 3 buttons for happy write.
            Button[][] buttons = new Button[3][3];
            final Random random = new Random(47);
            for (int i = 0; i < buttons.length; i++) {
                for (int i1 = 0; i1 < buttons[i].length; i1++) {
                    buttons[i][i1] = new Button(String.format("%03d", random.nextInt(1000)));
                    int finalI = i1;
                    int finalI1 = i;
                    buttons[i][i1].setOnAction(e -> {
                        buttons[finalI1][finalI].setEffect(new Glow(2.0));
                        new Thread(() -> {
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException ignored) {
                            }
                            buttons[finalI][finalI1].setEffect(null);
                        }).start();
                    });
                }
            }
            GridPane pane = new GridPane();
            for (int i = 0; i < 3; ++i)
                for (int c = 0; c < 3; ++c)
                    pane.add(buttons[i][c], i, c);

            mainPane.setCenter(pane);
        }

        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();
    }
}
