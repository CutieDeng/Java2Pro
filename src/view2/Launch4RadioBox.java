package view2;

import javafx.application.Application;
import javafx.scene.AccessibleAttribute;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Launch4RadioBox extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        {
            VBox selectBox = new VBox();
            mainPane.setRight(selectBox);
            {
                Label title = new Label("单选框");
                selectBox.getChildren().add(title);
            }
            {
                RadioButton normal = new RadioButton("快按我");
                RadioButton hard = new RadioButton("我很难按下");
                RadioButton great = new RadioButton("漂亮qwq");

                selectBox.getChildren().addAll(normal, great, hard);
            }
        }
        {
            VBox oneSelectBox = new VBox();
            mainPane.setLeft(oneSelectBox);
            {
                Label title = new Label("这次是真的单选了");
                Label question = new Label("请问以下四个犯人谁说了谎：");
                oneSelectBox.getChildren().addAll(title, question);
            }
            {
                ToggleGroup selectGroup = new ToggleGroup();
                RadioButton A = new RadioButton("LMQ: 不是我干的。");
                RadioButton B = new RadioButton("LMQ2: 是他干的。");
                RadioButton C = new RadioButton("LMQ3: 它干的。");
                RadioButton D = new RadioButton("LMQ0: 刚才有人说谎。");
                A.setToggleGroup(selectGroup);
                B.setToggleGroup(selectGroup);
                C.setToggleGroup(selectGroup);
                D.setToggleGroup(selectGroup);

                oneSelectBox.getChildren().addAll(A, B, C, D);
            }
        }
        {
            Shape circle = new Circle(20, Color.BLUE);
            mainPane.setCenter(circle);
        }
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();
    }
}
