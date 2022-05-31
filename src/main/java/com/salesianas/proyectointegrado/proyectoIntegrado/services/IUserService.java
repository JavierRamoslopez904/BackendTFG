package com.salesianas.proyectointegrado.proyectoIntegrado.services;

import com.salesianas.proyectointegrado.proyectoIntegrado.entity.User;

/**
 * Interfaz IUserService
 * 
 * @author javie
 *
 */
public interface IUserService {

	/**
	 * Método para añadir un usuario, recibiendo por parámetros un objeto Usuario
	 * 
	 * @param usuario
	 * @return
	 */
	public User addUser(User usuario);
	
	/**
	 * Método para encontrar un usuario a través de su Username
	 * 
	 * @param username
	 * @return
	 */
	public User findByEmail(String email);
	
	/**
	 * Método para encontrar un usuario a través de su Username y su email
	 * 
	 * @param username
	 * @param email
	 * @return
	 */
	public User findByEmailAndPassword(String email, String password);
	
	/**
	 * Método para encontrar un usuario por su id
	 * 
	 * @param id
	 * @return
	 */
	public User findUserById(Long id);
}
