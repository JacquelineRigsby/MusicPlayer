package Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.Album;
import util.Artist;
import util.FileSystem;
import util.Song;

public class Main extends Application{
	
	private static Stage mainStage = new Stage();
	private static Node mainLayout;
	private static Scene scene;
	private static Song nowPlaying;
	private static FileSystem file = new FileSystem();
	private static ArrayList<Song> getSongList;
	MainController cont = new MainController();
	private static MediaPlayer mediaPlayer;
	private static Song currentsong;
	private static Duration duration;
	/*
	 * Because of how JavaFX works, main is used to just launch the application.
	 * This method is part of the JavaFX library. 
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * Start is used as an initilization method. It sets up everything needed for the GUI to work properally.	 
	 */
	@Override
	public void start(Stage stage) throws Exception {
        stage.setTitle("Music Player");
        showSplashScreen();
        //This sets up the loading screen and makes sure there is a default directory to pull songs from
        Thread thread = new Thread(() -> {
        	
        	try {
        		//Sets Default Directory
        		if(!FileSystem.isDefaultDirectory()) {
					JOptionPane.showMessageDialog(null, "There is no default directory. Please choose one.");
				}
        		String path =file.getDefaultDirectory();
				file.createLibrary(path);
        		
        		//Gets all the songs from the library
        		file.getAlbums();
        		file.getArtists();
        		file.getSongs();
        		
        		//This is a list of all the songs. 
        		getSongList = file.loadPlayingList();
        		
        		if(getSongList.isEmpty()) {
        			Artist artist = file.getArtists().get(0);
        			
        			for(Album album : artist.getAlbums()) {
        				getSongList.addAll(album.getSongs());
        			}
        			
        		}
        		nowPlaying = getSongList.get(0);
                nowPlaying.setPlaying(true);
                //queue.addToBack(nowPlaying.getTitle());
                
   
                
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        	//This is used to run the actual GUI after it is initialized. 
        	Platform.runLater(() -> {
				try {
					showMainView();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
        });
        //This checks to see if there are any errors that wouldn't be caught in the stack trace. 
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println(thread + "throws exception: " + e);
				
			}
		});
		thread.start();
		

	}
	
	//This initalizes the stage and loads the main view. 
	public void showMainView() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		//cont = new MainController();
		mainLayout = loader.load();
		cont.setCurrentScene(mainLayout);
        scene = new Scene((Parent) mainLayout);
        mainStage.setScene(scene);
        mainStage.show();
	}
	//This loads the splash screen. 
	public void showSplashScreen() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SplashScreen.fxml"));
		AnchorPane pane = loader.load();
		Scene scene1 = new Scene(pane);
		mainStage.setScene(scene1);
		mainStage.show();

		
		
		
	}
	
	public Stage getStage() {
		return mainStage;
	}
	
	public Song getNowPlaying() {
		return nowPlaying;
	}
	public void setNowPlaying(Song nowPlaying) {
		Main.nowPlaying = nowPlaying;
	}
	/*
	 * This is the main method for playing music. 
	 * It creates a new MediaPlayer which is a Java built-in library, and then plays the music. 
	 */
	public void playMusic(String musicLocation){
		try {
			Media media = loadMusic(musicLocation);
			//Check if a song is already playing. 
			if(!isPlaying()) {
				//cont = new MainController();
				mediaPlayer = new MediaPlayer(media);
				
				if(currentsong != file.getSongLocation(musicLocation)) {
					duration =null;
				}
				//sets song from the given location. 
				currentsong = file.getSongLocation(musicLocation);

				//Sets start time.  
				if(duration != null) {
					mediaPlayer.setStartTime(duration);
				}
				mediaPlayer.play();
				
				
				setNowPlaying(currentsong);
				currentsong.setPlaying(true);
				//This sets a check at the end of the song to see if the loop is enabled. 
				mediaPlayer.setOnEndOfMedia(() -> {
					if(mediaPlayer.getCycleCount() != MediaPlayer.INDEFINITE) {
						mediaPlayer.dispose();
						//queue.dequeue();
					}
			
				});
			}
			//If media isn't found, it says song cannot be found. T
			//This is caused sometimes due to the way the mediaplayer reads files.
			//It has a hard time reading special characters. 
		} catch (MediaException e) {
			JOptionPane.showMessageDialog(null, "This song could not be found.");
		}
		
		
		
	}
	
	//This will traverse through the library.xml file and try and find the song. 
	public String getMusic(String song) throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		FileInputStream is = new FileInputStream(new File("lib/library.xml"));
		XMLStreamReader reader = factory.createXMLStreamReader(is, "UTF-8");
		
		String element; 
		boolean found = false;
		String path = "";
		 try {
			while(reader.hasNext() && !found) {
			     reader.next();
			     if (reader.isWhiteSpace()) {
			         continue;
			     } else if (reader.isStartElement()) {
			         element = reader.getName().getLocalPart();
			         if(element.equals("location")) {
			        	path = element;

			         }
			         if(element.equals("title")) {
			        	 path = element;
			         }
			      
			     } else if(reader.isCharacters() && path.equals("location")) {
			    	 if(reader.getText().toLowerCase().contains(song.toLowerCase())) {
			    		 path=reader.getText();
			    		 found = true;
			    	 }
			    	 
			    	 
			    	 
			     }else if(reader.isEndElement() && reader.getName().getLocalPart().equals("songs")) {
			    	 reader.close();
			    	 break;
			     }
			 }
		} catch (XMLStreamException e) {
			
		}
		return path;
	}
	
	//This loads the song into the musicplayer
	public Media loadMusic(String MusicLocation) {
		Media media = new Media(Paths.get(MusicLocation).toUri().toString());
		return media;
	}
	
	//Checks if a song is playing.
	public boolean isPlaying() {
		return mediaPlayer != null && MediaPlayer.Status.PLAYING.equals(mediaPlayer.getStatus());
	}
	
	//Pauses the music. 
	public void pauseMusic() {
		if(isPlaying()) {
			duration = mediaPlayer.getCurrentTime();
			currentsong.setPlaying(false);
			mediaPlayer.pause();
		}
	}
	
	//This is used for the time slider. It will get the duration chosen and set the duration to that timer
	public  void seek(int seconds) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(new Duration(seconds * 1000));
            
        }
    }
	
	public void repeat(MouseEvent event) {
		if(event.getClickCount() == 1) {
			if(mediaPlayer != null) {
				mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			}
		}
		else if(event.getClickCount() == 2) {
			mediaPlayer.setCycleCount(0);
		}
		
	}
	

	

}
