package CRUD;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

import DatabaseCommunicate.Database;

public class Add {
	public static void addPart(String name, String make, String model, String year, String quantity, String price, String currency) {
		int numYear = Validation.validateInt(year);
		int numQuantity = Validation.validateInt(quantity);
		Double numPrice = Validation.validateDouble(price);

		int returnVal = 0;
		Connection c = Database.getConnection();

		try{
			CallableStatement call = c.prepareCall("{? = call add_Part(?,?,?,?,?,?,?)}");
			call.registerOutParameter(1, Types.INTEGER);
			call.setString(2, name);
			call.setInt(3, numQuantity);
			call.setString(4, make);
			call.setString(5, model);
			call.setInt(6, numYear);
			call.setDouble(7, numPrice);
			call.setString(8, currency);
			call.execute();
			returnVal = call.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		if(returnVal != 0) {
			JOptionPane.showMessageDialog(null, "Error");
		}
	}

	public static void addEmployee(String alias, String Position, String Location, String range, String currency,
			String isAvalible, String terminate, String Password, String phone) {
		String phoneVer = Validation.validatePhone(phone);
		byte[] salt = Validation.getNewSalt();
		String HashedPassword = Validation.hashPassword(salt, Password);

		int term = Validation.cleanBoolean(terminate);
		int availible = Validation.cleanBoolean(isAvalible);
		int numRange = Validation.validateInt(range);

		int returnVal = 0;
		Connection c = Database.getConnection();

		try{
			CallableStatement call = c.prepareCall("{? = call add_employee(?,?,?,?,?,?,?,?,?,?)}");
			call.registerOutParameter(1, Types.INTEGER);
			call.setString(2, alias);
			call.setString(3, Position);
			call.setString(4,Location);
			call.setInt(5,numRange);
			call.setString(6, currency);
			call.setInt(7,availible);
			call.setInt(8,term);
			call.setString(9,HashedPassword);
			call.setString(10, Validation.getStringFromBytes(salt));
			call.setString(11, phoneVer);
			call.execute();
			returnVal = call.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		if(returnVal != 0) {
			JOptionPane.showMessageDialog(null, "Error");
		}

		// call the add procedure here
	}

	public static void addClient(String alias, String phone, String poBox, String frequent, String banned) {
		String phoneVer = Validation.validatePhone(phone);
		int numfrequent = Validation.cleanBoolean(frequent);
		int numbanned = Validation.cleanBoolean(banned);

		int returnVal = 0;
		Connection c = Database.getConnection();

		try{
			CallableStatement call = c.prepareCall("{? = call add_client(?,?,?,?,?)}");
			call.registerOutParameter(1, Types.INTEGER);
			call.setString(2, alias);
			call.setString(3, phoneVer);
			call.setString(4,poBox);
			call.setInt(5,numfrequent);
			call.setInt(6, numbanned);
			call.execute();
			returnVal = call.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		if(returnVal != 0) {
			JOptionPane.showMessageDialog(null, "Error");
		}
	}

	public static void addOrder(String deadline, String budget, String inProgress, String Client) {
		int numBudget = Validation.validateInt(budget);
		int numInProgress = Validation.cleanBoolean(inProgress);

		int returnVal = 0;
		Connection c = Database.getConnection();

		try{
			CallableStatement call = c.prepareCall("{? = call add_Order(?,?,?,?)}");
			call.registerOutParameter(1, Types.INTEGER);
			call.setString(2, Client);
			call.setString(3, deadline);
			call.setDouble(4, numBudget);
			call.setInt(5, numInProgress);
			call.execute();
			returnVal = call.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		if(returnVal != 0) {
			JOptionPane.showMessageDialog(null, "Error");
		}
		// call the add procedure here
	}
	
	public static void addCurrency(String name, String value) {
		Double numValue = Validation.validateDouble(value);
		int returnVal = 0;
		Connection c = Database.getConnection();

		try{
			CallableStatement call = c.prepareCall("{? = call CreateCurrency(?,?)}");
			call.registerOutParameter(1, Types.INTEGER);
			call.setString(2, name);
			call.setDouble(3, numValue);
			call.execute();
			returnVal = call.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		if(returnVal != 0) {
			JOptionPane.showMessageDialog(null, "Error");
		}
		// call the add procedure here
	}
	public static void addAssignedTo(String EAlias, String CAlias, String Deadline) {
		int returnVal = 0;
		Connection c = Database.getConnection();

		try{
			CallableStatement call = c.prepareCall("{? = call add_assignment(?,?,?)}");
			call.registerOutParameter(1, Types.INTEGER);
			call.setString(2, EAlias);
			call.setString(3, CAlias);
			call.setString(4, Deadline);
			call.execute();
			returnVal = call.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		if(returnVal != 0) {
			JOptionPane.showMessageDialog(null, "Error");
		}
	}
}
