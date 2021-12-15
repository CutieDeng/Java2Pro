package serviceimplements;

import service.*;

public class SimpleFactory implements ServiceFactory {
    @Override
    public MenuBarService getMenuBarService() {
        return null;
    }

    @Override
    public TipService getTipService() {
        return null;
    }

    @Override
    public TabPaneService getTabPaneService() {
        return null;
    }

    @Override
    public ShortcutService getShortcutService() {
        return null;
    }
}
