package springbootstarter.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import springbootstarter.SpringBootCodingApplication;
import springbootstarter.jpa.entity.Employee;
import springbootstarter.repository.IEmployeeRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringBootCodingApplication.class)
public class EmployeeServiceImplTest {
	
	@InjectMocks
	private EmployeeServiceImpl service;
	
	@Mock
	private IEmployeeRepository repository;
	
	// Repository returns record.
	@Test
	public void getEmployeeTest1() {
		final ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
		when(repository.findOne(anyInt())).thenReturn(new Employee(Long.valueOf(1), "Test Name", 8000));
		Employee emp = service.getEmployee(Long.valueOf(1));
		verify(repository, times(1)).findOne(idCaptor.capture());
		verifyNoMoreInteractions(repository);
		assertEquals(1, idCaptor.getValue().longValue());
		assertEquals(1, emp.getEmpId().longValue());
		assertEquals("Test Name", emp.getName());
		assertEquals(Integer.valueOf(8000), emp.getSalary());
	}
	
	// Repository returns null.
	@Test
	public void getEmployeeTest2() {
		final ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
		when(repository.findOne(anyInt())).thenReturn(null);
		Employee emp = service.getEmployee(Long.valueOf(1));
		verify(repository, times(1)).findOne(idCaptor.capture());
		verifyNoMoreInteractions(repository);
		assertEquals(null, emp);
	}
	
	// Repository return records.
	@Test
	public void getAllEmployeesTest1() {
		when(repository.findAll()).thenReturn(prepareEmployeesData());
		List<Employee> emps = service.getAllEmployees();
		verify(repository, times(1)).findAll();
		verifyNoMoreInteractions(repository);
		assertEquals(2, emps.size());
		assertEquals(1, emps.get(0).getEmpId().longValue());
		assertEquals(2, emps.get(1).getEmpId().longValue());
		assertEquals("Name 1", emps.get(0).getName());
		assertEquals("Name 2", emps.get(1).getName());
		assertEquals(2000, emps.get(0).getSalary().intValue());
		assertEquals(4000, emps.get(1).getSalary().intValue());
	}
	
	@Test
	public void deleteEmployeeTest1() {
		final ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
		service.deleteEmployee(Long.valueOf(1));
		verify(repository, times(1)).delete(idCaptor.capture());
		verifyNoMoreInteractions(repository);
		assertEquals(1, idCaptor.getValue().longValue());
	}
	
	private List<Employee> prepareEmployeesData() {
		List<Employee> emps = new ArrayList<Employee>(2);
		emps.add(new Employee(Long.valueOf(1), "Name 1", 2000));
		emps.add(new Employee(Long.valueOf(2), "Name 2", 4000));
		return emps;
	}
}
