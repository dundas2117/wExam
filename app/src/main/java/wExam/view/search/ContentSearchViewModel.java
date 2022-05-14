package view.search;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

import javafx.event.EventHandler;
import javafx.concurrent.WorkerStateEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import core.ISearch;
import core.IUpload;
;
import model.TagModel;
import model.TagSearchResultModel;
import model.UploadResModel;
import model.ContentModel;
import model.ContentSearchResultModel;
public class ContentSearchViewModel {


   
    private ISearch search ;
    private IUpload uploader;

    private String tagId;

    private ObservableList<ContentModel> list = FXCollections.observableArrayList();
    private StringProperty title = new SimpleStringProperty();
    private BooleanProperty cacheHit = new SimpleBooleanProperty(false);



    public ContentSearchViewModel(ISearch search, String tagId, IUpload uploader) {
        this.search = search;
        this.tagId = tagId;
        this.uploader = uploader;
    }

   
    public ObservableList<ContentModel> listProperty(){
        return list;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public BooleanProperty cacheHiProperty(){
        return this.cacheHit;
    }

   
    

    public void contentSearch(){
        //System.out.println("request" + this.request.getValue());
        this.search.contentSearchByTag(this.tagId,
        new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                ContentSearchResultModel result = search.getContentSearchResult();
                System.out.println(result.getTotal());
                System.out.println(result.getResults().size());

                
                for (ContentModel temp : result.getResults()) {
                    list.add(temp);
                }

                cacheHit.setValue(result.getFromCache());

                
            }
        }
        );
    }

    public String getTagId(){
        return this.tagId;
    }

    public void UploadShortRpt(File file){
        this.uploader.uploadImg(file,
        new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
              boolean flag = true;
              System.out.print("upload completed");
              UploadResModel result = uploader.getUploadResult();
              if ( result != null ){
                if (result.getSuccess() == "true"){
                    showMsg("Short report uploaded to " + result.getData().getLink());
                }
              }
              else {
                showMsg("Failed to uplaod short report.");

              }
            }
        });
    }

    public void showMsg(String msg){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("");
        alert.setContentText(msg);
        alert.showAndWait().ifPresent(rs -> {
           
        });
    }
   
}
