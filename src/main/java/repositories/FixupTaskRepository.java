
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixupTask;

@Repository
public interface FixupTaskRepository extends JpaRepository<FixupTask, Integer> {

	@Query("select (count(f)*1.0)/(select count(f1)*1.0 from FixupTask f1) from FixupTask f where f.complaints is not empty")
	double getRatioFixupTasksWithComplaints();

}
