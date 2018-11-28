
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

	public FixupTaskService		fixUpTaskService;


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
		//	final comprueba que no final hago bucles,que mi final hijo no es final un padre  o final un abuelo final etc etc
		return this.categoryRepository.save(category);
	}
	//
	//	public void delete(final Category c) {
	//		Assert.notNull(c);
	//		Assert.isTrue(c.getId() != 0);
	//		Assert.isTrue(this.categoryRepository.exists(c.getId()));
	//		this.categoryRepository.delete(c);
	//	}

	public Boolean tieneHijas(final Category c) {
		final Boolean res = false;
		if (c.getChildsCategories().isEmpty())
			return true;
		return res;

	}

	//aqui porque ma sale stackoverflow error y que pasa con as fixuptask ,si borro su categoria ,debe ponerles la 
	//inmediata superior
	public void deleteCategories(final Category c) {
		Assert.notNull(c);
		Assert.isTrue(c.getId() != 0);
		Assert.isTrue(this.categoryRepository.exists(c.getId()));
		//me da nullpointerexception aqui
		final Collection<FixupTask> res = this.fixUpTaskService.findAll();
		if (!c.getName().equals("CATEGORY") && !(c.getChildsCategories().isEmpty()))
			if (!(c.getChildsCategories().isEmpty())) {
				for (final Category child : c.getChildsCategories())
					this.deleteCategories(child);
				Assert.isTrue(this.tieneHijas(c));

			}
		if ((!c.getName().equals("CATEGORY")) && (this.tieneHijas(c) == true)) {

			for (final FixupTask f : res)
				if (f.getCategory().equals(c)) {
					final Category padre = c.getParentCategory();
					this.changeFixupTaskCategory(f, padre);
				}
			this.categoryRepository.delete(c);
		} else
			//System.out.println();
			throw new IllegalArgumentException("No se puede borrar la Categoria raiz");

	}

	//	public void reasignaCategoryFixUpTask(final Category c) {
	//		//si borro esa categoria tengo que asignarles la inmediata superior a los fixuptask
	//		final Collection<FixupTask> res = this.fixUpTaskService.findAll();
	//		Category padre = new Category();
	//		for (final FixupTask f : res)
	//			if (f.getCategory().equals(c))
	//				padre = c.getParentCategory();
	//		//f.setCategory(padre);
	//
	//	}

	//-----------

	//	public void categoryAbuelo(final Category c) {
	//		if (!(c.getParentCategory().getName() == "CATEGORY") || c.getName().equals("CATEGORY")) {
	//			final Category padre = c.getParentCategory();
	//			final Category abuelo = padre.getParentCategory();
	//
	//		}
	//
	//	}

	public void deleteCategory(final Category c) {
		final Collection<FixupTask> res = this.fixUpTaskService.findAll();
		if (!(c.getName() == ("CATEGORY")))
			for (final FixupTask t : res)
				if (t.getCategory().equals(c)) {
					final Category padre = c.getParentCategory();
					this.changeFixupTaskCategory(t, padre);
					if (c.getChildsCategories().isEmpty())
						this.categoryRepository.delete(c);
					else
						for (final Category v : c.getChildsCategories())
							this.deleteCategory(v);
				}
	}
	public void changeFixupTaskCategory(final FixupTask f, final Category cat) {
		f.setCategory(cat);
		this.fixUpTaskService.save(f);

	}

}
