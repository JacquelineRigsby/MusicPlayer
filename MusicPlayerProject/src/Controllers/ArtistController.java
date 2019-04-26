package Controllers;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import util.Album;
import util.Artist;
import util.FileSystem;
import util.Song;

public class ArtistController {

    @FXML
    private ListView<Artist> artistList;
    private Song selectedSong;
    private Album selectedAlbum;
    private Artist selectedArtist;
    
    public FileSystem file = new FileSystem();
    
    public void initialize() {
    	ObservableList<Artist> artists = file.getArtists();
    	artistList.setItems(artists);
    	/*
    	int limit = artists.size();
    	for(int i=0; i<artists.size(); i++) {
    		String artist = artists.get(i).toString();
    		System.out.println(artist);
    		artistList.getItems().add(artist);
    	}
    	*/
    	artistList.setOnMouseClicked(event -> {

             if (event.getClickCount() == 2) {
            	 selectedArtist=artistList.getSelectionModel().getSelectedItem();
            	 /*
                 ObservableList<Song> songs = FXCollections.observableArrayList();
                 ObservableList<Album> albums = FXCollections.observableArrayList();
                 for (Album album : selectedArtist.getAlbums()) {
                     albums.add(album);
                     songs.addAll(album.getSongs());
                 }
                 */
                try {
					MainController main = new MainController();
					System.out.println("Artist Click");
					//main.setView("ArtistSub");
					main.setSubView();
					
					
					
				} catch (Exception e) {
					
				}
                 
                 
             }
    	});
    }
    
    
    
}
