package components;

import java.util.HashMap;

/**
 * @description: 游戏难度枚举
 * @author: 郭小柒w
 * @time: 2023/6/11
 */
public enum GameEnum {
    EASY(9, 9, 10, 40.0, 30.0),
    MEDIUM(16, 16, 40, 35.0, 25.0),
    HARD(30, 16, 99, 30.0, 20.0),
    CUSTOM();

    // 游戏难度规格[宽 x 高], 相应地雷个数
    public int width, height, bomb;
    // 网格按钮尺寸, 数字字体大小
    public double buttonSize, numSize;

    GameEnum(int width, int height, int bomb, double buttonSize, double numSize) {
        this.width = width;
        this.height = height;
        this.bomb = bomb;
        this.buttonSize = buttonSize;
        this.numSize = numSize;
    }

    GameEnum() {
        this.buttonSize = 35.0;
        this.numSize = 25.0;
    }

    // 宽和高限制在简单和困难之间
    public void setWidth(int width) {
        if (width < EASY.width) {
            this.width = EASY.width;
        } else if (width > HARD.width) {
            this.width = HARD.width;
        } else {
            this.width = width;
        }
    }

    public void setHeight(int height) {
        if (height < EASY.height) {
            this.height = EASY.height;
        } else if (height > HARD.height) {
            this.height = HARD.height;
        } else {
            this.height = height;
        }
    }

    // 地雷数介于格子数之间
    public void setBomb(int bomb) {
        if (bomb < 0) {
            this.bomb = 0;
        } else if (bomb > width * height) {
            this.bomb = width * height;
        } else {
            this.bomb = bomb;
        }
    }

    /**
     * 生成游戏窗口和边框大小计算需要用到的参数
     * @return 参数集合
     */
    public HashMap<String, Double> genParamsMap() {
        HashMap<String, Double> params = new HashMap();
        // 标签宽度, 固定值10
        double thickness = 10.0;
        params.put("thickness", thickness);
        // 中间位置的标签框相对于布局顶部的偏移量, 固定值110
        double offset = 110.0;
        params.put("offset", offset);
        // 边框标签边的水平和竖直长度, 宽度为固定值10
        double lenVertical = height * buttonSize + thickness * 2 + offset;
        double lenHorizontal = width * buttonSize;
        params.put("lenVertical", lenVertical);
        params.put("lenHorizontal", lenHorizontal);
        return params;
    }
}
