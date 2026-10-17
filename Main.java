import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Create restaurant
        Restaurant restaurant = new Restaurant("Burger House", 20);

        // Create food items
        FoodItem item1 =
                new MainDish(1, "Classic Burger", 25.0, "Burger");

        FoodItem item2 =
                new MainDish(2, "Chicken Burger", 22.0, "Chicken");

        FoodItem item3 =
                new SpecialMainDish(3, "Special Burger", 30.0,
                        "Burger", "Extra Cheese", 5.0);

        FoodItem item4 =
                new Dessert(4, "Chocolate Cake", 15.0, true);

        FoodItem item5 =
                new Drink(5, "Orange Juice", 8.0, "Medium");

        // Add food items to restaurant
        restaurant.addMenuItem(item1);
        restaurant.addMenuItem(item2);
        restaurant.addMenuItem(item3);
        restaurant.addMenuItem(item4);
        restaurant.addMenuItem(item5);

        // Create customer
        Customer customer =
                new Customer(101, "Customer", "0500000000", false);

        restaurant.registerCustomer(customer);

        // Create order
        Order order = restaurant.createOrder(customer);

        int choice = 0;

        System.out.println("================================");
        System.out.println("   WELCOME TO BURGER HOUSE");
        System.out.println("================================");

        do {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Display Restaurant Menu");
            System.out.println("2. Search for Food Item");
            System.out.println("3. Add Item to Order");
            System.out.println("4. Remove Item from Order");
            System.out.println("5. Display Order and Total");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            while (!input.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                input.next();
                System.out.print("Enter your choice: ");
            }

            choice = input.nextInt();

            switch (choice) {

                case 1:
                    restaurant.displayMenu();
                    break;

                case 2:
                    System.out.print("Enter food item ID: ");

                    if (input.hasNextInt()) {
                        int searchId = input.nextInt();

                        FoodItem found =
                                restaurant.searchMenuItem(searchId);

                        if (found != null) {
                            System.out.println("Item found:");
                            found.display();

                            System.out.printf(
                                    "Final Price: %.2f SAR%n",
                                    found.calculateFinalPrice());
                        }
                    } else {
                        System.out.println("Invalid ID.");
                        input.next();
                    }
                    break;

                case 3:
                    System.out.print("Enter food item ID to add: ");

                    if (input.hasNextInt()) {
                        int addId = input.nextInt();

                        FoodItem selected =
                                restaurant.searchMenuItem(addId);

                        if (selected != null) {
                            if (order.addItem(selected)) {
                                System.out.println(
                                        "Item added successfully.");
                            }
                        }
                    } else {
                        System.out.println("Invalid ID.");
                        input.next();
                    }
                    break;

                case 4:
                    System.out.print("Enter food item ID to remove: ");

                    if (input.hasNextInt()) {
                        int removeId = input.nextInt();

                        if (order.removeItem(removeId)) {
                            System.out.println(
                                    "Item removed successfully.");
                        }
                    } else {
                        System.out.println("Invalid ID.");
                        input.next();
                    }
                    break;

                case 5:
                    order.displayOrderDetails();
                    break;

                case 6:
                    System.out.println(
                            "Thank you for visiting Burger House!");
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
            }

        } while (choice != 6);

        input.close();
    }
}
