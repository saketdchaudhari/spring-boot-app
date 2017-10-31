package springbootstarter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springbootstarter.jpa.entity.Employee;
import springbootstarter.repository.IEmployeeRepository;

@Service
public class EmployeeServiceImpl implements IEmployeeService{
	
	@Autowired(required=true)
	private IEmployeeRepository repository;

	@Override
	public Employee getEmployee(Long id) {
		return repository.findOne(id);
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		repository.findAll().forEach(employees::add);
		return employees;
	}

	@Override
	public Employee addEmployee(Employee emp) {
		return repository.save(emp);
	}

	@Override
	public void deleteEmployee(Long id) {
		repository.delete(id);
	}
	
}
