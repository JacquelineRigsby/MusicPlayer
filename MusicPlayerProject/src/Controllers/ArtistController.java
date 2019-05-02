package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.Album;
import util.Artist;
import util.ControllerInterface;
import util.FileSystem;
import util.Song;

public class ArtistController extends ControllerInterface implements Initializable{

    @FXML
    private ListView<String> artistList;
    private Song selectedSong;
    private Album selectedAlbum;
    private Artist selectedArtist;
    
    private FileSystem file = new FileSystem();

    
    public void initialize(URL location, ResourceBundle resources) {
    	//System.out.println("WOOO");
    	
    	ObservableList<Artist> artists = FXCollections.observableArrayList();
    	ObservableList<String> artistTitle =FXCollections.observableArrayList();
    	for(Artist artist: file.getArtists()) {
    		artists.add(artist);
    		artistTitle.add(artist.getTitle());
    	}
    	Collections.sort(artistTitle);
    	artistList.setItems(artistTitle);
    	
    	artistList.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
            		for(int i=0; i<artistTitle.size(); i++) {
                   		if(artistList.getSelectionModel().getSelectedItem().equals(artists.get(i).getTitle())) {
                   			selectedArtist=artists.get(i);
                   		}
                   	}
            	
            	 /*
                 ObservableList<Song> songs = FXCollections.observableArrayList();
                 ObservableList<Album> albums = FXCollections.observableArrayList();
                 for (Album album : selectedArtist.getAlbums()) {
                     albums.add(album);
                     songs.addAll(album.getSongs());
                 }
                 */
                try {
					setview();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
                 
                 event.consume();
             }
    		});

    }
    
    public void setview() {
    	Platform.runLater(() -> {
    		try {
        		//Node scene = FXMLLoader.load(getClass().getResource("ArtistSub.fxml"));
        		getMainController().setView("ArtistSub");

    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	});
    	
    }
    

    
}
