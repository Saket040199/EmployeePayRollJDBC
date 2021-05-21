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
import java.util.HashMap;
import java.util.List;
import java.util.Map;



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

	public Map<String, Double> getAverageSalaryByGender() {
		String sql="select gender, AVG(salary) as avg_salary from employee_payroll group by gender;";
		Map<String, Double> genderToAverageSalaryMap = new HashMap<>();
		try (Connection connection=this.getConnection()){
			
			Statement statement=connection.createStatement();
			ResultSet resultset=statement.executeQuery(sql);
		    while(resultset.next()) {
		    	String gender = resultset.getString("gender");
		    	double salary = resultset.getDouble("avg_salary");
		    	genderToAverageSalaryMap.put(gender, salary);
		    }
		}catch (SQLException e) {
		    	e.printStackTrace();
		    }
		return genderToAverageSalaryMap;
	}

	public EmployeePayRoll addEmployeeToPayRollUC7(String name, double salary, LocalDate startDate, String gender) {
		int employeeId= -1;
		EmployeePayRoll employeePayRoll=null;
		String sql = String.format("Insert into employee_payroll (name,gender,salary,startDate)" +
		                           "Values ('%s','%s','%s','%s')",name,gender,salary,Date.valueOf(startDate));
    	try (Connection connection=this.getConnection()){
		Statement statement=connection.createStatement();
	    int rowAffected= statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
	    if(rowAffected ==1) {
	    	ResultSet resultSet = statement.getGeneratedKeys();
	    	if(resultSet.next()) employeeId=resultSet.getInt(1);
	    }
	    employeePayRoll= new EmployeePayRoll(employeeId, name, salary,startDate);
	    }catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return employeePayRoll;
	}

	public EmployeePayRoll addEmployeeToPayRoll(String name, double salary, LocalDate startDate, String gender) {
		int employeeId= -1;
		Connection connection= null;
		EmployeePayRoll employeePayRoll= null;
		try {
			connection=this.getConnection();
			connection.setAutoCommit(false);
		}catch (SQLException e) {
			e.printStackTrace();
			}
		
		try (Statement statement=connection.createStatement()){
			String sql = String.format("Insert into employee_payroll (name,gender,salary,startDate)" +
                    "Values ('%s','%s','%s','%s')",name,gender,salary,Date.valueOf(startDate));

		    int rowAffected= statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
		    if(rowAffected ==1) {
		    	ResultSet resultSet = statement.getGeneratedKeys();
		    	if(resultSet.next()) employeeId=resultSet.getInt(1);
		    }
		    
		    }catch (SQLException e) {
		    	e.printStackTrace();
		    	try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	
		    }
		try (Statement statement=connection.createStatement()){
			double deductions = salary * 0.2;
			double taxablepay= salary - deductions;
			double tax = taxablepay * 0.1;
			double netPay = salary - tax;
			String sql = String.format("Insert into payroll_details "+ "(employee_id, basic_pay, deductions, taxable_pay, tax, net_pay) values"+
			                           "(%s, %s, %s, %s, %s, %s)", employeeId, salary,deductions, taxablepay, tax, netPay);
			int rowAffected = statement.executeUpdate(sql);
			if(rowAffected ==1) {
				employeePayRoll = new EmployeePayRoll(employeeId, name, salary, startDate);
				
			}
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(connection !=null) {
				try {
					connection.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
			return employeePayRoll;
		
	}
}
