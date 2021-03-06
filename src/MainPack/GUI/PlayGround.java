package MainPack.GUI;

import MainPack.CustomPlayer.CustomPlayer;
import MainPack.Database.DAO.AltarDAO;
import MainPack.Database.DAO.PlayerDAO;
import MainPack.Database.DAO.StockDAO;
import MainPack.Entity.Monster;
import MainPack.Entity.Stock;
import MainPack.Entity.Altar;
import MainPack.Entity.Player;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PlayGround {
    private Player player;
    private Altar playerAltar;
    private Stock playerStock;
    private Monster monster;

    private PlayerDAO playerDAO = new PlayerDAO();
    private StockDAO stockDAO = new StockDAO();
    private AltarDAO altarDAO = new AltarDAO();

    @FXML AnchorPane rune_1;
    @FXML AnchorPane rune_2;
    @FXML AnchorPane rune_3;
    @FXML AnchorPane rune_4;
    @FXML AnchorPane rune_5;
    @FXML AnchorPane rune_6;
    @FXML AnchorPane rune_7;
    @FXML AnchorPane rune_8;

    @FXML Text ability_text;
    @FXML AnchorPane stone_activate;
    @FXML AnchorPane stone_common;
    @FXML AnchorPane upg_dec_common_1;
    @FXML AnchorPane upg_dec_common_light_1;
    @FXML AnchorPane upg_dec_press_light_1;
    @FXML AnchorPane upg_dmg_common_2;
    @FXML AnchorPane upg_dmg_common_light_2;
    @FXML AnchorPane upg_dmg_press_light_2;
    @FXML AnchorPane upg_spdm_common_3;
    @FXML AnchorPane upg_spdm_common_light_3;
    @FXML AnchorPane upg_spdm_press_light_3;
    @FXML AnchorPane upg_inc_common_4;
    @FXML AnchorPane upg_inc_common_light_4;
    @FXML AnchorPane upg_inc_press_light_4;
    @FXML AnchorPane upg_spdp_common_5;
    @FXML AnchorPane upg_spdp_common_light_5;
    @FXML AnchorPane upg_spdp_press_light_5;

    @FXML ImageView left_bubble_5;
    @FXML ImageView left_bubble_4;
    @FXML ImageView left_bubble_3;
    @FXML ImageView left_bubble_2;
    @FXML ImageView left_bubble_1;
    @FXML ImageView right_bubble_10;
    @FXML ImageView right_bubble_9;
    @FXML ImageView right_bubble_8;
    @FXML ImageView right_bubble_7;
    @FXML ImageView right_bubble_6;
    @FXML ImageView alchemist;
    @FXML ImageView cauldron;
    @FXML ImageView earth_ess;
    @FXML ImageView energy_ess;
    @FXML ImageView storm_ess;
    @FXML ImageView sun_ess;
    @FXML ImageView void_ess;
    @FXML ImageView info_sign;
    @FXML Text info_text;

    @FXML Label monster_status;
    @FXML ImageView monster_skin;
    @FXML ImageView weapon_skin;
    @FXML ImageView attack_ball;
    @FXML ImageView health_bar;

    @FXML AnchorPane leaderboard_common;
    @FXML AnchorPane leaderboard_press;

    @FXML AnchorPane soundON_press;
    @FXML AnchorPane soundON_common;
    @FXML AnchorPane soundOFF_press;
    @FXML AnchorPane soundOFF_common;
    @FXML Slider volume_changer;

    private CustomPlayer background_player; // for background sound
    private CustomPlayer monster_player; // for monster death
    private CustomPlayer weapon_player; // for weapon fire
    private CustomPlayer upgrade_player; // for altar upgrade
    private CustomPlayer buy_player; // for something buy in shop
    private CustomPlayer alch_player;  // for alch click

    final private String BACKGROUND_SOUND = System.getProperty("user.dir") + "\\src\\Materials\\Music\\battle_music.mp3";
    private String MONSTER_DEATH = System.getProperty("user.dir") + "\\src\\Materials\\SoundEffects\\monster_death.wav";
    private String WEAPON_FIRE = System.getProperty("user.dir") + "\\src\\Materials\\SoundEffects\\weapon_fire.wav";
    private String UPGRADE_ALTAR = System.getProperty("user.dir") + "\\src\\Materials\\SoundEffects\\upgrade_altar.wav";
    private String BUY_SOMETHING = System.getProperty("user.dir") + "\\src\\Materials\\SoundEffects\\but_something.wav";
    private String ALCH_CLICK = System.getProperty("user.dir") + "\\src\\Materials\\SoundEffects\\alch_click.wav";


    private Timeline speedTimer;

    private List<ImageView> bubbles_list;

    private void initBubblesList() {

        bubbles_list = Arrays.asList(left_bubble_1, left_bubble_2, left_bubble_3, left_bubble_4,
                left_bubble_5, right_bubble_6, right_bubble_7, right_bubble_8, right_bubble_9, right_bubble_10);
    }

    private static Stage stage = new Stage();
    private static Stage leaderboard = new Stage();

    static void closeStage() {
        leaderboard.close();
        stage.close();
    }

    @FXML
    private void initialize () {
        background_player = new CustomPlayer(BACKGROUND_SOUND);
        background_player.changeAuto(true);
        background_player.setRecordSettings(Integer.MAX_VALUE);

        weapon_player = new CustomPlayer(WEAPON_FIRE);
        alch_player = new CustomPlayer(ALCH_CLICK);
        monster_player = new CustomPlayer(MONSTER_DEATH);
        upgrade_player = new CustomPlayer(UPGRADE_ALTAR);

        soundON_common.setVisible(true);
        soundON_press.setVisible(true);

        soundOFF_common.setVisible(false);
        soundOFF_press.setVisible(false);
    }

    //##############################  БЛОК ЗАКРЫТИЯ ОКНА И ВОЗВРАЩЕНИЯ В МЕНЮ ВХОДА  ##################################

    void closeGame () throws IOException {
        closeStage();
        background_player.disposeSound();
        weapon_player.disposeSound();
        alch_player.disposeSound();
        monster_player.disposeSound();
        upgrade_player.disposeSound();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Reg_gui.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 400);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Idle Alchemy!");
        stage.show();

        Image image = new Image("Materials\\Cursor\\CustomCursor.png");
        scene.setCursor(new ImageCursor(image));
    }

    //#############################################  БЛОК ЛЕВОГО МЕНЮ  #################################################

    @FXML
    private void clickLeaderboard () throws IOException {
        if ( leaderboard_common.isVisible()) {
            leaderboard_common.setVisible(false);
            leaderboard_press.setVisible(true);
        }
        else if ( !leaderboard_common.isVisible()) {
            leaderboard_press.setVisible(false);
            leaderboard_common.setVisible(true);

            openLeaderboard();
        }
    }

    private void openLeaderboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("leaderboard.fxml"));
        Parent root = loader.load();

        LeaderBoard leaderBoard = loader.getController();
        leaderBoard.initLists();

        Scene scene = new Scene(root, 480, 600);
        leaderboard.setScene(scene);
        leaderboard.setTitle("Leaderboard");
        leaderboard.show();

        Image image = new Image("Materials\\Cursor\\CustomCursor.png");
        scene.setCursor(new ImageCursor(image));
    }

    //################################  БЛОК КОДА МУЗЫКАЛЬНОГО ВОСПРОИЗВЕДЕНИЯ  ########################################

    private void playCustomSoundFX (CustomPlayer player) {
        if (player.showStatus() == MediaPlayer.Status.PLAYING) {
            player.stopSound();
            player.playSound();
        }
        else if (player.showStatus() != MediaPlayer.Status.STOPPED) player.playSound();
    }

    private void changeStatusSound () {
        if ( background_player.showStatus() == MediaPlayer.Status.PLAYING) {
            background_player.pauseSound();
            weapon_player.stopSound();
            alch_player.stopSound();
            monster_player.stopSound();
            upgrade_player.stopSound();

        }
        else if (background_player.showStatus() == MediaPlayer.Status.PAUSED) {
            background_player.playSound();
            weapon_player.playSound();
            alch_player.playSound();
            monster_player.playSound();
            upgrade_player.playSound();
        }
    }

    @FXML
    private void changeVolumeSound () {
        volume_changer.valueProperty().addListener((observable, oldValue, newValue) -> background_player.volumeSound(newValue.doubleValue()));

    }

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

    //################################  БЛОК ИНИЦИАЛИЗАЦИИ ОБЪЕКТОВ ДАННЫМИ С БД  ####################################

    void loadData(String username) throws Exception {
        player = playerDAO.get(username);
        playerAltar = altarDAO.get(username);
        playerStock = stockDAO.get(username);
        monster = new Monster(player);
    }

    void uploadData() throws Exception {
        playerDAO.update(player);
        altarDAO.update(playerAltar);
        stockDAO.update(playerStock);
    }

    //####################################### БЛОК МЕТОДОВ АНИМАЦИИ ##################################################

    void initAnimation() {
        weaponAnimation();
        attackAnimation();
        changeMonsterDataOnSpawn();
        alchemistAnimation();
        initBubblesList();
    }

    private void weaponAnimation() {
        weapon_skin.setImage(new Image("@../../Materials/BattleGround/Weapon_sprite.png"));
        weapon_skin.setViewport(new Rectangle2D(0, 0, 80, 155));

        final Animation animation = new Animation(
                weapon_skin,
                Duration.millis(2000),
                9, 9,
                0, 0,
                80, 155
        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }

    private void alchemistAnimation() {
        alchemist.setImage(new Image("@../../Materials/Alchemist/Alchemist_full.png"));
        alchemist.setViewport(new Rectangle2D(0, 0, 104, 122));

        final Animation animation = new Animation(
                alchemist,
                Duration.millis(400),
                7, 7,
                0, 0,
                104, 122
        );
        animation.setCycleCount(1);
        animation.play();
    }

    private void attackAnimation() {
        attack_ball.setImage(new Image("@../../Materials/BattleGround/attack.png"));
        attack_ball.setViewport(new Rectangle2D(0, 0, 40, 36));

        final Animation animation = new Animation(
                attack_ball,
                Duration.millis(1500),
                5, 5,
                0, 0,
                40, 36
        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        changeSpeedAnimation(player.getWeaponSpeed());
    }

    private void changeSpeedAnimation(double speed) {
        Line attackline = new Line(0,0,600,0);
        PathTransition transition = new PathTransition(Duration.seconds(speed*0.4), attackline, attack_ball);
        transition.setCycleCount(1);

        if( speedTimer != null) speedTimer.stop();
        speedTimer = new Timeline(
                new KeyFrame(
                        Duration.seconds(speed),
                        event -> {
                            playCustomSoundFX(weapon_player);
                            transition.play();
                            attack_ball.setVisible(true);

                            transition.setOnFinished((ActionEvent e) -> {
                                attack_ball.setVisible(false);

                                if ( monster.killMonster(player) ) {
                                    playCustomSoundFX(monster_player);
                                    monster.dieMonster(player, playerStock);
                                    changeMonsterDataOnSpawn();
                                }
                                else changeMonsterDataAfterAttack();
                            });

                        }
                )
        );
        speedTimer.setCycleCount(Animation.INDEFINITE);
        speedTimer.play();
    }

    //###################################  БЛОК ИЗМЕНЕНИЯ ИНФОРМАЦИИ ОБЪЕКТОВ  #####################################

    private void changeMonsterDataAfterAttack() {
        monster_status.setText("Stage " + monster.getStage() + " "+ monster.getMonstername() + " "+ monster.getHealthAmount() + " HP");
        double multiplier = monster.getHealthAmount() / (double)monster.getMaxHealth();
        health_bar.setFitWidth(multiplier*400);
    }

    private void changeMonsterDataOnSpawn() {
        changeMonsterDataAfterAttack();
        health_bar.setFitWidth(400);
        String skinname = monster.getSkinname();
        Image monster = new Image("@../../Materials/BattleGround/Monsters/" + skinname);
        monster_skin.setImage(monster);
    }

    private void changeAbilityDesc(Text panel, String text) {
        panel.setText(text);
    }

    private void warningUpgradeMessage(int warn, String message) {
        switch (warn) {
            case 0:
                changeAbilityDesc(ability_text, message);
                break;
            case 1:
                changeAbilityDesc(ability_text, "\n\n\n           Reached\n              max\n             level!");
                break;
            case 2:
                changeAbilityDesc(ability_text, "\n\n\n             Not\n          enough\n         essences!");
                break;
        }
    }

    //#################################  БЛОК МЕТОДОВ ПРИ ВЫЗОВЕ КНОПОК АЛХИМИКА  ####################################

    private void showEssInStock(ImageView st) {
        info_sign.setVisible(true);
        info_text.setVisible(true);

        if (storm_ess.equals(st)) {
            info_text.setText("Storm Ess (" + playerStock.getStormEssenceAmount() + ")");
        }
        else if (earth_ess.equals(st)) {
            info_text.setText("Earth Ess (" + playerStock.getEarthEssenceAmount() + ")");
        }
        else if (energy_ess.equals(st)) {
            info_text.setText("Energy Ess (" + playerStock.getEnergyEssenceAmount() + ")");
        }
        else if (sun_ess.equals(st)) {
            info_text.setText("Sun Ess (" + playerStock.getSunEssenceAmount() + ")");
        }
        else if (void_ess.equals(st)) {
            info_text.setText("Void Ess (" + playerStock.getVoidEssenceAmount() + ")");
        }
    }

    private void hideEssInStock() {
        info_text.setVisible(false);
        info_sign.setVisible(false);
    }

    private void bubblesOff() {
        left_bubble_1.setVisible(false); right_bubble_6.setVisible(false);
        left_bubble_2.setVisible(false); right_bubble_7.setVisible(false);
        left_bubble_3.setVisible(false); right_bubble_8.setVisible(false);
        left_bubble_4.setVisible(false); right_bubble_9.setVisible(false);
        left_bubble_5.setVisible(false); right_bubble_10.setVisible(false);
    }

    private void setBubbles(int progress) {
        for (int i = 0; i < progress; i ++) {
            bubbles_list.get(i).setVisible(true);
        }
    }

    @FXML
    private void clickOnCauldron() {
        alchemistAnimation();
        playCustomSoundFX(alch_player);

        player.clickOnCauldron(playerStock);
        if ( player.getProgress() == 0) bubblesOff();

        int prog = (int)((player.getProgress() / player.getAlchSpeed()) * 10);
        setBubbles(prog);
    }

    @FXML
    private void essSunEntered() {
        showEssInStock(sun_ess);
    }

    @FXML
    private void essStormEntered() {
        showEssInStock(storm_ess);
    }

    @FXML
    private void essEarthEntered() {
        showEssInStock(earth_ess);
    }

    @FXML
    private void essEnergyEntered() {
        showEssInStock(energy_ess);
    }

    @FXML
    private void essVoidEntered() {
        showEssInStock(void_ess);
    }

    @FXML
    private void essExited() {
        hideEssInStock();
    }


    //#################################  БЛОК МЕТОДОВ ПРИ ВЫЗОВЕ КНОПОК АЛТАРЯ  ####################################

    private void rune_lightOff() {
        rune_1.setVisible(false); rune_2.setVisible(false); rune_3.setVisible(false);
        rune_4.setVisible(false); rune_5.setVisible(false); rune_6.setVisible(false);
        rune_7.setVisible(false); rune_8.setVisible(false);

        upg_dec_common_light_1.setVisible(false); upg_dec_common_1.setVisible(true);
        upg_dmg_common_light_2.setVisible(false); upg_dmg_common_2.setVisible(true);
        upg_spdm_common_light_3.setVisible(false); upg_spdm_common_3.setVisible(true);
        upg_inc_common_light_4.setVisible(false); upg_inc_common_4.setVisible(true);
        upg_spdp_common_light_5.setVisible(false); upg_spdp_common_5.setVisible(true);
    }

    private void pressOff_Upg(AnchorPane upg_press_light, AnchorPane upg_common_light,
                                   AnchorPane rune1, AnchorPane rune2, AnchorPane rune3) {
        if( upg_press_light.isVisible() ) {
            rune_lightOff();
            rune1.setVisible(true); rune2.setVisible(true); rune3.setVisible(true);
            upg_press_light.setVisible(false);
            upg_common_light.setVisible(true);
        }
        if( !stone_activate.isVisible() ) stone_activate.setVisible(true);
    }

    private void pressOn_Upg(AnchorPane upg_press_light, AnchorPane upg_common_light, AnchorPane upg_common,
                                  AnchorPane rune1, AnchorPane rune2, AnchorPane rune3) {
        stone_activate.setVisible(false);
        if( !upg_common_light.isVisible() ) upg_common.setVisible(false);
        else upg_common_light.setVisible(false);

        upg_press_light.setVisible(true);
        rune_lightOff();

    }

    @FXML
    private void pressOn_1() { // 1 3 5
        playCustomSoundFX(upgrade_player);
        int result = 0;
        if( upg_dec_common_light_1.isVisible()) result = playerAltar.buyCostReduceUpg(playerStock);
        String message = "             Level: " + playerAltar.getCostReduceLevel() +
                "\n\nThese upgrade \nreduce the cost of \nother upgrades. \n\n " +
                "                " + playerAltar.getCostReduceCost() + " Earth \n                 Essence";
        warningUpgradeMessage(result, message);
        pressOn_Upg(upg_dec_press_light_1, upg_dec_common_light_1, upg_dec_common_1, rune_1, rune_3, rune_5);
    }

    @FXML
    private void pressOn_2() { // 2 4 7
        playCustomSoundFX(upgrade_player);
        int result = 0;
        if( upg_dmg_common_light_2.isVisible()) result = playerAltar.buyWeaponDamageUpg(playerStock, player);
        String message = "             Level: " + playerAltar.getWeaponDamageLevel() +
                "\n\nThese upgrade \nincreases the damage \nof your weapon. \n\n " +
                "                " + playerAltar.getWeaponDamageCost() + " Void \n                 Essence";
        warningUpgradeMessage(result, message);
        pressOn_Upg(upg_dmg_press_light_2, upg_dmg_common_light_2, upg_dmg_common_2, rune_2, rune_4, rune_7);
    }

    @FXML
    private void pressOn_3() { // 1 5 8
        playCustomSoundFX(upgrade_player);
        int result = 0;
        if( upg_spdm_common_light_3.isVisible()) {
            result = playerAltar.buyWeaponSpeedUpg(playerStock, player);
            changeSpeedAnimation(player.getWeaponSpeed());
        }
        String message = "             Level: " + playerAltar.getWeaponSpeedLevel() +
                "\n\nThese upgrade \nincreases the speed \nof your weapon. \n\n " +
                "                " + playerAltar.getWeaponSpeedCost() + " Storm \n                 Essence";
        warningUpgradeMessage(result, message);
        pressOn_Upg(upg_spdm_press_light_3, upg_spdm_common_light_3, upg_spdm_common_3, rune_1, rune_5, rune_8);
    }

    @FXML
    private void pressOn_4() { // 3 6 8
        playCustomSoundFX(upgrade_player);
        int result = 0;
        if( upg_inc_common_light_4.isVisible()) result = playerAltar.buyAlchGetEssenceUpg(playerStock, player);
        String message = "             Level: " + playerAltar.getAlchGetEssenceLevel() +
                "\n\nThese upgrade \nincreases production \nof essences. \n\n " +
                "                " + playerAltar.getGetAlchGetEssenceCost() + " Sun \n                 Essence";
        warningUpgradeMessage(result, message);
        pressOn_Upg(upg_inc_press_light_4, upg_inc_common_light_4, upg_inc_common_4, rune_3, rune_6, rune_8);
    }

    @FXML
    private void pressOn_5() { // 2 6 7
        playCustomSoundFX(upgrade_player);
        int result = 0;
        if( upg_spdp_common_light_5.isVisible()) result = playerAltar.buyAlchSpeedUpg(playerStock, player);
        String message = "             Level: " + playerAltar.getAlchSpeedLevel() +
                "\n\nThese upgrade \nincreases essence \nextraction speed. \n\n " +
                "                " + playerAltar.getAlchSpeedCost() + " Energy \n                 Essence";
        warningUpgradeMessage(result, message);
        pressOn_Upg(upg_spdp_press_light_5, upg_spdp_common_light_5, upg_spdp_common_5, rune_2, rune_6, rune_7);
    }

    @FXML
    private void pressOff_1() {
        pressOff_Upg(upg_dec_press_light_1, upg_dec_common_light_1, rune_1, rune_3, rune_5);
    }

    @FXML
    private void pressOff_2() {
        pressOff_Upg(upg_dmg_press_light_2, upg_dmg_common_light_2, rune_2, rune_4, rune_7);
    }

    @FXML
    private void pressOff_3() {
        pressOff_Upg(upg_spdm_press_light_3, upg_spdm_common_light_3, rune_1, rune_5, rune_8);
    }

    @FXML
    private void pressOff_4() {
        pressOff_Upg(upg_inc_press_light_4, upg_inc_common_light_4, rune_3, rune_6, rune_8);
    }

    @FXML
    private void pressOff_5() {
        pressOff_Upg(upg_spdp_press_light_5, upg_spdp_common_light_5, rune_2, rune_6, rune_7);
    }
}