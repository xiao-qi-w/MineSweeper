package utils;

import components.GameEnum;

/**
 * @description: 游戏中的常量或全局变量统一放在此类管理
 * @author: 郭小柒w
 * @time: 2023/6/4
 */
public class Constant {
    // 游戏难度
    public static GameEnum GAME = GameEnum.EASY;

    // 游戏状态相关 [1:获胜, 0:未知, -1:失败]
    public static byte WIN = 1;
    public static byte UNSURE = 0;
    public static byte LOSS = -1;
    public static byte STATE = UNSURE;

    // 数字常量 [0:空白格, 9:地雷, 10:旗帜标记, 11:问号标记, 99:边界标记, 超过这个数字代表当前格子已被点开]
    public static final byte BLANK = 0;
    public static final byte BOOM = 9;
    public static final byte FLAG = 10;
    public static final byte GUESS = 11;
    public static final byte BOUND = 99;

    // 八个方位坐标
    public static final byte[][] positions = new byte[][]{
            {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}
    };
}
