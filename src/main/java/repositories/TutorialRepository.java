
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Tutorial;
@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Integer> {

}
