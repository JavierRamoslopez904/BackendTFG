package com.salesianas.proyectointegrado.proyectoIntegrado.services;

import java.util.List;

import com.salesianas.proyectointegrado.proyectoIntegrado.entity.Product;
import com.salesianas.proyectointegrado.proyectoIntegrado.entity.User;

/**
 * Interfaz IProductService
 * @author javie
 *
 */
public interface IProductService {

	/**
	 * Método para devolver una lista de productos
	 * @return
	 */
	public List<Product> getAll();
	
	/**
	 * Método para añadir un producto
	 * @return
	 */
	public Product addProduct(Product product);
	
	/**
	 * Método para encontrar un producto por su id
	 * @param id
	 * @return
	 */
	public Product findById(Long id);
	
	/**
	 * Método para guardar un producto
	 * @param product
	 * @return
	 */
	public Product save (Product product);
	
	
}
