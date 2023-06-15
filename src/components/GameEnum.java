package components;

import java.util.HashMap;

import static utils.Constant.WINDOW_HEIGHT;
import static utils.Constant.WINDOW_WIDTH;

/**
 * @description: 游戏难度枚举
 * @author: 郭小柒w
 * @time: 2023/6/11
 */
public enum GameEnum {
    EASY(9, 9, 10, 40.0, 25.0),
    MEDIUM(16, 16, 40, 35.0, 20.0),
    HARD(30, 16, 99, 30.0, 15.0),
    CUSTOM();

    // 游戏难度规格[宽 x 高], 对应难度地雷个数
    public int width, height, boom;
    // 网格尺寸
    public double buttonSize, numSize;

    GameEnum(int width, int height, int boom, double buttonSize, double numSize) {
        this.width = width;
        this.height = height;
        this.boom = boom;
        this.buttonSize = buttonSize;
        this.numSize = numSize;
    }

    GameEnum() {
    }

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
        // 计算实际窗口宽高
        WINDOW_WIDTH += lenHorizontal + thickness * 2;
        WINDOW_HEIGHT += lenVertical;
        return params;
    }
}
