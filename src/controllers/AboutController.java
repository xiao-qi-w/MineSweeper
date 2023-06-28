package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import components.LedNumber;

/**
 * @description: 开发者信息界面控制逻辑
 * @author: 郭小柒w
 * @time: 2023/6/21
 */
public class AboutController {
    @FXML
    private GridPane grid;
    @FXML
    private Button btn;
    private LedNumber[] leds = new LedNumber[3];
    private int count = 90;

    public void initialize() {

    }
}
