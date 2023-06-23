package components;

import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

/**
 * @description: 点阵数字外观类
 * @author: 郭小柒w
 * @time: 2023/6/21
 */
public class LedNumberSkin extends SkinBase<LedNumber> {
    // 设置布局, 管理多边形相对位置
    private Pane pane;
    // 自定义梯形, 用于组合表示数字
    private Polygon polygons[] = new Polygon[7];
    // 控制每条边的颜色显示 [true:red, false:grey]
    private boolean edges[][] = {
            {true, true, true, true, true, true, false},
            {false, false, true, true, false, false, false},
            {false, true, true, false, true, true, true},
            {false, true, true, true, true, false, true},
            {true, false, true, true, false, false, true},
            {true, true, false, true, true, false, true},
            {true, true, false, true, true, true, true},
            {false, true, true, true, false, false, false},
            {true, true, true, true, true, true, true},
            {true, true, true, true, true, false, true}
    };
    // 长度属性 只需初始化高度和长水平边, 短边通过计算获得
    private int height, lenLong, lenShort;
    // 指定外观数字
    private int index;

    public LedNumberSkin(LedNumber ledNumber, int index) {
        super(ledNumber);
        this.index = index;
        this.height = 5;
        this.lenLong = 20;
        this.lenShort = lenLong - height * 2;
        this.pane = new Pane();
        init();
    }

    /**
     * 构建点阵数字需要的边
     */
    public void init() {
        // 初始化
        for (int i = 0; i < 7; ++i) {
            polygons[i] = new Polygon();
        }
        // 计算出各多边形的顶点坐标
        polygons[0].getPoints().addAll(getPoints(0, 1, 1));
        polygons[1].getPoints().addAll(getPoints(1, 2, 0));
        polygons[2].getPoints().addAll(getPoints(2, height + lenShort + 3, height + 1));
        polygons[3].getPoints().addAll(getPoints(2, height + lenShort + 3, height + lenLong + 3));
        polygons[4].getPoints().addAll(getPoints(3, height + 2, lenLong * 2 - 1));
        polygons[5].getPoints().addAll(getPoints(0, 1, lenLong + 3));
        polygons[6].getPoints().addAll(getPoints(4, 2, lenLong + 2));

        // 根据edges数组判断每条边是否要用高亮色
        for (int i = 0; i < 7; ++i) {
            if(edges[index][i]) {
                polygons[i].setFill(Color.web("#FF0000"));
            } else {
                polygons[i].setFill(Color.web("#680404"));
            }
            pane.getChildren().add(polygons[i]);
        }

        getChildren().add(pane);
    }

    /**
     * 计算自定义多边形各顶点坐标
     *
     * @param toward 多边形朝向 [01234: 右下左上中]
     * @param x      起始点横坐标
     * @param y      起始点纵坐标
     * @return 坐标数组
     */
    public ArrayList<Double> getPoints(int toward, double x, double y) {
        ArrayList<Double> points = new ArrayList();
        // 添加起始点坐标
        points.add(x);
        points.add(y);
        // 按顺时针方向依次添加其余坐标
        switch (toward) {
            case 0:
                points.add(x + height);
                points.add(y + height);
                points.add(x + height);
                points.add(y + height + lenShort);
                points.add(x);
                points.add(y + lenLong);
                break;
            case 1:
                points.add(x + lenLong);
                points.add(y);
                points.add(x + height + lenShort);
                points.add(y + height);
                points.add(x + height);
                points.add(y + height);
                break;
            case 2:
                points.add(x + height);
                points.add(y - height);
                points.add(x + height);
                points.add(y + height + lenShort);
                points.add(x);
                points.add(y + lenShort);
                break;
            case 3:
                points.add(x + lenShort);
                points.add(y);
                points.add(x + height + lenShort);
                points.add(y + height);
                points.add(x - height);
                points.add(y + height);
                break;
            case 4:
                points.add(x + height);
                points.add(y - height + 2);
                points.add(x + height + lenShort);
                points.add(y - height + 2);
                points.add(x + lenLong);
                points.add(y);
                points.add(x + height + lenShort);
                points.add(y + height - 2);
                points.add(x + height);
                points.add(y + height - 2);
                break;
        }
        return points;
    }
}
