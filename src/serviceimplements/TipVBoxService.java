package serviceimplements;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import service.GenerateTipBoxService;

import java.util.function.Consumer;

public class TipVBoxService implements GenerateTipBoxService {
    @Override
    public Consumer<String> generate(Consumer<Node> finalOperation) {
        VBox verticalBox = new VBox();
        // 合理的页面布局 qwq
        verticalBox.setPadding(new Insets(10));
        Consumer<String> ans;
        {
            final Label message = new Label("");
            verticalBox.getChildren().add(message);
            ans = new Consumer<String>() {
                @Override
                synchronized
                public void accept(String s) {
                    message.setText(s);
                }
            };
        }
        return ans;
    }
}
