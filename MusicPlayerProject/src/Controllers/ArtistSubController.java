package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ArtistSubController {

    @FXML
    private Label artistName;

    @FXML
    private TableView<?> songTable;

    @FXML
    private TableColumn<?, ?> songColumn;

    @FXML
    private TableColumn<?, ?> lengthColumn;

    @FXML
    private ListView<?> albumList;

}
