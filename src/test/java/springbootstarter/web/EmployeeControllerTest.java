package springbootstarter.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import springbootstarter.jpa.entity.Employee;
import springbootstarter.service.IEmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
	@Autowired
	private MockMvc mockedMvc;

	@MockBean
	private IEmployeeService mockedService;

	@Test
	public void findEmployeeTestCase1() throws Exception {
		final ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

		when(mockedService.getEmployee(anyLong())).thenReturn(new Employee(Long.valueOf(1), "Test Name", 8000));

		mockedMvc.perform(get("/employees/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().json("{\"empId\":1,\"name\":\"Test Name\",\"salary\":8000}"));

		verify(mockedService, times(1)).getEmployee(idCaptor.capture());
		verifyNoMoreInteractions(mockedService);
		assertEquals(idCaptor.getValue(), Long.valueOf(1));
	}

	@Test
	public void findEmployeeTestCase2() throws Exception {
		final ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

		when(mockedService.getEmployee(anyLong())).thenReturn(null);

		mockedMvc.perform(get("/employees/2").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent())
				.andExpect(content().string(""));

		verify(mockedService, times(1)).getEmployee(idCaptor.capture());
		verifyNoMoreInteractions(mockedService);
		assertEquals(idCaptor.getValue(), Long.valueOf(2));
	}

	@Test
	public void findAllEmployeesTestCase1() throws Exception {
		when(mockedService.getAllEmployees()).thenReturn(prepareAllEmployeeResult());

		mockedMvc.perform(get("/employees").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(content().json(
						"[{\"empId\":1,\"name\":\"Test Name 1\",\"salary\":8000}, {\"empId\":2,\"name\":\"Test Name 2\",\"salary\":6000}]"));

		verify(mockedService, times(1)).getAllEmployees();
		verifyNoMoreInteractions(mockedService);
	}

	@Test
	public void findAllEmployeesTestCase2() throws Exception {
		when(mockedService.getAllEmployees()).thenReturn(new ArrayList<Employee>());

		mockedMvc.perform(get("/employees").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent())
				.andExpect(content().string(""));

		verify(mockedService, times(1)).getAllEmployees();
		verifyNoMoreInteractions(mockedService);
	}

	@Test
	public void createEmployeesTestCase1() throws Exception {
		when(mockedService.addEmployee(any(Employee.class))).thenReturn(new Employee(Long.valueOf(1), "Test Name 1", 8000));
		
		mockedMvc
				.perform(post("/employees").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"Test Name 1\",\"salary\":8000}"))
				.andExpect(status().isCreated())
				.andExpect(content().string("{\"empId\":1,\"name\":\"Test Name 1\",\"salary\":8000,\"_links\":{\"employee\":{\"href\":\"http://localhost/employees/1\"},\"all-employees\":{\"href\":\"http://localhost/employees\"}}}"));

		verify(mockedService, times(1)).addEmployee(any(Employee.class));
		verifyNoMoreInteractions(mockedService);
	}

	@Test
	public void createEmployeesTestCase2() throws Exception {
		mockedMvc.perform(post("/employees").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().isBadRequest()).andExpect(content().string(""));

		verify(mockedService, times(0)).addEmployee(any(Employee.class));
		verifyNoMoreInteractions(mockedService);
	}

	@Test
	public void removeEmployeesTestCase1() throws Exception {
		final ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

		mockedMvc.perform(delete("/employees/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(""));

		verify(mockedService, times(1)).deleteEmployee(idCaptor.capture());
		verifyNoMoreInteractions(mockedService);
		assertEquals(idCaptor.getValue(), Long.valueOf(1));
	}

	@Test
	public void removeEmployeesTestCase2() throws Exception {
		mockedMvc.perform(delete("/employees").accept(MediaType.APPLICATION_JSON)).andExpect(status().is(405))
				.andExpect(content().string(""));

		verify(mockedService, times(0)).deleteEmployee(anyLong());
		verifyNoMoreInteractions(mockedService);
	}

	private List<Employee> prepareAllEmployeeResult() {
		List<Employee> employees = new ArrayList<>();
		Employee emp = new Employee(Long.valueOf(1), "Test Name 1", 8000);
		employees.add(emp);
		emp = new Employee(Long.valueOf(2), "Test Name 2", 6000);
		employees.add(emp);
		return employees;
	};

}
