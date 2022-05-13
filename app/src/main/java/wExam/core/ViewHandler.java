package core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.application.Application;


import view.main.MainViewController;
import view.search.TagSearchViewController;
import view.search.ContentSearchViewController;
import java.io.IOException;

public class ViewHandler {

    private Scene mainScene;
    
    private Stage stage;
    private ViewModelFactory vmf;


    private Application app;

    private MainViewController mvController;

    public ViewHandler(ViewModelFactory vmf, Application app) {
        this.vmf = vmf;
        this.app = app;
        stage = new Stage();
    }

    public void start() {
        openMainView();
        stage.show();
    }

    public void openMainView() {
        if (mainScene == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main.fxml"));
                Parent root = loader.load();

                this.mvController = loader.getController();
                this.mvController.init(this, vmf.getMainVM());

                //load tag search view into first tab ( always there )

                FXMLLoader tagSearchloader = new FXMLLoader();
                tagSearchloader.setLocation(getClass().getResource("/tagSearch.fxml"));
                Node node = tagSearchloader.load();
                TagSearchViewController tagSearchController = tagSearchloader.getController();
                tagSearchController.init(this, vmf.getTagSearchVM());
      
                this.mvController.loadSearchTab(node);

                mainScene = new Scene(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stage.setScene(mainScene);
        stage.setTitle("Assignment -- Wenxi");
    }

    public void addContentView(String tagId){
        try
        {
            FXMLLoader contentloader = new FXMLLoader();
            contentloader.setLocation(getClass().getResource("/contentSearch.fxml"));
            Node node = contentloader.load();
            ContentSearchViewController contentSearchController = contentloader.getController();
            contentSearchController.init(this, vmf.getContentSearchVM(tagId));
            this.mvController.addContentTab(tagId, node);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Application getApplication(){
        return this.app;
    }
    
}
