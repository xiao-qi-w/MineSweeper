package controllers;

import components.GameEnum;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import static utils.Constant.GAME;

/**
 * @description:
 * @author: 郭小柒w
 * @time: 2023/6/15
 */
public class SettingsController {
    @FXML  // 底层布局
    private AnchorPane anchorPane;
    @FXML  // 单选按钮组, 难度
    private RadioButton easy, medium, hard, custom;
    @FXML  // 单选按钮组, 声音
    private RadioButton on, off;
    @FXML  // 文本框组, 自定义游戏数据
    private TextField numWidth, numHeight, numBoom;

    private ToggleGroup degree, sound;

    public void initialize() {
        // 单选按钮分组
        degree = new ToggleGroup();
        easy.setToggleGroup(degree);
        medium.setToggleGroup(degree);
        hard.setToggleGroup(degree);
        custom.setToggleGroup(degree);
        // 按钮选中事件
        degree.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            String id = ((RadioButton) newValue).getId();
            // 只有选中自定义难度情况下可编辑文本框, 默认不可编辑
            if(id.equals("custom")) {
                numWidth.setEditable(true);
                numHeight.setEditable(true);
                numBoom.setEditable(true);
            } else {
                numWidth.setText(null);
                numHeight.setText(null);
                numBoom.setText(null);
                numWidth.setEditable(false);
                numHeight.setEditable(false);
                numBoom.setEditable(false);
                if (id.equals("easy")) {
                    GAME = GameEnum.EASY;
                } else if (id.equals("medium")) {
                    GAME = GameEnum.MEDIUM;
                } else {
                    GAME = GameEnum.HARD;
                }
            }
        }));


        sound = new ToggleGroup();
        on.setToggleGroup(sound);
        off.setToggleGroup(sound);

        // 默认选中按钮
        easy.setSelected(true);
        on.setSelected(true);
    }
}
