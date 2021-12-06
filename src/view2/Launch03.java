package view2;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class Launch03 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        {
            Slider rollSlider = new Slider(0, 25, 0);
            rollSlider.setBlockIncrement(1);
            rollSlider.setMajorTickUnit(5);
            rollSlider.setMinorTickCount(1);
            rollSlider.setShowTickLabels(true);
            rollSlider.setShowTickMarks(true);

            rollSlider.setPadding(new Insets(4));

            rollSlider.setAccessibleHelp(String.format("通过滑动条调整观察图像"));

            NumberAxis x = new NumberAxis(0, 25, 1);
            x.setLabel("x axis");
            NumberAxis y = new NumberAxis(0, 625, 25);
            y.setLabel("y axis");

            XYChart.Series<Number, Number> values = new XYChart.Series<>();
            values.setName("y=x^2");

            LineChart<Number, Number> chart = new LineChart<>(x, y);
            chart.getData().addAll(values);

            class DataCaching {
                private int now = 0;
                private ObservableList<XYChart.Data<Number, Number>> observableList = values.getData();

                synchronized
                public void change(int now) {
                    if (now == this.now)
                        return ;
                    if (now > this.now) {
                        for (int k = this.now + 1; k <= now; ++k) {
                            observableList.add(new XYChart.Data<>(k, k * k));
                        }
                    } else {
                        for (int k = this.now; k > now; --k) {
                            observableList.remove(k-1);
                        }
                    }
                    this.now = now;
                }
            }

            final DataCaching caching = new DataCaching();
            final Consumer<Event> move = e -> {
                System.out.println(String.format("Now value = %f\n", rollSlider.getValue()));
                caching.change((int) rollSlider.getValue());
            };

            rollSlider.addEventHandler(MouseEvent.MOUSE_DRAGGED, move::accept);
            rollSlider.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
                switch (e.getCode()) {
                    case A:
                        rollSlider.decrement();
                    case LEFT:
                        break;
                    case D:
                        rollSlider.increment();
                    case RIGHT:
                        break;
                    default:
                        return ;
                }
                move.accept(e);
            });

            BorderPane pane = new BorderPane(chart);
            pane.setBottom(rollSlider);

            primaryStage.setScene(new Scene(pane));
            primaryStage.show();
        }
    }
}
