package tool;

import java.util.Locale;

public enum DisplayType {
    GRAPH,
    TABLE;

    @Override public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
