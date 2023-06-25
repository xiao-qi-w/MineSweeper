package controllers;

import components.FileIO;
import components.GameEnum;
import components.LedNumber;
import components.MineSweeper;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Optional;

import static components.Constant.*;

/**
 * @description: 游戏进行中的控制逻辑
 * @author: 郭小柒w
 * @time: 2023/6/2
 */
public class GameController {
    @FXML  // 底层布局
    private AnchorPane anchorPane;
    @FXML  // 网格布局
    private GridPane grid, mark, time;
    @FXML  // 笑脸按钮
    private Button reset;
    @FXML  // 边框标签
    private Label labelTop, labelBottom, labelLeft, labelRight, labelCenter;
    // 获取GridPane包含的按钮集合
    private ObservableList<Node> buttons;
    // 定义游戏运行需要的类变量
    private MineSweeper mineSweeper;
    // 计时器
    private Timeline timeline;
    // 可监听属性
    private ReadOnlyIntegerWrapper rest, clicked;
    // 展示剩余可用标记和游戏用时的led数字
    private LedNumber[] ledMark = new LedNumber[3];
    private LedNumber[] ledTime = new LedNumber[3];

    public void initialize() {
        // 生成新游戏的用到的数据
        mineSweeper = new MineSweeper(GAME.width, GAME.height, GAME.bomb, new int[GAME.height][GAME.width]);
        // 设置监听
        addListener();
        // 绘制界面
        adjustControls();
        // 填充网格布局
        addToGrid();
    }

    /**
     * 为变量设置对应监听属性
     */
    private void addListener() {
        REST_FLAG = GAME.bomb;
        // 创建具有可观察特性的整数变量
        rest = new ReadOnlyIntegerWrapper(REST_FLAG);
        // 添加监听器, 在变量值变化时执行相应的操作, 下同
        ChangeListener<? super Number> restListener = (observable, oldValue, newValue) -> {
            // 在变量值变化时执行相应的操作
            ledMark[0].switchSkin(REST_FLAG / 100);
            ledMark[1].switchSkin(REST_FLAG % 100 / 10);
            ledMark[2].switchSkin(REST_FLAG % 10);
        };
        // 将监听器绑定到rest属性
        rest.addListener(restListener);

        CLICKED = NO;
        TIMER = 0;
        if (timeline != null) {
            timeline.stop();
        }
        clicked = new ReadOnlyIntegerWrapper(CLICKED);
        ChangeListener<? super Number> clickListener = (observable, oldValue, newValue) -> {
            // 已经被点击, 开始计时
            timeline = new Timeline(
                    new KeyFrame(Duration.millis(995), event -> {
                        TIMER += 1;
                        if (TIMER >= OVERTIME) {
                            STATE = LOSS;
                        }
                        if (STATE != UNSURE) {
                            String path = WIN_IMG;
                            timeline.stop();
                            if (STATE == LOSS) {
                                path = LOSS_IMG;
                            } else {
                                Platform.runLater(() -> showDialog());
                            }
                            reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + path + ")");
                        }
                        ledTime[0].switchSkin(TIMER / 100);
                        ledTime[1].switchSkin(TIMER % 100 / 10);
                        ledTime[2].switchSkin(TIMER % 10);
                    })
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        };
        clicked.addListener(clickListener);
    }

    /**
     * 向每个网格布局中填充内容
     */
    private void addToGrid() {
        // 地雷计数网格
        ledMark[0] = new LedNumber(REST_FLAG / 100);
        ledMark[1] = new LedNumber(REST_FLAG % 100 / 10);
        ledMark[2] = new LedNumber(REST_FLAG % 10);
        for (int i = 0; i < 3; ++i) {
            mark.add(ledMark[i], i, 0);
        }
        // 时间计数网格
        for (int i = 0; i < 3; ++i) {
            ledTime[i] = new LedNumber(0);
            time.add(ledTime[i], i, 0);
        }
        // 按钮网格
        if (buttons != null) {
            buttons.clear();
        }
        for (int i = 0; i < GAME.height; ++i) {
            for (int j = 0; j < GAME.width; ++j) {
                Button button = new Button();
                // 设置边界线的外观效果, 使按钮看起来更突出
                button.setBorder(new Border(new BorderStroke(Color.web("#737373"), BorderStrokeStyle.SOLID,
                        new CornerRadii(4), new BorderWidths(1))));
                button.setPadding(new Insets(0));
                // 设置按钮大小和点击事件
                button.setPrefSize(GAME.buttonSize, GAME.buttonSize);
                button.setOnMouseClicked(event -> {
                    handleEvent(event);
                });
                grid.add(button, j, i);
            }
        }
        buttons = grid.getChildren();
    }

