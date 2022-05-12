package view.main;

import core.ViewHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainViewController {

   
    private ViewHandler viewHandler;
    private MainViewModel mainVM;

    public void init(ViewHandler viewHandler, MainViewModel mainVM) {
        this.viewHandler = viewHandler;
        this.mainVM = mainVM;
     
    }

   
}
