
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import domain.Customer;

public interface CustomerRepository {

	//------------------------Query C9----------------------------------------
	//The listing of customers who have published at least 10% more fix-up tasks than the average, ordered by number of applications

	@Query("select c from Customer c join c.fixupTasks t group by c having   c.fixupTasks.size >= ( select avg(c1.fixupTasks.size) from Customer c1) order by t.applications.size")
	Collection<Customer> ListOfMorePublishingCustomers();
}
