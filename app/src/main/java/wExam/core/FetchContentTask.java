
package core;

import javafx.concurrent.Task;

public abstract class FetchContentTask<ContentSearchResultModel> extends Task<ContentSearchResultModel> {
    protected String request;

    public FetchContentTask(String request) {
        this.request = request;
     
    }


}