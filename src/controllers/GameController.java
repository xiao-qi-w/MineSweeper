package controllers;

import application.MineSweeper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import static utils.Constant.*;

/**
 * @description:
 * @author: 郭小柒w
 * @time: 2023/6/2
 */
public class GameController {
    @FXML
    private AnchorPane rootStage; // 父窗口面板
    @FXML
    private GridPane grid;          // 网格布局

    private ObservableList<Node> buttons;// 获取GridPane包含的按钮集合
    private MineSweeper mineSweeper;

    public void initialize(int width, int height, int boom) {
        this.mineSweeper = new MineSweeper(width, height, boom, new int[width][height]);
        this.mineSweeper.print();
        // 填充图形
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                Button button = new Button();
                button.setPrefSize(44.0, 44.0);
                button.setStyle("-fx-stroke: black");
                button.setOnMouseClicked(event -> {
                    int row = GridPane.getRowIndex((Node) event.getSource());
                    int column = GridPane.getColumnIndex((Node) event.getSource());
                    paint(row, column);
                });
                grid.add(button, j, i);
            }
        }
        this.buttons = grid.getChildren();
    }

    // 绘制游戏界面
    public void paint(int row, int column) {
        mineSweeper.clickCell(row, column);
        System.out.println();
        mineSweeper.print();
        int[][] map = mineSweeper.getMap();
        for (int i = 0; i < mineSweeper.getHeight(); ++i) {
            for (int j = 0; j < mineSweeper.getWidth(); ++j) {
                Button button = (Button)buttons.get(i * mineSweeper.getWidth() + j);
                if (map[i][j] > BOUND) {
                    int value = map[i][j] - 100;
                    if(value != BLANK) {
                        button.setText(value + "");
                    }
                    button.setStyle("-fx-background-color: #ffffff");
                    button.setDisable(true);
                }
            }
        }
    }
}
