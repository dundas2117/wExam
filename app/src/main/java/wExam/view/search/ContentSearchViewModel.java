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
    private String tagId;

    private ListProperty<String> list = new SimpleListProperty<>();
    private StringProperty title = new SimpleStringProperty();

    public ContentSearchViewModel(ISearch search, String tagId) {
        this.search = search;
        this.tagId = tagId;
    }

   
    public ListProperty<String>  ListProperty(){
        return list;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void contentSearch(){
        //System.out.println("request" + this.request.getValue());
        this.search.contentSearch(this.tagId,
        new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                ContentSearchResultModel result = search.getContentSearchResult();
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
   
}
