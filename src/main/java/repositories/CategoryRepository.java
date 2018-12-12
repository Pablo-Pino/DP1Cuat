
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends GenericRepository<Category> {

	@Query("select c from Category c where c.parentCategory.id = ?1")
	Collection<Category> findByParentId(int categoryId);
	
}
