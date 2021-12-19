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

}
