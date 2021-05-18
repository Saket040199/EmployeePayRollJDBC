package JavaFileIO;

import java.time.LocalDate;

public class EmployeePayRoll {
	
		int id;
		String name;
		double salary;
		public LocalDate startDate;
		
		public EmployeePayRoll(Integer id, String name, Double salary) {
			super();
			this.id = id;
			this.name = name;
			this.salary = salary;
		}
		public EmployeePayRoll(int id, String name, double salary, LocalDate startDate) {
			this(id, name, salary);
			this.startDate=startDate;
		}
		@Override
		public String toString() {
			return "EmployeePayRoll [id=" + id + ", name=" + name + ", salary=" + salary + "]";
		}
		
		@Override
		public boolean equals(Object obj) {
			if(this == obj) return true;
			if(obj == null || getClass() != obj.getClass()) return false;
			EmployeePayRoll that=(EmployeePayRoll) obj;
			return id== that.id &&
					Double.compare(that.salary, salary)== 0 &&
					name.equals(that.name);
		
		}
		
		
	}


