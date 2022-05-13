package model;

import java.util.List;

public class ContentSearchResultModel{

    private int total;
    private int pages;
    private int currentPage ;

    private List<ContentModel> results;

    public ContentSearchResultModel(){

    }
    


    public int getTotal(){
        return this.total;
    }

    public int getPages(){
        return this.pages;
    }

    public int getCurrentPage(){
        return this.currentPage;
    }

    public List<ContentModel> getResults(){
        return results;
    }

    public void setTotal(int total){
        this.total = total;
    }

    public void setPages(int pages){
        this.pages = pages;
    }

    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }

    public void setResults(List<ContentModel> result){
        this.results = result;
    }


}