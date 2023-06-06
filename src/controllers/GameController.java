package controllers;

import application.MineSweeper;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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

    private MineSweeper mineSweeper;

    public void initialize(int width, int height, int boom) {
        mineSweeper = new MineSweeper(width, height, boom, new int[width][height]);
        // 填充图形
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                Button button = new Button();
                button.setPrefSize(44.0,44.0);
//                button.setStyle("-fx-background-color: #d2d2d2");
                button.setOnMouseClicked(event->{
                    int column = GridPane.getColumnIndex((Node)event.getSource());
                    int row = GridPane.getRowIndex((Node)event.getSource());
                    System.out.println("GridPane clicked at column: " + column + ", row: " + row);
                });
//                GridPane.setMargin(button, new Insets(1, 1, 1, 1));
                grid.add(button, j, i);
            }
        }
    }
}
