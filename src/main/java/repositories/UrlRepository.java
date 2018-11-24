
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Integer> {

}
