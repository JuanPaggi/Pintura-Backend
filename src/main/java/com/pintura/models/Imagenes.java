package com.pintura.models;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;

import com.pintura.utils.Sha1Hasher;

/**
 * @author Juan Paggi
 * Modelo de la tabla imagenes.
 */

@Entity
public class Imagenes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_imagen;
	
	@Column(nullable = false)
	private byte[] imagen;
	
	@Column(nullable = false)
	private byte[] imagen_checksum;
	
	@Column(nullable = false)
	private Date fecha;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario_subido", referencedColumnName = "id_usuario")
	private Usuarios id_usuario_subido;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="imagen_relevante", referencedColumnName = "id_imagen", nullable = false, insertable = false, updatable = false)
	private List<Noticias> noticias_imagen_relevante;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "noticias_imagenes", 
    joinColumns = 
    @JoinColumn(name = "id_imagen", referencedColumnName = "id_imagen"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_noticia", referencedColumnName = "id_noticia"))
	private List<Noticias> noticias;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "galerias_imagenes", 
    joinColumns = 
    @JoinColumn(name = "id_imagen", referencedColumnName = "id_imagen"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_galeria", referencedColumnName = "id_galeria"))
	private List<Galerias> galerias;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public int getId_imagen() {
		return id_imagen;
	}

	public List<Noticias> getNoticias_imagen_relevante() {
		return noticias_imagen_relevante;
	}

	public void setNoticias_imagen_relevante(List<Noticias> noticias_imagen_relevante) {
		this.noticias_imagen_relevante = noticias_imagen_relevante;
	}

	public void setId_imagen(int id_imagen) {
		this.id_imagen = id_imagen;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) throws NoSuchAlgorithmException {
		this.imagen_checksum = Sha1Hasher.hashBytes(imagen);
		this.imagen = imagen;
	}

	public byte[] getImagen_checksum() {
		return imagen_checksum;
	}

	public void setImagen_checksum(byte[] imagen_checksum) {
		this.imagen_checksum = imagen_checksum;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuarios getId_usuario_subido() {
		return id_usuario_subido;
	}

	public void setId_usuario_subido(Usuarios id_usuario_subido) {
		this.id_usuario_subido = id_usuario_subido;
	}

	public List<Noticias> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticias> noticias) {
		this.noticias = noticias;
	}

	public List<Galerias> getGalerias() {
		return galerias;
	}

	public void setGalerias(List<Galerias> galerias) {
		this.galerias = galerias;
	}
	
}
