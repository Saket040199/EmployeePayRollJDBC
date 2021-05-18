package JavaFileIO;

public class EmployeePayRoll {
	
		String id;
		String name;
		String salary;
		public EmployeePayRoll(String id, String name, String salary) {
			super();
			this.id = id;
			this.name = name;
			this.salary = salary;
		}
		@Override
		public String toString() {
			return "EmployeePayRoll [id=" + id + ", name=" + name + ", salary=" + salary + "]";
		}
		
		
	}


