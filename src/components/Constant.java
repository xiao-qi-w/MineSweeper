package components;

import javafx.scene.paint.Color;

/**
 * @description: 游戏中的常量或全局变量统一放在此类管理
 * @author: 郭小柒w
 * @time: 2023/6/4
 */
public class Constant {
    // 游戏窗口实际宽高, 仅限在本电脑测出的偏差值,目前未找到较好的解决方法
    public static double WINDOW_WIDTH = 6.0;
    public static double WINDOW_HEIGHT = 35.0;

    // 游戏难度
    public static GameEnum GAME = GameEnum.EASY;

    // 游戏状态相关 [1:获胜, 0:未知, -1:失败]
    public static byte WIN = 1;
    public static byte UNSURE = 0;
    public static byte LOSS = -1;
    public static byte STATE = UNSURE;

    // 局内声音设置
    public static boolean SOUND = true;

    // 数字常量 [0:空白格, 9:地雷]
    public static final byte BLANK = 0;
    public static final byte BOOM = 9;
    // [20:旗帜标记判断, 40:问号标记判断]
    public static final byte FLAG = 20;
    public static final byte GUESS = 40;
    // [99:边界标记, 超过这个数字代表当前格子已被点开]
    public static final byte BOUND = 99;

    // 数字颜色常量 从1-8
    public static final Color[] NUMS = {
            Color.rgb(0, 0, 255),
            Color.rgb(0, 128, 0),
            Color.rgb(255, 0, 0),
            Color.rgb(0, 0, 128),
            Color.rgb(128, 0, 0),
            Color.rgb(0, 128, 128),
            Color.rgb(0, 0, 0),
            Color.rgb(128, 128, 128)
    };

    // 八个方位坐标
    public static final byte[][] positions = new byte[][]{
            {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}
    };

    // 所有图片文件相对路径
    public static final String WIN_IMG = "/images/win.png";
    public static final String LOSS_IMG = "/images/loss.png";
    public static final String SMILE_IMG = "/images/smile.png";
    public static final String EXPLODED_IMG = "/images/exploded.png";
    public static final String UNEXPLODED_IMG = "/images/unexploded.png";
    public static final String LOAD_IMG = "/images/loading.png";
    public static final String SAVE_IMG = "/images/save.png";
    public static final String FLAG_IMG = "/images/flag.png";
    public static final String GUESS_IMG = "/images/guess.png";
    public static final String ICON_IMG = "/images/icon.png";

    // 排行榜文件相对路径前缀
    public static final String[] RECORD_PATHS = {
            "../MineSweeper/src/ranks/easy.txt",
            "../MineSweeper/src/ranks/medium.txt",
            "../MineSweeper/src/ranks/hard.txt"
    };
}
