package com.pintura.models;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * @author Juan Paggi
 * Modelo de la tabla noticias.
 */

@Entity
public class Noticias {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_noticia;
	
	@Column(nullable = false)
	private String titulo;
	
	@Column(nullable = false)
	private String cuerpo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario")
	private Usuarios id_usuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="imagen_relevante", referencedColumnName = "id_imagen")
	private Imagenes imagen_relevante;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "noticas_tags", 
    joinColumns = 
    @JoinColumn(name = "id_noticia", referencedColumnName = "id_noticia"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_tag", referencedColumnName = "id_tag"))
	private List<Tags> tags;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "noticias_imagenes", 
    joinColumns = 
    @JoinColumn(name = "id_noticia", referencedColumnName = "id_noticia"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_imagen", referencedColumnName = "id_imagen"))
	private Set<Imagenes> imagenes;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public List<Tags> getTags() {
		return tags;
	}

	public void setTags(List<Tags> tags) {
		this.tags = tags;
	}

	public Set<Imagenes> getImagenes() {
		return imagenes;
	}

	public void setImagenes(Set<Imagenes> imagenes) {
		this.imagenes = imagenes;
	}

	public int getId_noticia() {
		return id_noticia;
	}

	public void setId_noticia(int id_noticia) {
		this.id_noticia = id_noticia;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Usuarios getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Usuarios id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Imagenes getImagen_relevante() {
		return imagen_relevante;
	}

	public void setImagen_relevante(Imagenes imagen_relevante) {
		this.imagen_relevante = imagen_relevante;
	}
	
}
