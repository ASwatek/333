package CRUD;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import DatabaseCommunicate.Database;

public class Read {

	public static byte[] getSalt(String Alias) {
		String returnVal = new String();
		Connection c = Database.getConnection();

		try {
			if (Alias == null) {
				throw new Exception();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Verifying Credentials Failed(NULL)");
			return null;
		}

		try {
			CallableStatement call = c.prepareCall("{? = call GetSalt(?)}");
			call.registerOutParameter(1, Types.VARCHAR);
			call.setString(2, Alias);
			ResultSet rs = call.executeQuery();
			while (rs.next()) {
				returnVal = rs.getString("Salt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (returnVal == null) {
			JOptionPane.showMessageDialog(null, "Verifying Credentials Failed");
		}
		return Validation.getBytesFromString(returnVal);
	}

	public static int AttemptLogin(String Alias, String hashedPassword) {
		int returnVal = 2;
		Connection c = Database.getConnection();

		try {
			if (Alias == null || hashedPassword == null) {
				throw new Exception();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Verifying Credentials Failed(NULL)");
			return -1;
		}

		try {
			CallableStatement call = c.prepareCall("{? = call VerifyCredentials(?,?)}");
			call.registerOutParameter(1, Types.INTEGER);
			call.setString(2, Alias);
			call.setString(3, hashedPassword);
			call.execute();
			returnVal = call.getInt(1);
			if (returnVal == 0 || returnVal == 1) {
				return returnVal;
			}
			JOptionPane.showMessageDialog(null, "Verifying Credentials Failed");
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	// good ol' duplication :^)

	// get the entire Employee table
	public static ArrayList<ArrayList<String>> getEmployees() {
		Connection c = Database.getConnection();
		ArrayList<ArrayList<String>> table = new ArrayList<>();
		ArrayList<String> header = new ArrayList<String>();
		header.add("Alias: ");
		header.add("Position: ");
		header.add("Location: ");
		header.add("Range: ");
		header.add("Currency: ");
		header.add("Available?: ");
		header.add("Terminate?: ");
		table.add(header);
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT Alias, Position, Location, Range, PreferedCurrency, IsAvailable, Terminate\n FROM Employee\n");

			while (rs.next()) {
				ArrayList<String> row = new ArrayList<String>();
				row.add(rs.getString("Alias"));
				row.add(rs.getString("Position"));
				row.add(rs.getString("Location"));
				row.add(rs.getString("Range"));
				row.add(rs.getString("PreferedCurrency"));
				if (Validation.cleanBoolean(rs.getString("IsAvailable")) == 0) {
					row.add("yes");
				} else {
					row.add("no");
				}
				
				if (Validation.cleanBoolean(rs.getString("Terminate")) == 0) {
					row.add("yes");
				} else {
					row.add("no");
				}

				table.add(row);
			}

		} catch (SQLException e) {
			System.out.println("oingo boingo");
			e.printStackTrace();
		}

		return table;
	}

	// get the entire Order table
	public static ArrayList<ArrayList<String>> getOrders() {
		Connection c = Database.getConnection();
		ArrayList<ArrayList<String>> table = new ArrayList<>();

		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ClientAlias, Deadline, Budget, InProgress\n FROM Order\n");

			while (rs.next()) { // get tableRow by line then populate listRow
								// then send it
				ArrayList<String> row = new ArrayList<String>();
				row.add(rs.getString("ClientAlias"));
				row.add(rs.getString("Deadline"));
				row.add(rs.getString("Budget"));
				row.add(rs.getString("InProgress"));

				table.add(row);
			}

		} catch (SQLException e) {
			System.out.println("bish bash bosh");
			e.printStackTrace();
		}

		return table;
	}

	// get the entire Client table
	public static ArrayList<ArrayList<String>> getClients() {
		Connection c = Database.getConnection();
		ArrayList<ArrayList<String>> table = new ArrayList<>();

		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Alias, [PO Box], Frequent, Banned\n FROM Client\n");

			while (rs.next()) { // get tableRow by line then populate listRow
								// then send it
				ArrayList<String> row = new ArrayList<String>();
				row.add(rs.getString("Alias"));
				row.add(rs.getString("[PO Box]"));
				row.add(rs.getString("Frequent"));
				row.add(rs.getString("Banned"));

				table.add(row);
			}

		} catch (SQLException e) {
			System.out.println("salil");
			e.printStackTrace();
		}

		return table;
	}

	// get the parts with orders in progress
	public static ArrayList<ArrayList<String>> getParts() {
		Connection c = Database.getConnection();
		ArrayList<ArrayList<String>> table = new ArrayList<>();

		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ID, Name, CarID, Quantity, Price, Currency\nFROM Parts\n");
			ArrayList<String> header = new ArrayList<String>();
			// Add all headers
			table.add(header);
			while (rs.next()) { // get tableRow by line then populate listRow
								// then send it
				ArrayList<String> row = new ArrayList<String>();
				row.add(rs.getString("ID"));
				row.add(rs.getString("Name"));

				String carID = rs.getString("CarID");
				if (carID == null) {
					row.add("");
				} else {
					row.add(carID);
				}

				row.add(rs.getString("Quantity"));
				row.add(rs.getString("Price"));
				row.add(rs.getString("Currency"));

				table.add(row);
			}

		} catch (SQLException e) {
			System.out.println("for country");
			e.printStackTrace();
		}

		return table;
	}
	
	public static int updateEmployee(String alias, String position, String location, int range, String currency, int isAvailable, int terminate) {
		Connection c = Database.getConnection();

		//int ret = -1;
		
		try {
			CallableStatement stmt = c.prepareCall("{call update_employee(?, ?, ?, ?, ?, ?, ?)}");
			stmt.setString(1, alias);
			stmt.setString(2, position);
			stmt.setString(3, location);
			stmt.setInt(4, range);
			stmt.setString(5, currency);
			stmt.setInt(6, isAvailable);
			stmt.setInt(7, terminate);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("for country");
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

}