/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rolan
 */
public class OwnerCustomerScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btn_deleteCustomer;
    @FXML
    private Button btn_addCustomer;
    @FXML
    private Button btn_backOwnerCustomer;

    @FXML
    public  TableView<CustomerContext> tbl_customers;
    
    @FXML
    public static TableView<CustomerContext> tbl_customers2;

    @FXML
    public TableColumn<CustomerContext, String> username;

    @FXML
    public TableColumn<CustomerContext, String> password;
    @FXML
    public TableColumn<CustomerContext, Integer> points;

    @FXML
    public TextField txt_username;

    @FXML
    public TextField txt_password;

    @FXML
    private void DeleteCustomer(ActionEvent event) throws IOException {

        // Get the selected customer
        CustomerContext selectedCustomer = tbl_customers.getSelectionModel().getSelectedItem();

        // If a customer is selected, remove it from the table's data list
        if (selectedCustomer != null) {
            tbl_customers.getItems().remove(selectedCustomer);
        }
        saveCustomerDataToFile();
    }

    @FXML
    private void AddCustomer(ActionEvent event) throws IOException, Exception {
        String username = txt_username.getText();
        String password = txt_password.getText();
        boolean dupe = false;
        CustomerContext customer = new CustomerContext(username, password);

        try {
            File myObj = new File("customers.txt");
            // Write each book's data to a line in the file
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split(",");//splitting the string [the name password and points] in the text document
                if (parts.length >= 2) {
                    //System.out.println("THESE ONES parts0 = (" + parts[0] + ") parts1(" + parts[1] + ") other ones" + username + " " + password);
                    if (password.equals(parts[1]) && username.equals(parts[0])) {
                        System.out.println("Already added this one");
                        dupe = true;
                    }

                }
            }
            if (dupe != true) {
                tbl_customers.getItems().add(customer);
                saveCustomerDataToFile();
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       
       

          
    }

@FXML
        private void BackOwnerCustomerScreen(ActionEvent event) throws IOException
    {
        //close current window
        saveCustomerDataToFile();
        Stage stage = (Stage) btn_backOwnerCustomer.getScene().getWindow(); 
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
    
    private void saveCustomerDataToFile() {
    // Get the data from the table
    ObservableList<CustomerContext> customers = tbl_customers.getItems();
    tbl_customers2 = tbl_customers;
     try (BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt"))) {
            // Write each book's data to a line in the file
            for (CustomerContext customer : customers) {
                String line = customer.getUsername() + "," + customer.getPassword() + "," +customer.getPoint() ;
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}
    
  //loads the strings inside of text file and shoves them onto the table
    private void load() throws FileNotFoundException, Exception {
        try {
            File myObj = new File("customers.txt");
            // Write each book's data to a line in the file
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split(",");//splitting the string [the name password and points] in the text document

                if (parts.length >= 2) {
                    String name = parts[0];
                    String password= parts[1];
                    int points = (int)Double.parseDouble(parts[2]);
                    CustomerContext customer = new CustomerContext(name, password, points);
                    tbl_customers.getItems().add(customer);

                }
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    @Override
        public void initialize(URL url, ResourceBundle rb) {
        username.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        password.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        points.setCellValueFactory(cellData -> cellData.getValue().pointProperty().asObject());

        
        // Add the columns to the table
        //tbl_books.getColumns().addAll( BookName, BookPrice);          //Commented out to avoid duplicate error
        //this try block is for loading the books.txt file
      try {
            load();
        } catch (FileNotFoundException e) {
            // If the file does not exist, create an empty one
            File file = new File("customers.txt");
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
       
    }
