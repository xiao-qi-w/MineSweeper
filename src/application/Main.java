package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * @description: 程序入口
 * @author: 郭小柒w
 * @time: 2023/5/31
 */

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			// 加载布局文件
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxmls/start.fxml"));
			Parent root = loader.load();
			// 设置窗体内容和标题
			Scene scene = new Scene(root);
			stage.setResizable(false);
			stage.setTitle("扫雷");
			stage.getIcons().add(new Image("/images/MineSweeper.png"));
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			System.out.println("Error on [Class:Main, Method:start]=>" + e);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
