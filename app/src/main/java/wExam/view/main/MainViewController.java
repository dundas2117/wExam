package view.main;

import core.ViewHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.Node;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

public class MainViewController {
    @FXML
    private Tab tagSearchTab;

    @FXML
    private TabPane mainTabPane;
   
    private ViewHandler viewHandler;
    private MainViewModel mainVM;

    public void init(ViewHandler viewHandler, MainViewModel mainVM) {
        this.viewHandler = viewHandler;
        this.mainVM = mainVM;

        
    }

    public void loadSearchTab(Node node){
        tagSearchTab.setContent(node);
    }

    public void addContentTab(String tag, Node node){
        Tab tab = new Tab("Tag - " + tag);
        tab.setContent(node);
        mainTabPane.getTabs().add(tab);
    }
   
}
