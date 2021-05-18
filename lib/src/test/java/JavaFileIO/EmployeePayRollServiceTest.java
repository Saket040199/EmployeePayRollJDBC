package JavaFileIO;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import JavaFileIO.EmployeePayRollService.IOService;



public class EmployeePayRollServiceTest {

	

	@Test
	public void givenEmployeePayroolInDB_ShouldMatchThreeCount() {
		EmployeePayRollService employeePayrollService=new EmployeePayRollService();
	    List<EmployeePayRoll> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
	    System.out.println(employeePayrollData);
	    Assert.assertEquals(3,employeePayrollData.size());
	    System.out.println("\n");
	}
	
	@Test
	public void givenNewSalaryforEmplyee_WhenUpdated_ShouldSyncWithDB() {
		EmployeePayRollService employeePayrollService=new EmployeePayRollService();
		List<EmployeePayRoll> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateEmployeeSalary("Farzan",3000000.00);
		boolean result=employeePayrollService.checkEmployeePayrollInSyncWithDB("Farzan");
		
		Assert.assertTrue(result);
	}
}