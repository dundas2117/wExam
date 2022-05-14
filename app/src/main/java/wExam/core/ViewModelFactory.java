package core;


import view.main.MainViewModel;
import view.search.TagSearchViewModel;
import wExam.wExamApp;
import view.search.ContentSearchViewModel;
import model.DummySearch;
import model.DummyUpload;
import model.GuardianSearch;
import model.ImgUrUpload;
import model.AI;

import javafx.application.Application;

public class ViewModelFactory {
    private ModelFactory mf;
    private wExamApp app;

    public ViewModelFactory(ModelFactory mf,wExamApp app) {
        this.mf = mf;
        this.app = app;
    }

    public MainViewModel getMainVM() {
        return new MainViewModel();
    }

    public TagSearchViewModel getTagSearchVM() {

        ISearch search = null;
        if ( app.getIsSearchDummy()){
            search = new DummySearch();
        }
        else{
             search = new GuardianSearch(this.app.getExecutorService());
        }
       
        return new TagSearchViewModel(search ,new AI(this.app.getExecutorService()));
    }

    public ContentSearchViewModel getContentSearchVM(String tagId, boolean usingCache) {

        ISearch search = null;
        if ( app.getIsSearchDummy()){
            search = new DummySearch();
        }
        else{
             search = new GuardianSearch(this.app.getExecutorService());
             search.enableCache(usingCache);
        }

        IUpload uploader = null;
        if ( app.getIsUploadDummy()){
            uploader = new DummyUpload();
        }
        else{
            uploader = new ImgUrUpload(this.app.getExecutorService());
        }

        
        return new ContentSearchViewModel(search, tagId,uploader );
    }
  
}
