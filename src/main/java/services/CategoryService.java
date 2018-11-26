
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;
import domain.FixupTask;

@Service
@Transactional
public class CategoryService {

	//Repositorio

	@Autowired
	private CategoryRepository	categoryRepository;


	//Servicios de soporte

	//Constructor

	public CategoryService() {
		super();
	}
	// --------------------CRUD methods----------------------------

	public Category create() {
		final Category c = new Category();
		c.setFixupTasks(new ArrayList<FixupTask>());

		return c;
	}

	public Category findOne(final int categoryId) {
		return this.categoryRepository.findOne(categoryId);
	}

	public Collection<Category> findAll() {
		Collection<Category> c;

		c = this.categoryRepository.findAll();
		Assert.notNull(c);

		return c;
	}

	public Category save(final Category category) {
		Assert.notNull(category);
		return this.categoryRepository.save(category);
	}

	public void delete(final Category c) {
		Assert.notNull(c);
		Assert.isTrue(c.getId() != 0);
		Assert.isTrue(this.categoryRepository.exists(c.getId()));
		this.categoryRepository.delete(c);
	}

	public Boolean tieneHijas(final Category c) {
		final Boolean res = false;
		if (c.getChildsCategories().isEmpty())
			return true;
		return res;

	}
	public void deleteCategories(final Category c) {
		Assert.notNull(c);
		Assert.isTrue(c.getId() != 0);
		Assert.isTrue(this.categoryRepository.exists(c.getId()));

		if (c.getName().equals("categoryRoot"))
			System.out.println("No se puede borrar la Categoria raiz");
		if (!(c.getChildsCategories().isEmpty())) {
			for (final Category child : c.getChildsCategories())
				this.deleteCategories(child);
			Assert.isTrue(this.tieneHijas(c));

		}
		this.categoryRepository.delete(c);

	}
}
