package JavaFileIO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class EmployeePayRollDBService {

	private PreparedStatement employeePayrollDataStatement;
     private static EmployeePayRollDBService employeePayrollDBService;
     
     public EmployeePayRollDBService() {
		// TODO Auto-generated constructor stub
	}
     
     public static EmployeePayRollDBService getInstance() {
    	 if(employeePayrollDBService ==null)
    		 employeePayrollDBService=new EmployeePayRollDBService();
    	 return employeePayrollDBService;
     }
	
	public List<EmployeePayRoll> readData(){
		String sql="select * from employee_payroll;";
		return this.getEmployeePayRollUsingDB(sql);
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

	public int updateEmployeeData(String name, double salary) {
		
		return this.updtaeEmployeeDataUsingStatement(name, salary);
	}

	public int updtaeEmployeeDataUsingStatement(String name, double salary) {
		String sql=String.format("update employee_payroll set salary =%.2f where name ='&s';", salary, name);
		try(Connection connection =this.getConnection()){
			Statement statement=connection.createStatement();
			return statement.executeUpdate(sql);
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<EmployeePayRoll> getEmployeePayrollData(String name) {
		List<EmployeePayRoll> employeePayRolls=null;
		if(this.employeePayrollDataStatement ==null)
			this.prepareStatementForEmployeeData();
		try {
			employeePayrollDataStatement.setString(1, name);
			ResultSet resultSet= employeePayrollDataStatement.executeQuery();
			employeePayRolls=this.getEmployeePayrollData(resultSet);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayRolls;
	}

	private List<EmployeePayRoll> getEmployeePayrollData(ResultSet resultSet) {
		List<EmployeePayRoll> employeePayRolls=new ArrayList<>();
		try {
			while(resultSet.next()) {
				int id= resultSet.getInt("id");
				String name=resultSet.getString("name");
				double salary=resultSet.getDouble("salary");
				LocalDate startDate=resultSet.getDate("startDate").toLocalDate();
				employeePayRolls.add(new EmployeePayRoll(id, name, salary,startDate));
			
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return employeePayRolls;
	}

	private void prepareStatementForEmployeeData() {
	 	try {
	 		Connection connection=this.getConnection();
	 		String sql = "SELECT * from employee_payroll WHERE name = ?";
	 		employeePayrollDataStatement = connection.prepareStatement(sql);
	 		
	 	}catch(SQLException e) {
	 		e.printStackTrace();
	 	}
	}

	public List<EmployeePayRoll> getEmployeeForDateRange(LocalDate startDate, LocalDate endDate) {
		String sql=String.format("Select * from employee_payroll where startdate between '%s' and '%s';", Date.valueOf(startDate),Date.valueOf(endDate));
		return this.getEmployeePayRollUsingDB(sql);
		
	}

	private List<EmployeePayRoll> getEmployeePayRollUsingDB(String sql) {
		List<EmployeePayRoll> employeePayRollList= new ArrayList<>();
		try {
			Connection connection=this.getConnection();
			Statement statement=connection.createStatement();
			ResultSet resultset=statement.executeQuery(sql);
			employeePayRollList=this.getEmployeePayrollData(resultset);
			connection.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayRollList;
	}
}
