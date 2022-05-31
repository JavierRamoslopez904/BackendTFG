package com.salesianas.proyectointegrado.proyectoIntegrado.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.salesianas.proyectointegrado.proyectoIntegrado.entity.Product;
import com.salesianas.proyectointegrado.proyectoIntegrado.entity.User;
import com.salesianas.proyectointegrado.proyectoIntegrado.services.IProductService;
import com.salesianas.proyectointegrado.proyectoIntegrado.services.IUserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/product")
public class ProductRestController {
	
	/** Inicialización del servicio IProductService **/
	@Autowired
	private IProductService productService;
	
	List<Product> products = new ArrayList<>();
	
	/**
	 * Método para devolver todos los productos
	 * 
	 * @return
	 */
	@GetMapping("/getAll")
	public List<Product> getAllProduct() {
		return productService.getAll();
	}

	/**
	 * Método para añadir un nuevo producto
	 * 
	 * @param product
	 * @return
	 */
	@PostMapping("/addProduct")
	public Product addProduct(@RequestBody Product product) {
		
		User u = new User();		
		u.setId(1L);
		
		// Seteamos y tenemos que pasarle el usuario que está actualmente registrado
		product.setUser(u);
		
		// Una vez que tengamos el valor, vamos a añadirlo a la colección y tras esto hacemos el save
		products.add(product);
		
		return productService.addProduct(product);
	}

	/**
	 * Método para configurar la foto
	 * 
	 * @param archivo
	 * @param id
	 * @return
	 */
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {

		Map<String, Object> response = new HashMap<>();

		// Método para encontrar un producto por su id
		Product product = productService.findById(id);

		if (!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString() + " _ "
					+ archivo.getOriginalFilename().replace(" ", " ");
			Path rutaArchivo = Paths.get("upload").resolve(nombreArchivo).toAbsolutePath();

			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {

				response.put("mensaje", "Error al subir la imagen");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreFotoAnterior = product.getFoto();

			if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();

				if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}

			product.setFoto(nombreArchivo);

			productService.addProduct(product);

			response.put("user", product);
			response.put("mensaje", "Imagen subida : " + nombreArchivo);

		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@GetMapping("/upload/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {

		Path rutaArchivo = Paths.get("upload").resolve(nombreFoto).toAbsolutePath();

		Resource recurso = null;

		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (!recurso.exists() && !recurso.isReadable()) {
			rutaArchivo = Paths.get("src/main/resources/static/images").resolve("nouser.png").toAbsolutePath();

			try {
				recurso = new UrlResource(rutaArchivo.toUri());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);

	}
	
}
