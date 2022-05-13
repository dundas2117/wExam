package model;

import core.ISearch;
import core.FetchTagTask;
import core.FetchContentTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import com.google.gson.Gson;

import model.TagSearchResultModel;
import model.TagModel;
import model.ContentModel;
import model.TagSearchResModel;

public class DummySearch implements ISearch{

    
    private ExecutorService executorService = Executors.newCachedThreadPool();
    

    private FetchTagTask<TagSearchResultModel> fetchTags ;
    private FetchContentTask<ContentSearchResultModel> fetchContents ;
    private String tagForSearch="";

    
    public void tagSearch(String tag,EventHandler<WorkerStateEvent> evt){
        System.out.println("dummy search:" + tag);
        this.tagForSearch = tag;

        this.fetchTags = new FetchTagTask() {
            @Override
            protected TagSearchResultModel call() throws Exception {
                TagSearchResultModel result = new TagSearchResultModel();
                try {
                    result.setPages(4);
                    result.setTotal(41);
                    List<TagModel> l = new ArrayList<TagModel>();
                    for ( int i = 0; i<=40; i++){
                        TagModel t = new TagModel(String.format("Tagtitle%s%d", this.request,i), String.format("Tagtitle%s%d", this.request,i));

                        l.add(t);
                    }
                    result.setResults(l);
                    

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
        System.out.println("dummy search Cotnent for:" + tagId);


        this.fetchContents = new FetchContentTask(tagId) {
            @Override
            protected ContentSearchResultModel call() throws Exception {
                ContentSearchResultModel result = new ContentSearchResultModel();
                try {
                    result.setPages(4);
                    result.setTotal(41);
                    List<ContentModel> l = new ArrayList<ContentModel>();
                    for ( int i = 0; i<=40; i++){
                        ContentModel t = new ContentModel();
                        t.setWebTitle(String.format("title%s%d", this.request,i));
                        t.setWebPublicationDate("2022-05-09T02:46:56Z");
                        t.setWebUrl("https://www.theguardian.com/australia-news/2022/may/09/second-leaders-debate-a-ratings-winner-for-nine-despite-employees-criticising-it-as-a-shambles");
                        
                        l.add(t);
                    }
                    result.setResults(l);
                    
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

  
}