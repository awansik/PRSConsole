package prs.ui;

import java.util.List;

import prs.business.Product;
import prs.business.ProductDB;
import prs.util.Console;

public class PRSConsoleApp {

	public static void main(String[] args) {
		System.out.println("Welcome to the PRS Console App - JDBC");
		ProductDB pdb = new ProductDB();
		String command = "";
		while(!command.equals("9")) {
			displayMenu();
			command = Console.getString("Enter command: ");
			if (command.equals("1")) {
				List<Product> products = pdb.getAll();
				
			for (Product p : products) {
				System.out.println(p);
			}
		}
		else if (command.equals("2")) {
			int pid = Console.getInt("Enter product ID: ");
			getProductByID(pdb, pid);
		}
		else if (command.equals("3")) {
			//Get VendorID,PartNumber,Name,Price from user
			int vid = Console.getInt("Enter Vendor ID: ");
			String partNum = Console.getString("Enter Part Number: ");
			String name = Console.getString("Enter Part Name: ");
			double price = Console.getDouble("Enter Price: ");
			Product product = new Product(vid, partNum, name, price);
			
			if (pdb.add(product)) {
				System.out.println("Product added successfully");
			}
			else {
				System.out.println("Error adding product");
			}
		}	
		else if (command.equals("4")) {
			//UPDATE - prompt user for product id, then new name
			int pid = Console.getInt("Enter Product ID: ");
			Product product = getProductByID(pdb, pid);
			
			String newName = Console.getString("Enter New Name: ");
			
			product.setName(newName);
			
			if (pdb.update(product)) {
				System.out.println("Product updated successfully");
			}
			else {
				System.out.println("Error updated product");
			}
		}	
		else if (command.equals("5")) {
			//Delete - prompt user for product id, then delete it
			int pid = Console.getInt("Enter Product ID: ");
			Product product = getProductByID(pdb, pid);
			
			if (pdb.delete(product)) {
				System.out.println("Product deleted successfully");
			}
			else {
				System.out.println("Error deleting product");
			}
		}	
	}
		
	
		
		System.out.println("bye");
	}

	private static Product getProductByID(ProductDB pdb, int pid) {
		Product product = pdb.getProduct(pid);
			
		if (product!=null) {
			System.out.println(product);
		}
		else {
			System.out.println("No product matching id: "+pid);
		}
		return product;
	}

	private static void displayMenu() {
		StringBuilder sb = new StringBuilder("Command Menu:\n");
		sb.append("1 - List all Products\n");
		sb.append("2 - Get a Product\n");
		sb.append("3 - Add a Product\n");
		sb.append("4 - Update a Product\n");
		sb.append("5 - Remove a Product\n");
		sb.append("9 - Exit\n");
		System.out.println(sb.toString());
	}
}
