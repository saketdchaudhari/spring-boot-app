package springbootstarter.service;

import java.util.List;

import springbootstarter.jpa.entity.Employee;

/**
 * Provides methods to perform the CRUD operation on {@link Employee}.
 * 
 * @author saket chaudhari
 *
 */
public interface IEmployeeService {

	/**
	 * This method finds the {@link Employee} by given employeeId
	 * 
	 * @param Long employeeId
	 *            Employee id to search Employee.
	 * @return {@link Employee}
	 */
	Employee getEmployee(Long employeeId);

	/**
	 * This method finds the list of {@link Employee}
	 * 
	 * @return List of {@link Employee}
	 */
	List<Employee> getAllEmployees();

	/**
	 * This method creates new {@link Employee} in database.
	 * 
	 * @param {@link Employee} employee employee to be created.
	 * @return {@link Employee} Newly created employee.
	 */
	Employee addEmployee(Employee employee);

	/**
	 * This method removes new {@link Employee} from database.
	 * 
	 * @param Long employeeId
	 *            id of employee to remove.
	 */
	void deleteEmployee(Long employeeId);

}
