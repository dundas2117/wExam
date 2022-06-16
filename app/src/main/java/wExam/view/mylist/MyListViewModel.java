package view.mylist;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

import javafx.event.EventHandler;
import javafx.concurrent.WorkerStateEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import model.TagModel;
import model.TagSearchResultModel;
import model.UploadResModel;
import model.ContentModel;
import model.ContentSearchResultModel;
public class MyListViewModel {


   
   

    private ObservableList<ContentModel> list = FXCollections.observableArrayList();
    private StringProperty title = new SimpleStringProperty();
    


    

   
    public ObservableList<ContentModel> listProperty(){
        return list;
    }

   

   

    public void showMsg(String msg){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("");
        alert.setContentText(msg);
        alert.showAndWait().ifPresent(rs -> {
           
        });
    }
   
}
