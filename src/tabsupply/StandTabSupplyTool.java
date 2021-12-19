package tabsupply;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public final class StandTabSupplyTool {

    public static VBox getSelectionsBox() {
        VBox ans = new VBox();
        ans.setSpacing(9.2);
        ans.setPadding(new Insets(9.2));
        ans.setPrefWidth(215.);
        return ans;
    }

    public static boolean filterMainColName(String columnName) {
        return !"iso_code".equals(columnName) && !"location".equals(columnName) && !"continent".equals(columnName) && !"date".equals(columnName);
    }

    public static File getChooseFile(FileChooser chooser, String initialFileName,
                                     FileChooser.ExtensionFilter... filters) {
        Stage stage = new Stage();

        chooser.setTitle("Export");

        //默认文件名
        chooser.setInitialFileName(initialFileName);

        //设置选择的文件的扩展名
        chooser.getExtensionFilters().addAll(filters);

        //返回用户选中的文件的路径，注意，如果用户不选，则会返回null
        return chooser.showSaveDialog(stage);
    }
}
