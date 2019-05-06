package Controllers;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.ListView;

import util.Artist;
import util.ControllerInterface;
import util.FileSystem;


public class ArtistController extends ControllerInterface implements Initializable{


    @FXML
    private ListView<String> artistList;
    public static Artist selectedArtist;
    
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
    			getMainController().setView("ArtistSub");

    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	});
    	
    }
    
    public Artist getselectedArtist() {
    	return selectedArtist;
    }
    

    
}
