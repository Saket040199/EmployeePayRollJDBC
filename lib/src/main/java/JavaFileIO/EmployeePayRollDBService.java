package JavaFileIO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class EmployeePayRollDBService {

	public List<EmployeePayRoll> readData(){
		String sql="select * from employee_payroll;";
		List<EmployeePayRoll> employeePayRollList= new ArrayList<>();
		try {
			Connection connection=this.getConnection();
			Statement statement=connection.createStatement();
			ResultSet resultset=statement.executeQuery(sql);
			while(resultset.next()) {
				int id= resultset.getInt("id");
				String name=resultset.getString("name");
				double salary=resultset.getDouble("salary");
				LocalDate startDate=resultset.getDate("startDate").toLocalDate();
				employeePayRollList.add(new EmployeePayRoll(id, name, salary,startDate));
			}
			connection.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayRollList;
	}

	private Connection getConnection() throws SQLException {
		 String jdbcURL="jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		 String userName="root";
		 String password="Saket@420";
		 Connection con;
		 System.out.println("Connecting to database:"+jdbcURL);
		 con=DriverManager.getConnection(jdbcURL,userName,password);
		 System.out.println("Connection is successfull "+con);
		return con;
	}
}
