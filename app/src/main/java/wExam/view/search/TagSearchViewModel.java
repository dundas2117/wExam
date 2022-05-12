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

public class TagSearchViewModel {


   
    private ISearch search ;

    private ListProperty<String> list = new SimpleListProperty<>();
    private StringProperty request = new SimpleStringProperty();

    public TagSearchViewModel(ISearch search) {
        this.search = search;
        
    }

    public void tagSearch(){
        //System.out.println("request" + this.request.getValue());
        this.search.tagSearch(this.request.getValue(),
        new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                TagSearchResultModel result = search.getTagSearchResult();
                System.out.println(result.getTotal());
                System.out.println(result.getResults().size());

                List<String> tagList = new ArrayList<String>();
                for (TagModel temp : result.getResults()) {
                    tagList.add(temp.getId());
                }

                list.set(FXCollections.observableArrayList(tagList));
            }
        }
        );
    }

    public ListProperty<String>  ListProperty(){
        return list;
    }

    public StringProperty requestProperty() {
        return request;
    }
   
}
