package application;

import java.util.HashSet;
import java.util.Random;

/**
 * @description: 扫雷类，包括扫雷游戏的基本功能，其中游戏地图指记录地雷分布以及地雷周围数字统计情况的二维数组
 * @author: 郭小柒w
 * @time: 2023/5/31
 */
public class MineSweeper {
    // 初级(8×8,10雷)、中级(16×16,40雷)、高级(16×30,99雷)
    // 随机数
    private static final Random rand = new Random();
    // 数字常量，[9:地雷, 10:旗帜标记, 11:问号标记]
    private static final int BOOM = 9;
    private static final int FLAG = 10;
    private static final int GUESS = 11;
    // 不同难度规格的宽高
    private int width, height;
    // 地雷数目
    private int boom;
    // 游戏地图
    private int[][] map;

    public MineSweeper(int height, int width, int boom, int[][] map) {
        this.height = height;
        this.width = width;
        this.boom = boom;
        this.map = new int[height][width];
    }

    /**
     * 生成新游戏的地图数据
     */
    public void init() {
        // 用于记录地雷的位置，避免重复选择
        HashSet<Integer> set = new HashSet();
        // 确定随机数据范围
        int count = height * width;
        // 开始随机
        for(int t = boom; t > 0; ) {
            int index = rand.nextInt(count);
            if(!set.contains(index)) {
                set.add(index);
                map[index / width][index % width] = BOOM;
                t -= 1;
            }
        }
        // 统计地雷分布情况
        /*for(int i = 0; i < height; ++i) {
            for(int j = 0; j < width; ++j) {

            }
        }*/
    }

    /**
     * 展开与当前位置相连的所有空白区域
     */
    public void showBlank(int x, int y) {

    }
}
