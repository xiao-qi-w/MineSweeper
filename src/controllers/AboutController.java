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
    private AnchorPane anchorPane;
    @FXML
    private GridPane grid;
    @FXML
    private Button btn;
    private LedNumber[] leds = new LedNumber[3];
    private int count = 90;

    public void initialize() {
        for (int i = 0; i < 3; ++i) {
            leds[i] = new LedNumber(0);
        }
        for(int i = 0; i < 3; ++i) {
            grid.add(leds[i], i,0);
        }
        grid.setStyle("-fx-background-color: #000000; -fx-vgap: 2.0; -fx-hgap: 2.0; -fx-padding: 2.0");
    }

    public void onAddClick() {
        count += 1;
        int f = count / 100;
        int s = count % 100 / 10;
        int t = count % 10;
        leds[0].switchSkin(f);
        leds[1].switchSkin(s);
        leds[2].switchSkin(t);
    }
}
