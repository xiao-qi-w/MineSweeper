package controllers;

import components.FileIO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import static components.Constant.RECORD_PATHS;

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
    private TableView<String[]> table;
    @FXML
    private TableColumn<String[], String> name, time;
    // 单选按钮组
    private ToggleGroup degree;
    // 用于存放数据的列表
    private ObservableList<String[]> data;

    public void initialize() {
        // 单选按钮分组
        degree = new ToggleGroup();
        easy.setToggleGroup(degree);
        medium.setToggleGroup(degree);
        hard.setToggleGroup(degree);
        // 默认选中简单, 并加载数据
        easy.setSelected(true);
        data = FileIO.readFromFile(RECORD_PATHS[0]);
        table.setItems(data);
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
            table.setItems(data);
        }));
        table.setFixedCellSize(36.0);
        // 设置每个 TableColumn 的 cellValueFactory
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        time.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
    }
}
