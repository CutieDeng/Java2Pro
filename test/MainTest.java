import data.Data;
import org.junit.Before;
import org.junit.Test;
import tool.Tool;

import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

public class MainTest {

    private List<Data> rows;

    @Before
    public void readDataFile() {
        rows = Tool.readDataFile(Paths.get("res", "file", "owid-covid-data.csv").toFile());
    }

    @Test
    public void showChinaTotalCases() {
        System.out.println("Show total cases changing in China. ");
        rows.stream().filter(r -> "china".equals(r.fetch("location").toLowerCase(Locale.ROOT)))
                .forEach(r -> System.out.format("date: %10s, total cases: %9s\n", r.fetch("date"), r.fetch("total cases")));
    }

    @Test
    public void showNewsTotalCases() {
        System.out.println("Show total cases in 2021-10-31. ");
        rows.stream().filter(d -> "2021-10-31".equals(d.fetch("date")))
                .sorted((d1, d2) -> d1.fetch("location").compareToIgnoreCase(d2.fetch("location")))
                .forEach(r -> System.out.format("[%-9s]%24s, total cases: %11s\n", r.fetch("iso code"), r.fetch("location"),
                        r.fetch("total cases")));
    }

}
