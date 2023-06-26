package components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

import static components.Constant.PREFIX;
import static components.Constant.RECORD_PATHS;

/**
 * @description: 处理文件读写
 * @author: 郭小柒w
 * @time: 2023/6/22
 */
public class FileIO {

    static {
        // 每次调用此类都先判断目录和文件是否存在
        try {
            File directory = new File(PREFIX + "/src/ranks");
            if (!directory.exists() || !directory.isDirectory()) {
                System.out.println("目录不存在, 将自动创建...");
                directory.mkdirs();
            }
            for (String path : RECORD_PATHS) {
                path = PREFIX + path;
                File file = new File(path);
                if (!file.exists()) {
                    System.out.println("文件不存在, 将自动创建...");
                    if (file.createNewFile()) {
                        System.out.println("创建成功");
                        // 写入内置数据
                        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                        for (int i = 0; i < 10; ++i) {
                            writer.write("未命名 999\n");
                        }
                        writer.flush();
                        writer.close();
                    } else {
                        System.out.println("创建失败, 请查找原因");
                    }
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
        ObservableList<String[]> list = FXCollections.observableArrayList();
        try {
            // 拼接路径, 创建读取对象
            filePath = PREFIX + filePath;
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            // 读取数据
            String line = null;
            while ((line = reader.readLine()) != null) {
                list.add(line.split(" "));
            }
            reader.close();
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
            ObservableList<String[]> list = readFromFile(filePath);
            // 将记录插入到合适位置
            for (int i = 0; i < 10; ++i) {
                if (record[1].compareTo(list.get(i)[1]) <= 1) {
                    list.add(i, record);
                    break;
                }
            }
            // 移除多余记录
            list.remove(10);
            // 重新写入数据
            BufferedWriter writer = new BufferedWriter(new FileWriter(PREFIX + filePath));
            for (String[] item : list) {
                writer.write(item[0] + " " + item[1] + "\n");
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println("Error on [Class:FileIO, Method:writeToFile]=>");
            e.printStackTrace();
        }
    }
}