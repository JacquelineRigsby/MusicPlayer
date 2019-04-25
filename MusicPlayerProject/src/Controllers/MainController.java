package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import javax.sound.sampled.Clip;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import util.*;

public class MainController {
	 @FXML private ImageView nowPlayingImage;
	 @FXML private Label nowPlayingSong;
	 @FXML private Button homeButton;
     @FXML private HBox mediaControls; 
     @FXML private SVGPath shuffleButton;  
     @FXML private SVGPath repeatButton;  
     @FXML private SVGPath playButton;  
     @FXML private SVGPath pauseButton;   
     @FXML private SVGPath skipForward;  
     @FXML private Button queueButton;  
     @FXML private Slider timeSlider; 
     @FXML private VBox playlists; 
     @FXML private VBox artists;
     @FXML private VBox albums; 
     @FXML private VBox songs;
     @FXML private TextField searchBar;
     @FXML private HBox subView;
     @FXML private ListView<String> queueList;

     private Main main = new Main();
     private MusicPlayer music = new MusicPlayer();
     private FileSystem file = new FileSystem();
     private Node currentScene;
     private boolean showing;


     public void initialize(URL location, ResourceBundle resources) {
    	 queueList.setVisible(false);
    	 queueButton.setVisible(false);
     }
     
     public void playListsScene(MouseEvent event) {
    	System.out.println("Test Text");

    	try {
    		
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    public void artistsScene(MouseEvent event) throws Exception {
    	setView("Artists");
    	//setSubView();
    	
    }
    
    
    public void play(MouseEvent event) throws Exception {
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
    
    public void setSubView() {
    	try {
    		BorderPane pane = new BorderPane();
			pane = FXMLLoader.load(getClass().getResource("ArtistSub.fxml"));
			subView.getChildren().setAll(pane);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
    }
    @FXML
    void queueClick(MouseEvent event) {
    	System.out.println("test");
    	if(!showing) {
    		queueList.setVisible(true);
    		showing = true;
    	}
    	else {
    		queueList.setVisible(false);
    		showing = false;
    	}

    }
    
    public void setView(String viewName) {    	  
    	  try {
    		  if(currentScene == null) {
    			  currentScene = FXMLLoader.load(getClass().getResource("Main.fxml"));
    			  //subView.getChildren().remove(currentScene);
    		  }
    		  subView.getChildren().remove(currentScene);
    		  homeButton.setVisible(true);
    		  String fileName = viewName.substring(0, 1).toUpperCase() + viewName.substring(1) + ".fxml";
    		  Node scene = FXMLLoader.load(getClass().getResource(fileName));
    		  subView.getChildren().setAll(scene);
    		  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    void goHome(MouseEvent event) {
    	try {
    		Stage stage = main.getStage();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
    		Node mainLayout = loader.load();
    		Scene scene = new Scene((Parent) mainLayout);
    		mainLayout.setVisible(true);
			stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

}
