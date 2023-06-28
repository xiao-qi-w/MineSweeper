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
import javafx.util.Duration;

import static components.Constant.*;

/**
 * @description: 设置界面控制逻辑
 * @author: 郭小柒w
 * @time: 2023/6/15
 */
public class SettingsController {
    @FXML  // 单选按钮, 难度
    private RadioButton easy, medium, hard, custom;
    @FXML  // 文本框组, 自定义游戏数据
    private TextField numWidth, numHeight, numBoom;
    @FXML // 保存按钮
    private Button save;
    @FXML // 辅助效果图
    private ImageView loading;
    // 单选按钮组
    private ToggleGroup degree;

    public void initialize() {
        // 文本框默认不可编辑
        numWidth.setEditable(false);
        numHeight.setEditable(false);
        numBoom.setEditable(false);
        // 首先尝试使用已保存设置
        switch (GAME) {
            case MEDIUM:
                medium.setSelected(true);
                break;
            case HARD:
                hard.setSelected(true);
                break;
            case CUSTOM:
                custom.setSelected(true);
                numWidth.setEditable(true);
                numHeight.setEditable(true);
                numBoom.setEditable(true);
                numWidth.setText(GAME.width + "");
                numHeight.setText(GAME.height + "");
                numBoom.setText(GAME.bomb + "");
                break;
            default:
                easy.setSelected(true);
                break;
        }

        // 单选按钮分组
        degree = new ToggleGroup();
        easy.setToggleGroup(degree);
        medium.setToggleGroup(degree);
        hard.setToggleGroup(degree);
        custom.setToggleGroup(degree);
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

        // 保存按钮点击事件
        save.setOnMouseClicked(event -> {
            try {
                // 如果是自定义难度, 保存输入的值
                if (GAME == GameEnum.CUSTOM) {
                    try {
                        // 保存自定义输入
                        GAME.setWidth(Integer.parseInt(numWidth.getText()));
                        GAME.setHeight(Integer.parseInt(numHeight.getText()));
                        GAME.setBomb(Integer.parseInt(numBoom.getText()));
                    } catch (NumberFormatException e) {
                        // 输入问题导致的转换失败, 按简单设置处理
                        GAME.setWidth(9);
                        GAME.setHeight(9);
                        GAME.setBomb(10);
                    }
                }
                // 设置用于动画效果的图片
                loading.setImage(new Image(LOAD_IMG));
                loading.setVisible(true);
                // 点击保存时的动画效果,分两步, 1:旋转缓冲 2:图片淡出
                RotateTransition transition1 = new RotateTransition(Duration.seconds(1), loading);
                // 旋转角度
                transition1.setByAngle(360);
                transition1.setOnFinished(event1 -> {
                    loading.setImage(new Image(SAVE_IMG));
                });

                FadeTransition transition2 = new FadeTransition(Duration.seconds(1), loading);
                // 不透明度变化
                transition2.setFromValue(1);
                transition2.setToValue(0);

                SequentialTransition sequence = new SequentialTransition(transition1, transition2);
                // 播放动画
                sequence.play();
            } catch (Exception e) {
                System.out.println("Error on [Class:SettingsController, Method:initialize, Event: save]=>");
                e.printStackTrace();
            }
        });
    }
}