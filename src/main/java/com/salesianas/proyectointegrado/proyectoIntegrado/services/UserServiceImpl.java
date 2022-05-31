package com.salesianas.proyectointegrado.proyectoIntegrado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianas.proyectointegrado.proyectoIntegrado.dao.IUserDao;
import com.salesianas.proyectointegrado.proyectoIntegrado.entity.User;

@Service
/**
 * Clase UserServiceImpl
 * 
 * @author javie
 *
 */
public class UserServiceImpl implements IUserService {

	/** Instanciación de la interfaz IUserDao **/
	@Autowired
	private IUserDao userDao;

	@Override
	public User addUser(User usuario) {
		return userDao.save(usuario);
	}

	@Override
	public User findByEmailAndPassword(String email, String password) {
		return userDao.findByEmailAndPassword(email, password);
	}

	@Override
	public User findUserById(Long id) {
		return userDao.findById(id).get();
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

}
