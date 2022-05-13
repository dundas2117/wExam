package view.main;


import core.ViewHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.FXML;

public class SplashController {
    @FXML
    private ImageView imageView;

    @FXML
    private ProgressBar pBar;

    public void init(ViewHandler viewHandler) {
        
        Image img= new Image(getClass().getResourceAsStream("/splash.jpeg"));

        this.imageView.setImage(img);

        IntegerProperty seconds = new SimpleIntegerProperty();
        pBar.progressProperty().bind(seconds.divide(15.0));
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(seconds, 0)),
            new KeyFrame(Duration.seconds(15), e-> {
                // do anything you need here on completion...
                System.out.println("time over");
                viewHandler.openMainView();
            }, new KeyValue(seconds, 15))   
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

   

    
   
}
