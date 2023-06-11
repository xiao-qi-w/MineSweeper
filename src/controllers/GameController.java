package controllers;

import components.GameEnum;
import components.MineSweeper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.HashMap;

import static utils.Constant.*;

/**
 * @description:
 * @author: 郭小柒w
 * @time: 2023/6/2
 */
public class GameController {
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
        /*String pathb = "images/failed.png";
        this.reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + pathb + ")");*/
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
                String path = "images/fail.png";
                reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + path + ")");
            } else {
                String path = "images/fail.png";
                reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + path + ")");
            }
        }
    }

    public void onResetClick() {
        if (STATE != UNSURE) {
            STATE = UNSURE;
            String path = "images/failed.png";
            reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + path + ")");
        }
        buttons.clear();
        initialize();
    }

    public void paintBorder() {
        HashMap<String, Double> params = GAME.genParamsMap();
        // TODO 设置标签的长高
    }
}
