package model;

import java.util.List;

public class TagSearchResultModel{

    private int total;
    private int pages;
    private int currentPage ;

    private List<TagModel> results;

    public TagSearchResultModel(){

    }
    public TagSearchResultModel(

        int total,
        int pages,
        int currentPage,
        List<TagModel> tags
    ){
        this.total = total;
        this.pages = pages;
        this.currentPage = currentPage;
        this.results = tags;
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

    public List<TagModel> getResults(){
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

    public void setResults(List<TagModel> result){
        this.results = result;
    }


}