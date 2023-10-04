/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
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
 *
 * @author Itay
 */
public class LoginScreenController implements Initializable {
   
    
    @FXML private TextField txt_username;
    
    
    @FXML private PasswordField txt_password;
   
    
    @FXML private Button button_login;
    
    public static LoginScreenController sendInfo;
    
    @FXML private Label loginFail;
    
     
    private String user;
    private String pass;
    private boolean dupe = false;
    @FXML 

    private void CheckLogin(ActionEvent event) throws IOException  {
      
      String username = txt_username.getText();
      String password = txt_password.getText();
      
        sendInfo.setUser(username);//send username and password for customer log in to check if it exists within the user file
        sendInfo.setPass(password);
            
        if (username.equals("admin") && password.equals("admin")) {
            System.out.println("admin login ");
            
            //close current window
            Stage stage = (Stage) txt_username.getScene().getWindow();
            stage.close();
            
            // create a new stage object
            Stage newStage = new Stage();

            // load the FXML file for the new window
            Parent root = FXMLLoader.load(getClass().getResource("OwnerStartScreen.fxml"));

            // set the scene of the new stage to the loaded FXML file
            newStage.setScene(new Scene(root));

            // show the new stage
            newStage.show();
        }
        else {
            try {
                File myObj = new File("customers.txt");
                // Write each book's data to a line in the file
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine() && dupe == false) {
                    String data = myReader.nextLine();
                    String[] parts = data.split(",");//splitting the string [the name and password (points as well but they don't matter here)] in the text document

                    if (parts.length >= 2) {
                       String fileUsername = parts[0];
                       String filePassword = parts[1];
                       
                        if (username.equals(fileUsername) && password.equals(filePassword)) {
                            dupe = true;

                            //close current window
                            Stage stage = (Stage) txt_username.getScene().getWindow();
                            stage.close();

                            // create a new stage object
                            Stage newStage = new Stage();


                            // load the FXML file for the new window
                            Parent root = FXMLLoader.load(getClass().getResource("CustomerStartScreen.fxml"));
                            
                            // set the scene of the new stage to the loaded FXML file
                            newStage.setScene(new Scene(root));

                            // show the new stage
                            newStage.show();
                        } else {
                             loginFail.setVisible(true);
                             
                        }

                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            
        }
    }
   
    private void setUser(String user){
        this.user = user;
    }
    public String getUser(){
        return user;
    }
     private void setPass(String pass){
        this.pass = pass;
    }
    public String getPass(){
        return pass;
    }
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sendInfo = this;
      
        
        // TODO
    }    
    
}
