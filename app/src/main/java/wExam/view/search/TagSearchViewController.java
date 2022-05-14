package view.search;

import core.ViewHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.CacheRepo;
import javafx.scene.input.KeyCode;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;

public class TagSearchViewController {

   
    private ViewHandler viewHandler;
    private TagSearchViewModel tagSearchVM;

    @FXML
    private ListView tagListView;

    @FXML
    private TextField tagForSearch;

    @FXML
    private Button searchBtn;

    @FXML
    private CheckBox cacheCB;

    @FXML
    private ProgressIndicator loadingInd;
    
    
   
    public void init(ViewHandler viewHandler, TagSearchViewModel vm) {
        this.viewHandler = viewHandler;
        this.tagSearchVM = vm;

        tagListView.itemsProperty().bind(this.tagSearchVM.ListProperty());
        tagForSearch.textProperty().bindBidirectional(tagSearchVM.requestProperty());
        cacheCB.selectedProperty().bindBidirectional(tagSearchVM.enableCacheProperty());
        loadingInd.visibleProperty().bindBidirectional(tagSearchVM.isLoadingProperty());

        searchBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                searchBtn.fire();
               ev.consume(); 
            }
        });

        tagListView.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {

                String tag = (String)tagListView.getSelectionModel().getSelectedItem();
                //System.out.println("clicked on " + tagListView.getSelectionModel().getSelectedItem());
               this.viewHandler.addContentView(tag,this.cacheCB.selectedProperty().getValue());
                ev.consume(); 
            }
        });

        tagListView.addEventHandler(MouseEvent.MOUSE_CLICKED, ev -> {
           
                String tag = (String)tagListView.getSelectionModel().getSelectedItem();
                //System.out.println("clicked on " + tagListView.getSelectionModel().getSelectedItem());
               this.viewHandler.addContentView(tag,this.cacheCB.selectedProperty().getValue());
                ev.consume(); 

        });
    }

   
    public void onTagSearchButton(){
        this.tagSearchVM.tagSearch();
    }

    public void onClearCache(){
        CacheRepo.clearCache();
    }
   
}
