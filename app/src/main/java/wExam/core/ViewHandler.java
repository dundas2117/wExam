package core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import view.main.MainViewController;

import java.io.IOException;

public class ViewHandler {

    private Scene mainScene;
    
    private Stage stage;
    private ViewModelFactory vmf;


    public ViewHandler(ViewModelFactory vmf) {
        this.vmf = vmf;
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

                MainViewController controller = loader.getController();
                controller.init(this, vmf.getMainVM());

                mainScene = new Scene(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stage.setScene(mainScene);
        stage.setTitle("Assignment -- Wenxi");
    }

    
}
