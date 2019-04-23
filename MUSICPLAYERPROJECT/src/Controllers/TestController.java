package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class TestController {

    @FXML
    private Button button1;
    
    private Main main = new Main();

    @FXML
    void goBack(MouseEvent event) throws Exception {
    	main.showMainView();

    }

}
