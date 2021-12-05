package view2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Launch01 extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(new Group()));
        primaryStage.getScene().getRoot().setVisible(true);

        {
            final NumberAxis marks = new NumberAxis();
            marks.setLabel("GPA");
            final CategoryAxis peopleNames = new CategoryAxis();
            peopleNames.setLabel("姓名");
            final BarChart<String, Number> chart = new BarChart<>(peopleNames, marks);
            chart.setTitle("GPA - 姓名 分布图");

            XYChart.Series<String, Number> s = new XYChart.Series<>();
            final Random random = new Random(47);

            //noinspection unchecked
            chart.getData().addAll(s);

            final int[] cnt = new int[] {0};
            String[] names = new String[30];


            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                    action -> {
                    if (cnt[0] < 30) {
                        names[cnt[0]] =
                                random.ints().map(i -> i % 26).limit(4)
                                        .map(i -> (char)(i + 'a'))
                                        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
                        float tmp = random.nextFloat() * 4;
                        s.getData().add(new XYChart.Data<>(names[cnt[0]], tmp));
                        cnt[0]++;
                    }
                    }));
            timeline.setCycleCount(30);

            AnchorPane pane = new AnchorPane(chart);
            primaryStage.setScene(new Scene(pane));

            timeline.play();
        }
        primaryStage.show();
    }
}
