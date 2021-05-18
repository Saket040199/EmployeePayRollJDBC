package JavaFileIO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayRollService {
	
	public enum IOService {CONSOLE_IO,FILE_IO,DB_IO,REST_IO}
	
	private List<EmployeePayRoll> employeePayrollList;
	
	
	public EmployeePayRollService(List<EmployeePayRoll> employeePayrollList) {
		this.employeePayrollList=employeePayrollList;
	}
	
	public EmployeePayRollService() {
		this.employeePayrollList=employeePayrollList;
		// TODO Auto-generated constructor stub
	}

	public void read(Scanner sc) {
		System.out.println("Employee ID ");
		Integer id=sc.nextInt();
		System.err.println("Employee name ");
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
		this.employeePayrollList=EmployeePayRollDBService.getInstance().readData();
		return this.employeePayrollList;
	}

	public void updateEmployeeSalary(String name, double salary) {
		int result=EmployeePayRollDBService.getInstance().updateEmployeeData(name,salary);
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
		List<EmployeePayRoll> employeePayRolls= EmployeePayRollDBService.getInstance().getEmployeePayrollData(name);
		return employeePayRolls.get(0).equals(getEmployeePayroll(name));
	}
	
}
