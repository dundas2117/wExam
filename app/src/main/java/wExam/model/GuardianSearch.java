package model;

import core.ISearch;
import core.FetchTagTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import com.google.gson.Gson;

import model.TagSearchResultModel;
import model.TagSearchResModel;

public class GuardianSearch implements ISearch{

    private static final String GUARDIAN_URL = "http://content.guardianapis.com/tags?web-title=%s?&api-key=%s";
    private ExecutorService executorService = Executors.newCachedThreadPool();
    

    private FetchTagTask<TagSearchResultModel> fetchTags ;
    private String tagForSearch="";

    public void tagSearch(String tag,EventHandler<WorkerStateEvent> evt){
        System.out.println("search:" + tag);
        this.tagForSearch = tag;

        this.fetchTags = new FetchTagTask() {
            @Override
            protected TagSearchResultModel call() throws Exception {
                TagSearchResultModel result = null;
                try {
                    Gson gson = new Gson();
                    String gurl = String.format(GUARDIAN_URL,this.request,"ab489ac0-6ff6-4dbc-96fc-eaa3b2658d50");
                    System.out.println(gurl);
                    String jsonString = readUrl(gurl);
                    System.out.println(jsonString);
                    TagSearchResModel res  = new Gson().fromJson(jsonString, TagSearchResModel.class);
                    result = res.getResponse();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        };
        this.fetchTags.setRequest(new String(this.tagForSearch));
        this.fetchTags.setOnSucceeded(evt);
        executorService.submit(fetchTags);
    }

    public void contentSearchByTag(String tag){

    }



   



    public TagSearchResultModel getTagSearchResult(){
        return this.fetchTags.getValue();
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}