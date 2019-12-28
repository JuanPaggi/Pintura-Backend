package com.pintura.models;

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

/**
 * @author Juan Paggi
 * Modelo de la tabla galerias.
 */

@Entity
public class Galerias {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_galeria;
	
	@Column(nullable = false)
	private Date fecha;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario")
	private Usuarios id_usuario;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "galerias_imagenes", 
    joinColumns = 
    @JoinColumn(name = "id_galeria", referencedColumnName = "id_galeria"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_imagen", referencedColumnName = "id_imagen"))
	private List<Imagenes> imagenes;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public List<Imagenes> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<Imagenes> imagenes) {
		this.imagenes = imagenes;
	}

	public int getId_galeria() {
		return id_galeria;
	}

	public void setId_galeria(int id_galeria) {
		this.id_galeria = id_galeria;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuarios getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Usuarios id_usuario) {
		this.id_usuario = id_usuario;
	}
	
}
