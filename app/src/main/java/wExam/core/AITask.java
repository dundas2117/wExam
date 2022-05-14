
package core;

import javafx.concurrent.Task;

import model.AIResultModel;

public abstract class AITask<AIResultModel> extends Task<AIResultModel> {
    protected String request;
    
    public AITask(String request) {
        this.request = request;
       
     
    }


}