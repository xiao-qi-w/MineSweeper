package controllers;

import application.MineSweeper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static utils.Constant.*;

/**
 * @description:
 * @author: 郭小柒w
 * @time: 2023/6/2
 */
public class GameController {
    @FXML
    private BorderPane borderPane; // 父窗口面板
    @FXML
    private GridPane grid;          // 网格布局

    private ObservableList<Node> buttons;// 获取GridPane包含的按钮集合
    private MineSweeper mineSweeper;

    public void initialize(int width, int height, int boom) {
        this.mineSweeper = new MineSweeper(width, height, boom, new int[width][height]);
        // 填充图形
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                Button button = new Button();
                button.setPrefSize(44.0, 44.0);
                button.setOnMouseClicked(event -> {
                    handleEvent(event);
                });
                grid.add(button, j, i);
            }
        }
        this.buttons = grid.getChildren();
    }

    // 处理点击事件
    public void handleEvent(MouseEvent event) {
        // 获取按钮所在行列
        int row = GridPane.getRowIndex((Node) event.getSource());
        int column = GridPane.getColumnIndex((Node) event.getSource());

        if(event.getButton() == MouseButton.SECONDARY) {
            // 右键单击
            String path = "images/mark.png";
            Button button = (Button)buttons.get(row * mineSweeper.getWidth() + column);
            button.setStyle("-fx-background-size: contain; -fx-background-image: url(" + path + ")");
        } else {
            // 左键单击
            mineSweeper.clickCell(row, column);
            if(STATE == UNSURE) {
                int[][] map = mineSweeper.getMap();
                for (int i = 0; i < mineSweeper.getHeight(); ++i) {
                    for (int j = 0; j < mineSweeper.getWidth(); ++j) {
                        Button button = (Button)buttons.get(i * mineSweeper.getWidth() + j);
                        if (map[i][j] > BOUND) {
                            int value = map[i][j] - 100;
                            if(value != BLANK) {
                                button.setText(value + "");
                            }
                            button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #4F4E48;");
                            button.setDisable(true);
                        }
                    }
                }
            } else if(STATE == WIN) {
                //TODO show dialog
                showDialog("You win");
            } else {
                String path = "images/fail.png";
                Button button = (Button)buttons.get(row * mineSweeper.getWidth() + column);
                button.setStyle("-fx-background-size: contain; -fx-background-image: url(" + path + ")");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                showDialog("You loss");
            }
        }
    }

    public void showDialog(String title) {
        try {
            // 加载迷提示窗界面布局文件
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmls/dialog.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            /*// 获取Controller
            GameController controller = loader.getController();
            // 游戏初始化操作
            controller.initialize(9,9,10);
            // 设置Stage*/
            Stage stage = new Stage();
            stage.setResizable(false);
            // stage.getIcons().add(new Image("/images/MineSweeper.png"));
            stage.setScene(scene);
            // 设置父窗体
            stage.initOwner(borderPane.getScene().getWindow());
            // 设置除当前窗体外其他窗体均不可编辑
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error on [Class:MenuController, Method:onPlayClick]=>" + e);
        }
    }
}
