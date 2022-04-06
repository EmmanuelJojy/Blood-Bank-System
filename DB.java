import java.sql.*;
import java.util.ArrayList;

public class DB {

	private static Connection c;

	private DB() {
		// Disable Instantiation
		// Pure Static
	}

	public static void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@LAPTOP-JED0L27F:1521/XEPDB1";
			String username = "emmanuel";
			String password = "emmanuel";
			c = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("\n# DB Query Error - " + e);
			System.exit(0);
		} catch (ClassNotFoundException e) {
			System.out.println("\n# sqlite Driver Error - " + e);
			System.exit(0);
		}
		System.out.println("\n  DB Connected\n  Session Connection: " + c);
		System.out.println("  Query Printing: " + Main.printflag + "\n");
	}

	public static void disconnect() {
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println("# DB Query Error - " + e);
			System.exit(0);
		}
	}

	public static void print(String query) {
		if (Main.printflag) {
			System.out.println("  db: " + query);
		}
	}

	public static ResultSet query(String query) {
		ResultSet res = null;
		print(query);
		try {
			res = c.prepareStatement(query).executeQuery();
		} catch (SQLException e) {
			System.out.println("# DB Query Error - " + e);
			System.exit(0);
		}
		return res;
	}

	public static void update(String query) {
		print(query);
		try {
			c.prepareStatement(query).executeUpdate();
		} catch (SQLException e) {
			System.out.println("# DB Query Error - " + e);
			System.exit(0);
		}
	}

	public static void executeProcedure(String query) {
		print(query);
		try {
			c.prepareCall(query).execute();
		} catch (SQLException e) {
			System.out.println("# DB Query Error - " + e);
			System.exit(0);
		}
	}

	public static String executeFunction(String query) {
		print(query);
		String res = "";
		try {
			CallableStatement cstm = c.prepareCall(query + "INTO ?");
			cstm.registerOutParameter(1, Types.VARCHAR);
			cstm.execute();
			res = cstm.getString(1);
		} catch (SQLException e) {
			System.out.println("# DB Query Error - " + e);
			System.exit(0);
		}
		return res;
	}

	public static ArrayList<ArrayList<String>> fetchList(String table, String condition, String... column) {
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		ArrayList<String> interColumn = new ArrayList<>();
		try {
			for (int i = 0; i < column.length; i++) {
				interColumn = new ArrayList<String>();
				String query = "SELECT " + column[i] + " FROM " + table;
				if (!condition.equals("")) {
					query += " WHERE " + condition;
				}
				ResultSet res = DB.query(query);
				while (res.next()) {
					interColumn.add(res.getString(column[i]));
				}
				data.add(interColumn);
			}
		} catch (SQLException e) {
			System.out.println("# DB Query Error - " + e);
			System.exit(0);
		}
		return data;
	}
}
