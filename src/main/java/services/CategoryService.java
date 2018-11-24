
package services;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;

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

	@Autowired
	private FixupTaskService	fixupTaskService;


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
		final Collection<FixupTask> ft = c.getFixupTasks();
		
		if(ft.contains(c))
			ft.remove(c);
		this.fixupTaskService.save(ft.);
		this.categoryRepository.delete(c);
		Assert.isTrue(c.getId()!=0);
		this.categoryRepository.delete(c);
	}
}
