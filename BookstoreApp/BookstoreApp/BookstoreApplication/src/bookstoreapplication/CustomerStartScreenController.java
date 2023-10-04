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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * FXML Controller class
 *
 * @author Itay
 */
public class CustomerStartScreenController implements Initializable {
    
    private LoginScreenController grabInfo;
    
    private double sum = 0;
    
    public static CustomerStartScreenController sendInfoCustomer;
    
    static boolean redeem;
    
    @FXML
    private Label customerMessage; //Message displayed at the top of the customer books screen.

    @FXML
    private Button logoutButton;

    @FXML
    private Button BuyButton;

    @FXML
    private Button BuyAndRedeemButton;

   @FXML
    public TableView<Books> tbl_customerbooks;

    @FXML
    public TableColumn<Books, String> col_bookname;

    @FXML
    public TableColumn<Books, Double> col_bookprice;
    
    @FXML
    public TableColumn<Books, CheckBox> col_bookselect;

    @FXML
    private void logout(ActionEvent event) throws IOException {

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

    @FXML
    private void customerBuy(ActionEvent event) throws IOException {
        /*
         Maybe add in a boolean that changes what price operation gets performed
         */
        
        boolean SelectedBook = false;
        
        for(Books bookObject : BookStore.BooksList){
            if(bookObject.getSelected().isSelected()){
                System.out.println(bookObject.getBookName());
                sum += bookObject.getPrice();
                BookStore.selectedBooksList.add(bookObject);
                SelectedBook = true;
            }
        }
        redeem = false;
        if (!SelectedBook) {
            System.out.println("Cannot buy no books!");
            return;
        }
        
        BookStore.BooksList.removeAll(BookStore.selectedBooksList);
        System.out.println(BookStore.BooksList);
        System.out.println("Sum: " + sum);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt",false))) { //deletes the purchased books from the file
            // Write each book's data to a line in the file
            for (Books book : BookStore.BooksList) {
                String line = book.getBookName() + "," + book.getPrice();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        BookStore.BooksList.removeAll(BookStore.BooksList); // Duplicate bug fix
        BookStore.BooksList.removeAll(BookStore.selectedBooksList); //I think this will prevent future bugs
        
        
        
        //FXML Screen handling
        //close current window
        Stage stage = (Stage) BuyButton.getScene().getWindow();
        stage.close();

        // create a new stage object
        Stage newStage = new Stage();

        // load the FXML file for the new window
        Parent root = FXMLLoader.load(getClass().getResource("CustomerCostScreen.fxml"));

        // set the scene of the new stage to the loaded FXML file
        newStage.setScene(new Scene(root));

        // show the new stage
        newStage.show();
    }
    
    @FXML
    private void customerBuyAndRedeem(ActionEvent event) throws IOException {
        /*
         Maybe add in a boolean that changes what price operation gets performed
         */
        
        boolean SelectedBook = false;
        
        for(Books bookObject : BookStore.BooksList){
            if(bookObject.getSelected().isSelected()){
                System.out.println(bookObject.getBookName());
                sum += bookObject.getPrice();
                BookStore.selectedBooksList.add(bookObject);
                SelectedBook = true;
            }
        }
        redeem = true;
        if (!SelectedBook) {
            System.out.println("Cannot buy no books!");
            return;
        }
        
        BookStore.BooksList.removeAll(BookStore.selectedBooksList);
        System.out.println(BookStore.BooksList);
        System.out.println("Sum: " + sum);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt",false))) { //deletes the purchased books from the file
            // Write each book's data to a line in the file
            for (Books book : BookStore.BooksList) {
                String line = book.getBookName() + "," + book.getPrice();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        BookStore.BooksList.removeAll(BookStore.BooksList); //Duplicate bug fix
        BookStore.BooksList.removeAll(BookStore.selectedBooksList); //I think this will prevent future bugs
        
        
        
        //FXML Screen handling
        //close current window
        Stage stage = (Stage) BuyAndRedeemButton.getScene().getWindow();
        stage.close();

        // create a new stage object
        Stage newStage = new Stage();

        // load the FXML file for the new window
        Parent root = FXMLLoader.load(getClass().getResource("CustomerCostScreen.fxml"));

        // set the scene of the new stage to the loaded FXML file
        newStage.setScene(new Scene(root));

        // show the new stage
        newStage.show();
    }

    
    //loads the strings inside of text file and shoves them onto the table
    private void load() throws FileNotFoundException, Exception {
        try {
            File myObj = new File("books.txt");
            // Write each book's data to a line in the file
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split(",");//splitting the string [the book and price] in the text document

                if (parts.length >= 2) {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    Books book = new Books(name, price);
                    tbl_customerbooks.getItems().add(book);

                }
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void writeUserDetails() {
        String user = grabInfo.getUser();
        String pass = grabInfo.getPass();

        try {
            File myObj = new File("customers.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                String[] parts = data.split(",");//splitting the customer data string in the text document

                if ((user.equals(parts[0])) && (pass.equals(parts[1]))) {//checking if things are the same
                    if (parts.length >= 3) {

                        String name = parts[0];

                        int points = (int) Double.parseDouble(parts[2]);

                        CustomerContext customer = new CustomerContext(name, points);

                        customerMessage.setText("Welcome " + customer.getUsername() + "." + " You have " + customer.getPoint() + " points. Your status is " + customer.getState() + ".");

                    }
                } else {
                    System.out.print("passing onto next customer\n");
                }
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setSum(double sum){
        this.sum = sum;
    }
    public double getSum(){
        return sum;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grabInfo = LoginScreenController.sendInfo;
        sendInfoCustomer = this;
        writeUserDetails();
        col_bookname.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        col_bookprice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        col_bookselect.setCellValueFactory(new PropertyValueFactory<>("selected"));
        
        
        
        
        
        

 try {
            load();
        } catch (FileNotFoundException e) {
            // If the file does not exist, create an empty one
            File file = new File("books.txt");
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
