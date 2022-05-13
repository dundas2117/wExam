
package model;

public class ContentSearchResModel{
    private ContentSearchResultModel response;

    public ContentSearchResModel(){

    }

    public ContentSearchResultModel getResponse(){
        return this.response;
    }

    public void setResponse (ContentSearchResultModel res){
        this.response = res;
    }
}