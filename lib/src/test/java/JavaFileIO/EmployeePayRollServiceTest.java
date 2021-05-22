package JavaFileIO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;


import JavaFileIO.EmployeePayRollService.IOService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EmployeePayRollServiceTest {

	

/*	@Test
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
	
	
	@Test
	public void givenPayrollData_WhenAverageRetrievedByGender_ShouldReturnProperValue() {
		EmployeePayRollService employeePayrollService=new EmployeePayRollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		Map<String, Double> averageSalaryByGender = employeePayrollService.readAverageSalaryByGender(IOService.DB_IO);
		Assert.assertTrue(averageSalaryByGender.get("M").equals(3500000.00) && 
				          averageSalaryByGender.get("F").equals(4000000.00) );
		
	}
	
	@Test
	public void givenNewEmployee_WhenAdded_ShouldSyncWithDB() {
		EmployeePayRollService employeePayrollService=new EmployeePayRollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.addEmployeeToPayRoll("Mark",5000000.00,LocalDate.now(),"M");
		boolean result= employeePayrollService.checkEmployeePayrollInSyncWithDB("Mark");
		Assert.assertTrue(result);
	}
	
	*/
	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
		
	}
	
	@Test
	public void givenEmployeeDataInJsonServer_WhenRetrieved_ShouldMatchCount() {
		EmployeePayRoll[] arrayOfEmps = getEmployeeList();
		EmployeePayRollService employeePayRollService;
		employeePayRollService = new EmployeePayRollService(Arrays.asList(arrayOfEmps));
		long entries = employeePayRollService.countEntries(IOService.REST_IO);
		Assert.assertEquals(2, entries);
	}

	public EmployeePayRoll[] getEmployeeList() {
        Response response =  RestAssured.get("/Employee");
	    System.out.println("Employee payroll entries in JSON Server : \n " +response.asString());
	    EmployeePayRoll[] arrayOfEmps = new Gson().fromJson(response.asString(), EmployeePayRoll[].class);
		return arrayOfEmps;
	}
	
	@Test
	public void givenNewEmployee_WhenAdded_ShouldMatch201ResponseAndCount() {
		EmployeePayRollService employeePayRollService;
		EmployeePayRoll[] arrayOfEmps = getEmployeeList();
		employeePayRollService= new EmployeePayRollService(Arrays.asList(arrayOfEmps));
		
		EmployeePayRoll employeePayRoll = new EmployeePayRoll(0, "MArk", 3000.00, LocalDate.now());
		Response response = addEmployeeToJsonServer(employeePayRoll);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);
		
		employeePayRoll = new Gson().fromJson(response.asString(), EmployeePayRoll.class);
		employeePayRollService.addEmployeeToPayRoll(employeePayRoll, IOService.REST_IO);
		long entries = employeePayRollService.countEntries(IOService.REST_IO);
		Assert.assertEquals(3, entries);
	}

	public Response addEmployeeToJsonServer(EmployeePayRoll employeePayRoll) {
		String empJson = new Gson().toJson(employeePayRoll);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(empJson);
		return request.post("/Employee");
		
	}
}