package Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
	private static MainController mainController;
	private static Scene scene;
	private static Song nowPlaying;
	private static FileSystem file = new FileSystem();
	private static ArrayList<Song> getSongList;
	MainController cont;
	private static MediaPlayer mediaPlayer;
	private static Song currentsong;
	private static boolean playing = false;
	private static Duration duration;
	private static int timerCounter;



	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
        stage.setTitle("Music Player");
        showSplashScreen();
        Thread thread = new Thread(() -> {
        	
        	try {
        		//Sets Default Directory
        		if(!file.isDefaultDirectory()) {
					JOptionPane.showMessageDialog(null, "There is no default directory. Please choose one.");
					String path =file.getDefaultDirectory();
					file.createLibrary(path);
				}
        		
        		
        		file.getAlbums();
        		file.getArtists();
        		file.getSongs();
        		
        		getSongList = file.loadPlayingList();
        		
        		if(getSongList.isEmpty()) {
        			Artist artist = file.getArtists().get(0);
        			
        			for(Album album : artist.getAlbums()) {
        				getSongList.addAll(album.getSongs());
        			}
        			
        		}
        		nowPlaying = getSongList.get(0);
                nowPlaying.setPlaying(true);
   
                
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        	
        	Platform.runLater(() -> {
				try {
					showMainView();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
        });
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println(thread + "throws exception: " + e);
				
			}
		});
		thread.start();
		

	}
	
	public void showMainView() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		cont = new MainController();
		mainLayout = loader.load();
		cont.setCurrentScene(mainLayout);
        scene = new Scene((Parent) mainLayout);
        mainStage.setScene(scene);
        mainStage.show();
	}
	
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
		this.nowPlaying = nowPlaying;
	}
	public void playMusic(String musicLocation){
		try {
			Media media = loadMusic(musicLocation);
			if(!isPlaying()) {
				//mainController.setNowPlaying(media.toString());
				cont = new MainController();
				mediaPlayer = new MediaPlayer(media);
				mainController = new MainController();
				if(currentsong != file.getSongLocation(musicLocation)) {
					duration =null;
				}
				currentsong = file.getSongLocation(musicLocation);
				String title = currentsong.getTitle();
				String artist = currentsong.getArtist();
				cont.setNowPlaying(title, artist);
				if(duration != null) {
					mediaPlayer.setStartTime(duration);
				}
				mediaPlayer.play();
				setNowPlaying(currentsong);
				currentsong.setPlaying(true);
				playing = true;
			}
		} catch (MediaException e) {
			JOptionPane.showMessageDialog(null, "This song could not be found.");
		}
		
		
		
	}
	
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
	
	public Media loadMusic(String MusicLocation) {
		Media media = new Media(Paths.get(MusicLocation).toUri().toString());
		return media;
	}
	
	public boolean isPlaying() {
		return mediaPlayer != null && MediaPlayer.Status.PLAYING.equals(mediaPlayer.getStatus());
	}
	
	public void pauseMusic() {
		if(isPlaying()) {
			duration = mediaPlayer.getCurrentTime();
			mediaPlayer.pause();
		}
	}
	
	public  void seek(int seconds) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(new Duration(seconds * 1000));
            timerCounter = seconds * 4;
            
        }
    }

	
	

}
