package JavaFileIO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeePayRollService {
	
	public enum IOService {CONSOLE_IO,FILE_IO,DB_IO,REST_IO}
	
	private List<EmployeePayRoll> employeePayrollList;
	private EmployeePayRollDBService employeePayRollDBService;
	
	public EmployeePayRollService() {
		employeePayRollDBService=EmployeePayRollDBService.getInstance();
	}
	
	public EmployeePayRollService(List<EmployeePayRoll> employeePayrollList) {
		this();
		this.employeePayrollList=employeePayrollList;
	}
	
	public void read(Scanner sc) {
		System.out.println("Employee ID ");
		Integer id=sc.nextInt();
		System.out.println("Employee name ");
		String name=sc.nextLine();
		System.out.println("Employee Salary");
		Double salary=sc.nextDouble();
		employeePayrollList.add(new EmployeePayRoll(id, name, salary));
		
	}
	public void write() {
		System.out.println("Employee PayRole Data : \n"+employeePayrollList);
	}
	public static void main(String[] args) {
		ArrayList<EmployeePayRoll> employeePayrollList=new ArrayList<>();
		EmployeePayRollService employeePayRollService=new EmployeePayRollService(employeePayrollList);
		Scanner sc=new Scanner(System.in);
		employeePayRollService.read(sc);
		employeePayRollService.write();
		
	}

	public List<EmployeePayRoll> readEmployeePayrollData(IOService ioService) {
	if(ioService.equals(IOService.DB_IO))
		this.employeePayrollList=employeePayRollDBService.readData();
		return this.employeePayrollList;
	}

	public void updateEmployeeSalary(String name, double salary) {
		int result=employeePayRollDBService.updateEmployeeData(name,salary);
	    if(result==0) return;
	    EmployeePayRoll employeePayRoll=this.getEmployeePayroll(name);
	    if (employeePayRoll !=null) employeePayRoll.salary=salary;
	}

	private EmployeePayRoll getEmployeePayroll(String name) {
		return this.employeePayrollList.stream()
				    .filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name))
				    .findFirst()
				    .orElse(null);
	}

	public boolean checkEmployeePayrollInSyncWithDB(String name) {
		List<EmployeePayRoll> employeePayRolls= employeePayRollDBService.getEmployeePayrollData(name);
		return employeePayRolls.get(0).equals(getEmployeePayroll(name));
	}

	public List<EmployeePayRoll> readEmployeePayrollDataForDateRange(IOService ioService, LocalDate startDate,
			LocalDate endDate) {
		if(ioService.equals(IOService.DB_IO)) {
			
	     return employeePayRollDBService.getEmployeeForDateRange(startDate,endDate);
		}return null;	
	}

	public Map<String, Double> readAverageSalaryByGender(IOService ioService) {
		if(ioService.equals(IOService.DB_IO)) {
			return employeePayRollDBService.getAverageSalaryByGender();
		}
		return null;
	}

	public void addEmployeeToPayRoll(String name, double salary, LocalDate startDate, String gender) {
		employeePayrollList.add(employeePayRollDBService.addEmployeeToPayRoll(name,salary,startDate,gender));
		
		
	}
	
}
