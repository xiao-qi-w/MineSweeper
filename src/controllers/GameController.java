package controllers;

import components.MineSweeper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

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
        mineSweeper = new MineSweeper(width, height, boom, new int[height][width]);
        // 绘制界面
        paintBorders();
        // 向网格布局中填充按钮
        if (buttons != null) {
            buttons.clear();
        }
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Button button = new Button();
                button.setPrefSize(size, size);
                button.setOnMouseClicked(event -> {
                    handleEvent(event);
                });
                grid.add(button, j, i);
            }
        }
        buttons = grid.getChildren();
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
        initialize();
    }

    public void paintBorders() {
        HashMap<String, Double> params = GAME.genParamsMap();
        double thickness = params.get("thickness");
        double offset = params.get("offset");
        double lenVertical = params.get("lenVertical");
        double lenHorizontal = params.get("lenHorizontal");
        // 设置窗口大小
        anchorPane.setPrefSize(WINDOW_WIDTH, lenVertical);

        // 设置网格布局位置
        AnchorPane.setTopAnchor(grid, offset + thickness);
        AnchorPane.setLeftAnchor(grid, thickness);

        // 设置重置按钮的位置
        String pathb = "images/smile.png";
        reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + pathb + ")");
        AnchorPane.setLeftAnchor(reset, thickness + (lenHorizontal - 50) / 2);
        AnchorPane.setTopAnchor(reset, (offset - 50) / 2);

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

        anchorPane.setOnMouseClicked(event -> System.out.println("anchorPane:" + anchorPane.widthProperty().doubleValue() + "," + anchorPane.heightProperty().doubleValue()));
        labelLeft.setOnMouseClicked(event -> System.out.println("labelLeft:" + labelLeft.widthProperty().doubleValue() + "," + labelLeft.heightProperty().doubleValue()));
    }
}
