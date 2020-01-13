package com.pintura.services;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pintura.controllers.dto.NoticiaItem;
import com.pintura.exceptions.ApiException;
import com.pintura.models.Imagenes;
import com.pintura.models.Noticias;
import com.pintura.models.Tags;
import com.pintura.models.Usuarios;
import com.pintura.repository.NoticiasRepository;
import com.pintura.repository.TagRepository;
import com.pintura.repository.UsuariosRepository;

/**
 * @author Juan Paggi 
 * Logica de servicio para las noticias
 */

@Service
public class NoticiasServices {

	@Autowired
	NoticiasRepository noticiasRepository;
	
	@Autowired
	UsuariosRepository usuariosRepository;
	
	@Autowired
	FileService fileService;

	@Autowired
	TagRepository tagRepository;
	
	public String formatSeo(String text) {
		return StringUtils.strip(text.replaceAll("([^a-zA-Z0-9]+)", "-"), "-");
	}
	
	public NoticiaItem getNoticia(int id) {

		try {
			Optional<Noticias> noticia = noticiasRepository.findById(id);
			NoticiaItem noticiaItem = new NoticiaItem();
			if (noticia.isPresent()) {
				noticiaItem.id_noticia = noticia.get().getId_noticia();
				noticiaItem.titulo = noticia.get().getTitulo();
				noticiaItem.cuerpo = noticia.get().getCuerpo();
				ArrayList<Integer> tag_id = new ArrayList<>();
				List<Tags> tag_noticia = noticia.get().getTags();
				for (Tags tag : tag_noticia) {
					tag_id.add(tag.getId_tag());
				}
				noticiaItem.tags = tag_id;
				noticiaItem.id_usuario = noticia.get().getId_usuario().getId_usuario();
				
				ArrayList<String> imagenes = new ArrayList<String>();
				for (Imagenes imagen : noticia.get().getImagenes()) {
					if (imagen != null) {
						imagenes.add("/image/" + imagen.getId_imagen() + "/" + formatSeo(noticia.get().getTitulo())
								+ ".jpg");
					}
				}
				noticiaItem.imagenes = imagenes;
				
				String imagen_relevante = null;
				if (noticia.get().getImagen_relevante() != null) {
					imagen_relevante = "/image/" + noticia.get().getImagen_relevante().getId_imagen() + "/" + formatSeo(noticia.get().getTitulo()) + ".jpg";
				}
				noticiaItem.imagen_relevante = imagen_relevante;
				
				return noticiaItem;
			} else {
				throw new ApiException(404, "No existe la noticia");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}
	
	public List<NoticiaItem> getAllNoticias() {

		List<Noticias> noticias = noticiasRepository.findAll();
		List<NoticiaItem> out = new ArrayList<NoticiaItem>();
		for (Noticias noticia : noticias) {
			NoticiaItem item = new NoticiaItem();
			item.id_noticia = noticia.getId_noticia();
			item.titulo = noticia.getTitulo();
			item.cuerpo = noticia.getCuerpo();
			ArrayList<Integer> tag_id = new ArrayList<>();
			List<Tags> tag_noticia = noticia.getTags();
			for (Tags tag : tag_noticia) {
				tag_id.add(tag.getId_tag());
			}
			item.tags = tag_id;
			item.id_usuario = noticia.getId_usuario().getId_usuario();

			ArrayList<String> imagenes = new ArrayList<String>();
			for (Imagenes imagen : noticia.getImagenes()) {
				if (imagen != null) {
					imagenes.add("/image/" + imagen.getId_imagen() + "/" + formatSeo(noticia.getTitulo()) + ".jpg");
				}
			}
			item.imagenes = imagenes;
			
			String imagen_relevante = null;
			if (noticia.getImagen_relevante() != null) {
				imagen_relevante = "/image/" + noticia.getImagen_relevante().getId_imagen() + "/" + formatSeo(noticia.getTitulo()) + ".jpg";
			}
			item.imagen_relevante = imagen_relevante;

			out.add(item);
		}
		return out;

	}
	
	public int addNoticia(NoticiaItem noticiaIn) throws NoSuchAlgorithmException {

		try {
			Optional<Usuarios> usuario = usuariosRepository.findById(noticiaIn.id_usuario);
			if (!usuario.isPresent()) {
				throw new ApiException(404, "No existe el usuario");
			}
			Noticias noticia = new Noticias();
			noticia.setTitulo(noticiaIn.titulo);
			noticia.setCuerpo(noticiaIn.cuerpo);
			noticia.setId_usuario(usuario.get());
			
			List<Tags> lista_tag = tagRepository.findAllById(noticiaIn.tags);
			if (lista_tag.size() != noticiaIn.tags.size()) {
				throw new ApiException(404, "Error al cargar los tags");
			}
			noticia.setTags(lista_tag);
			
			Set<Imagenes> imagenes = new HashSet<Imagenes>();
			if(noticiaIn.archivoImagen != null) {				
				for (byte[] imagen : noticiaIn.archivoImagen) {
					Optional<Imagenes> aux = fileService.selectImageFile(imagen);
					if (aux.isPresent()) {
						imagenes.add(aux.get());
					} else {
						imagenes.add(fileService.uploadImageFile(imagen, usuario.get()));
					}
				}
				noticia.setImagenes(imagenes);
			}
			
			Imagenes imagen_relevante;
			if(noticiaIn.archivoImagen_relevante != null) {
				Optional<Imagenes> aux = fileService.selectImageFile(noticiaIn.archivoImagen_relevante);
				if (aux.isPresent()) {
					imagen_relevante = aux.get();
				} else {
					imagen_relevante = fileService.uploadImageFile(noticiaIn.archivoImagen_relevante, usuario.get());
				}
				noticia.setImagen_relevante(imagen_relevante);
			}

			noticia = noticiasRepository.save(noticia);
			return noticia.getId_noticia();
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}
	
	public void removeNoticia(int id) {

		try {
			Optional<Noticias> noticia = noticiasRepository.findById(id);
			if (noticia.isPresent()) {
				noticiasRepository.delete(noticia.get());
			} else {
				throw new ApiException(404, "No existe la noticia");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}
	
	public void editNoticia(int id, NoticiaItem noticiaIn) throws NoSuchAlgorithmException {

		try {
			Optional<Noticias> noticia = noticiasRepository.findById(id);
			Optional<Usuarios> usuario = usuariosRepository.findById(noticiaIn.id_usuario);
			if (!noticia.isPresent()) {
				throw new ApiException(404, "No existe la galeria");
			}
			if (!usuario.isPresent()) {
				throw new ApiException(404, "No existe el usuario");
			}
			Noticias noticiaObj = noticia.get();
			noticiaObj.setTitulo(noticiaIn.titulo);
			noticiaObj.setCuerpo(noticiaIn.cuerpo);
			noticiaObj.setId_usuario(usuario.get());

			Set<Imagenes> imagenes = new HashSet<Imagenes>();
			for (byte[] imagen : noticiaIn.archivoImagen) {
				imagenes.add(fileService.uploadImageFile(imagen, usuario.get()));
			}
			
			Imagenes imagen_relevante;
			if(noticiaIn.archivoImagen_relevante != null) {
				Optional<Imagenes> aux = fileService.selectImageFile(noticiaIn.archivoImagen_relevante);
				if (aux.isPresent()) {
					imagen_relevante = aux.get();
				} else {
					imagen_relevante = fileService.uploadImageFile(noticiaIn.archivoImagen_relevante, usuario.get());
				}
				noticiaObj.setImagen_relevante(imagen_relevante);
			}


			noticiaObj.setImagenes(imagenes);

			noticiasRepository.save(noticiaObj);
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}
	
}
