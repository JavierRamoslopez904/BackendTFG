package com.salesianas.proyectointegrado.proyectoIntegrado.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salesianas.proyectointegrado.proyectoIntegrado.entity.User;
import com.salesianas.proyectointegrado.proyectoIntegrado.services.IUserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
/**
 * Controlador UserRestController
 * 
 * @author javie
 *
 */

public class UserRestController {

	@Autowired
	/** Instanciación del servicio IUserService **/
	private IUserService userService;

	/** Creación de un objeto Logger **/
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

	/**
	 * Método para añadir un usuario
	 * 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/aniadirUsuario")
	public void registroUsuario(@RequestBody User usuario) throws Exception {
		
		String email = usuario.getEmail();

		// Vemos si el username ya existe
		if (email != null && !"".equals(email)) {
			// Vemos si existe otro usuario con el mismo email
			User user = userService.findByEmail(email);

			if (user != null) {
				throw new Exception("El usuario con el email : " + email + " ya existe");
			} else {
				LOGGER.info("El usuario con email -{}- no existe", email);
				userService.addUser(usuario);
			}
		}
	}

	/**
	 * Método para logear a un usuario
	 * 
	 * @param usuario
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/logearUsuario")
	public User loginUsuario(@RequestBody User usuario) throws Exception {

		// Almacenamiento en variables el email y la contraseña
		String pass = usuario.getPassword();
		String email = usuario.getEmail();
		User user = null;

		// Establecimiento de condiciones para comprobar si el email
		// tiene contenido
		if (email != null && pass != null) {

			// Vemos si hay un usuario con ese email
			User logUser = userService.findByEmailAndPassword(email, pass);

			if (logUser != null) {
				LOGGER.info("El usuario con email -{}- está registrado en nuestra BBDD", email);
			} else {
				throw new Exception("El usuario no existe");
			}
		}
		return user;
	}

	/**
	 * Método para obtener el usuario por su id
	 * 
	 * @return
	 */
	@GetMapping("/find/{id}")
	public User findUserById(@PathVariable("id") Long id) {
		User u = userService.findUserById(id);

		return u;
	}
}
