package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import util.Album;
import util.Artist;
import util.FileSystem;
import util.Song;

public class ArtistController implements Initializable{

    @FXML
    private ListView<String> artistList;
    private Song selectedSong;
    private Album selectedAlbum;
    private Artist selectedArtist;
    
    private FileSystem file = new FileSystem();

    
    public void initialize(URL location, ResourceBundle resources) {
    	//System.out.println("WOOO");
    	Platform.runLater(() -> {
    	ObservableList<Artist> artists = FXCollections.observableArrayList();
    	ObservableList<String> artistTitle =FXCollections.observableArrayList();
    	for(Artist artist: file.getArtists()) {
    		artists.add(artist);
    		artistTitle.add(artist.getTitle());
    	}
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
					//setview();
                	MainController main = new MainController();
					System.out.println("Artist Click");
					
		    		 Node scene = FXMLLoader.load(getClass().getResource("ArtistSub.fxml"));
		    		 main.setView("ArtistSub");
		    		 System.out.println("test2");
		    		 
		    		 
					//main.setSubView();
					
					
					
				} catch (Exception e) {
					
				}
                 
                 
             }
    		});
    	});
    }
    

    
    
}
