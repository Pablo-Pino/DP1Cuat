
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Url;

public interface UrlRepository extends JpaRepository<Url, Integer> {

}
