package view.search;

import java.util.ArrayList;
import java.util.List;


import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

import javafx.event.EventHandler;
import javafx.concurrent.WorkerStateEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import core.ISearch;
import core.ViewHandler;
import model.AI;
import model.AIIntent;
import model.AIEntities;
import model.AIEntity;
import model.AIResultModel;
import model.TagModel;
import model.TagSearchResultModel;

public class TagSearchViewModel {


   
    private ISearch search ;

    private ListProperty<String> list = new SimpleListProperty<>();
    private StringProperty request = new SimpleStringProperty();
    private BooleanProperty enableCache = new SimpleBooleanProperty(true);
    private BooleanProperty isLoading = new SimpleBooleanProperty(false);
    private StringProperty aiInput = new SimpleStringProperty();

    private AI ai;

    private ViewHandler vh;

    public TagSearchViewModel(ISearch search, AI ai) {
        this.search = search;
        this.ai = ai;    
    }

    public void tagSearch(String request){
        this.isLoading.setValue(true);
        //System.out.println("request" + this.request.getValue());
        this.search.enableCache(this.enableCache.getValue());
        this.search.tagSearch(request,
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
                isLoading.setValue(false);
                
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

    public StringProperty aiInputProperty() {
        return aiInput;
    }

    public BooleanProperty enableCacheProperty(){
        return this.enableCache;
    }
   
    public BooleanProperty isLoadingProperty(){
        return this.isLoading;
    }

    public void setViewHandler(ViewHandler vh){
        this.vh = vh;
    }

    public void callAI(){
        String q = this.aiInput.getValue();
        if (q !=""){
            isLoading.setValue(true);
            this.ai.callAI(q,
            new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    AIResultModel result = ai.getAIResult();
                   // System.out.println(result.getTotal());
                   List<AIIntent> intents = result.getIntents();
                   if (intents.size() > 0){
                        String intent = intents.get(0).getName();
                        System.out.println(intent);

                        List<AIEntity>  ents = result.getEntities().getWexamTag();
                        if ( ents != null){
                            if (ents.size() > 0 ){
                                String body = ents.get(0).getBody();
                                System.out.println(body);
                                if ( body != ""){
                                    handleAIAction(intent,body);
                                }
                            }
                        }




                   }
                   
                    isLoading.setValue(false);
                    
                }
            }
            );
        }
    }

    private void handleAIAction(String intent, String body){
        if ( intent.compareToIgnoreCase( "search_tag") == 0) {
            System.out.println("Tag serach " + body);
           
            tagSearch(body);
        }

        if ( intent.compareToIgnoreCase( "select_tag") == 0) {
            System.out.println("content search " + body);
            if ( list != null && list.getValue()!= null){
                for (String temp : list.getValue()) {
                    if ( temp.contains(body)){
                        //ideally this should be don in controller
                        //but javafx doesn't support selecteditem bind
                        this.vh.addContentView(temp,this.enableCache.getValue());
                        break;
                    }
                }
            }
            
        }
    }
}
