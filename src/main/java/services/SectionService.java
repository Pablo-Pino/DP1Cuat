
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import security.Authority;
import domain.Section;
import domain.Tutorial;
import domain.Url;

@Service
@Transactional
public class SectionService {

	// Repository
	
	@Autowired
	private SectionRepository	repository;

	// Services
	
	@Autowired
	private ServiceUtils		serviceUtils;
	@Autowired
	private TutorialService		tutorialService;

	// CRUD methods

	public Section findOne(Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<Section> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<Section> findAll() {
		return this.repository.findAll();
	}
	
	public Collection<Section> findAll(final Tutorial dependency) {
		this.serviceUtils.checkId(dependency.getId());
		Assert.notNull(this.tutorialService.findOne(dependency.getId()));
		return this.repository.findByTutorial(dependency.getId());
	}

	public Section create(final Tutorial dependency) {
		final Section res = new Section();
		res.setPictures(new ArrayList<Url>());
		return res;
	}

	public Section save(final Section object) {
		final Section section = (Section) this.serviceUtils.checkObjectSave(object);
		if (section.getId() == 0) {
			this.serviceUtils.checkId(section.getTutorial());
			Assert.notNull(section.getTutorial());
			section.setNumberOrder(this.findAll(section.getTutorial()).size());
		} else {
			section.setPictures(object.getPictures());
			section.setText(object.getText());
			section.setTitle(object.getTitle());
		}
		this.serviceUtils.checkActor(section.getTutorial().getHandyWorker());
		this.serviceUtils.checkAuthority(Authority.HANDYWORKER);
		final Section res = this.repository.save(section);
		return res;
	}

	public void delete(final Section object) {
		final Section section = (Section) this.serviceUtils.checkObject(object);
		this.serviceUtils.checkActor(section.getTutorial().getHandyWorker());
		this.serviceUtils.checkAuthority(Authority.HANDYWORKER);
		this.repository.delete(section);
	}

	// Other methods
	
	public void move(final Section section1, final Section section2) {
		final Section s1 = (Section) this.serviceUtils.checkObject(section1);
		final Section s2 = (Section) this.serviceUtils.checkObject(section2);
		final Integer pos1 = s1.getNumberOrder();
		final Integer pos2 = s2.getNumberOrder();
		s1.setNumberOrder(pos2);
		s2.setNumberOrder(pos1);
		this.save(s1);
		this.save(s2);
	}

	public void flush() {
		this.repository.flush();
	}

}
