package Controllers;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.ControllerInterface;
import util.FileSystem;
import util.Song;

public class SongsController extends ControllerInterface implements Initializable{
	
		@FXML
		private TableView<Song> songsList;

	 	@FXML
	    private TableColumn<Song, String> songColumn;

	    @FXML
	    private TableColumn<Song, String> artistColumn;

	    @FXML
	    private TableColumn<Song, String> albumColumn;

	    @FXML
	    private TableColumn<Song, Integer> lengthColumn;
	    
	    FileSystem file = new FileSystem();
	    Main main = new Main();
	    private Song selectedSong;
	    

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		songColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
		albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
		lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
		//System.out.println("test");
		//songList = new 
		//selectedSong = file.getSong("foward");
		
		
		showSongs();
		
		songsList.setOnMouseClicked( event -> {
 		   if( event.getClickCount() == 2 ) {
 		      try {
					main.playMusic(main.getMusic(songsList.getSelectionModel().getSelectedItem().getTitle()));
					//System.out.println(songsList.getSelectionModel().getSelectedItem().getTitle());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 		   }});
		
		
	}
		
		
	
	
	public void showSongs() {
		Platform.runLater(() -> {
			ObservableList<Song> songs = FXCollections.observableArrayList();
			songs.addAll(file.getSongs());
			songsList.setItems(songs);
		});

	}

}
