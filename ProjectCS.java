import java.util.*;

//Abstract
abstract class FoodItem{

protected int id;
protected String name;
protected double price;

public FoodItem(int id, String name, double price){

this.id= id;
this.name=name;
this.price=price;

}//end Constracter FoodItem

public abstract double calculateFinalPrice();

public void display(){

System.out.println("ID: "+id+"\nName: "+name+"\nPrice: "+price);

}//end display FoodItem

public int getId() {
    return id;
}//end getId

}//end class FoodItem

class MainDish extends FoodItem{

    private String Category;

public MainDish(int id, String name, double price,String Category){ 
    
super(id, name, price);
this.Category=Category;

}//end Constracter MainDish

@Override
public double calculateFinalPrice() {

// Implementation for main dish pricing
return price;
}//end calculateFinalPrice MainDish

@Override 
public void display(){

System.out.println("ID: "+id+"\nName: "+name+"\nPrice: "+price+"\nCategory: "+Category);

}//end display MainDish

}//end Class MainDish

class SpecialMainDish extends MainDish{

   private String extraIngredient;
    private double extraCost;

public SpecialMainDish(int id, String name, double price, String Category, String extraIngredient, double extraCost) {

super(id, name, price, Category);
this.extraIngredient = extraIngredient;
this.extraCost = extraCost;

}//end Constracter SpecialMainDish

@Override
public double calculateFinalPrice() {
    return price + extraCost;
}//end calculateFinalPrice SpecialMainDish

}//end Class SpecialMainDish

class Dessert extends FoodItem{

    private boolean isColdServed;

public Dessert(int id, String name, double price, boolean isColdServed) {
super(id, name, price);
this.isColdServed = isColdServed;
}//end Constracter Dessert

@Override
public double calculateFinalPrice() {
    return price;
}//end calculateFinalPrice Dessert

}//end Class Dessert

class Drink extends FoodItem{

    private String size;

public Drink(int id, String name, double price, String size) {
super(id, name, price);
this.size = size;
}//end Constracter Drink

@Override
public double calculateFinalPrice() {
    return price;
}//end calculateFinalPrice Drink

}//end Class Drink

interface Discountable{

    public double applyDiscount(double percent);

}//end Class Discountable


class Customer implements Discountable{

    private int custumerId;
    private String customerName;
    private String phone;
    private boolean isVIP;

public Customer(int custumerId, String customerName, String phone, boolean isVIP) {
this.custumerId = custumerId;
this.customerName = customerName;
this.phone = phone;
this.isVIP = isVIP;
}//end Constracter Customer

public double applyDiscount(double percent) {
   if (isVIP) {
        return percent + 5; // VIP customers get an additional 5% discount
    } else {
        return percent;
    }
}//end applyDiscount

public void displayCustomerInfo() {
    System.out.println("Customer ID: " + custumerId+"Customer Name: " + customerName+"Phone: " + phone+"VIP Status: " + (isVIP ? "Yes" : "No"));

}//end displayCustomerInfo

}//end Class Customer

class Order{

    private int orderId;
    private List<FoodItem> items; //composition relationship with FoodItem class
    private int itemCount;
    private Customer customer; //Aggregation relationship with Customer class
    private int maxItems;

public Order(int orderId, Customer customer,int maxItems) {
    this.orderId = orderId;
    this.customer = customer;
    this.items = new ArrayList<>();
    this.itemCount = 0;
    this.maxItems = maxItems;
}//end Constracter Order

public boolean addItem(FoodItem item) {
    if (itemCount < maxItems) { // Assuming a maximum of 10 items per order
        items.add(item);
        itemCount++;
        return true;
    } else {
        System.out.println("Cannot add more items. Maximum limit reached.");
        return false;
    }
}//end addItem

public boolean removeItem(int id) {
    for (FoodItem item : items) {
        if (item.getId() == id) {
            items.remove(item);
            itemCount--;
            return true;
        }
    }
    System.out.println("Item not found in the order.");
        return false;
    
}//end removeItem

public FoodItem searchItem(int id) {
    for (FoodItem item : items) {
        if (item.getId() == id) {
            return item;
        }
    }
    System.out.println("Item not found in the order.");
    return null; // Return null or throw an exception if not found

}//end searchItem

public double calculateTotalPrice(int index) {
   if (index >= itemCount){
    return 0;
   }
    return items.get(index).calculateFinalPrice() + calculateTotalPrice(index + 1);
}//end calculateTotalPrice

public void displayOrderDetails() {
    System.out.println("Order ID: " + orderId);
    customer.displayCustomerInfo();
    System.out.println("Items in the order:");
    for (FoodItem item : items) {
        item.display();
        System.out.println("-------------------");
    }
    System.out.println("Total Price: " + calculateTotalPrice(0));
}//end displayOrderDetails



}//end Class Order

class Restaurant{

    private String Name;
    private List<FoodItem> menu; //composition relationship with FoodItem class
    private int menuCount;
    private List<Customer> customers; //Aggregation relationship with Customer class
    private List<Order> orders; 
    private int maxMenuSize;
    
public Restaurant(String Name,int menuSize) {
    this.Name = Name;
    this.menu = new ArrayList<>();
    this.menuCount = 0;
    this.customers = new ArrayList<>();
    this.orders = new ArrayList<>();
    this.maxMenuSize = menuSize;

}//end Constracter Restaurant

public boolean addMenuItem(FoodItem item) {
    if (menuCount < maxMenuSize) { // Assuming a maximum of 10 items in the menu
        menu.add(item);
        menuCount++;
        return true;
    } else {
        System.out.println("Cannot add more items to the menu. Maximum limit reached.");
        return false;
    }
}//end addMenuItem

public boolean removeMenuItem(int id) {
    for (FoodItem item : menu) {
        if (item.getId() == id) {
            menu.remove(item);
            menuCount--;
            return true;
        }
    }
    System.out.println("Menu item not found.");
    return false;
}//end removeMenuItem

public FoodItem searchMenuItem(int id) {
    for (FoodItem item : menu) {
        if (item.getId() == id) {
            return item;
        }
    }
    System.out.println("Menu item not found.");
    return null; // Return null or throw an exception if not found
}//end searchMenuItem

public void registerCustomer(Customer customer) {
    customers.add(customer);
}//end registerCustomer

public Order createOrder(Customer customer) {
    int newId = orders.size() + 1; // Generate a new order ID based on the current number of orders
    Order order = new Order(newId,customer, 10); // Assuming a maximum of 10 items per order
    orders.add(order);
    return order;
}//end createOrder

public void displayMenu() {
    System.out.println("Restaurant Menu:");
    for (FoodItem item : menu) {
        item.display();
        System.out.println("-------------------");
    }
}//end displayMenu
}//end Class Restaurant



