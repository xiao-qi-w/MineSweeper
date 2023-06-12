package controllers;

import components.MineSweeper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.HashMap;

import static utils.Constant.*;

/**
 * @description:
 * @author: 郭小柒w
 * @time: 2023/6/2
 */
public class GameController {
    @FXML  // 底层布局
    private AnchorPane anchorPane;
    @FXML  // 网格布局
    private GridPane grid;
    @FXML  // 笑脸按钮
    private Button reset;
    @FXML  // 边框标签
    private Label labelTop, labelBottom, labelLeft, labelRight, labelCenter;
    // 获取GridPane包含的按钮集合
    private ObservableList<Node> buttons;
    // 定义游戏运行需要的类变量
    private MineSweeper mineSweeper;

    public void initialize() {
        int width = GAME.width;
        int height = GAME.height;
        int boom = GAME.boom;
        double size = GAME.size;
        this.mineSweeper = new MineSweeper(width, height, boom, new int[width][height]);
        // 绘制边框
        paintBorder();
        // 向网格布局中填充按钮
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                Button button = new Button();
                button.setPrefSize(size, size);
                button.setOnMouseClicked(event -> {
                    handleEvent(event);
                });
                grid.add(button, j, i);
            }
        }
        this.buttons = grid.getChildren();
        String pathb = "images/smile.png";
        this.reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + pathb + ")");
    }

    // 处理点击事件
    public void handleEvent(MouseEvent event) {
        // 获取按钮所在行列
        int row = GridPane.getRowIndex((Node) event.getSource());
        int column = GridPane.getColumnIndex((Node) event.getSource());

        if (event.getButton() == MouseButton.SECONDARY) {
            // 右键单击
            String path = "images/mark.png";
            Button button = (Button) buttons.get(row * mineSweeper.getWidth() + column);
            button.setStyle("-fx-background-size: contain; -fx-background-image: url(" + path + ")");
        } else {
            // 左键单击
            mineSweeper.clickCell(row, column);
            if (STATE == UNSURE) {
                int[][] map = mineSweeper.getMap();
                for (int i = 0; i < mineSweeper.getHeight(); ++i) {
                    for (int j = 0; j < mineSweeper.getWidth(); ++j) {
                        Button button = (Button) buttons.get(i * mineSweeper.getWidth() + j);
                        if (map[i][j] > BOUND) {
                            int value = map[i][j] - 100;
                            if (value != BLANK) {
                                button.setText(value + "");
                            }
                            button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #4F4E48;");
                            button.setDisable(true);
                        }
                    }
                }
            } else if (STATE == WIN) {
                String path = "images/win.png";
                reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + path + ")");
            } else {
                String path = "images/loss.png";
                reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + path + ")");
            }
        }
    }

    public void onResetClick() {
        if (STATE != UNSURE) {
            STATE = UNSURE;
            String path = "images/smile.png";
            reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + path + ")");
        }
        buttons.clear();
        initialize();
    }

    public void paintBorder() {
        HashMap<String, Double> params = GAME.genParamsMap();
        double thickness = params.get("thickness");
        double offset = params.get("offset");
        double lenVertical = params.get("lenVertical");
        double lenHorizontal = params.get("lenHorizontal");
        // 设置窗口大小
        anchorPane.setPrefSize(lenHorizontal + thickness * 2, lenVertical);
        // 设置网格布局位置
        AnchorPane.setTopAnchor(grid, offset + thickness);
        // 设置重置按钮的位置
        AnchorPane.setTopAnchor(reset, (anchorPane.getWidth() - 50) / 2);
        AnchorPane.setLeftAnchor(reset, offset / 2);
        // 设置边框标签的大小和位置
        labelTop.setPrefSize(lenHorizontal, thickness);
        AnchorPane.setLeftAnchor(labelTop, thickness);
        AnchorPane.setTopAnchor(labelTop, 0.0);

        labelCenter.setPrefSize(lenHorizontal, thickness);
        AnchorPane.setLeftAnchor(labelCenter, thickness);
        AnchorPane.setTopAnchor(labelCenter, offset);

        labelBottom.setPrefSize(lenHorizontal, thickness);
        AnchorPane.setLeftAnchor(labelBottom, thickness);
        AnchorPane.setTopAnchor(labelBottom, lenVertical - thickness);

        labelLeft.setPrefSize(thickness, lenVertical);
        AnchorPane.setLeftAnchor(labelLeft, 0.0);
        AnchorPane.setTopAnchor(labelLeft, 0.0);

        labelRight.setPrefSize(thickness, lenVertical);
        AnchorPane.setLeftAnchor(labelRight, lenHorizontal + thickness);
        AnchorPane.setTopAnchor(labelRight, 0.0);
    }
}
