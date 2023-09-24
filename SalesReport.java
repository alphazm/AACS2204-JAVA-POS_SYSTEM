/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package testass;

/**
 *
 * @author laiyo
 */
import java.util.*;
import java.io.*;

public class SalesReport{ 
    
    // data field
    int totalQuantity;
    double totalRevenue;
    private int id;
    private List<SalesReport> items;
    private long second;
    private int qty;
    private String product;
    private double price;
    
    //constructor
        public SalesReport(int id, long second, List<SalesReport> items) {
            this.id = id;
            this.second = second;
            this.items = items;
        }
        
        public SalesReport(String product, int qty, double price) {
            this.product = product;
            this.qty = qty;
            this.price = price;
        }
        
        public SalesReport(){
            
        }
        
        // getter
        public int getId(){
            return id;
        }
        
        public long getSecond(){
            return second;
        }
        
        public List<SalesReport> getItems(){
            return items;
        }

        public String getProduct(){
            return product;
        }
        
        public int getQty(){
            return qty;
        }
        
        public double getPrice(){
            return price;
        }
 
    // methods    
    // read from text file
    public static ArrayList<SalesReport> readFile(String filePath){
            ArrayList<SalesReport> receiptList = new ArrayList<>();

          try{
            
             // open file using FileReader and wrap it using BufferedReader
             FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader);

             // read the content of file until EOF
             String line;
             while ((line = bufferedReader.readLine()) != null) {
                 
                 // split the line by commas to get values
                 String[] values = line.split(",");
                 
                 // convert the values to appropriate data types
                 int receiptID = Integer.parseInt(values[0]);
                 long second = Long.parseLong(values[1]);
                 
                 // create a list of products
                 List<SalesReport> items = new ArrayList<>();
                 for (int i = 2; i < values.length; i += 3) {
                     String name = values[i];
                     int qty = Integer.parseInt(values[i+1]);
                     double price = Double.parseDouble(values[i+2]);
                     items.add(new SalesReport(name,qty,price));
                 }
                 
                 // create a Receipt object and add to ArrayList
                 SalesReport receipt = new SalesReport(receiptID, second, items);
                 receiptList.add(receipt);
                 
                }
     
          // close file
           bufferedReader.close();
            } catch (IOException e) {
              System.out.println("Error occured when reading receipt text file!!");
         }
          return receiptList;
        }
    
    // mapping and calculate for productSales
    public static Map<String, SalesReport> calculateProductSales(List<SalesReport> receiptList) {
        Map<String, SalesReport> productSalesMap = new HashMap<>();

        for (SalesReport receipt : receiptList) {
            for (SalesReport product : receipt.getItems()) {
                String productName = product.getProduct();
                int quantity = product.getQty();
                double price = product.getPrice();

                if (!productSalesMap.containsKey(productName)) {
                    productSalesMap.put(productName, new SalesReport());
                }

                SalesReport salesData = productSalesMap.get(productName);
                salesData.totalQuantity += quantity;
                salesData.totalRevenue += quantity * price;
            }
        }

        return productSalesMap;
    }
    
    // generate Product Sales
    public static void generateProductSalesReport(ArrayList<SalesReport> receiptList) {
        Map<String, SalesReport> productSalesMap = calculateProductSales(receiptList);

        // Print the product sales report
        System.out.println("Product Sales Report");
        System.out.println("--------------------");
        System.out.printf("%-20s %-15s %-15s%n", "Product Name", "Total Quantity", "Total Revenue");

        for (Map.Entry<String, SalesReport> entry : productSalesMap.entrySet()) {
            String productName = entry.getKey();
            SalesReport salesData = entry.getValue();

            System.out.printf("%-20s %-15d $%.2f%n", productName, salesData.totalQuantity, salesData.totalRevenue);
        }
    }
    
    // calculate Total Sales
    public static double calculateTotalSales(ArrayList<SalesReport> receiptList) {
        double totalSalesRevenue = 0;

        for (SalesReport receipt : receiptList) {
            for (SalesReport product : receipt.getItems()) {
                totalSalesRevenue += product.getPrice() * product.getQty();
            }
        }

        return totalSalesRevenue;
    }
    
    // generate Total Sales
    public static void generateTotalSalesReport(ArrayList<SalesReport> receiptList) {
        double totalSalesRevenue = calculateTotalSales(receiptList);

        // Print the total sales report
        System.out.println("Total Sales Report");
        System.out.println("------------------");
        System.out.printf("Total Sales Revenue: $%.2f%n", totalSalesRevenue);
        System.out.println();
    }
    
    public static void main(String[] args) {
        String filePath = "src/testass/receipt.txt";
        ArrayList<SalesReport> receiptList = readFile(filePath);

        generateTotalSalesReport(receiptList);
        generateProductSalesReport(receiptList);
    }
    
}