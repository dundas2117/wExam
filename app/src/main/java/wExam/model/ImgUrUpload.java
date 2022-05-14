package model;

import core.IUpload;
import core.UploadTask;
import core.Util;

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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import com.google.gson.Gson;

public class ImgUrUpload implements IUpload{

    private final static String UPLOAD_API_URL = "https://api.imgur.com/3/image";
    private static final String ENV_API_ID="IMGUR_API_KEY";

    private UploadTask<UploadResModel> uploadTask ;
    private ExecutorService executorService ;

    public ImgUrUpload(ExecutorService executorService){
        this.executorService = executorService;
    }
    public void uploadImg(File file,EventHandler<WorkerStateEvent> evt){
  
  

        this.uploadTask = new UploadTask(file) {
            @Override
            protected UploadResModel call() throws Exception {
                UploadResModel result = null ;
                try {
                    HttpURLConnection conn = getHttpConnection(UPLOAD_API_URL);
                    writeToConnection(conn, "image=" + toBase64(file));
                    String json =  getResponse(conn);
                    System.out.print("Upload:" + json);
                    result  = new Gson().fromJson(json, UploadResModel.class);

                    
           

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

    private static HttpURLConnection getHttpConnection(String url) throws UnknownHostException,
    IOException
    {
        HttpURLConnection conn;
        String client_id = System.getenv(ENV_API_ID);
        System.out.print(client_id);
        conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Client-ID " + client_id);
        conn.setReadTimeout(100000);
        conn.connect();
        return conn;
    
    }

    private static String toBase64(File file) throws IOException
    {
 
            byte[] b = new byte[(int) file.length()];
            FileInputStream fs = new FileInputStream(file);
            fs.read(b);
            fs.close();
            return URLEncoder.encode(Base64.getEncoder().encodeToString(b), "UTF-8");
 
    }

    private static void writeToConnection(HttpURLConnection conn, String message)
    {
        OutputStreamWriter writer;
        try
        {
            writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(message);
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();;
        }
    }



    private static String getResponse(HttpURLConnection conn)
    {
       return Util.getResponse(conn);
    }
    
  
}