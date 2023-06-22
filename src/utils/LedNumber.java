package utils;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * @description: 自定义控件, 点阵数字类
 * @author: 郭小柒w
 * @time: 2023/6/17
 */
public class LedNumber extends Control {

    public LedNumber(int index) {
        setSkin(createDefaultSkin(index));
    }

    protected Skin<LedNumber> createDefaultSkin(int index) {
        return new LedNumberSkin(this, index);
    }

    public void switchSkin(int index) {
        // 清空当前控件的子节点, 重新添加
        getChildren().clear();
        LedNumber newLedNumber = new LedNumber(index);
        getChildren().add(newLedNumber);
    }
}