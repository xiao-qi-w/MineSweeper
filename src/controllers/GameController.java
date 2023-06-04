package controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


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

    public void initialize() {
        // TODO: 如有需要初始化的内容，请在此方法内完成
        // 填充图形
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                Rectangle rect = new Rectangle();
                rect.setHeight(40);
                rect.setWidth(40);
                rect.setFill(Color.GREY);
                GridPane.setMargin(rect, new Insets(1,1,1,1));
                grid.add(rect, j, i);
            }
        }
    }
}
