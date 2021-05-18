package JavaFileIO;


import java.lang.IllegalStateException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;



public class DBDemo {
	public static void main(String[] args) {
		String jdbcURl="jdbc:mysql://localhost:3306/payroll_service?useSSl=false";
		String userName= "root";
		String password= "Saket@420";
		Connection connection;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded");
			
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("Cannot find class",e);
		}
		listDriver();
		
		try {
			System.out.println("Connecting to database:"+jdbcURl);
			connection=DriverManager.getConnection(jdbcURl,userName,password);
			System.out.println("Connection Successfull "+connection);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void listDriver() {
		Enumeration<java.sql.Driver> driverList= DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println("  "+driverClass.getClass().getName());
		}
		
	}

}
