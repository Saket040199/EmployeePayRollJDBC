package JavaFileIO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayRollService {
	
	
	private List<EmployeePayRoll> employeePayrollList;
	
	public EmployeePayRollService(List<EmployeePayRoll>employeePayRollList) {
		this.employeePayrollList=employeePayRollList;
	}
	
	public void read(Scanner sc) {
		System.out.println("Employee ID ");
		String id=sc.nextLine();
		System.err.println("Employee name ");
		String name=sc.nextLine();
		System.out.println("Employee Salary");
		String salary=sc.nextLine();
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
	
}
