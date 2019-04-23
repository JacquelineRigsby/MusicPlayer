package Controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.Album;
import util.Artist;
import util.FileSystem;
import util.Song;

public class Main extends Application{
	
	private Stage mainStage = new Stage();
	private BorderPane mainLayout;
	private MainController mainController;
	private Scene scene;
	private Song nowPlaying;
	private int nowPlayingIndex;
	private FileSystem file = new FileSystem();
	private ArrayList<Song> getSongList;


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
                nowPlayingIndex = 0;
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

		thread.start();

	}
	
	public void showMainView() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));

		mainLayout = loader.load();
		
        scene = new Scene(mainLayout);
        mainStage.setScene(scene);
        mainStage.show();
	}
	
	public void showSplashScreen() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SplashScreen.fxml"));
		FXMLLoader loader1 = new FXMLLoader(getClass().getResource("Artist.fxml"));
		AnchorPane pane = loader.load();
		Scene scene1 = new Scene(pane);
		mainStage.setScene(scene1);
		mainStage.show();
		//((Pane) scene.getRoot()).getChildren().add(scene1.getRoot());
		
		
		
	}

	
	

}
