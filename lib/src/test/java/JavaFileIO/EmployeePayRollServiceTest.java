package JavaFileIO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import JavaFileIO.EmployeePayRollService.IOService;





public class EmployeePayRollServiceTest {

	
/*
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
		employeePayrollService.updateEmployeeSalary("Terisa",3000000.00);
		boolean result=employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
		
		Assert.assertTrue(result);
	}
	
	@Test
	public void givenDateAsInput_WhenExecuted_ShouldReturnNumberOfEmployees() {
		EmployeePayRollService employeePayrollService=new EmployeePayRollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		LocalDate startDate= LocalDate.of(2021, 01, 01);
		LocalDate endDate= LocalDate.now();
		List<EmployeePayRoll> employeePayrollData = 
				employeePayrollService.readEmployeePayrollDataForDateRange(IOService.DB_IO, startDate,endDate);
		 Assert.assertEquals(3,employeePayrollData.size());
				
	}
	*/
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void givenPayrollData_WhenAverageRetrievedByGender_ShouldReturnProperValue() {
		EmployeePayRollService employeePayrollService=new EmployeePayRollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		Map<String, Double> averageSalaryByGender = employeePayrollService.readAverageSalaryByGender(IOService.DB_IO);
		Assert.assertTrue(averageSalaryByGender.get("M").equals(3500000) && averageSalaryByGender.get("F").equals(4000000) );
		
	}
}