package model;

import core.ISearch;
import core.FetchTagTask;
import core.AITask;
import core.FetchContentTask;
import core.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.UnknownHostException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import com.google.gson.Gson;

import model.TagSearchResultModel;
import model.TagSearchResModel;
import model.CacheRepo;
import model.AIResultModel;

public class AI {

    private static final String AI_URL = "https://api.wit.ai/message?q=%s";
    
    private static final String AI_API_TOKEN="JVVVX2GBOHMWLXFT2CSYYVSGSQTZSHWJ";

    //need to use this to run on thread other than UI
    private ExecutorService executorService ;
    

    private AITask<AIResultModel> aiTask ;
    private String tagForSearch="";

    private boolean usingCache;

    public AI(ExecutorService executorService ){
        this.executorService = executorService;
    }

    public void callAI(String request,EventHandler<WorkerStateEvent> evt){
        System.out.println("ai:" + request);
     
        this.aiTask = new AITask(request) {
            @Override
            protected AIResultModel call() throws Exception {
                AIResultModel result = null;
                try {

                    
                    Gson gson = new Gson();
                    String q = URLEncoder.encode(this.request, "UTF-8");
                    String gurl = String.format(AI_URL,q);

                    HttpURLConnection conn = getHttpConnection(gurl);

                    String json =  Util.getResponse(conn);
                    
                    json = json.replace("\"wexam_tag:wexam_tag\"", "wexamTag");
                    System.out.print("ai:" + json);
                    result = new Gson().fromJson(json, AIResultModel.class);
                    
                    //saveTagsToCache(this.request, result.getResults());
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        };

        this.aiTask.setOnSucceeded(evt);
        executorService.submit(aiTask);
    }

 
    private static HttpURLConnection getHttpConnection(String url) throws UnknownHostException,
    IOException
    {
        HttpURLConnection conn;
        String token = AI_API_TOKEN;
      
        conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setReadTimeout(100000);
        conn.connect();
        return conn;
    
    }



    public AIResultModel getAIResult(){
        return this.aiTask.getValue();
    }



    

   


}