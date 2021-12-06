package view2;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * Launch4PopStage 是一个专门研究弹窗效果的一个 JavaFX 应用实验类。<br>
 * <p/>
 *
 * 通过一个简单的新 Stage 和时间线，我们便可以控制弹窗进入程序。<br>
 * <p/>
 *
 * todo: <br>
 * - 增加弹窗动画的动画。<br>
 * - 设置更合适的弹窗组织形式，例如描述某个超类。<br>
 * - 调整弹窗出现的位置，或使用非 stage 的方式描述弹窗。<br>
 * <p/>
 *
 */
public class Launch4PopStage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        System.out.println("执行程序！");

        // 主页控制
        {
            Label label = new Label("这是一个主页！");

            // 无意义填充页
            BorderPane p = new BorderPane();
//            p.getChildren().add(label);

            // 设置主窗口的大小关系
            p.setPrefHeight(600);
            p.setPrefWidth(800);

            // 展示主窗口
            primaryStage.setScene(new Scene(p));
            primaryStage.show();
        }

        // 弹窗控制
        {
            // 设置弹窗所在 stage.
            Stage infoStage = new Stage(StageStyle.UNDECORATED);
            infoStage.setAlwaysOnTop(true);
            infoStage.initModality(Modality.APPLICATION_MODAL);

            // 设置弹窗的关闭按钮
            Button c = new Button("关闭弹窗");
            c.setOnAction((e) -> infoStage.close());

            // 设置弹窗的主体页面
            VBox box = new VBox();
            box.setPadding(new Insets(20));
            box.setAlignment(Pos.BASELINE_CENTER);
            box.setSpacing(20);
            box.getChildren().addAll(new Label("这是一个 happy 的弹窗\n--Cutie Deng"), c);

            infoStage.setScene(new Scene(box));
//            infoStage.setTitle("提示信息");

            // 设置弹窗的生命周期，以便于控制倒计时.
            int[] ttl = new int[1];

            // 设置弹窗大小
            infoStage.setMaxWidth(400);
            infoStage.setMaxHeight(200);

            // 创建一个新的时间线
            Timeline closingLine = new Timeline();

            // 用时间线对弹窗进行计时
            closingLine.getKeyFrames().add(new KeyFrame(Duration.millis(1000), e -> {
                if (!infoStage.isShowing())
                    return ;
                ttl[0]--;
//                System.out.println(String.format("此时生命值=%d", ttl[0]));
                if (ttl[0] <= 0) {
                    infoStage.close();
                }
            }));

            // 标记该时间线为无穷循环
            closingLine.setCycleCount(Animation.INDEFINITE);

            // 运行该时间线
            closingLine.play();

            System.out.println("即将打开消息弹窗");

            // 弹出该弹窗
            Platform.runLater(() -> {
                ttl[0] = 5;
                infoStage.setX(primaryStage.getWidth() - 400 + primaryStage.getX());
                infoStage.setY(primaryStage.getHeight() - 200 + primaryStage.getY());
                infoStage.show();
                infoStage.addEventHandler(WindowEvent.WINDOW_HIDDEN, e -> System.out.println("弹窗已被关闭。"));
            }
            );
        }
    }
}
