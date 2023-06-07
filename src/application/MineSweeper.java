package application;

import utils.Constant;

import java.util.HashSet;
import java.util.Random;

import static utils.Constant.*;

/**
 * @description: 扫雷类，包括扫雷游戏的基本功能，其中游戏地图指记录地雷分布以及地雷周围数字统计情况的二维数组
 * @author: 郭小柒w
 * @time: 2023/5/31
 */
public class MineSweeper {
    // 初级(9×9,10雷)、中级(16×16,40雷)、高级(16×30,99雷)
    // 随机数
    private Random rand;
    // 不同难度规格的宽高
    private int width, height;
    // 地雷数目
    private int boom;
    // 游戏地图
    private int[][] map;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBoom() {
        return boom;
    }

    public void setBoom(int boom) {
        this.boom = boom;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public MineSweeper(int height, int width, int boom, int[][] map) {
        this.rand = new Random();
        this.height = height;
        this.width = width;
        this.boom = boom;
        this.map = new int[height][width];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                this.map[i][j] = BLANK;
            }
        }
        this.init();
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
        for (int t = boom; t > 0; ) {
            int index = rand.nextInt(count);
            // 如果当前位置可以设置为地雷，标记该位置，待分配地雷个数减一
            if (!set.contains(index)) {
                set.add(index);
                map[index / width][index % width] = BOOM;
                t -= 1;
            }
        }
        // 统计地雷分布情况
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (map[i][j] != BOOM) {
                    map[i][j] = countBoom(i, j);
                }
            }
        }
    }

    /**
     * 统计当前格子周围的地雷个数
     *
     * @param x 横坐标
     * @param y 纵坐标
     * @return count 地雷个数
     */
    public int countBoom(int x, int y) {
        int count = 0;
        // 依次判断周围格子是否存在地雷
        for (int i = 0; i < 8; ++i) {
            int newX = x + Constant.positions[i][0];
            int newY = y + Constant.positions[i][1];
            if (newX > -1 && newX < height && newY > -1 && newY < width && map[newX][newY] == BOOM) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * 展开与当前位置相连的所有空白区域，包括包裹这层空白区域数字边界
     * @param x 横坐标
     * @param y 纵坐标
     */
    public void clickCell(int x, int y) {
        if (map[x][y] == BLANK) {
            map[x][y] += 100;
            // 点击到空白区域，递归判断周围8个方向
            for (int i = 0; i < 8; i += 1) {
                int newX = x + positions[i][0];
                int newY = y + positions[i][1];
                if (newX > -1 && newX < height && newY > -1 && newY < width && map[newX][newY] != BOOM) {
                    clickCell(newX, newY);
                }
            }
        } else if(map[x][y] == BOOM) {
            // 点击到地雷，游戏状态设置为失败
            STATE = LOSS;
        } else if(map[x][y] < BOUND){
            // 点击到数字格，数值加100用于区分是否已被点开
            map[x][y] += 100;
        }
    }

    public static void main(String[] args) {
        MineSweeper ms = new MineSweeper(8, 8, 10, new int[8][8]);
        ms.print();
    }

    public void print() {
        for (int[] row : map) {
            for (int x : row) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }
}
