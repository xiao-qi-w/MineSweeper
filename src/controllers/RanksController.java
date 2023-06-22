package controllers;

import components.FileIO;
import components.GameEnum;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

import static components.Constant.*;

/**
 * @description: 排行榜页面控制逻辑
 * @author: 郭小柒w
 * @time: 2023/6/22
 */
public class RanksController {
    @FXML  // 底层布局
    private AnchorPane anchorPane;
    @FXML  // 单选按钮, 难度
    private RadioButton easy, medium, hard;
    @FXML  // 排行展示表
    private TableView table;
    // 单选按钮组
    private ToggleGroup degree;
    // 用于存放数据的列表
    private ArrayList<String[]> data;

    public void initialize() {
        // 单选按钮分组
        degree = new ToggleGroup();
        easy.setToggleGroup(degree);
        medium.setToggleGroup(degree);
        hard.setToggleGroup(degree);
        // 默认选中简单
        easy.setSelected(true);
        // 难度按钮选中事件
        degree.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            String id = ((RadioButton) newValue).getId();
            // 根据不同按钮设置不同文件路径
            if (id.equals("easy")) {
                data = FileIO.readFromFile(RECORD_PATHS[0]);
            } else if (id.equals("medium")) {
                data = FileIO.readFromFile(RECORD_PATHS[1]);
            } else {
                data = FileIO.readFromFile(RECORD_PATHS[2]);
            }

        }));
    }
}
