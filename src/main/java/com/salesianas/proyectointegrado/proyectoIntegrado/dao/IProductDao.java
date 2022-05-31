package com.salesianas.proyectointegrado.proyectoIntegrado.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianas.proyectointegrado.proyectoIntegrado.entity.Product;
import com.salesianas.proyectointegrado.proyectoIntegrado.entity.User;

/**
 * Interfaz IProductDao
 * @author javie
 *
 */
@Repository
public interface IProductDao extends JpaRepository<Product, Long>{

	
}
