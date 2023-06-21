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

import static utils.Constant.*;

/**
 * @description: 游戏初始页
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
        try {
            // 加载游戏界面布局文件
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmls/game.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            // 设置Stage
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setOnShown(event -> {
                stage.setWidth(WINDOW_WIDTH); // 设置实际宽度
                stage.setHeight(WINDOW_HEIGHT); // 设置实际高度
            });
            stage.setOnCloseRequest(event -> {
                WINDOW_WIDTH = 6.0;
                WINDOW_HEIGHT = 35.0;
            });
            stage.getIcons().add(new Image(ICON_IMG));
            stage.setScene(scene);
            // 设置父窗体
            stage.initOwner(anchorPane.getScene().getWindow());
            // 设置除当前窗体外其他窗体均不可编辑
            stage.initModality(Modality.WINDOW_MODAL);

            stage.show();
        } catch (IOException e) {
            System.out.println("Error on [Class:MenuController, Method:onPlayClick]=>");
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
        try {
            // 加载设置界面布局文件
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmls/settings.fxml"));
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
            System.out.println("Error on [Class:MenuController, Method:onSettingsClick]=>");
            e.printStackTrace();
        }
    }

    /**
     * 点击开始新游戏
     */
    public void onAboutClick() {
        try {
            // 加载设置界面布局文件
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmls/about.fxml"));
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
            System.out.println("Error on [Class:MenuController, Method:onAboutClick]=>");
            e.printStackTrace();
        }
    }

    /**
     * 炸弹爆炸动画
     */
    public void onBoomClick() {
        bomb.setImage(new Image(EXPLODED_IMG));
    }
}
