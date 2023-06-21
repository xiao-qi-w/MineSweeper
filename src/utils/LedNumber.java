package utils;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * @description: 自定义控件, 点阵数字类
 * @author: 郭小柒w
 * @time: 2023/6/17
 */
public class LedNumber extends Control {

    // 十个数字外观
    private Skin<LedNumber> skins[];

    public LedNumber() {
        createDefaultSkin();
        /*this.skins = new Skin[10];
        for (int i = 0; i < 10; ++i) {
            skins[i] = createDefaultSkin(i);
        }*/
        setSkin(createDefaultSkin(7));
    }

    protected Skin<LedNumber> createDefaultSkin(int i) {
        return new LedNumberSkin(this, i);
    }

    /**
     * 数字外观切换
     * @param i 将外观切换为数字i
     */
    public void switchSkin(int i) {
        setSkin(createDefaultSkin(i));
    }
}