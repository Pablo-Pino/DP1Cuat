
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.WorkPlan;

@Repository
public interface WorkPlanRepository extends JpaRepository<WorkPlan, Integer> {

	@Query("select w from WorkPlan w where w.handyWorker.id = ?1")
	Collection<WorkPlan> findWorkPlanByHandyWorker(int handyWorkerId);
	
}
