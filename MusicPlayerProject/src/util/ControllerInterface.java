package util;

import Controllers.MainController;

public abstract class ControllerInterface {
	MainController main;
	
	public void setMainController(MainController controller) {
		main = controller;
	}
	public MainController getMainController() {
		return main;
	}

}
