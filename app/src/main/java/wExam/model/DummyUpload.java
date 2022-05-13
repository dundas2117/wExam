package model;

import core.IUpload;
import core.UploadTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import java.util.List;
import java.util.Base64;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import model.UploadResModel;
import model.UploadResultModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import com.google.gson.Gson;

public class DummyUpload implements IUpload{

  

    private UploadTask<UploadResModel> uploadTask ;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public void uploadImg(File file,EventHandler<WorkerStateEvent> evt){
  
  

        this.uploadTask = new UploadTask(file) {
            @Override
            protected UploadResModel call() throws Exception {
                UploadResModel result = new  UploadResModel();
                try {
                    result.setSuccess("true");
                    UploadResultModel r = new UploadResultModel();
                    r.setLink("dummy-link");
                    result.setData(r);
                    Thread.sleep(1000 * 3);
                    
           

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        };
    
        this.uploadTask.setOnSucceeded(evt);
        executorService.submit(this.uploadTask);
        

    }

    public UploadResModel getUploadResult(){
        return this.uploadTask.getValue();
    }

   
  
}