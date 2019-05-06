package Controllers;

import java.net.URL;

import java.util.Collections;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.Album;
import util.Artist;
import util.ControllerInterface;
import util.FileSystem;
import util.Song;

public class ArtistSubController extends ControllerInterface implements Initializable {

    @FXML
    private Label artistName;

    @FXML
    private TableView<Song> songTable = new TableView<Song>();

    @FXML
    private TableColumn<Song, String> songColumn;

    @FXML
    private TableColumn<Song, Integer> lengthColumn;

    @FXML
    private ListView<String> albumList;
    
    private Artist artist;
    private Album selectedAlbum;
    
    FileSystem file = new FileSystem();
    Main main = new Main();


	@Override
	public void initialize(URL location, ResourceBundle resources) {

			artist = ArtistController.selectedArtist;
		 	
			ObservableList<Album> albums = FXCollections.observableArrayList();
			ObservableList<String> albumTitle = FXCollections.observableArrayList();
			
			
			for(Album album: artist.getAlbums()) {
				albums.add(album);
				albumTitle.add(album.getTitle());
			}
			Collections.sort(albumTitle);
			albumList.setItems(albumTitle);
			artistName.setText(artist.getTitle());
			
			songColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
	        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
			
	    	albumList.setOnMouseClicked(event -> {

	            if (event.getClickCount() == 2) {
	            	for(int i=0; i<albumTitle.size(); i++) {
	               		if(albumList.getSelectionModel().getSelectedItem().equals(albums.get(i).getTitle())) {
	               			selectedAlbum=albums.get(i);
	               		}
	               	}
	               try {
	            	   showSongs(selectedAlbum);

						
						
					} catch (Exception e) {
						
					}
	                
	                
	            }
	   	});
	    	songTable.setOnMouseClicked( event -> {
	    		   if( event.getClickCount() == 2 ) {
	    		      try {
	    		    	  Platform.runLater(new Runnable() {

							@Override
							public void run() {
			    		    	  //System.out.println(songTable.getSelectionModel().getSelectedItem().getTitle());
			    		    	  getMainController().addToQueueList(songTable.getSelectionModel().getSelectedItem().getTitle());
			    		    	  main.playMusic(songTable.getSelectionModel().getSelectedItem().getLocation());
							}
	    		    		  
	    		    	  });

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		   }});
			
			

	}
	
	
	public void showSongs(Album album) {
		ObservableList<Song> songs = FXCollections.observableArrayList();
        songs.addAll(album.getSongs());
        songTable.setItems(songs);
	}
	
	public void setArtist(Artist artist) {
		System.out.println(artist.getTitle());
		this.artist=artist;
	}

    

}
