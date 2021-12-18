package serviceimplements;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import service.TipService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.function.Consumer;

public class AmuseTipServiceImpl implements TipService {

    private HBox box;
    private Consumer<String> setInformationAction;

    private final Random random = new Random();
    private static final String[] meaninglessSentences = new String[]{
            "......",
            "以我个人名誉保证，全文完全使用 Java 语言实现",
            "你知道吗，按下 Ctrl, W 可以关闭当前页面哦",
            "傲慢让别人无法来爱我，偏见让我无法去爱别人。",
            "我想，有时候感到时间过得越来越快了，不过是时间变得越来越重要了",
            "小时候我们词不达意，长大了我们言不由衷",
            "出发之前永远是梦想，上路之后永远是挑战"
    };

    @Override
    public void init() {
        box = new HBox();
        box.setPadding(new Insets(10));
        Label mess = new Label("我是一个幽默的提示框");
        box.getChildren().add(mess);
        setInformationAction = m -> {
            if ("".equals(m)) {
                int i = random.nextInt(meaninglessSentences.length + 1);
                if (i == 0) {
                    mess.setText("现在是北京时间\t" + DateTimeFormatter.ofPattern("yyyy-MM-dd\tHH:mm:ss", Locale.ROOT).format(LocalDateTime.now()));
                } else
                    mess.setText(meaninglessSentences[random.nextInt(meaninglessSentences.length)]);
            }
            else
                mess.setText(m);
        };
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
