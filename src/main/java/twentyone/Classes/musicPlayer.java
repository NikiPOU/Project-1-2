package twentyone.Classes;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class musicPlayer {
    private String path = "src/main/resources/twentyone/Music/";
    private File file;
    private String fileName;
    private MediaPlayer MP;

    /**
     * Creates a new musicPlayer.
     */
    public musicPlayer(){}

    /**
     * Creates a new musicPlayer with the correct fileName.
     * @param fileName
     */
    public musicPlayer(String fileName){
        this.fileName = fileName;
        file = new File(path + fileName);
    }

    /**
     * Sets the file to play.
     * @param filename
     */
    public void setFile(String filename){
        file = new File(path + filename);
    }

    /**
     * Runs the music file.
     */
    public void run() {
        Media sound = new Media(file.toURI().toString());

        // Create a MediaPlayer object for the audio file
        MP = new MediaPlayer(sound);

        // Play the audio in a separate thread
        Thread audioThread = new Thread(() -> {
            MP.play();
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        audioThread.setDaemon(true);
        audioThread.start();
    }

    /**
     * Stops the musicFile.
     */
    public void stop(){MP.stop();}

    /**
     * Checks if the music Player is running.
     * @return true if running and false if not running
     */
    public boolean isRunning(){
        if(MP.getStatus().equals(MediaPlayer.Status.PLAYING)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the name of the file that is playing.
     * @return the file name
     */
    public String getFileName(){
        return fileName;
    }

    /**
     * Fades the music until you cannot hear it anymore.
     */
    public void fadeOut(){
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(5), new KeyValue(MP.volumeProperty(), 0)));
        timeline.play();
    }

    /**
     * Speeds up the music 0.5 speed.
     */
    public void speedUp(){
        MP.setRate(MP.getRate() + 0.5);
    }

    /**
     * Slows down the music 0.5 speed.
     */
    public void speedDown(){
        MP.setRate(MP.getRate() - 0.5);
    }

}