import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.io.*;

class ItemCodeNotFound extends Exception {
    public ItemCodeNotFound() {
        super("Item doesn't exist.");
    }
}

class ValueError extends Exception {
    public ValueError() {
        super("Invalid value!");
    }
}

class POS {
    public GroceryItem getItemDetails() {
        GroceryItem item = null;
        try {
            System.out.print("\nEnter the item code : ");
            InputStreamReader r = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(r);
            String item_code = br.readLine();

            // Return null only if item_code is equal to -1
            if (Objects.equals(item_code, "-1")) return null;

            // Search for the item in store
            for (GroceryItem groceryitem : GroceryItem.groceryItemList) {
                if (groceryitem.itemCode.equals(item_code)) {
                    item = groceryitem;
                    break;
                }
            }
            if (item == null) {
                throw new ItemCodeNotFound();
            }
        } catch (ItemCodeNotFound e) {
            // Ask again for valid item code
            System.out.println("Invalid Item code. Please check it and try Again!");
            item = Main.newPos.getItemDetails();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Fetch item details from the database
        return item;
    }
}

class GroceryItem implements Serializable {
    String itemCode;
    double price;
    double weight;
    String size;
    String dateOfManufacture;
    String dateOfExpiry;
    String manufactureName;
    public static Vector<GroceryItem> groceryItemList = new Vector<>();

    public GroceryItem(String itemCode, double price, double weight, String size, String dateOfManufacture, String dateOfExpiry, String manufactureName) {
        this.itemCode = itemCode;
        this.price = price;
        this.weight = weight;
        this.size = size;
        this.dateOfManufacture = dateOfManufacture;
        this.dateOfExpiry = dateOfExpiry;
        this.manufactureName = manufactureName;

        // Add newly created item to the groceryItemList
        groceryItemList.add(this);
    }

    public int getDiscount() {
        // Generate random value for the discount in between 0 - 25
        Random rand = new Random();
        return rand.nextInt(0,25);
    }
}

class BilledItem implements Serializable {
    String itemCode;
    double unitPrice;
    int quantity;
    int discount;
    double netPrice;

    public BilledItem(GroceryItem item, int quantity) {
        this.itemCode = item.itemCode;
        this.quantity = quantity;
        this.unitPrice = item.price;
        this.discount = item.getDiscount();

        // Calculate the discount and subtract it from the price
        this.netPrice = unitPrice*(100-discount)*quantity/100.0;
    }
}

class Bill implements Serializable {
    String cashierName;
    String branch;
    String customerName = "Unknown";
    Vector<BilledItem> itemList = new Vector<>();
    double totalDiscount = 0;
    double totalPrice = 0;
    LocalDate date;
    LocalTime time;

    public Bill(String cashierName, String branch) throws IOException {
        this.cashierName = cashierName;
        this.branch = branch;

        System.out.println("If you Registered customer, Please enter your name");
        System.out.print("(If not, just hit the Enter) : ");

        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);
        String input = br.readLine();

        // Add customer name only if he/she is registered
        if (!Objects.equals(input, "")) {
            this.customerName = input;
        }
    }

    public void addItem(GroceryItem gItem) throws IOException {
        // Get non-negative value for the quantity from user
        int quantity = 0;
        try {
            System.out.print("Enter the item quantity : ");
            InputStreamReader r = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(r);
            String input = br.readLine();
            quantity = Integer.parseInt(input);
            if (quantity < 0) {
                System.out.println("Quantity must be greater than zero.");
                throw new ValueError();
            }
        } catch (ValueError e) {
            System.out.print("Enter the item quantity : ");
            InputStreamReader r = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(r);
            String input = br.readLine();
            quantity = Integer.parseInt(input);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a billed item and add it to the item list
        BilledItem item = new BilledItem(gItem, quantity);
        this.itemList.add(item);
        this.totalDiscount += (item.unitPrice * item.quantity) - item.netPrice;
        this.totalPrice += item.netPrice;
    }

    public void print() {
        System.out.println("\n" + String.join("", Collections.nCopies(56, "-")));
        System.out.println("Cashier's Name  : " + this.cashierName);
        System.out.println("Branch          : " + this.branch);
        System.out.println("Customer's Name : " + this.customerName + "\n");

        // Print the item list as a table
        System.out.printf("%s %12s %10s %10s %11s %n","Item Code", "Unit Price", "Quantity", "Discount", "Net Price");
        for (BilledItem item: itemList) {
            System.out.printf("%9s %12.2f %10d %10d %11.2f %n", item.itemCode, item.unitPrice, item.quantity, item.discount, item.netPrice);
        }
        System.out.printf("\nTotal Discount : %10.2f %n", this.totalDiscount);
        System.out.printf("Total Price    : %10.2f %n%n", this.totalPrice);

        // Get real date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        this.date = currentDateTime.toLocalDate();
        this.time = currentDateTime.toLocalTime();
        System.out.println("Date : " + this.date);
        System.out.println("Time : " + this.time.getHour() + ":" + this.time.getMinute() + ":" + this.time.getSecond());
        System.out.println(String.join("", Collections.nCopies(56, "-")));
    }
}

public class Main {
    static POS newPos = new POS();

    public static void main(String[] args) throws IOException, ClassNotFoundException, ValueError {

        // Create grocery items in the store
        GroceryItem gitem1 = new GroceryItem("0001", 250.00, 70.8, "Medium", "20-12-2022", "20-12-2023", "Unilever");
        GroceryItem gitem2 = new GroceryItem("0002", 50.00, 23.5, "Small", "12-04-2023", "20-06-2023", "Highland");
        GroceryItem gitem3 = new GroceryItem("0003", 1070.00, 500.24, "Large", "20-01-2023", "13-10-2023", "Maliban");
        GroceryItem gitem4 = new GroceryItem("0004", 100.00, 45.4, "Medium", "02-11-2022", "05-07-2023", "Manchee");
        GroceryItem gitem5 = new GroceryItem("0005", 80.00, 75.0, "Small", "10-05-2023", "29-05-2023", "Kothmale");

        // Create the bill
        Bill bill1 = new Bill("Ms. Kumari De Silva", "Katubedda - Moratuwa");

        System.out.println("\nEnter the item codes and quantities of the products");
        System.out.println("If you're done, enter -1 ;");

        // Add bought items to bill
        GroceryItem item = newPos.getItemDetails();
        bill1.addItem(item);

        FileOutputStream fileStream = new FileOutputStream("MyBill.txt");
        ObjectOutputStream os = new ObjectOutputStream(fileStream);
        os.writeObject(bill1);

        os.close();

        /*
        This application can be upgraded to add another bill from here.
        But, since this is only a demonstration, let's reopen the closed bill and update it.
        */

        ObjectInputStream is = null;
        try {
            FileInputStream filestream = new FileInputStream("MyBill.txt");
            is = new ObjectInputStream(filestream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Object obj = is.readObject();

        bill1 = (Bill) obj;

        // Add bought items to the bill
        while (true) {
            item = newPos.getItemDetails();
            if (item==null) break;
            bill1.addItem(item);
        }

        // Print the bill
        bill1.print();
        is.close();

    }
}
