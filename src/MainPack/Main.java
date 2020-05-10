package MainPack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage stage = new Stage();

    public static void closeStage() {
        stage.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI/Reg_gui.fxml"));
        stage.setTitle("Idle Alchemy!");
        Scene scene = new Scene(root, 600, 400);

        stage.setScene(scene);
        stage.show();

        Image image = new Image("Materials\\Cursor\\CustomCursor.png");  //pass in the image path
        scene.setCursor(new ImageCursor(image));

    }

    public static void main(String[] args) {
        launch(args);
    }
}
