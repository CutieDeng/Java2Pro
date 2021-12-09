package tool;

import data.Data;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据控制器类，负责收取数据和数据的内容。<br>
 * <p/>
 *
 *
 *
 *
 */
public class Controller {

    public static final Controller instance = new Controller();

    private static final Map<String, FileController> hashFile = new HashMap<>();

    public FileController getFileData(File file) {
        String r = Tool.hashFileInSHA1(file);
        if (!hashFile.containsKey(r)) {
            FileController fileController = new FileController(file);
            hashFile.put(r, fileController);
        }
        return hashFile.get(r);
    }

    /**
     * 私有构造器，禁止进行二次构造。<br>
     * <p/>
     */
    private Controller() {}

}
