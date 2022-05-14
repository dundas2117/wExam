
package core;

import javafx.concurrent.Task;

public abstract class FetchContentTask<ContentSearchResultModel> extends Task<ContentSearchResultModel> {
    protected String request;
    protected Boolean usingCache;
    public FetchContentTask(String request, Boolean usingCache) {
        this.request = request;
        this.usingCache = usingCache;
     
    }


}