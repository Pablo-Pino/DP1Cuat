
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
public class SectionService extends GenericService<Section, SectionRepository> implements ServiceObjectDependantI<Section, Tutorial> {

	@Autowired
	private SectionRepository	repository;

	@Autowired
	private ServiceUtils		serviceUtils;
	@Autowired
	private TutorialService		tutorialService;


	@Override
	public Collection<Section> findAll(final Tutorial dependency) {
		this.serviceUtils.checkId(dependency.getId());
		Assert.notNull(this.tutorialService.findOne(dependency.getId()));
		return this.repository.findByTutorial(dependency.getId());
	}

	@Override
	public Section create(final Tutorial dependency) {
		final Section res = new Section();
		res.setPictures(new ArrayList<Url>());
		return res;
	}

	@Override
	public Section save(final Section object) {
		final Section section = this.checkObjectSave(object);
		if (object.getId() == 0) {
			this.serviceUtils.checkId(object.getTutorial());
			Assert.notNull(object.getTutorial());
			object.setNumberOrder(object.getTutorial().getSections().size());
		} else {
			object.setNumberOrder(section.getNumberOrder());
			object.setTutorial(section.getTutorial());
		}
		this.serviceUtils.checkActor(section.getTutorial().getHandyWorker());
		this.serviceUtils.checkAuthority(Authority.HANDYWORKER);
		final Section res = this.repository.save(object);
		return res;
	}

	public void move(final Section section1, final Section section2) {
		final Section s1 = super.checkObject(section1);
		final Section s2 = super.checkObject(section2);
		final Integer pos1 = s1.getNumberOrder();
		final Integer pos2 = s2.getNumberOrder();
		s1.setNumberOrder(pos2);
		s2.setNumberOrder(pos1);
		this.save(s1);
		this.save(s2);
	}

	@Override
	public void delete(final Section object) {
		final Section section = this.checkObject(object);
		this.serviceUtils.checkActor(section.getTutorial().getHandyWorker());
		this.serviceUtils.checkAuthority(Authority.HANDYWORKER);
		this.repository.delete(section);
	}

	public void flush() {
		this.repository.flush();
	}

}
