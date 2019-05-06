package Controllers;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

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
import util.ControllerInterface;
import util.FileSystem;

import util.Song;

public class AlbumController extends ControllerInterface implements Initializable{

    @FXML
    private ListView<String> albumList;

    @FXML
    private Label artistName;

    @FXML
    private TableView<Song> songTable;

    @FXML
    private TableColumn<Song, String> songColumn;

    @FXML
    private TableColumn<Song, Integer> lengthColumn;
    

    private Album selectedAlbum;
    
    FileSystem file = new FileSystem();
    Main main = new Main();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Album> albums = FXCollections.observableArrayList();
		ObservableList<String> albumTitle = FXCollections.observableArrayList();
		artistName.setVisible(false);
		for(Album album: file.getAlbums()) {
			albums.add(album);
			albumTitle.add(album.getTitle());
		}
		//file.sortSongs(albumTitle);
		Collections.sort(albumTitle);
		albumList.setItems(albumTitle);
		
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
            	   artistName.setText(selectedAlbum.getTitle());
            	   artistName.setVisible(true);

					
					
				} catch (Exception e) {
					
				}
                
                
            }
   	});
    	songTable.setOnMouseClicked( event -> {
    		   if( event.getClickCount() == 2 ) {
    		      try {
					main.playMusic(songTable.getSelectionModel().getSelectedItem().getLocation());
    		    	getMainController().addToQueueList(songTable.getSelectionModel().getSelectedItem().getTitle());
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
	
	

}
