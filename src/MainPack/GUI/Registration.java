package MainPack.GUI;

import MainPack.CustomPlayer.CustomPlayer;
import MainPack.Database.DAO.AltarDAO;
import MainPack.Database.DAO.PlayerDAO;
import MainPack.Database.DAO.StockDAO;
import MainPack.Database.DAO.UserDAO;
import MainPack.Entity.Altar;
import MainPack.Entity.Player;
import MainPack.Entity.Stock;
import MainPack.Entity.User;
import MainPack.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Registration {
    private UserDAO userDAO = new UserDAO();
    private PlayerDAO playerDAO = new PlayerDAO();
    private StockDAO stockDAO = new StockDAO();
    private AltarDAO altarDAO = new AltarDAO();

    private User user;

    @FXML PasswordField password_field;
    @FXML TextField username_field;

    @FXML AnchorPane sign_in;
    @FXML AnchorPane sign_in_light;
    @FXML AnchorPane register;

    @FXML AnchorPane register_light;

    @FXML AnchorPane panel_sign_in;
    @FXML AnchorPane panel_register;

    @FXML Text error_field;

    @FXML AnchorPane soundON_press;
    @FXML AnchorPane soundON_common;
    @FXML AnchorPane soundOFF_press;
    @FXML AnchorPane soundOFF_common;
    @FXML Slider volume_changer;

    private CustomPlayer my_player;
    private String menu_music = System.getProperty("user.dir") + "\\src\\Materials\\Music\\menu_music.mp3";

    @FXML
    private void initialize () {
        my_player = new CustomPlayer(menu_music);
        my_player.changeAuto(true);
        my_player.setRecordSettings(Integer.MAX_VALUE);

        soundON_common.setVisible(true);
        soundON_press.setVisible(true);

        soundOFF_common.setVisible(false);
        soundOFF_press.setVisible(false);
    }

    private void changeLight(AnchorPane on, AnchorPane off) {
        on.setVisible(true);
        off.setVisible(false);
    }

    @FXML
    private void changeMode() {
        if ( panel_sign_in.isVisible() ) {
            panel_sign_in.setVisible(false);
            panel_register.setVisible(true);

            sign_in.setVisible(false);
            register.setVisible(true);
        }
        else {
            panel_register.setVisible(false);
            panel_sign_in.setVisible(true);

            sign_in.setVisible(true);
            register.setVisible(false);
        }
    }

    //################################  БЛОК КОДА МУЗЫКАЛЬНОГО ВОСПРОИЗВЕДЕНИЯ  ########################################

    private void changeStatusSound () {
        if ( my_player.showStatus() == MediaPlayer.Status.PLAYING) {
            my_player.pauseSound();
        }
        else if (my_player.showStatus() == MediaPlayer.Status.PAUSED) {
            my_player.playSound();
        }
    }

    @FXML
    private void changeVolumeSound () {
        volume_changer.valueProperty().addListener((observable, oldValue, newValue) -> my_player.volumeSound(newValue.doubleValue()));

    }

    //#####################################  БЛОК КОДА С ЭФФЕКТАМИ ЭЛЕМЕНТОВ  ##########################################

    @FXML
    private void pressSound () {
        if ( soundON_common.isVisible() && soundON_press.isVisible()) {
            soundON_common.setVisible(false);
        }
        else if (soundOFF_common.isVisible() && soundOFF_press.isVisible()) {
            soundOFF_common.setVisible(false);
        }
    }

    @FXML
    private void releaseSound () {
        if ( !soundON_common.isVisible() && soundON_press.isVisible()) {
            soundON_press.setVisible(false);
            soundOFF_press.setVisible(true);
            soundOFF_common.setVisible(true);

            volume_changer.setVisible(false);
        }
        else if (!soundOFF_common.isVisible() && soundOFF_press.isVisible()) {
            soundOFF_press.setVisible(false);
            soundON_press.setVisible(true);
            soundON_common.setVisible(true);

            volume_changer.setVisible(true);
        }

        changeStatusSound();
    }

    @FXML
    private void enterSignInLight() {
        changeLight(sign_in_light, sign_in);
    }

    @FXML
    private void exitSignInLight() {
        changeLight(sign_in, sign_in_light);
    }

    @FXML
    private void enterRegisterLight() {
        changeLight(register_light, register);
    }

    @FXML
    private void exitRegisterLight() {
        changeLight(register, register_light);
    }

    //##################################  БЛОК КОДА РЕГИСТРАЦИИ (РАБОТА С БД)  ########################################


    // метод нахождения пользователя в БД
    private String checkUser() throws Exception {
        user = userDAO.get(username_field.getText());
        if ( user != null) {
            if(user.getPassword().equals(password_field.getText())) {
                return "Success!";
            }
            else return "IncorrectPassword";
        }
        else return "UserNotFound";

    }

    // метод загрузки нового окна
    private void loadNewWindow(String username) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("play_gui.fxml"));
        Parent root = loader.load();

        PlayGround playGround = loader.getController();
        playGround.loadData(username);
        playGround.initAnimation();

        Scene scene = new Scene(root, 1000, 650);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Idle Alchemy!");
        stage.setOnHidden(event -> {
            try {
                playGround.uploadData();
                playGround.closeGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        stage.show();

        Image image = new Image("Materials\\Cursor\\CustomCursor.png");
        scene.setCursor(new ImageCursor(image));

        my_player.stopSound();
        Main.closeStage();
    }

    // кнопка входа в учетную запись
    @FXML
    private void signIn() throws Exception {
        String response = checkUser();
        switch (response) {
            case "Success!":
                loadNewWindow(user.getUsername());
                break;
            case "IncorrectPassword":
                error_field.setText("Something went wrong :( It looks like your password does not match!");
                break;
            case "UserNotFound":
                error_field.setText("This user was not found in the database! Check the correctness of the entered data.");
                break;
        }

        PlayGround.closeStage();
    }


    // кнопка регистрации - создания нового пользователя
    // добавляет необходимые данные в БД
    @FXML
    private void register() throws Exception {
        String response = checkUser();
        String username = username_field.getText();
        if (response.equals("UserNotFound")) {
            user = new User(username, password_field.getText());
            Altar altar = new Altar(username, 0, 0, 0, 0, 0 );
            Stock stock = new Stock(username, 0, 0, 0, 0, 0);
            Player player = new Player(username, 1, 30, 10, 10, 5);

            userDAO.insert(user);
            altarDAO.insert(altar);
            stockDAO.insert(stock);
            playerDAO.insert(player);

            loadNewWindow(username);
        }
        else error_field.setText("Attention! This user is already registered in the database!");

        PlayGround.closeStage();
    }
}
