package core;
import java.io.File;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import model.UploadResModel;

public interface IUpload{
    public void uploadImg(File file,EventHandler<WorkerStateEvent> evt);

    public UploadResModel getUploadResult();
}