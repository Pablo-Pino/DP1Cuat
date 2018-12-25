
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixupTask;

@Repository
public interface FixupTaskRepository extends JpaRepository<FixupTask, Integer> {

	@Query("select f from FixupTask f where f.category.id = ?1")
	Collection<FixupTask> findByCategoryId(int categoryId);
	
	@Query("select f from FixupTask f where f.customer.id = ?1")
	Collection<FixupTask> findByCustomerId(int customerId);
	
	@Query("select f from FixupTask f where f.warranty.id = ?1")
	Collection<FixupTask> findByWarrantyId(int warrantyId);
	
//	@Query("select (count(f)*1.0)/(select count(f1)*1.0 from FixupTask f1) from FixupTask f where f.complaints is not empty")
//	double getRatioFixupTasksWithComplaints();

	//------------------------Query C2----------------------------------------
	//The average, the minimum, the maximum, and the standard deviation of the number of applications per fix-up task

	//@Query("select min(f.applications.size),max(f.applications.size),avg(f.applications.size),sqrt(sum(f.applications.size * f.applications.size) /count(f.applications.size) - (avg(f.applications.size) *avg(f.applications.size))) from FixupTask f")
	//Double[] appsStats();

	@Query("select min(count(a)), max(count(a)), avg(count(a)), sqrt(sum(count(a) * count(a)) /count(a1) - (avg(count(a)) *avg(count(a)))) from Application a, Application a1 group by a.fixupTask")
	Double[] appsStats();
	
	//------------------------Query C3---------------------------------------
	//The average, the minimum, the maximum, and the standard deviation of the maximum price of the fix-up tasks.

	@Query("select min(f.maximumPrice),max(f.maximumPrice),avg(f.maximumPrice),sqrt(sum(f.maximumPrice * f.maximumPrice) /count(f.maximumPrice) - (avg(f.maximumPrice) *avg(f.maximumPrice))) from FixupTask f")
	Double[] maxFixupStaskStats();

	//----------------------------Query B1/1-------------------------------------
	
	//@Query("select min(t.complaints.size), max(t.complaints.size),avg(t.complaints.size),sqrt(sum(t.complaints.size*t.complaints.size)/count(t.complaints.size)-(avg(t.complaints.size)*avg(t.complaints.size))) from FixupTask t")
	//Double[] fixupComplaintsStats();
	
	@Query("select min(count(c)), max(count(c)), avg(count(c)), sqrt(sum(count(c) * count(c)) /count(c1) - (avg(count(c)) *avg(count(c)))) from Complaint c, Complaint c1 group by c.fixuptask")
	Double[] fixupComplaintsStats();
	
	//-----------------------------Query B3------------------------
	//@Query("select (count(f)*1.0)/(select count(f1)*1.0 from FixupTask f1) from FixupTask f where f.complaints is not empty)")
	//Double ratiofixupComplaint();
	
	@Query("select (count(f)*1.0)/(select count(f1)*1.0 from FixupTask f1) from FixupTask f where not exists(select c from Complaint c where c.fixuptask.id = f.id))")
	Double ratiofixupComplaint();
}
