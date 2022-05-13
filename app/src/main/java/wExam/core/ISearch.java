package core;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import model.TagSearchResultModel;
import model.ContentSearchResultModel;

public interface ISearch{
    public void tagSearch(String tag,EventHandler<WorkerStateEvent> evt);
    public void contentSearchByTag( String tag,EventHandler<WorkerStateEvent> evt);

    //public void setOnTagSearchSucceeded(EventHandler<WorkerStateEvent> evt);

    public TagSearchResultModel getTagSearchResult();
    public ContentSearchResultModel getContentSearchResult();
}