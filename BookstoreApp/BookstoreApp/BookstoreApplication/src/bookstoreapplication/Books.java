/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;



/**
 *
 * @author Itay
 */


public class Books {
    private String BookName;
    private double price;
    private CheckBox selected;
   

    //EFFECTS:
    public Books(String BookName, double price) throws Exception{
        if(price < 0){
            throw new Exception ("Invalid price, Price must be $0 or higher");
        }else{
            this.price = price;
            this.BookName = BookName;
            this.selected = new CheckBox();
            BookStore.BooksList.add(this);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECCTS: 
    public String getBookName(){
        return this.BookName;
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECCTS: 
    public double getPrice(){
        return this.price;
    }
    
    public CheckBox getSelected(){
        return this.selected;
    }
    
    public void setBookPrice(double price) throws Exception{
        if(price < 0){
            throw new Exception("invalid input!");
        }else{
            this.price = price;
        }
    }

    @Override
    public String toString() {
        return "Book [BookName=" + getBookName() + ", price=" + getPrice() + "]";
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(BookName);
    }

    public DoubleProperty priceProperty() {
        return new SimpleDoubleProperty(price);
    }
    
    
   
}