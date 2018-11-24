
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Application;

public interface ApplicationRepository extends JpaRepository<Application,Integer>{
	
	
	//---------------------Query C4------------------------------
	//The average, the minimum, the maximum, and the standard deviation of the
	//price offered in the applications

		@Query("select min(a.price),max(a.price),avg(a.price),sqrt(sum(a.price * a.price) /count(a.price) - (avg(a.price) *avg(a.price))) from Application a")
		Double[] applicationPriceStats();
	
	
	//------------------------Query C5----------------------------------------
	//The ratio of pending applications.
	
		@Query ("select (select count(*) from a where status='PENDING' )/count(*)*100 from Application a")
		Double pendingRatio();

	
	//------------------------Query C6----------------------------------------
	//The ratio of accepted applications.
		
		@Query("select (select count(*) from a where status='ACCEPTED' )/count(*)*100 from Application a")
		Double acceptedRatio();
		
	//------------------------Query C7----------------------------------------
	//The ratio of rejected applications.

		@Query("select count(app)/(select count(ap) from Application ap)from Application app where app.status='REJECTED'")
		Double appsRejectedRatio();
	
	
	//------------------------Query C8----------------------------------------
	//The ratio of pending applications that cannot change its status because their time period elapsed.
	
		@Query("select 100*(count(app)/(select count(ap) from Application ap))from Application app where app.status='PENDING' and app.fixupTask.end > CURRENT_TIMESTAMP ")
		Double lateApplicationsRatio();
	

	
		
}
