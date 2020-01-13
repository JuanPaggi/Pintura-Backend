package com.pintura.controllers.dto;

import java.util.ArrayList;
import java.util.List;

public class NoticiaItem {
	
	public int id_noticia;
	public int id_usuario;
	public String titulo;
	public String cuerpo;
	
	public List<Integer> tags;
	
	public ArrayList<String> imagenes;
	public ArrayList<byte[]> archivoImagen;
	
	public String imagen_relevante;
	public byte[] archivoImagen_relevante;
}
