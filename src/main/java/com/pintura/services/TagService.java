package com.pintura.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pintura.controllers.dto.TagItem;
import com.pintura.exceptions.ApiException;
import com.pintura.models.Tags;
import com.pintura.repository.TagRepository;

/**
 * @author Juan Paggi
 * Logica de servicio para los tags
 */

@Service
public class TagService {
	
	@Autowired
	TagRepository tagRepository;
	
	public TagItem getTag(int id) {
		
		try {			
			Optional<Tags> tag = tagRepository.findById(id);
			TagItem tagItem = new TagItem();
			if(tag.isPresent()) {				
				tagItem.id_tag = tag.get().getId_tag();
				tagItem.etiqueta = tag.get().getTag();
				return tagItem;
			}else {
				throw new ApiException(404, "No existe el tag");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public List<TagItem> getAllTags() {
		
		try {			
			List<Tags> tags = tagRepository.findAll();
			List<TagItem> out = new ArrayList<TagItem>();
			for(Tags tag: tags) {
				TagItem item = new TagItem();
				item.id_tag = tag.getId_tag();
				item.etiqueta = tag.getTag();
				out.add(item);
			}
			return out;
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public long addTag(TagItem tagIn) {
		
		try {			
			Tags tag = new Tags();
			tag.setTag(tagIn.etiqueta);
			tag = tagRepository.save(tag);
			return tag.getId_tag();
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public void removeTag(int id) {
		
		try {			
			Optional<Tags> tag = tagRepository.findById(id);
			if (tag.isPresent()) {
				tagRepository.delete(tag.get());
			}else {
				throw new ApiException(404, "No existe el tag");
			}	
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public void editTag(int id, TagItem tagIn) {
		
		try {			
			Optional<Tags> tag = tagRepository.findById(id);
			if(tag.isPresent()) {
				Tags tagObj = tag.get();
				tagObj.setTag(tagIn.etiqueta);
				tagRepository.save(tagObj);
			}else {
				throw new ApiException(404, "No existe el tag");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
}
