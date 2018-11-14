
package Repositories;

import org.springframework.data.jpa.repository.Query;

public interface ApplicationRepository {

	//------------------------Query C7----------------------------------------
	//The ratio of rejected applications.

	@Query("select count(app)/(select count(ap) from Application ap)from Application app where app.status='REJECTED'")
	Double appsRejectedRatio();
	//------------------------Query C8----------------------------------------
	//The ratio of pending applications that cannot change its status because their time period elapsed.
	@Query("select 100*(count(app)/(select count(ap) from Application ap))from Application app where app.status='PENDING' and app.fixupTask.end > CURRENT_TIMESTAMP ")
	Double lateApplicationsRatio();
}
