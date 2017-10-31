package springbootstarter.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springbootstarter.jpa.entity.Employee;
import springbootstarter.service.IEmployeeService;

/**
 * EmployeeController exposes REST end-point to find, findAll, create and delete
 * {@link Employee} resource.
 * 
 * @author saket chaudhari
 */
@RestController
@RequestMapping("employees")
public class EmployeeController {
	private static final Logger logger = LogManager.getLogger(EmployeeController.class);

	@Autowired
	private IEmployeeService service;

	/**
	 * This method finds the {@link Employee} which matches the employeeId
	 * 
	 * @param employeeId
	 *            id to search employee.
	 * @return {@link ResponseEntity<Employee>}
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> findEmployee(@PathVariable(value = "id", required = true) long employeeId) {
		logger.info("Received request for EmployeeController#findEmployee. EmpId ::  {}", employeeId);
		Employee employee = service.getEmployee(employeeId);
		if (employee != null) {
			logger.info("Return response for EmployeeController#findEmployee. Result ::  {}", employee);
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		} else {
			logger.info("Return response for EmployeeController#findEmployee. Result ::  {}", employee);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * This method finds the list of {@link Employee} resource
	 * 
	 * @return {@link ResponseEntity<List<Employee>>}
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> findAllEmployees() {
		logger.info("Received request for EmployeeController#findAllEmployees");
		List<Employee> employees = service.getAllEmployees();
		if (employees == null || employees.isEmpty()) {
			logger.info("Return response for EmployeeController#findAllEmployees. Result ::  {}", employees);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		for (Employee employee : employees) {
			Link employeeLink = ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder.methodOn(EmployeeController.class).findEmployee(employee.getEmpId()))
					.withRel("employee");
			employee.add(employeeLink);
		}
		logger.info("Return response for EmployeeController#findAllEmployees. Result ::  {}", employees);
		return ResponseEntity.ok(employees);
	}

	/**
	 * This method creates the {@link Employee} resource
	 * 
	 * @param employee
	 *            {@link Employee} resource to be created
	 * @return list of {@link ResponseEntity<Employee>}
	 */
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> createEmployee(@RequestBody(required = true) Employee employee) {
		logger.info("Received request for EmployeeController#createEmployee" + employee);
		Employee persistedEmployee = service.addEmployee(employee);
		Link employeeLink = ControllerLinkBuilder.linkTo(
				ControllerLinkBuilder.methodOn(EmployeeController.class).findEmployee(persistedEmployee.getEmpId()))
				.withRel("employee");
		Link allEmployeeLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(EmployeeController.class).findAllEmployees())
				.withRel("all-employees");
		persistedEmployee.add(employeeLink);
		persistedEmployee.add(allEmployeeLink);
		logger.info("Return response for EmployeeController#createEmployee" + persistedEmployee);
		return new ResponseEntity<Employee>(persistedEmployee, HttpStatus.CREATED);
	}

	/**
	 * This method deletes the {@link Employee} resource by given employeeId
	 * 
	 * @param employeeId
	 *            Employee id of employee to be deleted.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeEmployee(@PathVariable(value = "id", required = true) long employeeId) {
		logger.info("Received request for EmployeeController#removeEmployee" + employeeId);
		service.deleteEmployee(employeeId);
		logger.info("Return response for EmployeeController#removeEmployee. Result :: Employee deleted");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
