package components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Comparator;

import static components.Constant.RECORD_PATHS;

/**
 * @description: 处理文件读写
 * @author: 郭小柒w
 * @time: 2023/6/22
 */
public class FileIO {

    private static ObservableList<String[]> list = FXCollections.observableArrayList();

    // 定义比较器
    private static Comparator<String[]> comparator = (o1, o2) -> o1[1].compareTo(o2[1]);

    static {
        try {
            File directory = new File("./src/ranks");
            if (!directory.exists() || !directory.isDirectory()) {
                System.out.println("目录不存在, 将自动创建...");
                directory.mkdirs();
            }
            for (String path : RECORD_PATHS) {
                File file = new File(path);
                if (!file.exists()) {
                    System.out.println("文件不存在, 将自动创建...");
                    file.createNewFile();
                    // 写入内置数据
                    BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                    for (int i = 0; i < 10; ++i) {
                        writer.write("未命名 999\n");
                    }
                    writer.flush();
                }
            }
        } catch (Exception e) {
            System.out.println("Error on [Class:FileIO, Method:static segment]=>");
            e.printStackTrace();
        }
    }

    /**
     * 读取文件
     * @param filePath 文件路径
     * @return 排行数据集合
     */
    public static ObservableList<String[]> readFromFile(String filePath) {
        try {
            // 清空列表
            if(list != null && list.size() > 0) {
                list.clear();
            }
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = null;
            while ((line = reader.readLine()) != null) {
                list.add(line.split(" "));
            }
            FXCollections.sort(list, comparator);
        } catch (Exception e) {
            System.out.println("Error on [Class:FileIO, Method:readFromFile]=>");
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 向文件内写入数据
     * @param filePath 文件路径
     * @param item 待更新数据项
     */
    public static void writeToFile(String filePath, String[] item) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(item[0] + " " + item[1]);
        } catch (Exception e) {
            System.out.println("Error on [Class:FileIO, Method:writeToFile]=>");
            e.printStackTrace();
        }
    }
}
