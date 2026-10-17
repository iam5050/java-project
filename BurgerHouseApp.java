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
    System.out.println("Customer ID: " + custumerId+"\nCustomer Name: " + customerName+"\nPhone: " + phone+"\nVIP Status: " + (isVIP ? "Yes" : "No"));

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
}//end Class Restaurantimport java.util.Scanner;import java.util.Scanner;

public class BurgerHouseApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Restaurant restaurant = new Restaurant("Burger House", 20);

        // Add items to the restaurant menu
        restaurant.addMenuItem(
                new MainDish(1, "Classic Burger", 25, "Beef"));
        restaurant.addMenuItem(
                new MainDish(2, "Chicken Burger", 22, "Chicken"));
        restaurant.addMenuItem(
                new SpecialMainDish(3, "Special Burger", 30,
                        "Beef", "Extra Cheese", 5));
        restaurant.addMenuItem(
                new Dessert(4, "Chocolate Cake", 15, false));
        restaurant.addMenuItem(
                new Drink(5, "Orange Juice", 8, "Medium"));

        // Get customer information
        System.out.println("===== Welcome to Burger House =====");

        String customerName = readValidName(input);
        String phone = readValidPhone(input);
        boolean isVIP = readYesNo(input);

        Customer customer =
                new Customer(101, customerName, phone, isVIP);

        restaurant.registerCustomer(customer);

        Order order = restaurant.createOrder(customer);
        boolean isPaid = false;

        int choice;

        do {
            System.out.println("\n===== Main Menu =====");
            System.out.println("1. Display Menu");
            System.out.println("2. Search for an Item");
            System.out.println("3. Add Item to Order");
            System.out.println("4. Remove Item from Order");
            System.out.println("5. View Order and Checkout");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            choice = readInt(input);

            switch (choice) {

                case 1:
                    restaurant.displayMenu();
                    break;

                case 2:
                    System.out.print("Enter item ID to search: ");
                    int searchId = readInt(input);

                    FoodItem foundItem =
                            restaurant.searchMenuItem(searchId);

                    if (foundItem != null) {
                        foundItem.display();
                        System.out.printf("Final Price: %.2f SAR%n",
                                foundItem.calculateFinalPrice());
                    }
                    break;

                case 3:
                    if (isPaid) {
                        System.out.println(
                                "This order has already been paid.");
                        break;
                    }

                    System.out.print("Enter item ID to add: ");
                    int addId = readInt(input);

                    FoodItem itemToAdd =
                            restaurant.searchMenuItem(addId);

                    if (itemToAdd != null) {
                        if (order.addItem(itemToAdd)) {
                            System.out.println(
                                    "Item added successfully!");
                        }
                    }
                    break;

                case 4:
                    if (isPaid) {
                        System.out.println(
                                "This order has already been paid.");
                        break;
                    }

                    System.out.print("Enter item ID to remove: ");
                    int removeId = readInt(input);

                    if (order.removeItem(removeId)) {
                        System.out.println(
                                "Item removed successfully!");
                    }
                    break;

                case 5:
                    if (isPaid) {
                        System.out.println(
                                "This order has already been paid.");
                        break;
                    }

                    double subtotal = order.calculateTotalPrice(0);

                    if (subtotal == 0) {
                        System.out.println(
                                "Your order is empty. Add an item first.");
                        break;
                    }

                    System.out.println("\n===== Order Summary =====");
                    order.displayOrderDetails();

                    double discountPercent =
                            customer.applyDiscount(0);
                    double discountAmount =
                            subtotal * discountPercent / 100.0;
                    double finalTotal = subtotal - discountAmount;

                    System.out.printf("Subtotal: %.2f SAR%n", subtotal);

                    if (isVIP) {
                        System.out.printf(
                                "VIP Discount (%.0f%%): -%.2f SAR%n",
                                discountPercent, discountAmount);
                    } else {
                        System.out.println("VIP Discount: 0.00 SAR");
                    }

                    System.out.printf(
                            "Final Total: %.2f SAR%n", finalTotal);

                    System.out.println("\n===== Checkout =====");
                    System.out.println("1. Pay Now");
                    System.out.println("2. Back to Main Menu");
                    System.out.print("Choose an option: ");

                    int paymentChoice = readInt(input);

                    if (paymentChoice == 1) {
                        System.out.println("\nProcessing payment...");

                        isPaid = true;

                        System.out.println(
                                "Payment completed successfully!");
                        System.out.printf(
                                "Amount Paid: %.2f SAR%n", finalTotal);
                        System.out.println(
                                "Thank you for ordering from Burger House!");
                        System.out.println(
                                "Returning to the main menu...");

                    } else if (paymentChoice == 2) {
                        System.out.println(
                                "Returning to the main menu...");
                    } else {
                        System.out.println(
                                "Invalid choice. Returning to the main menu.");
                    }
                    break;

                case 6:
                    System.out.println("Thank you for visiting us!");
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
            }

        } while (choice != 6);

        input.close();
    }

    // Validate name: letters and spaces only
    public static String readValidName(Scanner input) {
        String name;

        while (true) {
            System.out.print("Enter your name: ");
            name = input.nextLine().trim();

            if (!name.isEmpty() &&
                    name.matches("[\\p{L} ]+")) {
                return name;
            }

            System.out.println(
                    "Invalid name! Use letters only.");
        }
    }

    // Validate phone number: digits only
    public static String readValidPhone(Scanner input) {
        String phone;

        while (true) {
            System.out.print("Enter your phone number: ");
            phone = input.nextLine().trim();

            if (phone.matches("[0-9]+")) {
                return phone;
            }

            System.out.println(
                    "Invalid phone number! Enter digits only.");
        }
    }

    // Accept yes/no regardless of letter case
    public static boolean readYesNo(Scanner input) {
        while (true) {
            System.out.print("Are you a VIP customer? (yes/no): ");
            String answer = input.nextLine().trim();

            if (answer.equalsIgnoreCase("yes")) {
                return true;
            }

            if (answer.equalsIgnoreCase("no")) {
                return false;
            }

            System.out.println(
                    "Invalid input! Please enter yes or no.");
        }
    }

    // Read an integer safely
    public static int readInt(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            input.nextLine();
        }

        int number = input.nextInt();
        input.nextLine();
        return number;
    }
}
