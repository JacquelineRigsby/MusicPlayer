package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import util.*;

public class MainController implements Initializable{
	 @FXML private ImageView nowPlayingImage;
	 @FXML private Label nowPlayingSong = new Label();
	 @FXML private Label nowPlayingArtist = new Label();
	 @FXML private Button homeButton;
     @FXML private HBox mediaControls; 
     @FXML private SVGPath shuffleButton;  
     @FXML private SVGPath repeatButton;  
     @FXML private SVGPath playButton;  
     @FXML private SVGPath pauseButton;   
     @FXML private SVGPath skipForward;  
     @FXML private Button queueButton;  
     @FXML private Slider timeSlider = new Slider(); 
     @FXML private VBox playlists; 
     @FXML private VBox artists;
     @FXML private VBox albums; 
     @FXML private VBox songs;
     @FXML private TextField searchBar;
     @FXML private HBox subView = new HBox();
     @FXML private ListView<String> queueList;
     @FXML private Pane searchResultView = new Pane();

     private Main main = new Main();
     private FileSystem file = new FileSystem();
     private Node currentScene;
     private boolean showing;
     private String test;
     private ArtistSubController artist = new ArtistSubController();
     private String searchResult;


     public void initialize(URL location, ResourceBundle resources) {
    	 
    	 queueList.setVisible(false);
    	 searchResultView.setVisible(false);
    	 homeButton.setVisible(false);
    	 
    	 timeSlider.setFocusTraversable(false);
     	

         timeSlider.valueProperty().addListener(
             (slider, oldValue, newValue) -> {
                 double current = newValue.doubleValue();
                 if (!timeSlider.isValueChanging()) {

                     int seconds = (int) Math.round(current / 4.0);
                     timeSlider.setValue(seconds * 4);
                     main.seek(seconds);
                 }
             }
         );
         
         fillQueue();
         //nowPlayingSong.setText(main.getNowPlaying().getTitle());
         
         
         
         
         initializeTimeSlider();


     }
     
     public void initializeTimeSlider() {

         Song song = main.getNowPlaying();
         if (song != null) {
             timeSlider.setMin(0);
             timeSlider.setMax(song.getLengthInSeconds() * 4);
             timeSlider.setValue(0);
             timeSlider.setBlockIncrement(1);
         } else {
             timeSlider.setMin(0);
             timeSlider.setMax(1);
             timeSlider.setValue(0);
             timeSlider.setBlockIncrement(1);
         }
     }
     
     public void updateTimeSlider() {
         timeSlider.increment();
     }
     
     
     public void playListsScene(MouseEvent event) {
    	setView("ArtistSub");

    }
    
    public void artistsScene(MouseEvent event) throws Exception {
    	setView("Artists");
    	
    }
    
    public void albumScene(MouseEvent event) {
    	setView("Albums");
    }
    
    public void songsScene(MouseEvent event) {
    	setView("Songs");
    }
    
    
    public void play(MouseEvent event) throws Exception {
    	String song = main.getNowPlaying().getTitle();
    	String artist = main.getNowPlaying().getArtist();

    	String media = main.getMusic(song);
    	try {
    		
			main.playMusic(media);
			setNowPlaying(song, artist);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void pause(MouseEvent event) {
    	pauseButton.requestFocus();
    	main.pauseMusic();
    }
    
    public void setSubView() {
    	try {
    		subView.setVisible(true);
    		BorderPane pane = new BorderPane();
			pane = FXMLLoader.load(getClass().getResource("ArtistSub.fxml"));
			subView.getChildren().setAll(pane);
			//subView.setVisible(true);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
    }
    
    public void setSearchView() {
    	try {
    		Node pane;
    		pane = FXMLLoader.load(getClass().getResource("Search.fxml"));
    		//SearchController search = new SearchController();
    		//search.searchBarText("duumu");
    		homeButton.setVisible(true);
    		subView.getChildren().setAll(pane);
    		System.out.println(subView.getChildren());
    		subView.setVisible(false);
    		subView.setVisible(true);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    @FXML
    void queueClick(MouseEvent event) {
    	queueButton.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {

            long startTime;

            @Override
            public void handle(MouseEvent event) {
           	
                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    startTime = System.currentTimeMillis();
                } else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED) && showing) {
                    if (System.currentTimeMillis() - startTime > 2 * 1000) {
                    	System.out.println("Pressed for at least 2 seconds (" + (System.currentTimeMillis() - startTime) + " milliseconds)");
                        queueList.getItems().removeAll();
                        showing = false;
                    } else
                        System.out.println("Pressed for " + (System.currentTimeMillis() - startTime) + " milliseconds");
                }
            }
        });
    	
    	if(!showing) {
    		queueList.requestFocus();
    		queueList.setVisible(true);
    		showing = true;
    	}

    	else {
    		queueList.setVisible(false);
    		showing = false;
    	}

    }
    
    public void fillQueue() {
    	//get songs right away
    	//option to clear songs
    	//if song is playing, add to queue
    	//if double click on item in queue, move it to front of queue and play immediatly
    	//if item in queue is held for more than 2-3 seconds, remove it
    	
    	ObservableList<String> queue= FXCollections.observableArrayList();
    	for(Song song: file.getSongs()) {
    		queue.add(song.getTitle());
    	}
    	queueList.setItems(queue);
    }
    
    public void setView(String viewName) {    	  
    	Platform.runLater(() -> {
    		try {
       		 
      		  String fileName = viewName.substring(0, 1).toUpperCase() + viewName.substring(1) + ".fxml";
      		  FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
      		  currentScene = loader.load();
      		  //currentScene.toFront();
      		  System.out.println(currentScene);  		  
      		  //homeButton.setVisible(true);
      		  subView.getChildren().setAll(currentScene);
      		  
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
    	});  
    }
    @FXML
    void goHome(MouseEvent event) {
    		Platform.runLater(() -> {
				try {
					Stage stage = main.getStage();
	        		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
	        		Node pane;
					pane = loader.load();
	        		Scene scene = new Scene((Parent) pane);
	    			stage.setScene(scene);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		});

    }
    
    public void addToQueue() {
    	
    }
    
    public void sendText(KeyEvent event) {
    	 switch(event.getCode()){
    	 case ENTER:
    		 try {
    			 if(main.getMusic(searchBar.getText())=="location") {
    				 JOptionPane.showMessageDialog(null,"No songs found!");
    			 }
    			 else {
    				 searchResult=searchBar.getText();
    				 SearchController controller = new SearchController();
    				 controller.setSearchResult(searchBar.getText());
    				 System.out.println(searchResult);
    				 setView("Search");
    				 
    				 //music.playMusic(music.getMusic(searchBar.getText()));
        			 //controller.searchBarText(searchBar.getText());
    			 }

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }
    
    public void setNowPlaying(String song, String artist) {
    	nowPlayingSong.setText(song);
    	nowPlayingArtist.setText(artist);
    }
    
    public void setCurrentScene(Node scene) {
    	this.currentScene=currentScene;
    }
    
    

    

}
