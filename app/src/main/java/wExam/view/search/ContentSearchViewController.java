package view.search;

import core.ViewHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;

public class ContentSearchViewController {

   
    private ViewHandler viewHandler;
    private ContentSearchViewModel contentSearchVM;

    @FXML
    private ListView contentListView;

    
    
   
    public void init(ViewHandler viewHandler, ContentSearchViewModel vm) {
        this.viewHandler = viewHandler;
        this.contentSearchVM = vm;

       
    }

    public void contentSearch(){
        this.contentSearchVM.contentSearch();
    }

  
   
}
