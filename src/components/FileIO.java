package components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Comparator;

import static components.Constant.PREFIX;
import static components.Constant.RECORD_PATHS;

/**
 * @description: 处理文件读写
 * @author: 郭小柒w
 * @time: 2023/6/22
 */
public class FileIO {

    // 存放数据的列表
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
                    writer.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Error on [Class:FileIO, Method:static segment]=>");
            e.printStackTrace();
        }
    }

    /**
     * 读取文件
     *
     * @param filePath 文件路径
     * @return 排行数据集合
     */
    public static ObservableList<String[]> readFromFile(String filePath) {
        try {
            filePath = PREFIX + filePath;
            System.out.println(filePath);
            // 清空列表
            if (list != null && list.size() > 0) {
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
     *
     * @param filePath 文件路径
     * @param record   待更新数据项
     */
    public static void writeToFile(String filePath, String[] record) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            list = readFromFile(filePath);
            // 将记录插入到合适位置
            for (int i = 0; i < 10; ++i) {
                if (record[1].compareTo(list.get(i)[1]) <= 1) {
                    list.add(i, record);
                }
            }
            System.out.println(list);
            list.remove(10);
            for (String[] item : list) {
                writer.write(item[0] + " " + item[1]);
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println("Error on [Class:FileIO, Method:writeToFile]=>");
            e.printStackTrace();
        }
    }
}
