package com.pintura.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pintura.models.Imagenes;

/**
 * @author Juan Paggi
 * Repositorio de la tabla Imagenes.
 */

@Repository
public interface ImagenesRepository extends JpaRepository<Imagenes, Long>{
	
	// Consulta para buscar una imagen
	@Query(value="select * from imagenes where imagen = ?1",nativeQuery=true)
	Optional<Imagenes> findByImgen(byte[] img);
}
