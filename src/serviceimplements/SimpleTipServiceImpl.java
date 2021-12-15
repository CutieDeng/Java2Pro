package serviceimplements;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import service.TipService;

import java.util.function.Consumer;

public class SimpleTipServiceImpl implements TipService {

    private HBox box;
    private Consumer<String> setInformationAction;

    @Override
    public void init() {
        box = new HBox();
        box.setPadding(new Insets(10));
        Label mess = new Label("这是一条恒定的测试信息！");
        box.getChildren().add(mess);
        setInformationAction = mess::setText;
    }

    @Override
    public Node getTip() {
        return box;
    }

    @Override
    synchronized
    public void setTipMessage(String information) {
        setInformationAction.accept(information);
    }
}
