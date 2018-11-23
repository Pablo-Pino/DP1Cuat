
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.WorkPlan;

public interface WorkPlanRepository extends JpaRepository<WorkPlan, Integer> {

}
