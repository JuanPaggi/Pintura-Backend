package com.pintura.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * @author Juan Paggi
 * Modelo de la tabla usuarios.
 */

@Entity
public class Usuarios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_usuario;
	
	@Column(nullable = false)
	private String usuario;
	
	@Column(nullable = false)
	private String clave;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_usuario_subido", referencedColumnName = "id_usuario", nullable = false, insertable = false, updatable = false)
	private List<Imagenes> imagenes;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario", nullable = false, insertable = false, updatable = false)
	private List<Galerias> galerias;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario", nullable = false, insertable = false, updatable = false)
	private List<Noticias> noticias;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public List<Imagenes> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<Imagenes> imagenes) {
		this.imagenes = imagenes;
	}

	public List<Galerias> getGalerias() {
		return galerias;
	}

	public void setGalerias(List<Galerias> galerias) {
		this.galerias = galerias;
	}

	public List<Noticias> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticias> noticias) {
		this.noticias = noticias;
	}

}
