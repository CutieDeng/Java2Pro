package view2;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * Launch03 是一个试验性类，负责实验滑动条的相关特性。<br>
 * <p/>
 *
 * 该类实验了通过一个滑动条对图像的相关内容进行调控。<br>
 * 即通过滑动条推动图像展开更多的图像信息。<br>
 * <p/>
 *
 * todo: <br>
 * - 实现滑动条平移图像的特性。<br>
 * - 试实现对图像进行编码和存储。<br>
 * - 更漂亮的关联实现，减少硬编码。<br>
 * <p/>
 *
 */
public class Launch03save extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        {
            // 初始化滑动条相关信息，并设置其相关的 tick 信息，increment 信息。
            Slider rollSlider = new Slider(0, 25, 0);
            rollSlider.setBlockIncrement(1);
            rollSlider.setMajorTickUnit(5);
            rollSlider.setMinorTickCount(1);
            rollSlider.setShowTickLabels(true);
            rollSlider.setShowTickMarks(true);

            // 调整滑动条的位置情形，设置 padding.
            rollSlider.setPadding(new Insets(4));

            // [failure]: 设置滑动条的帮助信息.
            rollSlider.setAccessibleHelp(String.format("通过滑动条调整观察图像"));

            // 设置折线图的 x 轴和 y 轴的信息。
            NumberAxis x = new NumberAxis(0, 25, 1);
            x.setLabel("x axis");
            NumberAxis y = new NumberAxis(0, 625, 25);
            y.setLabel("y axis");

            // 设置 y = x^2 曲线数据集，便于中途增删数据。
            XYChart.Series<Number, Number> values = new XYChart.Series<>();
            values.setName("y=x^2");

            // 创建折线图。
            LineChart<Number, Number> chart = new LineChart<>(x, y);
            chart.getData().addAll(values);


            //todo:图像显示有问题
            Button click = new Button("shoot!");
            click.setOnAction(e -> {
                SnapshotParameters parameters = new SnapshotParameters();
                WritableImage writableImage = new WritableImage(4000, 4000);
                chart.snapshot(new SnapshotParameters(), writableImage);
                File fileA = new File("chart.png");
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", fileA);
                } catch (Exception s) {
                    s.printStackTrace();
                }
                boolean newFile = false;
                try {
                    newFile = fileA.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Logger.getGlobal().info("fileA.createNewFile = " + newFile);
            });

            // 内部类 DataCaching, 负责控制图像中相应的函数信息。
            // 设置临界区，便于控制其数据的变化。
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

            // 创建函数数据的控制对象。
            final DataCaching caching = new DataCaching();
            // 创建事件监听器，让控制器在此控制函数相关数据。
            final Consumer<Event> move = e -> {
                caching.change((int) rollSlider.getValue());
            };

            // 滑动条事件监听器注册。
            rollSlider.addEventHandler(MouseEvent.MOUSE_DRAGGED, move::accept);
            rollSlider.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
                switch (e.getCode()) {
                    // 添加 A, D 作为滑动条移动的快捷键。
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

            // 设置视图页面，并将滑动条设置在该页面的底部。
            BorderPane pane = new BorderPane(chart);
            pane.setBottom(rollSlider);

            pane.setRight(click);

            primaryStage.setScene(new Scene(pane));
            primaryStage.show();
        }
    }
}
