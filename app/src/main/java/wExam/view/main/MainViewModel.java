package view.main;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ContentModel;


public class MainViewModel {


    private ObservableList<ContentModel> myList = FXCollections.observableArrayList();

    public MainViewModel() {
        
    }

    public ObservableList<ContentModel> MyListProperty(){
        
        return myList;
    }

    public void addToMyList(ContentModel data){
        this.myList.add(data);
    }

    public void removeFromMyList(int index){
        this.myList.remove(index);
    }

    

   
}
