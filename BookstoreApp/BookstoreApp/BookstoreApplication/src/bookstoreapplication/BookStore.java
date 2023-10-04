package bookstoreapplication;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class BookStore {


    static ObservableList<Books> BooksList = FXCollections.observableArrayList();
    static ObservableList<Books> selectedBooksList = FXCollections.observableArrayList();
    static ArrayList<CustomerContext> customerList = new ArrayList<CustomerContext>();

}