    /**
     * 按钮点击事件
     *
     * @param event 点击事件
     */
    private void handleEvent(MouseEvent event) {
        // 判断游戏是否开局
        if (CLICKED == NO) {
            CLICKED = YES;
            clicked.set(CLICKED);
        }
        // 获取按钮所在行列
        int row = GridPane.getRowIndex((Node) event.getSource());
        int column = GridPane.getColumnIndex((Node) event.getSource());
        int[][] map = mineSweeper.getMap();
        // 只有在胜负未确定状态下可以更新网格按钮状态
        if (STATE != UNSURE) {
            return;
        }
        // 定义图片路径
        String buttonPath = null;
        // 获取按钮
        Button button = (Button) buttons.get(row * GAME.width + column);
        // 根据左右键设置不同响应逻辑
        if (event.getButton() == MouseButton.SECONDARY) {
            // 右键对应行为
            if (map[row][column] >= GUESS) {
                // 不设置图片, 还原雷的数目
                map[row][column] -= GUESS;
                REST_FLAG += 1;
            } else if (map[row][column] >= FLAG) {
                // 如果已经被标记, 路径更换为问号图片, 表示不确定
                buttonPath = GUESS_IMG;
                map[row][column] = map[row][column] - FLAG + GUESS;
            } else {
                // 未被标记过, 判断是否还有可用标记
                if (REST_FLAG > 0) {
                    buttonPath = FLAG_IMG;
                    map[row][column] += FLAG;
                    REST_FLAG -= 1;
                }
            }
        } else {
            // 左键对应行为
            if (map[row][column] <= BOUND && map[row][column] >= FLAG) {
                // 如果被标记, 则先清空标记
                map[row][column] -= map[row][column] >= GUESS ? GUESS : FLAG;
                REST_FLAG += 1;
            } else {
                mineSweeper.clickCell(row, column);

                if (STATE == UNSURE) {
                    // 统计非雷格子已点开数目
                    int count = 0;
                    for (int i = 0; i < GAME.height; ++i) {
                        for (int j = 0; j < GAME.height; ++j) {
                            button = (Button) buttons.get(i * GAME.width + j);
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
                                button.setStyle("-fx-border-color: #737373");
                                button.setDisable(true);
                            }
                        }
                    }
                    // 判断全部非雷格子是否全部点开
                    if (count + GAME.bomb == GAME.width * GAME.height) {
                        STATE = WIN;
                    }
                } else if (STATE == LOSS) {
                    buttonPath = UNEXPLODED_IMG;
                }
            }
        }
        rest.set(REST_FLAG);
        button.setStyle("-fx-background-size: contain; -fx-background-image: url(" + buttonPath + ")");
    }

    /**
     * 调整边框以及其他组件的位置和大小
     */
    private void adjustControls() {
        HashMap<String, Double> params = GAME.genParamsMap();
        double thickness = params.get("thickness");
        double offset = params.get("offset");
        double lenVertical = params.get("lenVertical");
        double lenHorizontal = params.get("lenHorizontal");

        // 计算实际窗口宽高
        WINDOW_WIDTH += lenHorizontal + thickness * 2;
        WINDOW_HEIGHT += lenVertical;

        // 设置窗口大小
        anchorPane.setPrefSize(WINDOW_WIDTH, lenVertical);

        // 设置网格布局位置
        AnchorPane.setTopAnchor(grid, offset + thickness);
        AnchorPane.setLeftAnchor(grid, thickness);

        // 设置重置按钮的位置
        reset.setStyle("-fx-background-size: contain; -fx-background-image: url(" + SMILE_IMG + ")");
        AnchorPane.setLeftAnchor(reset, thickness + (lenHorizontal - 50) / 2);

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

    /**
     * 重置游戏进度
     */
    public void onResetClick() {
        initialize();
    }

    /**
     * 用时少于排行版某一项, 输入玩家名称
     */
    private void showDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("少侠请留名");
        dialog.setHeaderText("请输入您的昵称:");
        dialog.setContentText("新玩家");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            System.out.println("Your name: " + name);
            String filePath = null;
            String[] record = new String[]{name, TIMER + ""};
            switch (GAME) {
                case HARD:
                    filePath = RECORD_PATHS[2];
                    break;
                case MEDIUM:
                    filePath = RECORD_PATHS[1];
                    break;
                case EASY:
                    filePath = RECORD_PATHS[0];
                default:
                    break;
            }
            FileIO.writeToFile(filePath, record);
        });
    }
}