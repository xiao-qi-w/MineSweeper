package controllers;

import components.GameEnum;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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
            case EASY:
                easy.setSelected(true);
                break;
            case MEDIUM:
                medium.setSelected(true);
                break;
            case HARD:
                hard.setSelected(true);
                break;
            default: {
                custom.setSelected(true);
                numWidth.setText(GAME.width + "");
                numHeight.setText(GAME.height + "");
                numBoom.setText(GAME.boom + "");
            }
        }
        if (SOUND) {
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
            if (id.equals("custom")) {
                GAME = GameEnum.CUSTOM;
                numWidth.setEditable(true);
                numHeight.setEditable(true);
                numBoom.setEditable(true);
            } else {
                // 清空文本框并设置为不可编辑
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
            SOUND = "on".equals(id);
        }));

        // 保存按钮点击事件
        save.setOnMouseClicked(event -> {
            try {
                // 如果是自定义难度, 保存输入的值
                if (GAME == GameEnum.CUSTOM) {
                    GAME.width = Integer.parseInt(numWidth.getText());
                    GAME.height = Integer.parseInt(numHeight.getText());
                    GAME.boom = Integer.parseInt(numBoom.getText());
                }
                // 设置用于动画效果的图片
                loading.setImage(new Image("/images/loading.png"));
                loading.setVisible(true);
                // 点击保存时的动画效果,分两步, 1:旋转缓冲 2:图片淡出
                RotateTransition transition1 = new RotateTransition(Duration.seconds(1), loading);
                transition1.setByAngle(360); // 旋转角度
                transition1.setOnFinished(event1 -> {
                    loading.setImage(new Image("/images/save.png"));
                });

                FadeTransition transition2 = new FadeTransition(Duration.seconds(1), loading);
                transition2.setFromValue(1); // 起始不透明度
                transition2.setToValue(0); // 目标不透明度

                SequentialTransition sequence = new SequentialTransition(transition1, transition2);
                sequence.play(); // 播放动画
            } catch (Exception e) {
                System.out.println("Error on [Class:SettingsController, Method:initialize, Event: save]=>");
                e.printStackTrace();
            }
        });
    }
}