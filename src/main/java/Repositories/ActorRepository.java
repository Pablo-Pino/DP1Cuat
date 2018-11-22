
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	//---------------------Query C1------------------------------
	//The average, the minimum, the maximum, and the standard deviation of the number of fix-up tasks per user

	@Query("select min(u.fixupTasks.size),max(u.fixupTasks.size),avg(u.fixupTasks.size),sqrt(sum(u.fixupTasks.size * u.fixupTasks.size) /count(u.fixupTasks.size) - (avg(u.fixupTasks.size) *avg(u.fixupTasks.size))) from Actor u")
	Double[] fixupTasksStats();
	//-----------------------------------------------------------

}
