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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.FileWriter;


/**
 * FXML Controller class
 *
 * @author Itay
 */


public class CustomerCostScreenController implements Initializable {
    
    private CustomerStartScreenController grabInfoCustomer;
    private LoginScreenController grabInfo;
    
    
    
    @FXML
    private Label C; //Total cost
    
    @FXML
    private Label S; //Status variable
    
    @FXML
    private Label P; //Points
    
    @FXML
    private Button logoutButton;
    
    private void writePurchaseDetails() {
        String user = grabInfo.getUser();
        String pass = grabInfo.getPass();
        double sum = grabInfoCustomer.getSum();

        try {
            File myObj = new File("customers.txt");
            Scanner myReader = new Scanner(myObj);
            StringBuilder newFileContents = new StringBuilder();
            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                String[] parts = data.split(",");//splitting the customer data string in the text document

                if ((user.equals(parts[0])) && (pass.equals(parts[1]))) {//checking if things are the same
                    if (parts.length >= 3) {

                        String name = parts[0];

                        int points = (int) Double.parseDouble(parts[2]);

                        CustomerContext customer = new CustomerContext(name, points);
                        
                        if (CustomerStartScreenController.redeem == false){
                            customer.buy(sum);
                            System.out.println("buy!" + customer.getPoint());
                            String tempPoints = Double.toString(customer.getPoint());
                            System.out.println("this one! TEMP POINTS " + tempPoints);
                            String changingPoints = data.replace(parts[2], tempPoints);
                            newFileContents.append(changingPoints).append("\n");
                        }
                        else{
                            int i = (int) customer.getPoint();
                            customer.redeemAndBuy(sum);
                            if((i/100) >= sum){
                            sum = 0;
                            }
                            else if((customer.getPoint()/100) < sum){
                                sum -= (double)points/100;
                                customer.setPoint(0);
                                customer.setPoint((int) (sum*10));
                                customer.setState(customer.state);
                            }
                            System.out.println("redeem and buy!" + customer.getPoint());
                            String tempPoints = Double.toString(customer.getPoint());
                            System.out.println("this one! TEMP POINTS " + tempPoints);
                            String changingPoints = data.replace(parts[2], tempPoints);
                            newFileContents.append(changingPoints).append("\n");
                            
                            
                        }
                        
                        C.setText("" + sum);
                        S.setText(customer.getState());
                        P.setText("" + customer.getPoint());

                    }
                } else {
                    System.out.print("passing onto next customer\n");
                    newFileContents.append(data).append("\n");
                }
            }

        myReader.close();
        
        
        FileWriter writer = new FileWriter("customers.txt");
        writer.write(newFileContents.toString());
        writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();;
        }
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
        grabInfo = LoginScreenController.sendInfo;
        grabInfoCustomer = CustomerStartScreenController.sendInfoCustomer;
        writePurchaseDetails();
    }    
    
}