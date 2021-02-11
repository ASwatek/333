package PopulateDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;

import DatabaseCommunicate.Database;

import CRUD.Add;

public class Populate {

	/**
	 * Used https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
	 * for help spliting csv file
	 */
	public static void populateDatabase() {
		if(Database.getConnection() == null){
			createDatabase();
		}

		//from employee table
		try (BufferedReader reader = new BufferedReader(new FileReader("./Employees.csv"))) {
			//String line = reader.readLine();
			String line;
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] pieces = line.split(",");
				Add.addCurrency(pieces[8], pieces[9]);
				Add.addEmployee(pieces[0], pieces[3], pieces[5], pieces[4], pieces[8], pieces[7], pieces[2], pieces[6], pieces[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		//from orders table
		try (BufferedReader reader = new BufferedReader(new FileReader("./Orders.csv"))) {
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] pieces = line.split(",");
				Add.addPart(pieces[0], pieces[1], pieces[2], pieces[3],pieces[6], pieces[4], pieces[5]);
				Add.addClient(pieces[10], pieces[14], pieces[11], pieces[12], pieces[13]);
				Add.addOrder(pieces[16], pieces[15], pieces[17], pieces[10]);
				Add.addAssignedTo(pieces[8], pieces[10], pieces[16]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

	}
	
	private static void createDatabase() {
		System.out.println("Creating Database....");
		Connection master = Database.getMasterConnection();
		callScripts(master);
		Populate.populateDatabase();
	}
	
	private static void callScripts(Connection connection){
		
		System.out.println("Database Created");
	}

}
