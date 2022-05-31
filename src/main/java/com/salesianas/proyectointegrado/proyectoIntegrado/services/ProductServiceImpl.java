package com.salesianas.proyectointegrado.proyectoIntegrado.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianas.proyectointegrado.proyectoIntegrado.dao.IProductDao;
import com.salesianas.proyectointegrado.proyectoIntegrado.dao.IUserDao;
import com.salesianas.proyectointegrado.proyectoIntegrado.entity.Product;
import com.salesianas.proyectointegrado.proyectoIntegrado.entity.User;

/**
 * Implementacion de la interfaz IProductService
 * @author javie
 *
 */
@Service
public class ProductServiceImpl implements IProductService{

	@Autowired
	private IProductDao productDao;
	
	@Autowired
	private IUserDao userDao;

	@Override
	public List<Product> getAll() {
		return productDao.findAll();
	}

	@Override
	public Product addProduct(Product product) {
		return productDao.save(product);
	}

	@Override
	public Product findById(Long id) {
		return productDao.findById(id).orElse(null);
	}

	@Override
	public Product save(Product product) {
		return productDao.save(product);
	}
}
