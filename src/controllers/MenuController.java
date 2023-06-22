package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static components.Constant.*;

/**
 * @description: 游戏初始页控制逻辑
 * @author: 郭小柒w
 * @time: 2023/5/31
 */
public class MenuController {

    @FXML  // 底层布局
    private AnchorPane anchorPane;
    @FXML  // 炸弹图片
    private ImageView bomb;

    public void initialize() {
    }

    /**
     * 点击开始新游戏
     */
    public void onPlayClick() {
        openNewWindow("/fxmls/game.fxml", "onPlayClick");
    }

    /**
     * 点击打开排行榜
     */
    public void onRanksClick() {
        openNewWindow("/fxmls/ranks.fxml", "onRanksClick");
    }

    /**
     * 点击打开设置页
     */
    public void onSettingsClick() {
        openNewWindow("/fxmls/settings.fxml", "onSettingsClick");
    }

    /**
     * 点击打开制作人信息页
     */
    public void onAboutClick() {
        openNewWindow("/fxmls/about.fxml", "onAboutClick");
    }

    /**
     * 炸弹爆炸动画
     */
    public void onBoomClick() {
        bomb.setImage(new Image(EXPLODED_IMG));
    }

    /**
     * 打开新窗口
     * @param filePath fxml文件相对路径
     * @param method 方法名
     */
    public void openNewWindow(String filePath, String method) {
        try {
            // 加载设置界面布局文件
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(filePath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            // 设置Stage
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.getIcons().add(new Image(ICON_IMG));
            stage.setScene(scene);
            // 设置父窗体
            stage.initOwner(anchorPane.getScene().getWindow());
            // 设置除当前窗体外其他窗体均不可编辑
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error on [Class:MenuController, Method:" + method + "]=>");
            e.printStackTrace();
        }
    }
}
