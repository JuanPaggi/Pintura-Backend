package com.pintura.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pintura.controllers.dto.UsuarioItem;
import com.pintura.exceptions.ApiException;
import com.pintura.models.Usuarios;
import com.pintura.repository.UsuariosRepository;

/**
 * @author Juan Paggi 
 * Logica de servicio para los usuarios
 */

@Service
public class UsuarioServices {

	@Autowired
	UsuariosRepository usuariosRepository;

	public UsuarioItem getUsuario(int id) {

		try {
			Optional<Usuarios> usuario = usuariosRepository.findById(id);
			UsuarioItem usuarioItem = new UsuarioItem();
			if (usuario.isPresent()) {
				usuarioItem.id_usuario = usuario.get().getId_usuario(); 
				usuarioItem.usuario = usuario.get().getUsuario();
				usuarioItem.clave = usuario.get().getClave();
				return usuarioItem;
			} else {
				throw new ApiException(404, "No existe el usuario");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

	public List<UsuarioItem> getAllUsuarios() {

		try {
			List<Usuarios> usuarios = usuariosRepository.findAll();
			List<UsuarioItem> out = new ArrayList<UsuarioItem>();
			for (Usuarios usuario : usuarios) {
				UsuarioItem item = new UsuarioItem();
				item.id_usuario = usuario.getId_usuario();
				item.usuario = usuario.getUsuario();
				item.clave = usuario.getClave();
				out.add(item);
			}
			return out;
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}
	
	public int addUsuario(UsuarioItem usuarioIn) {
		
		try {			
			Usuarios usuario = new Usuarios();
			usuario.setUsuario(usuarioIn.usuario);
			usuario.setClave(usuarioIn.clave);
			usuario = usuariosRepository.save(usuario);
			return usuario.getId_usuario();
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public void removeUsuario(int id) {
		
		try {			
			Optional<Usuarios> usuario = usuariosRepository.findById(id);
			if (usuario.isPresent()) {
				usuariosRepository.delete(usuario.get());
			}else {
				throw new ApiException(404, "No existe el usuario");
			}	
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public void editUsuario(int id, UsuarioItem usuarioIn) {
		
		try {			
			Optional<Usuarios> usuario = usuariosRepository.findById(id);
			if(usuario.isPresent()) {
				Usuarios usuarioObj = usuario.get();
				usuarioObj.setUsuario(usuarioIn.usuario);
				usuarioObj.setClave(usuarioIn.clave);
				usuariosRepository.save(usuarioObj);
			}else {
				throw new ApiException(404, "No existe el usuario");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public int verificarLogin(String usuario, String clave) {

		try {
			Optional<Usuarios> user = usuariosRepository.findByUser(usuario, clave);
			if (user.isPresent()) {
				return user.get().getId_usuario();
			}
			return 0;
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

}
