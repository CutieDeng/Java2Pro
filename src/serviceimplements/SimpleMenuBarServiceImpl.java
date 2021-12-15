package serviceimplements;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import service.MenuBarService;

import java.util.function.Consumer;

@SuppressWarnings("all")
public class SimpleMenuBarServiceImpl implements MenuBarService {

    private MenuBar ans;

    private MenuItem export;
    private MenuItem close;

    private MenuItem showLocationTable;
    private MenuItem showLocationBar;
    private MenuItem showLocationPie;

    private MenuItem showCovidTable;
    private MenuItem showCovidBar;
    private MenuItem showCovidPie;
    private MenuItem showCovidLine;

    @Override
    public void init() {
        // 初始化我们的菜单栏！！！
        ans = new MenuBar();

        // 给我们的菜单栏增加我们喜欢的实现吧！
        // 实现一个文件管理栏位...
        {
            Menu file = new Menu("文件");
            ans.getMenus().add(file);

            // 设置导出内容
            export = new MenuItem("导出");
            export.setDisable(true);

            // 设置标签页的关闭功能
            close = new MenuItem("关闭");
            close.setDisable(true);

            file.getItems().addAll(export, close);
        }

        // 增加一个快速视图实现
        {
            Menu view = new Menu("视图");
            ans.getMenus().add(view);

            MenuItem countryLevel = new MenuItem("地区信息汇总");
            countryLevel.setDisable(true);

            showLocationTable = new MenuItem("表格");
            showLocationBar = new MenuItem("条形图");
            showLocationPie = new MenuItem("饼状图");

            showLocationTable.setDisable(true);
            showLocationBar.setDisable(true);
            showLocationPie.setDisable(true);

            view.getItems().addAll(countryLevel, showLocationTable, showLocationBar, showLocationPie, new SeparatorMenuItem());

            MenuItem covidLevel = new MenuItem("疫情信息汇总");
            covidLevel.setDisable(true);

            showCovidTable = new MenuItem("表格");
            showCovidBar = new MenuItem("条形图");
            showCovidPie = new MenuItem("饼状图");
            showCovidLine = new MenuItem("折线图");

            showCovidTable.setDisable(true);
            showCovidBar.setDisable(true);
            showCovidPie.setDisable(true);
            showCovidLine.setDisable(true);

            view.getItems().addAll(covidLevel, showCovidTable, showCovidBar, showCovidPie, showCovidLine, new SeparatorMenuItem());
        }
    }

    @Override
    public MenuBar getMenuBar() {
        return ans;
    }

    @Override
    public boolean setExportOnAction(Consumer<Void> consumer) {
        if (consumer == null)
            export.setDisable(true);
        else {
            export.setOnAction(e -> consumer.accept(null));
            export.setDisable(false);
        }
        return true;
    }

    @Override
    public boolean setCloseOnAction(Consumer<Void> consumer) {
        if (consumer == null) {
            export.setDisable(true);
        } else {
            export.setOnAction(e -> consumer.accept(null));
            export.setDisable(false);
        }
        return true;
    }

    @Override
    public boolean setShowLocationTableOnAction(Consumer<Void> consumer) {
        if (consumer == null) {
            showLocationTable.setDisable(true);
        } else {
            showLocationTable.setOnAction(e -> consumer.accept(null));
            showLocationTable.setDisable(true);
        }
        return true;
    }

    @Override
    public boolean setShowLocationBarOnAction(Consumer<Void> consumer) {
        if (consumer == null) {
            showLocationBar.setDisable(true);
        } else {
            showLocationBar.setOnAction(e -> consumer.accept(null));
            showLocationBar.setDisable(false);
        }
        return true;
    }

    @Override
    public boolean setShowLocationPieOnAction(Consumer<Void> consumer) {
        if (consumer == null)
            showLocationPie.setDisable(true);
        else {
            showLocationPie.setOnAction(e -> consumer.accept(null));
            showLocationPie.setDisable(false);
        }
        return true;
    }

    @Override
    public boolean setShowCovidTableOnAction(Consumer<Void> consumer) {
        if (consumer == null)
            showCovidTable.setDisable(true);
        else {
            showCovidTable.setOnAction(e -> consumer.accept(null));
            showCovidTable.setDisable(true);
        }
        return true;
    }

    @Override
    public boolean setShowCovidBarOnAction(Consumer<Void> consumer) {
        if (consumer == null)
            showCovidBar.setDisable(true);
        else {
            showCovidBar.setOnAction(e -> consumer.accept(null));
            showCovidBar.setDisable(false);
        }
        return true;
    }

    @Override
    public boolean setShowCovidPieOnAction(Consumer<Void> consumer) {
        if (consumer == null)
            showCovidPie.setDisable(true);
        else {
            showCovidPie.setOnAction(e -> consumer.accept(null));
            showCovidPie.setDisable(false);
        }
        return true;
    }

    @Override
    public boolean setShowCovidLineOnAction(Consumer<Void> consumer) {
        if (consumer == null)
            showCovidLine.setDisable(true);
        else {
            showCovidLine.setOnAction(e -> consumer.accept(null));
            showCovidLine.setDisable(false);
        }
        return true;
    }
}
