package view2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Shadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class Launch4TipBar extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static Consumer<String> setString;


    @Override
    public void start(Stage primaryStage) throws Exception {
        // 设置主页面的相关信息
        {
            BorderPane mainPane = new BorderPane();

            mainPane.setPrefSize(800, 600);
            VBox box = new VBox();
            box.setPadding(new Insets(10));
            box.setFillWidth(true);
            box.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(15), new Insets(2))));
            box.setBorder(new Border(new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID,
                    new CornerRadii(15),new BorderWidths(2))));
//            box.setEffect(new Shadow(BlurType.GAUSSIAN, Color.LIGHTCYAN, 2));
            Label message = new Label("empty");
            setString = message::setText;

            box.getChildren().add(message);

            mainPane.setBottom(box);

            // 创建快乐输入框
            TextField field = new TextField("Hello World!");
            field.setPromptText("快乐输入框");

            mainPane.setCenter(field);

            field.addEventHandler(MouseEvent.MOUSE_ENTERED, m -> setString.accept("快乐输入框"));

            // 创建指示按钮
            HBox box1 = new HBox();
            Button[] buttons = new Button[3];
            String[] s = new String[] {"test!", "ignore.", "fail~"};
            String[] tips = new String[] {"点击开始测试", "点击忽略错误", "点击测试失败"};
            for (int i = 0; i < 3; i++) {
                buttons[i] = new Button(s[i]);
                final int finalI = i;
                buttons[i].addEventHandler(MouseEvent.MOUSE_ENTERED, m -> setString.accept(tips[finalI]));
            }
            box1.getChildren().addAll(buttons);

            mainPane.setRight(box1);

            primaryStage.setScene(new Scene(mainPane));
        }
        primaryStage.show();
    }
}
