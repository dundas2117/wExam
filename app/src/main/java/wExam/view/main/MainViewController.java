package view.main;

import core.ViewHandler;

import java.io.File;
import java.io.IOException;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.SnapshotResult;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;

import javafx.util.Callback;

public class MainViewController {
    @FXML
    private Tab tagSearchTab;

    @FXML
    private TabPane mainTabPane;
   
    private ViewHandler viewHandler;
    private MainViewModel mainVM;

    public void init(ViewHandler viewHandler, MainViewModel mainVM) {
        this.viewHandler = viewHandler;
        this.mainVM = mainVM;

        
    }

    public void loadSearchTab(Node node){
        tagSearchTab.setContent(node);
    }

    public void addContentTab(String tag, Node node){
        Tab tab = new Tab("Tag - " + tag);
        tab.setContent(node);
        mainTabPane.getTabs().add(tab);
    }

    public void onAbout(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Assignment for Wenxi Ou");
        alert.setContentText("This application is an implementaion of MVVM. It used JAVAFX, ZXING and GSON.");
        alert.showAndWait().ifPresent(rs -> {
        });
    }

    
    
   
}
