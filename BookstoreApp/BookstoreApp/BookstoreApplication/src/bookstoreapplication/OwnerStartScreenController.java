/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
/**
 * FXML Controller class
 *
 * @author rolan
 */
public class OwnerStartScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
     private Button btn_books;
     
     @FXML
     private Button btn_customers;
     
     @FXML
     private Button logoutButton;
    
    @FXML
     private void OwnersBooks(ActionEvent event) throws IOException  {
         
         //close current window
        Stage stage = (Stage) btn_books.getScene().getWindow(); 
        stage.close(); 
                // create a new stage object
        Stage newStage = new Stage();

        // load the FXML file for the new window
        Parent root = FXMLLoader.load(getClass().getResource("OwnersBookScreen.fxml"));

        // set the scene of the new stage to the loaded FXML file
        newStage.setScene(new Scene(root));

        // show the new stage
        newStage.show();  
     }
     
     @FXML
     private void OwnerCustomer(ActionEvent event) throws IOException  {
         
         //close current window
        Stage stage = (Stage) btn_customers.getScene().getWindow(); 
        stage.close(); 
        
        // create a new stage object
        Stage newStage = new Stage();

        // load the FXML file for the new window
        Parent root = FXMLLoader.load(getClass().getResource("OwnerCustomerScreen.fxml"));

        // set the scene of the new stage to the loaded FXML file
        newStage.setScene(new Scene(root));

        // show the new stage
        newStage.show();  
     }
     
     @FXML
     private void logout(ActionEvent event) throws IOException  {
         
         //close current window
        Stage stage = (Stage) logoutButton.getScene().getWindow(); 
        stage.close(); 
        
        // create a new stage object
        Stage newStage = new Stage();

        // load the FXML file for the new window
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));

        // set the scene of the new stage to the loaded FXML file
        newStage.setScene(new Scene(root));

        // show the new stage
        newStage.show();   
     }
     
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
