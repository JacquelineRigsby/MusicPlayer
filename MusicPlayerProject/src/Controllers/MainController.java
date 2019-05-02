package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import util.*;

public class MainController implements Initializable{
	 @FXML private ImageView nowPlayingImage;
	 @FXML private Label nowPlayingSong = new Label();
	 @FXML private Label nowPlayingArtist = new Label();
	 @FXML private Button homeButton = new Button();
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
     @FXML private ListView<String> queueList = new ListView();
     @FXML private Pane searchResultView = new Pane();

     private static Main main = new Main();
     private static FileSystem file = new FileSystem();
     private static Queue queue = new Queue();
     private static Node currentScene;
     private static boolean showing;
     private static boolean cleared = false;
     private static ArtistSubController artist = new ArtistSubController();
     public static String searchResult;
     ObservableList<String> songList= FXCollections.observableArrayList();


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
         
         //nowPlayingSong.setText(main.getNowPlaying().getTitle());
         
         queueList.setOnMouseClicked(event -> {
        	 if( event.getClickCount() == 2 ) {
    		      try {
    		    	 
   					main.playMusic(main.getMusic(queueList.getSelectionModel().getSelectedItem()));
   					
   				} catch (Exception e) {
   					// TODO Auto-generated catch block
   					e.printStackTrace();
   				}
        	 }
         });
         
         
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
    
    public void artistsScene(MouseEvent event) {
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
    
    public void loop(MouseEvent event) {
    	main.repeat(event);
    	if(event.getClickCount() == 1) {
			repeatButton.setOpacity(0.5);
			
		}
		else if(event.getClickCount() == 2) {
			repeatButton.setOpacity(1);
		}
    }
    
    @FXML
    void queueClick(MouseEvent event) {
    	 if(queueList.getItems().isEmpty() && !cleared){
         	fillQueue();
         }
    	queueButton.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {

            long startTime;
            

            @Override
            public void handle(MouseEvent event) {
           	
                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    startTime = System.currentTimeMillis();
                } else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED) && showing) {
                    if (System.currentTimeMillis() - startTime > 1 * 1000 && System.currentTimeMillis() - startTime < 2 * 1000) {
                    	//System.out.println(queue.getFront().getTitle());
                        queueList.getItems().clear();
                        cleared = true;
                        showing = false;
                    } else if (System.currentTimeMillis() - startTime > 3 * 1000) {
                    	//System.out.println("Pressed for " + (System.currentTimeMillis() - startTime) + " milliseconds");
                    	updateQueue();
                    } else {
                    	//System.out.println("Pressed for " + (System.currentTimeMillis() - startTime) + " milliseconds");
                    }
                       
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
    	
    	;
    	for(Song song: file.getSongs()) {
    		songList.add(song.getTitle());
    		queue.addToFront(song.getTitle());
    	}
    	//System.out.println(queue.getFront().getTitle());
    	queueList.setItems(songList);
    }
    
    public void updateQueue() {
    	
    		songList.add(queue.Next());
    		System.out.println(queue.Next());
    
    	queueList.setItems(songList);
    }
    
    public void addToQueueList(String title) {
    		queueList.getItems().add(title);
    }
    
    public void setView(String viewName) {    	  
    	Platform.runLater(() -> {
    		try {
       		 
      		  String fileName = viewName.substring(0, 1).toUpperCase() + viewName.substring(1) + ".fxml";
      		  FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
      		  currentScene = loader.load();
      		((ControllerInterface) loader.getController()).setMainController(this);
      		  
      		  //currentScene.toFront();
      		  //System.out.println(currentScene);  
      		  if(!homeButton.isVisible()) {
      			 homeButton.setVisible(true);
      		  }
      		  
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
    
    public void sendText(KeyEvent event) {
    	 switch(event.getCode()){
    	 case ENTER:
    		 try {
    			 if(main.getMusic(searchBar.getText())=="location") {
    				 JOptionPane.showMessageDialog(null,"No songs found!");
    			 }
    			 else {
    				 setView("Search");
    				 searchResult = searchBar.getText();
    				 
    				 
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
    
    public String getsearchResult() {
    	return searchResult;
    }
    
    public HBox getSubView() {
    	return subView;
    }
    

    
    

    

}
