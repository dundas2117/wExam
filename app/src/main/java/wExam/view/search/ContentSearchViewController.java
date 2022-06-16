package view.search;

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
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

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
import javafx.scene.control.Tooltip;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import model.ContentModel;


public class ContentSearchViewController {

    private ViewHandler viewHandler;
    private ContentSearchViewModel contentSearchVM;
    private final SimpleDateFormat dtFormat =  new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

    @FXML
    private TableView<ContentModel> contentTable;


    @FXML
    private TableColumn<ContentModel, Void> addBtnCol;

    @FXML
    private TableColumn<ContentModel, String> titleCol;


    @FXML
    private TableColumn<ContentModel, String> dateCol;

    @FXML
    private TableColumn<ContentModel, String> urlCol;
    
    @FXML
    private Label cacheHitLabel;

    @FXML
    private ProgressIndicator loadingInd;
   
    public void init(ViewHandler viewHandler, ContentSearchViewModel vm) {
        this.viewHandler = viewHandler;
        this.contentSearchVM = vm;
        
        titleCol.setCellValueFactory(new PropertyValueFactory<>("webTitle"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("webPublicationDate"));
        urlCol.setCellValueFactory(new PropertyValueFactory<>("webUrl"));
        contentTable.setItems(this.contentSearchVM.listProperty());
        cacheHitLabel.visibleProperty().bind(this.contentSearchVM.cacheHiProperty());
        loadingInd.visibleProperty().bindBidirectional(contentSearchVM.isLoadingProperty());


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
        this.contentSearchVM.contentSearch();


       
    }

    public void onLongReporthButton(){
        PrintWriter fw = null;
        try {
            String fname = String.format("long_%s.txt", dtFormat.format(new Date()));
            fw = new PrintWriter(fname);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Long report - " + this.contentSearchVM.getTagId());
            bw.newLine();
            for (ContentModel temp : this.contentSearchVM.listProperty()) {
                bw.write(temp.getWebTitle());
                bw.newLine();
            }  
            bw.flush();
            bw.close();
            fw.flush();
            fw.close();
            this.contentSearchVM.showMsg("Long report - " + fname + " generated.");
           
        } catch (IOException e) {
            e.printStackTrace();
            fw.close();
        }
    }

    public void onShortReporthButton(){
        ContentModel item = (ContentModel)contentTable.getSelectionModel().getSelectedItem();
        if ( item == null ){
            this.contentSearchVM.showMsg("Please choose an entry in the list before generating short form report.");
            return;
        }    
        String data = this.contentSearchVM.getTagId() + "\r\n" + item.getWebTitle();
        this.saveShortReport(data);  
        
        String jpgFile = String.format("short_%s.jpg", dtFormat.format(new Date()));

       
        int width = 300;
        int height = 300;
   

        String charset = "UTF-8";

        try
        {
            File file = new File(jpgFile);
            BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, width, height);
    
            MatrixToImageWriter.writeToFile(
                matrix,
                jpgFile.substring(jpgFile.lastIndexOf('.') + 1),
                file);

            this.contentSearchVM.UploadShortRpt(file);
        }
        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch(WriterException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

   

    private void saveShortReport(String data){
        PrintWriter fw = null;
        try {
            String fname = String.format("short_%s.txt", dtFormat.format(new Date()));
            fw = new PrintWriter(fname);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.newLine();
            
            bw.flush();
            bw.close();
            fw.flush();
            fw.close();
            this.contentSearchVM.showMsg("Short report - " + fname + " generated.");
           
        } catch (IOException e) {
            e.printStackTrace();
            fw.close();
        }
    }

    private void addButtonToTable() {
        
        Callback<TableColumn<ContentModel, Void>, TableCell<ContentModel, Void>> cellFactory = new Callback<TableColumn<ContentModel, Void>, TableCell<ContentModel, Void>>() {
            @Override
            public TableCell<ContentModel, Void> call(final TableColumn<ContentModel, Void> param) {
                final TableCell<ContentModel, Void> cell = new TableCell<ContentModel, Void>() {

                    private final Button btn = new Button("+");
                    

                    {
                        btn.setTooltip(new Tooltip("Add to My List"));
                        btn.setOnAction((ActionEvent event) -> {
                            ContentModel data = getTableView().getItems().get(getIndex());

                            viewHandler.getMVController().getMainVM().addToMyList(data);
                            //System.out.println("selectedData: " + data);
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

        addBtnCol.setCellFactory(cellFactory);
    }

   
   
  
    //get the conneciton
   
   
}
