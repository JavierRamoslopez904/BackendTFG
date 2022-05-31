package com.salesianas.proyectointegrado.proyectoIntegrado.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase User
 * 
 * @author javie
 *
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

	/** Atributo privado propio de la interfaz Serializable **/
	private static final long serialVersionUID = 1L;

	/** Atributo de tipo Long encargado de almacenar el ID **/
	private Long id;

	/** Atributo de tipo String encargado de almacenar el username **/
	private String username;

	/** Atributo de tipo String encargado de almacenar el password **/
	private String password;

	/** Atributo de tipo String encargado de almacenar el email **/
	private String email;
 
	/** Atributo de tipo List<Product> encargado de almacenar los products **/
	private List<Product> products;
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the products
	 */
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "user")
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
