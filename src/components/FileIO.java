package components;

import java.io.*;
import java.util.ArrayList;

import static components.Constant.RECORD_PATHS;

/**
 * @description: 处理文件读写
 * @author: 郭小柒w
 * @time: 2023/6/22
 */
public class FileIO {

    static {
        try {
            File directory = new File("/ranks");
            if (!directory.exists() || !directory.isDirectory()) {
                System.out.println("目录不存在, 将自动创建...");
                directory.mkdirs();
            }
            for (String path : RECORD_PATHS) {
                File file = new File(path);
                if (!file.exists()) {
                    System.out.println("文件不存在, 将自动创建...");
                    file.createNewFile();
                }
            }
        } catch (Exception e) {
            System.out.println("Error on [Class:FileIO, Method:static segment]=>");
            e.printStackTrace();
        }
    }

    public static ArrayList<String[]> readFromFile(String filePath) {
        ArrayList<String[]> data = new ArrayList();
        // 读取文件
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void writeToFile(String filePath) {
        // 写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("这是写入的文本内容");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
