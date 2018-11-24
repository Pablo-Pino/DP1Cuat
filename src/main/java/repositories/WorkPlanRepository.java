
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.WorkPlan;

@Repository
public interface WorkPlanRepository extends JpaRepository<WorkPlan, Integer> {

}
