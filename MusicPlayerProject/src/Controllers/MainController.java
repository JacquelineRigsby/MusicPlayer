package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
     @FXML private ListView<String> queueList = new ListView<String>();
     @FXML private Pane searchResultView = new Pane();

     private static Main main = new Main();
     private static FileSystem file = new FileSystem();
     private static Queue<?> queue = new Queue<>();
     private static Node currentScene;
     private static boolean showing;
     private static boolean cleared = false;
     public static String searchResult;
     ObservableList<String> songList= FXCollections.observableArrayList();
     
     /* This class is used to initialize the GUI. 
      * It also sets up the listeners for events. 
      */
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
     
     /*
      * This initializes the time slider. I am still unsure why the time slider does not update with the song, but it may have to do with a lack of 
      * communication between the mediaplayer and the timeslider. This still is important to make the timeslider accessable so that way we can seek 
      * through a song. 
      */
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
     /*
      * This would update the timeslider. 
      */
     public void updateTimeSlider() {
         timeSlider.increment();
     }
     
     /*
      * When clicked on the playlist box, it will load the playlist scene. 
      * We were unable to make the playlist scene(due to time constrants and focusing on making the data structures work)
      */
     public void playListsScene(MouseEvent event) {
    	//setView("ArtistSub");
    	 JOptionPane.showMessageDialog(null, "Coming soon");

    }
    
     /*
      * This will load the Artist.fxml file when it is clicked. 
      */
    public void artistsScene(MouseEvent event) {
    	setView("Artists");
    	
    }
    
    /*
     * This will load the Albums.fxml file when it is clicked. 
     */
    public void albumScene(MouseEvent event) {
    	setView("Albums");
    }
    /*
     * This will load the Songs.fxml file when it is clicked. 
     */
    public void songsScene(MouseEvent event) {
    	setView("Songs");
    }
    
    //Method if something isn't added yet. 
    public void comingSoon(MouseEvent event) {
    	JOptionPane.showMessageDialog(null, "Coming soon!");
    }
    
    /*
     * When the play button is pressed, it will call the playMusic() method in Main method and play music
     * This also changes the label when a song is played. 
     */
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
    
    /*
     * This calls the pauseMusic() method in main, which pauses the song that is currently played.
     */
    public void pause(MouseEvent event) {
    	pauseButton.requestFocus();
    	main.pauseMusic();
    }
    
    /*
     * This will call the repeat method in main, and loop the current song playing. 
     * This also will change the opacity of the loop button. 
     */
    public void loop(MouseEvent event) {
    	main.repeat(event);
    	if(event.getClickCount() == 1) {
			repeatButton.setOpacity(0.5);
			
		}
		else if(event.getClickCount() == 2) {
			repeatButton.setOpacity(1);
		}
    }
    
    /*
     * This will open the queue menu and deals with all things queue related. 
     */
    @FXML
    void queueClick(MouseEvent event) {
    	//If the queue is empty, this will fill up the queue with a general list of songs. 
    	 if(queueList.getItems().isEmpty() && !cleared){
         	fillQueue();
         }
    	queueButton.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {

            long startTime;
            

            @Override
            public void handle(MouseEvent event) {
           	
                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    startTime = System.currentTimeMillis();
                    //This clears the queue if the button is pressed for more than 1 second and less than 2 seconds. 
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
    	//This makes the queue visible/invisible
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
    //This gets a general list if songs and fills the listview with those items. . 
    public void fillQueue() {

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
    //This adds items to the list view. 
    public void addToQueueList(String title) {
    		queueList.getItems().add(title);
    }
    /*
     * This will change the view of the GUI to one of the other fxml files. 
     * It loads the fxml file, and then loads an instance of that fxml file so data can be passed through the files. 
     */
    public void setView(String viewName) {    	  
    	Platform.runLater(() -> {
    		try {
       		 
      		  String fileName = viewName.substring(0, 1).toUpperCase() + viewName.substring(1) + ".fxml";
      		  FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
      		  currentScene = loader.load();
      		  //This loads an instance of the controller. 
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
    
    //This is used for the go back button. I have to create a whole new scene here since the main fxml file includes the media controls. 
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
    //This gets the results from the search bar and sends it to the searchResult method. 
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
		default:
			break;
    		
    	}
    }
    
    //This changes the label of the song to match with what is currently playing. 
    public void setNowPlaying(String song, String artist) {
    	nowPlayingSong.setText(song);
    	nowPlayingArtist.setText(artist);
    }
    
    //This sends the currentScene to main method. 
    public void setCurrentScene(Node scene) {
    	MainController.currentScene=scene;
    }
    
    
    

    
    

    

}
