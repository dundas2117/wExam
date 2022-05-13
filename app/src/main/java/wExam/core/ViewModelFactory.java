package core;


import view.main.MainViewModel;
import view.search.TagSearchViewModel;
import view.search.ContentSearchViewModel;
import model.GuardianSearch;


public class ViewModelFactory {
    private ModelFactory mf;

    public ViewModelFactory(ModelFactory mf) {
        this.mf = mf;
    }

    public MainViewModel getMainVM() {
        return new MainViewModel();
    }

    public TagSearchViewModel getTagSearchVM() {
        GuardianSearch search = new GuardianSearch();
        return new TagSearchViewModel(search);
    }

    public ContentSearchViewModel getContentSearchVM(String tagId) {
        GuardianSearch search = new GuardianSearch();
        return new ContentSearchViewModel(search), tagId;
    }
  
}
