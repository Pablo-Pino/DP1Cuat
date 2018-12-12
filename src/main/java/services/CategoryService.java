
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import domain.Category;
import domain.FixupTask;

@Service
@Transactional
public class CategoryService extends GenericService<Category, CategoryRepository> implements ServiceObjectDependantI<Category, Category> {

	//Repositorio

	@Autowired
	private CategoryRepository	categoryRepository;

	//Servicios de soporte
	@Autowired
	public FixupTaskService		fixUpTaskService;


	//Constructor

	public CategoryService() {
		super();
	}
	// --------------------CRUD methods----------------------------

	public Category create() {
		final Category c = new Category();
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
		final Category padre = category.getParentCategory();
		//	final comprueba que no final hago bucles,que mi final hijo no es final un padre  o final un abuelo final etc etc
		if (!this.findByParent(category).contains(padre))
			return this.categoryRepository.save(category);
		else
			throw new IllegalArgumentException("Incompatibilidad de recursividad en el guardado");
	}

	public Boolean tieneHijas(final Category c) {
		final Boolean res = true;
		if (this.findByParent(c).isEmpty())
			return false;
		return res;

	}

	public void changeFixupTaskCategory(final Category cat) {
		final Collection<FixupTask> res = this.fixUpTaskService.findAll();
		for (final FixupTask f : res)
			if (f.getCategory().equals(cat)) {
				final Category padre = cat.getParentCategory();
				f.setCategory(padre);
				this.save(padre);
				this.fixUpTaskService.save(f);
			}
	}

	public void delete(final Category cat) {
		Assert.notNull(cat);
		//	Assert.isTrue(this.categoryRepository.exists(cat.getId()));
		if (cat.getName().equals("CATEGORY"))
			throw new IllegalArgumentException("NO SE PUEDE BORRAR LA CATEGORIA RAIZ");
		if (!(cat.getName().equals("CATEGORY"))) {
			if (this.tieneHijas(cat) == false) {
				this.changeFixupTaskCategory(cat);
				this.categoryRepository.delete(cat);
			}

			if (this.tieneHijas(cat) == true)
				for (final Category hija : this.findByParent(cat))
					this.delete(hija);

		}

	}
	
	public Collection<Category> findByParent(Category parent) {
		Assert.notNull(parent);
		Assert.isTrue(parent.getId() > 0);
		Assert.notNull(this.categoryRepository.findOne(parent.getId()));
		return this.categoryRepository.findByParentId(parent.getId());
	}

	@Override
	public Collection<Category> findAll(Category dependency) {
		return this.findByParent(dependency);
	}

	@Override
	public Category create(Category dependency) {
		this.checkObject(dependency);
		this.checkPermisionActor(null, new String[] {
			Authority.ADMIN
		});
		Category res = new Category();
		res.setParentCategory(dependency);
		return res;
	}
	
}
