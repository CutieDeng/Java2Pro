package tabsupply;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.DataService;
import service.ServiceFactory;
import serviceimplements.HighDataServiceImpl;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static tabsupply.StandTabSupplyTool.getSelectionsBox;

public class LocationBarChartTabSupplyImpl extends AbstractTabSupplyImpl{
    @Override
    protected Consumer<Void> getBeforeAction() {
        return beforeAction;
    }

    @Override
    protected Consumer<Void> getAfterAllAction() {
        return after;
    }

    private Consumer<Void> beforeAction;
    private Consumer<Void> after;


    private DataService service = new HighDataServiceImpl();

    private static IntSupplier cntSupplier = new IntSupplier() {
        private int cnt = 1;
        @Override
        public int getAsInt() {
            return cnt++;
        }
    };

    @Override
    protected Tab tabGenerate() {
        Tab ans = super.tabGenerate();
        ans.setText("区域信息条形统计图 " + cntSupplier.getAsInt());
        return ans;
    }

    @Override
    public Tab supply(ServiceFactory factory) {
        super.supply(factory);

        BorderPane pane = new BorderPane();
        ans.setContent(pane);

        {
            VBox verticalBox = getSelectionsBox();
            pane.setRight(verticalBox);

            List<String> names = service.getColumnNames().stream()
                    .filter(StandTabSupplyTool::filterMainColName)
                    .collect(Collectors.toList());

            RadioButton[] buttons = new RadioButton[names.size()];
            ToggleGroup group = new ToggleGroup();
            for (int i = 0; i < buttons.length; i++) {
                buttons[i] = new RadioButton(names.get(i));
            }

            {
                // 创建一个相关的图表信息...
                Axis<String> x = new CategoryAxis();
                Axis<Number> y = new NumberAxis();
                BarChart<String, Number> chart = new BarChart<>(x, y);
                x.setAnimated(false);

                pane.setCenter(chart);

                XYChart.Series<String, Number> allData = new XYChart.Series<>();
                chart.getData().add(allData);
                ObservableList[] infoGroup = new ObservableList[buttons.length];
                for (int i = 0; i < buttons.length; i++) {
                    int finalI = i;
                    infoGroup[finalI] = FXCollections.observableArrayList();
                    service.getDataList().stream().filter(d -> !d.fetch(buttons[finalI].getText()).equals(""))
                            .sorted((o1, o2) -> {
                                double v = Double.parseDouble(o1.fetch(buttons[finalI].getText())) - Double.parseDouble(o2.fetch(buttons[finalI].getText()));
                                if (v < 0)
                                    return 1;
                                else if (v > 0)
                                    return -1;
                                else
                                    return 0;
                            })
                            .limit(14)
                            .forEach(d -> infoGroup[finalI].add(new XYChart.Data<>(d.fetch("location"),
                                    Double.parseDouble(d.fetch(buttons[finalI].getText())))));
                }

                group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                    OptionalInt any = IntStream.range(0, buttons.length).filter(b -> buttons[b].equals(newValue)).findAny();
                    if (any.isPresent()) {
//                        Logger.global().info("Now set the data from infoGroup[" + any.getAsInt() + "]. ");
                        chart.getData().remove(0);
                        XYChart.Series<String, Number> series = new XYChart.Series<>();
                        series.setData(infoGroup[any.getAsInt()]);
                        chart.getData().add(series);
                    }

                    waitTwoSeconds.play();

                });

                {
                    waitTwoSeconds = new Timeline(new KeyFrame(Duration.seconds(2),
                            event -> writableImage = chart.snapshot(new SnapshotParameters(), null)));
                    waitTwoSeconds.setCycleCount(1);
                }
            }

            group.getToggles().addAll(buttons);
            verticalBox.getChildren().addAll(buttons);

        }



        //文件导出
        beforeAction = v -> {
            factory.getMenuBarService()
                    .setExportOnAction(e -> exportAction());
        };
        after = v -> factory.getMenuBarService().setExportOnAction(null);

        return ans;
    }

    private Timeline waitTwoSeconds;

    WritableImage writableImage;

    private void exportAction() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("save one file");

        //默认文件名
        fileChooser.setInitialFileName("LocationBarChart");

        //设置选择的文件的扩展名
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("image", "*.png"));

        //返回用户选中的文件的路径，注意，如果用户不选，则会返回null
        File file = fileChooser.showSaveDialog(stage);

        if (file == null) return;

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
        } catch (Exception s) {
            s.printStackTrace();
        }

        try {
            file.createNewFile();//如果保存的文件没有数据，则需要这句话。否则，不需要
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
