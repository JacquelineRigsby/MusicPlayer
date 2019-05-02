package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

public class SearchController extends ControllerInterface implements Initializable{
	
	//get music based on search result
	//fill that music into an array
	//display that array in a table
	
	@FXML
	private TableView<Song> searchTable;

    @FXML
    private TableColumn<Song, String> songColumn;

    @FXML
    private TableColumn<Song, String> artistColumn;

    @FXML
    private TableColumn<Song, String> albumColumn;

    @FXML
    private TableColumn<Song, Integer> lengthColumn;
    
    private Song song;
    private String searchResult;
    
    FileSystem file = new FileSystem();
    Main main = new Main();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		songColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("title"));
		artistColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("artist"));
		albumColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("artist"));
		lengthColumn.setCellValueFactory(new PropertyValueFactory<Song,Integer>("length"));
		

		ObservableList<Song> songs = FXCollections.observableArrayList();
		for(Song song: file.getSongs()) {
			if(song.getLocation().toLowerCase().contains(MainController.searchResult.toLowerCase())) {
				
				songs.add(song);
			}
		}
		
		searchTable.setItems(songs);
		
		searchTable.setOnMouseClicked( event -> {
 		   if( event.getClickCount() == 2 ) {
 		      try {
					main.playMusic(searchTable.getSelectionModel().getSelectedItem().getLocation());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 		   }});
		
		
	}
	

    

}
