package springbootstarter.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import springbootstarter.jpa.entity.Employee;

@Repository
public interface IEmployeeRepository extends CrudRepository<Employee, Serializable> {

}
