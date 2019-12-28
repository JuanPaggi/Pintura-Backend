package com.pintura.controllers.dto;

import java.util.ArrayList;
import java.util.Date;

public class GaleriaItem {

	public int id_galeria;
	public int id_usuario;
	public String titulo;
	public Date fecha;
	
	public ArrayList<String> imagenes;
	public ArrayList<byte[]> archivoImagen;
}
