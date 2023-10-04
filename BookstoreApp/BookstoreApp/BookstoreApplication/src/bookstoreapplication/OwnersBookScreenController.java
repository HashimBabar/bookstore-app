/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
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
public class OwnersBookScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btn_delete;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_back;

    @FXML
    public TableView<Books> tbl_books;

    @FXML
    public TableColumn<Books, String> BookName;

    @FXML
    public TableColumn<Books, Double> BookPrice;

    @FXML
    public TextField txt_name;

    @FXML
    public TextField txt_price;

    @FXML
    private void DeleteBook(ActionEvent event) throws IOException {

        // Get the selected book
        Books selectedBook = tbl_books.getSelectionModel().getSelectedItem();

        // If a book is selected, remove it from the table's data list
        if (selectedBook != null) {
            tbl_books.getItems().remove(selectedBook);
        }
        saveDataToFile();
    }

   @FXML
    private void AddBook(ActionEvent event) throws IOException, Exception {
        String BookName = txt_name.getText();
        double price;
        try {
             price = Double.parseDouble(txt_price.getText());
             Books book = new Books(BookName, price);
             tbl_books.getItems().add(book);//adds it to the table
             saveDataToFile();
        }catch(NumberFormatException e) {
            System.out.println("Please enter a valid price");
        }
    }

    @FXML
    private void BackOwnersBookScreen(ActionEvent event) throws IOException {
        //close current window
        saveDataToFile();
        Stage stage = (Stage) btn_back.getScene().getWindow();
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
                    tbl_books.getItems().add(book);

                }
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveDataToFile() {
        // Get the data from the table
        ObservableList<Books> books = tbl_books.getItems();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt",false))) {
            // Write each book's data to a line in the file
            for (Books book : books) {
                String line = book.getBookName() + "," + book.getPrice();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        BookStore.BooksList.removeAll(BookStore.BooksList); //Duplicate bug fix
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Set up the Book Name and Book Price columns
        BookName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        BookPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        // Add the columns to the table
        //tbl_books.getColumns().addAll( BookName, BookPrice);        
        //this try block is for loading the books.txt file
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
