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
		this.employeePayrollList=new EmployeePayRollDBService().readData();
		return this.employeePayrollList;
	}
	
}
