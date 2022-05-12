
package model;

public class TagSearchResModel{
    private TagSearchResultModel response;

    public TagSearchResModel(){

    }

    public TagSearchResultModel getResponse(){
        return this.response;
    }

    public void setResponse (TagSearchResultModel res){
        this.response = res;
    }
}