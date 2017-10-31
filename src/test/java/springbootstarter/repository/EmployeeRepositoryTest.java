package springbootstarter.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import springbootstarter.SpringBootCodingApplication;
import springbootstarter.jpa.entity.Employee;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringBootCodingApplication.class)
public class EmployeeRepositoryTest {
	
	@Autowired
	IEmployeeRepository repository;
	
	
	/**
	 * 
	 */
	@Test
	public void createEmployeeTest() {
		Employee emp1 = new Employee(null, "Test name 1", 2000);
		Employee persistedEmp = repository.save(emp1);
		assertEquals(1, persistedEmp.getEmpId().longValue());
		assertEquals(emp1.getName(), persistedEmp.getName());
		assertEquals(emp1.getSalary(), persistedEmp.getSalary());
		
		Employee emp2 = new Employee(null, "Test name 2", 4000);
		persistedEmp = repository.save(emp2);
		assertEquals(2, persistedEmp.getEmpId().longValue());

		persistedEmp = repository.findOne(Long.valueOf(1));
		assertEquals(1, persistedEmp.getEmpId().longValue());
		assertEquals(emp1.getName(), persistedEmp.getName());
		assertEquals(emp1.getSalary(), persistedEmp.getSalary());
		
		List<Employee> persistedEmployees = new ArrayList<>();
		repository.findAll().forEach(persistedEmployees :: add);
		assertEquals(2, persistedEmployees.size());
		assertEquals(emp1.getEmpId(), persistedEmployees.get(0).getEmpId());
		assertEquals(emp1.getName(), persistedEmployees.get(0).getName());
		assertEquals(emp1.getSalary(), persistedEmployees.get(0).getSalary());
		assertEquals(emp2.getEmpId(), persistedEmployees.get(1).getEmpId());
		assertEquals(emp2.getName(), persistedEmployees.get(1).getName());
		assertEquals(emp2.getSalary(), persistedEmployees.get(1).getSalary());
		
		repository.delete(Long.valueOf(1));
		emp1 = repository.findOne(Long.valueOf(1));
		assertNull(emp1);
		
		emp2 = repository.findOne(Long.valueOf(2));
		assertNotNull(emp2);
	}
	
}
