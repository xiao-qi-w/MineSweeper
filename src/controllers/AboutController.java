package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import utils.LedNumber;

/**
 * @description: 开发者信息界面
 * @author: 郭小柒w
 * @time: 2023/6/21
 */
public class AboutController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane grid;

    public void initialize() {
        LedNumber[] leds = new LedNumber[10];
        for (int i = 0; i < 10; ++i) {
            leds[i] = new LedNumber();
            leds[i].switchSkin(i);
        }
        for(int i = 0; i < 10; ++i) {
            grid.add(leds[i], i,0);
        }
        grid.setStyle("-fx-background-color: #000000; -fx-vgap: 2.0; -fx-hgap: 2.0; -fx-padding: 2.0");
    }
}
