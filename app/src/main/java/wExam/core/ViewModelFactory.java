package core;


import view.main.MainViewModel;

public class ViewModelFactory {
    private ModelFactory mf;

    public ViewModelFactory(ModelFactory mf) {
        this.mf = mf;
    }

    public MainViewModel getMainVM() {
        return new MainViewModel();
    }

  
}
