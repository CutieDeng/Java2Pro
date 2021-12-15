package serviceimplements;

import service.*;

public class SimpleFactory implements ServiceFactory {

    private final MenuBarService menuBarService = new SimpleMenuBarServiceImpl();
    private final TipService tipService = new SimpleTipServiceImpl();
    private final TabPaneService tabPaneService = new SimpleTabPaneServiceImpl();
    private final ShortcutService shortcutService = null;

    @Override
    public MenuBarService getMenuBarService() {
        return menuBarService;
    }

    @Override
    public TipService getTipService() {
        return tipService;
    }

    @Override
    public TabPaneService getTabPaneService() {
        return tabPaneService;
    }

    @Override
    public ShortcutService getShortcutService() {
        return shortcutService;
    }
}
