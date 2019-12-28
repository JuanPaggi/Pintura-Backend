package com.pintura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pintura.models.Tags;

/**
 * @author Juan Paggi
 * Repositorio de la tabla tag.
 */

@Repository
public interface TagRepository extends JpaRepository <Tags , Integer> {

}
