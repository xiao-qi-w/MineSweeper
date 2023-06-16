package controllers;

import components.GameEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static utils.Constant.GAME;
import static utils.Constant.SOUND;

/**
 * @description: 主要控制设置界面按钮选中后的行为
 * @author: 郭小柒w
 * @time: 2023/6/15
 */
public class SettingsController {
    @FXML  // 底层布局
    private AnchorPane anchorPane;
    @FXML  // 单选按钮, 难度
    private RadioButton easy, medium, hard, custom;
    @FXML  // 单选按钮, 声音
    private RadioButton on, off;
    @FXML  // 文本框组, 自定义游戏数据
    private TextField numWidth, numHeight, numBoom;
    @FXML // 保存按钮
    private Button save;
    @FXML // 辅助效果图
    private ImageView loading;
    // 单选按钮组
    private ToggleGroup degree, sound;

    public void initialize() {
        // 首先尝试使用已保存设置
        switch (GAME) {
            case EASY: easy.setSelected(true); break;
            case MEDIUM: medium.setSelected(true); break;
            case HARD: hard.setSelected(true); break;
            default: {
                custom.setSelected(true);
                numWidth.setText(GAME.width + "");
                numHeight.setText(GAME.height + "");
                numBoom.setText(GAME.boom + "");
            }
        }
        if(SOUND) {
            on.setSelected(true);
        } else {
            off.setSelected(true);
        }

        // 单选按钮分组
        degree = new ToggleGroup();
        sound = new ToggleGroup();

        easy.setToggleGroup(degree);
        medium.setToggleGroup(degree);
        hard.setToggleGroup(degree);
        custom.setToggleGroup(degree);

        on.setToggleGroup(sound);
        off.setToggleGroup(sound);

        // 难度按钮选中事件
        degree.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            String id = ((RadioButton) newValue).getId();
            // 只有选中自定义难度情况下可编辑文本框, 默认不可编辑
            if(id.equals("custom")) {
                GAME = GameEnum.CUSTOM;
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

        // 声音按钮选中事件
        sound.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            String id = ((RadioButton) newValue).getId();
            // 只有选中自定义难度情况下可编辑文本框, 默认不可编辑
            if(id.equals("on")) {
                SOUND = true;
            } else {
                SOUND = false;
            }
        }));
        try {
            loading.setImage(new Image("../images/loading.png"));
            loading.setVisible(true);
            Thread.sleep(500);
            loading.setImage(new Image("../images/save.png"));
            Thread.sleep(500);
            loading.setVisible(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 保存按钮点击事件
        save.setOnMouseClicked(event -> {
            if(GAME == GameEnum.CUSTOM) {
                GAME.width = Integer.parseInt(numWidth.getText());
                GAME.height = Integer.parseInt(numHeight.getText());
                GAME.boom = Integer.parseInt(numBoom.getText());
            }
        });
    }
}
