package Controllers;

import java.io.IOException;

import javax.sound.sampled.Clip;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.shape.SVGPath;
import util.*;

public class MainController {
	 @FXML private ImageView nowPlayingImage;
	 @FXML private Label nowPlayingSong;

     @FXML private HBox mediaControls; 
     @FXML private SVGPath shuffleButton;  
     @FXML private SVGPath repeatButton;  
     @FXML private SVGPath playButton;  
     @FXML private SVGPath pauseButton;   
     @FXML private SVGPath skipForward;  
     @FXML private SVGPath queueButton;  
     @FXML private Slider timeSlider; 
     @FXML private VBox playlists; 
     @FXML private VBox artists;
     @FXML private VBox albums; 
     @FXML private VBox songs;
     @FXML private TextField searchBar;
     @FXML private HBox subView;

     private Main main = new Main();
     private MusicPlayer music = new MusicPlayer();


    
    public void playListsScene(MouseEvent event) {
    	System.out.println("Test Text");

    	try {
    		
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    public void artistsScene(MouseEvent event) throws Exception {
    	VBox scene2 = FXMLLoader.load(getClass().getResource("Artists.fxml"));
    	subView.getChildren().setAll(scene2);
    }
    
    
    public void play(MouseEvent event) {
    	String media = music.getMusic();
    	try {
			music.playMusic(media);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void pause(MouseEvent event) {
    	if(music.isPlaying()) {
    		music.pauseMusic();
    	}
    }

}
