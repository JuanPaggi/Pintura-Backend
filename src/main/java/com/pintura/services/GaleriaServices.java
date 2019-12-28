package com.pintura.services;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pintura.controllers.dto.GaleriaItem;
import com.pintura.exceptions.ApiException;
import com.pintura.models.Galerias;
import com.pintura.models.Imagenes;
import com.pintura.models.Usuarios;
import com.pintura.repository.GaleriasRepository;
import com.pintura.repository.UsuariosRepository;

/**
 * @author Juan Paggi 
 * Logica de servicio para las galerias
 */

@Service
public class GaleriaServices {

	@Autowired
	GaleriasRepository galeriasRepository;
	
	@Autowired
	UsuariosRepository usuariosRepository;
	
	@Autowired
	FileService fileService;
	
	public String formatSeo(String text) {
		return StringUtils.strip(text.replaceAll("([^a-zA-Z0-9]+)", "-"), "-");
	}
	
	public GaleriaItem getGaleria(int id) {
	
		try {
			Optional<Galerias> galeria = galeriasRepository.findById(id);
			GaleriaItem galeriaItem = new GaleriaItem();
			if (galeria.isPresent()) {
				galeriaItem.id_galeria = galeria.get().getId_galeria();
				galeriaItem.fecha = galeria.get().getFecha();
				galeriaItem.titulo = galeria.get().getTitulo();
				ArrayList<String> imagenes = new ArrayList<String>();
				for (Imagenes imagen : galeria.get().getImagenes()) {
					if (imagen != null) {
						imagenes.add("/image/" + imagen.getId_imagen() + "/" + formatSeo(galeria.get().getTitulo())
								+ ".jpg");
					}
				}
				galeriaItem.imagenes = imagenes;
				return galeriaItem;
			} else {
				throw new ApiException(404, "No existe el usuario");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}
		
	}
	
	public List<GaleriaItem> getAllGalerias() {

		List<Galerias> galerias = galeriasRepository.findAll();
		List<GaleriaItem> out = new ArrayList<GaleriaItem>();
		for (Galerias galeria : galerias) {
			GaleriaItem item = new GaleriaItem();
			item.id_galeria = galeria.getId_galeria();
			item.id_usuario = galeria.getId_usuario().getId_usuario();
			item.titulo = galeria.getTitulo();
			item.fecha = galeria.getFecha();

			ArrayList<String> imagenes = new ArrayList<String>();
			for (Imagenes imagen : galeria.getImagenes()) {
				if (imagen != null) {
					imagenes.add("/image/" + imagen.getId_imagen() + "/" + formatSeo(galeria.getTitulo()) + ".jpg");
				}
			}
			item.imagenes = imagenes;

			out.add(item);
		}
		return out;

	}
	
	public int addGaleria(GaleriaItem galeriaIn) throws NoSuchAlgorithmException {

		try {
			Optional<Usuarios> usuario = usuariosRepository.findById(galeriaIn.id_usuario);
			if (!usuario.isPresent()) {
				throw new ApiException(404, "No existe el usuario");
			}
			Galerias galeria = new Galerias();
			galeria.setTitulo(galeriaIn.titulo);
			galeria.setId_usuario(usuario.get());
			galeria.setFecha(new Date());

			Set<Imagenes> imagenes = new HashSet<Imagenes>();
			if(galeriaIn.archivoImagen != null) {				
				for (byte[] imagen : galeriaIn.archivoImagen) {
					Optional<Imagenes> aux = fileService.selectImageFile(imagen);
					if (aux.isPresent()) {
						imagenes.add(aux.get());
					} else {
						imagenes.add(fileService.uploadImageFile(imagen, usuario.get()));
					}
				}
				galeria.setImagenes(imagenes);
			}

			galeria = galeriasRepository.save(galeria);
			return galeria.getId_galeria();
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}
	
	public void removeGaleria(int id) {

		try {
			Optional<Galerias> galeria = galeriasRepository.findById(id);
			if (galeria.isPresent()) {
				galeriasRepository.delete(galeria.get());
			} else {
				throw new ApiException(404, "No existe la galeria");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}
	
	public void editGaleria(int id, GaleriaItem galeriaIn) throws NoSuchAlgorithmException {

		try {
			Optional<Galerias> galeria = galeriasRepository.findById(id);
			Optional<Usuarios> usuario = usuariosRepository.findById(galeriaIn.id_usuario);
			if (!galeria.isPresent()) {
				throw new ApiException(404, "No existe la galeria");
			}
			if (!usuario.isPresent()) {
				throw new ApiException(404, "No existe el usuario");
			}
			Galerias galeriaObj = galeria.get();
			galeriaObj.setTitulo(galeriaIn.titulo);
			galeriaObj.setFecha(new Date());
			galeriaObj.setId_usuario(usuario.get());

			Set<Imagenes> imagenes = new HashSet<Imagenes>();
			for (byte[] imagen : galeriaIn.archivoImagen) {
				imagenes.add(fileService.uploadImageFile(imagen, usuario.get()));
			}

			for (Imagenes imagen : galeria.get().getImagenes()) {
				fileService.removeImage(imagen.getId_imagen());
			}

			galeriaObj.setImagenes(imagenes);

			galeriasRepository.save(galeriaObj);
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}
	
}
