package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.FileSystem;
import util.Song;

public class SearchController implements Initializable{
	
	//get music based on search result
	//fill that music into an array
	//display that array in a table
	
	@FXML
	private TableView<Song> searchTable = new TableView<Song>();

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
    MainController main = new MainController();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		songColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("location"));
		artistColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("artist"));
		albumColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("artist"));
		lengthColumn.setCellValueFactory(new PropertyValueFactory<Song,Integer>("length"));
		
		
		System.out.println(searchResult);
		
	}
	
	public void searchBarText(String search) {
		ObservableList<Song> songs = file.getSongs();
		for(int i=0; i<songs.size(); i++) {
			if(songs.get(i).getLocation().toLowerCase().contains(search)) {
				//System.out.println(songs.get(i).getLocation());
				//System.out.println(songs.get(i));
				searchTable.setItems(songs);
			}
		}
		searchTable.setItems(songs);
		
		
	}
	
	public void setSearchResult(String searchResult) {
		this.searchResult=searchResult;
	}
    
    

}
