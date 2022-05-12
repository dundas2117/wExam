package core;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import model.TagSearchResultModel;


public interface ISearch{
    public void tagSearch(String tag,EventHandler<WorkerStateEvent> evt);
    public void contentSearchByTag( String tag);

    //public void setOnTagSearchSucceeded(EventHandler<WorkerStateEvent> evt);

    public TagSearchResultModel getTagSearchResult();
}