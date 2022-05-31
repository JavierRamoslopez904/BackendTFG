package com.salesianas.proyectointegrado.proyectoIntegrado.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianas.proyectointegrado.proyectoIntegrado.entity.User;

/**
 * Interfaz IUserDao
 * 
 * @author javie
 *
 */
@Repository
public interface IUserDao extends JpaRepository<User, Long> {

	/**
	 * Método para encontrar un usuario por su username
	 * 
	 * @param username
	 * @return
	 */
	public User findByEmail(String email);

	/**
	 * Método para encontrar un usuario por su username y su email
	 * 
	 * @param username
	 * @param email
	 * @return
	 */
	public User findByEmailAndPassword(String email, String password);

	/**
	 * Método para encontrar un usuario por el username y el email
	 * 
	 * @param email
	 * @param username
	 * @return
	 */
	public User findByUsernameAndEmail(String email, String username);

}
