package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Folder;

import repositories.FolderRepository;

@Service
@Transactional
public class FolderService2 {

		
		//Managed Repository
	

		@Autowired
		private FolderRepository folderRepository;

		// Supporting Service
		
		
		// Simple CRUD methods
		public Folder create() {
			final Folder f = new Folder();
			return f;
		}
		
		public Collection<Folder> findAll() {
			return this.folderRepository.findAll();
		}

		public Folder findOne(final int folderId) {
			return this.folderRepository.findOne(folderId);
		}
		
		public Folder save(final Folder f) {
			Assert.notNull(f);
			return this.folderRepository.save(f);
		}

		public void delete(final Folder f) {
			Assert.notNull(f);
			this.folderRepository.delete(f);
		}
	}
