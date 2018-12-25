
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	//@Query("select c from Customer c join c.fixupTasks f group by c.id order by sum(f.complaints.size) desc")
	//Collection<Customer> getTop3CustomerWithMoreComplaints();
	
	@Query("select f.customer from FixupTask f, Complaint c group by f.customer having c.fixuptask.id = f.id order by count(c) desc")
	Collection<Customer> getTop3CustomerWithMoreComplaints();

	//@Query("select c from Customer c join c.fixupTasks t group by c having c.fixupTasks.size >= ( select avg(c1.fixupTasks.size) from Customer c1) order by t.applications.size")
	//Collection<Customer> ListOfMorePublishingCustomers();
	
	// ¿ Es necesaria esta query ?
	@Query("select f.customer from FixupTask f, Application a group by f.customer having count(f) >= avg(count(f)) and a.fixupTask.id = f.id order by count(a)")
	Collection<Customer> ListOfMorePublishingCustomers();

	@Query("select c from Customer c where c.userAccount.id = ?1")
	Customer findByUserAccountId(int userAccountId);

	//The listing of customers who have published at least 10% more fix-up tasks than the average, ordered by number of applications
	//@Query("select c from Customer c join c.fixupTasks ft group by c.id having ft.size/(select avg(c1.fixupTasks.size) from Customer c1) > 1.1 order by ft.applications.size)")
	//Collection<Customer> listCustomer10();
	
	@Query("select f.customer from FixupTask f, Application a group by f.customer having count(f) >= 1.1*avg(count(f)) and a.fixupTask.id = f.id order by count(a)")
	Collection<Customer> listCustomer10();

}
