package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @description: 游戏初始页
 * @author: 郭小柒w
 * @time: 2023/5/31
 */
public class StartController {

    @FXML
    private AnchorPane rootStage; // 父窗口面板

    public void initialize() {
        // TODO: 如有需要初始化的内容，请在此方法内完成
    }

    /**
     * 点击开始新游戏
     */
    public void onStartClick() {
        try {
            // 加载迷宫主界面布局文件
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmls/game.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            // 获取Controller
            GameController controller = loader.getController();
            // 游戏初始化操作

            controller.initialize();
            // 设置Stage
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.getIcons().add(new Image("/images/MineSweeper.png"));
            stage.setScene(scene);
            // 设置父窗体
            stage.initOwner(rootStage.getScene().getWindow());
            // 设置除当前窗体外其他窗体均不可编辑
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击开始新游戏
     */
    public void onRanksClick() {
        System.out.println("ranks clicked");
    }

    /**
     * 点击开始新游戏
     */
    public void onSettingsClick() {
        System.out.println("Settings clicked");
    }

    /**
     * 点击开始新游戏
     */
    public void onAboutClick() {
        System.out.println("about clicked");
    }
}
