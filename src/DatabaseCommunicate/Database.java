package DatabaseCommunicate;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class Database {
	private static Database db = new Database();
	private static Connection connection = null;
	private static String username;
	private static String password;
	private final static String URL = "jdbc:sqlserver://golem.csse.rose-hulman.edu;databaseName=${dbName};user=${user};password={${pass}}";

	private Database() {
		try(Scanner properties = new Scanner(new File("chopShopApp.properties"))){
			while(properties.hasNextLine()){
				String line = properties.nextLine();
				if(line.startsWith("serverUsername=")){
					username = line.replace("serverUsername=", "");
				}
				else if(line.startsWith("serverPassword=")){
					password = line.replace("serverPassword=", "");
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void Finalize(){
		closeConnection();
	}
	
	public static Database getDatabase(){
		return db;
	}
	
	public static Connection getMasterConnection() {
		try {
			return connectToMaster();
		} catch (SQLException e) {
			System.out.println("Could not connect to Master");
			e.printStackTrace();
		}
		return null;
	}

	private static Connection connectToMaster() throws SQLException{
		// TODO Auto-generated method stub
		return null;
	}

	public static Connection getConnection() {
		if (connection != null) {
			return connection;
		}
		try {
			connect();
		} catch (SQLException e) {
			System.out.println("Connection Failed");
			
		}
		return connection;
	}

	private static void connect() throws SQLException {
		System.out.println("Attempting to connect to Chopshop....");
		String dbName = "ChopShop-S2G8";

		String newString = URL.replace("${dbName}", dbName);
		String newString2 = newString.replace("${user}", username);
		String newStringF = newString2.replace("${pass}", password);

		connection = DriverManager.getConnection(newStringF);
	}
	
	private void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
