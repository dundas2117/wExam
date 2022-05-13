package core;

import javafx.concurrent.Task;

import java.io.File;;

public abstract class UploadTask<String> extends Task<String> {
    protected File file;

    public UploadTask(File file) {
        this.file = file;
     
    }


}