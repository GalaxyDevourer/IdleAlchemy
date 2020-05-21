package MainPack.CustomPlayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class CustomPlayer {
    private MediaPlayer mediaPlayer;

    public CustomPlayer(String file) {
        Media sound = new Media(new File(file).toURI().toString());
        this.mediaPlayer = new MediaPlayer(sound);
    }

    public void setRecordSettings (Integer counts) {
        mediaPlayer.setCycleCount(counts);
    }

    public void changeAuto (boolean choice) {
        mediaPlayer.setAutoPlay(choice);
    }

    public MediaPlayer.Status showStatus () {
        return mediaPlayer.getStatus();
    }

    public void volumeSound (double volume) {
        mediaPlayer.setVolume(volume);
    }

    public void playSound () {
        mediaPlayer.play();
    }

    public void pauseSound () {
        mediaPlayer.pause();
    }

    public void stopSound () {
        mediaPlayer.stop();
    }

    public void disposeSound () {
        mediaPlayer.dispose();
    }
}
