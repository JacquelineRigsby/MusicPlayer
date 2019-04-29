package Controllers;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.FileSystem;
import util.Song;

public class SongsController implements Initializable{
	
		@FXML
		private TableView<Song> songList = new TableView<Song>();

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
		
		Instant first = Instant.now();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Instant second = Instant.now();
		
		Duration duration = Duration.between(first, second);
		ObservableList<Song> test = FXCollections.observableArrayList(
				new Song(5, "wish", "diplo", "soundcloud",duration, "location"));
		songList.setItems(test);
		
		songList.setOnMouseClicked( event -> {
 		   if( event.getClickCount() == 2 ) {
 		      try {
					//music.playMusic(music.getMusic(songList.getSelectionModel().getSelectedItem().getTitle()));
					System.out.println(songList.getSelectionModel().getSelectedItem().getTitle());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 		   }});
		
		
	}
		//showSongs();
		
		
	
	
	public void showSongs() {
		//ObservableList<Song> songs = FXCollections.observableArrayList();
		//songs.add(song);
		
		Instant first = Instant.now();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Instant second = Instant.now();
		
		Duration duration = Duration.between(first, second);
		ObservableList<Song> test = FXCollections.observableArrayList(
				new Song(5, "wish", "diplo", "soundcloud",duration, "location"));
		songList.setItems(test);
	}

}
