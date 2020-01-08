package com.pintura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pintura.models.Noticias;

/**
 * @author Juan Paggi
 * Repositorio de la tabla noticias.
 */

@Repository
public interface NoticiasRepository extends JpaRepository <Noticias , Integer> {

}
