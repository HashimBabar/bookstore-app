/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication;

import bookstoreapplication.CustomerState;
import bookstoreapplication.GoldCustomer;
import bookstoreapplication.SilverCustomer;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class CustomerContext{
    CustomerState state = new SilverCustomer();
    private int points;
    private String username;
    private String password;
    private ArrayList<Books> CustomersBook;

    //EFFECTS: Initializes 
    public CustomerContext(String username,String password ,int points) {
        this.username = username;
        this.password = password;
        this.CustomersBook = new ArrayList<Books>();
        this.points = points;
        setState(state);
    }
    
    public CustomerContext(String username, String password){
        this.username = username;
        this.password = password;
        this.CustomersBook = new ArrayList<Books>();
        points = 0;
        setState(state);
    }
     public CustomerContext(String username, int points){
        this.username = username;
        this.CustomersBook = new ArrayList<Books>();
        this.points = points;
        setState(state);
    }

    

    public double buy(double price) {
        this.points += (int) (price * 10);
        setState(state); 
        return this.points;
    }

    public double redeemAndBuy(double price){
        if((this.points/100) >= price){ //if customer has more points than necessary to cover purchase
             this.points -= price*100;
             setState(state);
             return this.points;
            
        }else if(this.points/100 < price){ //otherwise return points, gets handled in purchase in CustomerCostScreenController
            return this.points;
        }
        return this.points;
    }
    
    
    
    
    public void setState(CustomerState state){
        if (this.points < 1000){
             this.state = new SilverCustomer();
        }
        else{
            this.state = new GoldCustomer();
        }
       
    }
    
    public String getState(){
        return state.getState();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPoint(int points) {
        this.points = points;
        setState(state);
    }

    public double getPoint() {
        return this.points;
    }

    public StringProperty usernameProperty() {
        return new SimpleStringProperty(username);
    }

    public StringProperty passwordProperty() {
        return new SimpleStringProperty(password);
    }

    public IntegerProperty pointProperty() {
        return new SimpleIntegerProperty(points);
    }

}
