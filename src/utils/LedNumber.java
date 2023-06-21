package utils;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

/**
 * @description: 自定义控件, 点阵数字类
 * @author: 郭小柒w
 * @time: 2023/6/17
 */
public class LedNumber {
    // 自定义梯形
    private Polygon polygons[];
    // 0-9数字对应的多边形集合
    private int numbers[][];
    // 颜色属性 字体色, 高亮字体色, 背景色
    private Color fontColor, fontColorHighlight, backgroundColor;
    // 长度属性 本类只采用下底边和高度确定一个梯形, 上底边通过计算获得
    private double lenH, lenT, lenB;

    public LedNumber() {
        this.fontColor = Color.RED;
        this.fontColorHighlight = Color.DARKGRAY;
        this.backgroundColor = Color.BLACK;
        this.lenH = 5.0;
        this.lenB = 20.0;
        this.lenT = lenB - lenH * 2;

        this.polygons = new Polygon[8];
        this.numbers = new int[][]{{0, 1, 2, 4, 6, 7},
                {2, 6},
                {1, 2, 3, 4, 5, 7},
                {1, 2, 3, 5, 6, 7},
                {0, 2, 3, 5, 6},
                {0, 1, 3, 5, 6, 7},
                {0, 1, 3, 4, 5, 6, 7},
                {1, 2, 6},
                {0, 1, 2, 3, 4, 5, 6, 7},
                {0, 1, 2, 3, 5, 6, 7}
        };

        init();
    }

    /**
     * 构建点阵数字需要的边
     */
    public void init() {
        for(int i = 0; i < 8; ++i) {
            polygons[i] = new Polygon();
        }
        polygons[0].getPoints().addAll(paintTrapezoidal(1, 0.0, 0.0));
        polygons[0].setStyle("-fx-background-color: #ec0707");
    }

    /**
     * 绘制自定义梯形
     * @param toward 梯形从长底边到短底边的方向[上下左右]
     * @param x 左上角横坐标
     * @param y 左上角纵坐标
     * @return 坐标数组
     */
    public ArrayList<Double> paintTrapezoidal(int toward, double x, double y) {
        ArrayList<Double> points = new ArrayList();
        points.add(x);
        points.add(y);
        points.add(x + lenT);
        points.add(y);
        points.add(x + lenT + lenH);
        points.add(y - lenH);
        points.add(x - lenH);
        points.add(y - lenH);
        return points;
    }
}