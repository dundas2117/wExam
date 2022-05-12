
package core;

import javafx.concurrent.Task;

public abstract class FetchTagTask<TagSearchResultModel> extends Task<TagSearchResultModel> {
    protected String request;

    public FetchTagTask() {
     
    }

    public void setRequest(String request){
        this.request = request;
    }
}