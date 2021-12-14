package service;

import javafx.scene.Node;

import java.util.function.Consumer;

public interface GenerateTipBoxService {

    Consumer<String> generate(Consumer<Node> finalOperation);

}
