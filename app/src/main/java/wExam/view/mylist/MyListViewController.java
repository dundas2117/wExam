package view.mylist;

import core.ViewHandler;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.Date;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.File;

import java.text.SimpleDateFormat;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.HostServices;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressIndicator;

import javafx.fxml.FXML;
import javafx.util.Callback;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import model.ContentModel;


public class MyListViewController {

    private ViewHandler viewHandler;
    private MyListViewModel myListVM;
    private final SimpleDateFormat dtFormat =  new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

    @FXML
    private TableView<ContentModel> contentTable;


    @FXML
    private TableColumn<ContentModel, String> titleCol;

    @FXML
    private TableColumn<ContentModel, Void> colRmBtn;


    @FXML
    private TableColumn<ContentModel, String> urlCol;
    



   
    public void init(ViewHandler viewHandler, MyListViewModel vm) {
        this.viewHandler = viewHandler;
        this.myListVM = vm;
        
        titleCol.setCellValueFactory(new PropertyValueFactory<>("webTitle"));
        urlCol.setCellValueFactory(new PropertyValueFactory<>("webUrl"));
        contentTable.setItems( this.viewHandler.getMVController().getMainVM().MyListProperty());
       

        contentTable.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {

               
                ContentModel item = (ContentModel)contentTable.getSelectionModel().getSelectedItem();
                if ( item != null){
              
                    //System.out.println("clicked on " + item.getWebUrl());
                    this.viewHandler.getApplication().getHostServices().showDocument(item.getWebUrl());
                }    
                ev.consume(); 
            }
        });

        contentTable.addEventHandler(MouseEvent.MOUSE_CLICKED, ev -> {
           
            ContentModel item = (ContentModel)contentTable.getSelectionModel().getSelectedItem();
            if ( item != null){
          
                //System.out.println("clicked on " + item.getWebUrl());
                this.viewHandler.getApplication().getHostServices().showDocument(item.getWebUrl());
            }    
            ev.consume(); 
        });

        addButtonToTable();
        

       // this.contentSearchVM.contentSearch();


       
    }

    private void addButtonToTable() {
        
        Callback<TableColumn<ContentModel, Void>, TableCell<ContentModel, Void>> cellFactory = new Callback<TableColumn<ContentModel, Void>, TableCell<ContentModel, Void>>() {
            @Override
            public TableCell<ContentModel, Void> call(final TableColumn<ContentModel, Void> param) {
                final TableCell<ContentModel, Void> cell = new TableCell<ContentModel, Void>() {

                    private final Button btn = new Button("-");

                    {
                        
                        btn.setTooltip(new Tooltip("Remove from list"));
                        btn.setOnAction((ActionEvent event) -> {
                            int index = getIndex();
                            //ContentModel data = getTableView().getItems().get(getIndex());
                            System.out.println("index: " + index);
                            viewHandler.getMVController().getMainVM().removeFromMyList(index);

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colRmBtn.setCellFactory(cellFactory);
    }

   
   

  
    //get the conneciton
   
   
}
