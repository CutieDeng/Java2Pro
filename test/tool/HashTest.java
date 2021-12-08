package tool;

import org.junit.Test;

import java.nio.file.Paths;

public class HashTest {

    @Test
    public void test01() {
        String s = Tool.hashFileInSHA1(Paths.get("res", "file", "owid-covid-data.csv").toFile());
        System.out.println(s);
    }

}
