package controllers;

import components.MineSweeper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.HashMap;

import static utils.Constant.*;

/**
 * @description:
 * @author: 郭小柒w
 * @time: 2023/6/2
 */
public class GameController {
    @FXML  // 底层布局
    private AnchorPane anchorPane;
    @FXML  // 网格布局
    private GridPane grid;
    @FXML  // 笑脸按钮
    private Button reset;
    @FXML  // 边框标签
    private Label labelTop, labelBottom, labelLeft, labelRight, labelCenter;
    // 获取GridPane包含的按钮集合
    private ObservableList<Node> buttons;
    // 定义游戏运行需要的类变量
    private MineSweeper mineSweeper;
    // 剩余标记数
    private static int REST_FLAG = 0;

    public void initialize() {
        REST_FLAG = GAME.bomb;
        mineSweeper = new MineSweeper(GAME.width, GAME.height, GAME.bomb, new int[GAME.height][GAME.width]);
        // 绘制界面
        paintBorders();
        // 向网格布局中填充按钮
        if (buttons != null) {
            buttons.clear();
        }
        for (int i = 0; i < GAME.height; ++i) {
            for (int j = 0; j < GAME.width; ++j) {
                Button button = new Button();
                button.setPrefSize(GAME.buttonSize, GAME.buttonSize);
                button.setOnMouseClicked(event -> {
                    handleEvent(event);
                });
                grid.add(button, j, i);
            }
        }
        buttons = grid.getChildren();
    }

    // 处理点击事件
    public void handleEvent(MouseEvent event) {
        // 获取按钮所在行列
        int row = GridPane.getRowIndex((Node) event.getSource());
        int column = GridPane.getColumnIndex((Node) event.getSource());
        int[][] map = mineSweeper.getMap();

        if (event.getButton() == MouseButton.SECONDARY) {
            // 判断是否还有可用标记
            if(REST_FLAG > 0) {
                // 获取按钮
                Button button = (Button) buttons.get(row * GAME.width + column);
                // 右键单击, 定义图片路径
                String path = null;

                if(map[row][column] >= GUESS) {
                    // 不设置图片, 还原雷的数目
                    map[row][column] -= GUESS;
                    REST_FLAG += 1;
                } else if(map[row][column] >= FLAG){
                    // 如果已经被标记, 路径更换为问号图片, 表示不确定
                    path = GUESS_IMG;
                    map[row][column] = map[row][column] - FLAG + GUESS;
                } else {
                    // 未被标记过, 添加标记
                    path = FLAG_IMG;
                    map[row][column] += FLAG;
                    REST_FLAG -= 1;
                }
                button.setStyle("-fx-background-size: contain; -fx-background-image: url(" + path + ")");
            }
        } else {
            // 左键单击
            mineSweeper.clickCell(row, column);
            if (STATE == UNSURE) {
                // count统计非雷格子已点开数目
                int count = 0;
                for (int i = 0; i < GAME.height; ++i) {
                    for (int j = 0; j < GAME.height; ++j) {
                        Button button = (Button) buttons.get(i * GAME.width + j);
                        if (map[i][j] > BOUND) {
                            count += 1;
                            int value = map[i][j] - 100;
                            if (value != BLANK) {
                                // 消除空白填充
                                button.setPadding(new Insets(0.0));
                                // 创建粗体字体
                                Font boldFont = Font.font("Arial", FontWeight.BOLD, GAME.numSize);
                                button.setFont(boldFont);
                                button.setTextFill(NUMS[value - 1]);
                                button.setText(value + "");
                            }
                            button.setStyle("-fx-border-insets: 0.0; -fx-border-color: #7A7A7A");
                            button.setDisable(true);
                        }
                    }
                }
                // 判断全部非雷格子是否全部点开
                if(count + GAME.bomb == GAME.width * GAME.height) {
                    STATE = WIN;
                    reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + WIN_IMG + ")");
                }
            } else if (STATE == LOSS) {
                reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + LOSS_IMG + ")");
            }
        }
    }

    public void onResetClick() {
        if (STATE != UNSURE) {
            STATE = UNSURE;
            reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + SMILE_IMG + ")");
        }
        initialize();
    }

    public void paintBorders() {
        HashMap<String, Double> params = GAME.genParamsMap();
        double thickness = params.get("thickness");
        double offset = params.get("offset");
        double lenVertical = params.get("lenVertical");
        double lenHorizontal = params.get("lenHorizontal");
        // 设置窗口大小
        anchorPane.setPrefSize(WINDOW_WIDTH, lenVertical);

        // 设置网格布局位置
        AnchorPane.setTopAnchor(grid, offset + thickness);
        AnchorPane.setLeftAnchor(grid, thickness);

        // 设置重置按钮的位置
        reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + SMILE_IMG + ")");
        AnchorPane.setLeftAnchor(reset, thickness + (lenHorizontal - 50) / 2);
        AnchorPane.setTopAnchor(reset, (offset - 50) / 2);

        // 设置边框标签的大小和位置
        labelTop.setPrefSize(lenHorizontal, thickness);
        AnchorPane.setLeftAnchor(labelTop, thickness);
        AnchorPane.setTopAnchor(labelTop, 0.0);

        labelCenter.setPrefSize(lenHorizontal, thickness);
        AnchorPane.setLeftAnchor(labelCenter, thickness);
        AnchorPane.setTopAnchor(labelCenter, offset);

        labelBottom.setPrefSize(lenHorizontal, thickness);
        AnchorPane.setLeftAnchor(labelBottom, thickness);
        AnchorPane.setTopAnchor(labelBottom, lenVertical - thickness);

        labelLeft.setPrefSize(thickness, lenVertical);
        AnchorPane.setLeftAnchor(labelLeft, 0.0);
        AnchorPane.setTopAnchor(labelLeft, 0.0);

        labelRight.setPrefSize(thickness, lenVertical);
        AnchorPane.setLeftAnchor(labelRight, lenHorizontal + thickness);
        AnchorPane.setTopAnchor(labelRight, 0.0);
    }
}
