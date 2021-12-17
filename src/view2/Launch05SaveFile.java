package view2;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

public class Launch05SaveFile extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        VBox vBox = new VBox(10);

        Button button1 = new Button("one file");
        Button button2 = new Button("multi files");
        Button button3 = new Button("open file");
        Button button4 = new Button("save file");
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);//设置自动换行

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                FileChooser fileChooser = new FileChooser();

                fileChooser.setTitle("choose one file");

                //默认路径
                fileChooser.setInitialDirectory(new File("D:" + File.separator));

                //设置选择的文件的扩展名
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt type", "*.txt"));

                //返回用户选中的文件的路径，注意，如果用户不选，则会返回null
                File file = fileChooser.showOpenDialog(stage);

                if (file != null) System.out.println(file.getAbsoluteFile());
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                FileChooser fileChooser = new FileChooser();

                fileChooser.setTitle("choose one file");

                //默认路径
                fileChooser.setInitialDirectory(new File("D:" + File.separator));

                //设置选择的文件的扩展名
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt type", "*.txt"));

                //返回用户选中的文件的路径，注意，如果用户不选，则会返回null
                List<File> files = fileChooser.showOpenMultipleDialog(stage);

                if (files != null) {
                    for (File f : files) {
                        System.out.println(f.getAbsoluteFile());
                    }
                }

            }
        });

        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                FileChooser fileChooser = new FileChooser();

                fileChooser.setTitle("open one file");

                //默认路径
                fileChooser.setInitialDirectory(new File("D:" + File.separator));

                //设置选择的文件的扩展名
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt type", "*.txt"));

                //返回用户选中的文件的路径，注意，如果用户不选，则会返回null
                File file = fileChooser.showOpenDialog(stage);

                if (file != null) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

                        String s = null;
                        while ((s = bufferedReader.readLine()) != null) {
                            textArea.appendText(s);
                        }
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                FileChooser fileChooser = new FileChooser();

                fileChooser.setTitle("save one file");

                //默认文件名
                fileChooser.setInitialFileName("default");

                //默认路径
                fileChooser.setInitialDirectory(new File("D:" + File.separator));

                //设置选择的文件的扩展名
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt type", "*.txt"));

                //返回用户选中的文件的路径，注意，如果用户不选，则会返回null
                File file = fileChooser.showSaveDialog(stage);

                if (file == null) return;


                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);

                    outputStreamWriter.write(textArea.getText());

                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


//                try {
//                    file.createNewFile();//如果保存的文件没有数据，则需要这句话。否则，不需要
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });


        anchorPane.getChildren().add(vBox);
        vBox.getChildren().addAll(button1, button2, button3, button4, textArea);

        primaryStage.setScene(new Scene(anchorPane));
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);
        primaryStage.show();
    }
}
