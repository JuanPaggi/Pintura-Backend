package com.pintura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pintura.models.Galerias;

/**
 * @author Juan Paggi
 * Repositorio de la tabla Galerias.
 */

@Repository
public interface GaleriasRepository extends JpaRepository <Galerias , Long> {

}
