package serviceimplements;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import service.GenerateTipBoxService;

import java.util.function.Consumer;

public class TipVBoxService implements GenerateTipBoxService {


    @Override
    public Consumer<String> generate(Consumer<Node> finalOperation) {
        VBox verticalBox = new VBox();
        throw new UnsupportedOperationException();

    }
}
