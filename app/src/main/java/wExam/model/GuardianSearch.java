package model;

import core.ISearch;
import core.FetchTagTask;
import core.FetchContentTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
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

    private static final String GUARDIAN_TAG_URL = "http://content.guardianapis.com/tags?web-title=%s&api-key=%s";
    private static final String GUARDIAN_CONTENT_URL = "https://content.guardianapis.com/search?tag=%s&api-key=%s";
   
    private static final String ENV_API_KEY="INPUT_API_KEY";

    private ExecutorService executorService = Executors.newCachedThreadPool();
    

    private FetchTagTask<TagSearchResultModel> fetchTags ;
    private FetchContentTask<ContentSearchResultModel> fetchContents ;
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
                    String q = URLEncoder.encode(this.request, "UTF-8");
                    String gurl = String.format(GUARDIAN_TAG_URL,q,System.getenv(ENV_API_KEY));
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

    public void contentSearchByTag(String tag,EventHandler<WorkerStateEvent> evt){
        String tagId = new String(tag);
        System.out.println("search Cotnent for:" + tagId);


        this.fetchContents = new FetchContentTask(tagId) {
            @Override
            protected ContentSearchResultModel call() throws Exception {
                ContentSearchResultModel result = null;
                try {
                    String apiKey = System.getenv(ENV_API_KEY);
                    Gson gson = new Gson();
                    String q = URLEncoder.encode(this.request, "UTF-8");
                    String gurl = String.format(GUARDIAN_CONTENT_URL,q,apiKey);
                    System.out.println(gurl);
                    String jsonString = readUrl(gurl);
                    System.out.println(jsonString);
                    ContentSearchResModel res  = new Gson().fromJson(jsonString, ContentSearchResModel.class);
                    result = res.getResponse();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        };

        this.fetchContents.setOnSucceeded(evt);
        executorService.submit(fetchContents);
    }



   



    public TagSearchResultModel getTagSearchResult(){
        return this.fetchTags.getValue();
    }

    public ContentSearchResultModel getContentSearchResult(){
        return this.fetchContents.getValue();
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