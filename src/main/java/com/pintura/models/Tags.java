package com.pintura.models;

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

/**
 * @author Juan Paggi
 * Modelo de la tabla tags.
 */

@Entity
public class Tags {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_tag;
	
	@Column(nullable = false)
	private String tag;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "noticas_tags", 
    joinColumns = 
    @JoinColumn(name = "id_tag", referencedColumnName = "id_tag"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_noticia", referencedColumnName = "id_noticia"))
	private List<Noticias> noticias;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public int getId_tag() {
		return id_tag;
	}

	public void setId_tag(int id_tag) {
		this.id_tag = id_tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<Noticias> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticias> noticias) {
		this.noticias = noticias;
	}
	
}
