package view.search;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

import javafx.event.EventHandler;
import javafx.concurrent.WorkerStateEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import core.ISearch;
import model.TagModel;
import model.TagSearchResultModel;

public class ContentSearchViewModel {


   
    private ISearch search ;

    private ListProperty<String> list = new SimpleListProperty<>();
    private StringProperty title = new SimpleStringProperty();

    public ContentSearchViewModel(ISearch search) {
        this.search = search;
        
    }

   
    public ListProperty<String>  ListProperty(){
        return list;
    }

    public StringProperty titleProperty() {
        return title;
    }
   
}
