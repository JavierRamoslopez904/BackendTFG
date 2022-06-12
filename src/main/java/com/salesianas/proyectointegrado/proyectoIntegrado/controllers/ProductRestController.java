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

		// Vemos si el archivo no está vacío
		if (!archivo.isEmpty()) {
			// Obtención de la foto que el usuario está intentando subir
			String nombreArchivo = UUID.randomUUID().toString() + " _ "
					+ archivo.getOriginalFilename().replace(" ", " ");
			
			// Almacenamiento en una variable de tipo Path, el nombre del archivo 
			Path rutaArchivo = Paths.get("upload").resolve(nombreArchivo).toAbsolutePath();

			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {

				response.put("mensaje", "Error al subir la imagen");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			// Almacenamiento en una variable de tipo String la foto del product
			String nombreFotoAnterior = product.getFoto();

			// Establecimiento de condiciones para ver si la foto existe
			if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0) {
				
				// Almacenamiento en una variable de tipo Path la ruta de la foto anterior
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				
				// Almacenamiento en una variable de tipo File, el archivo anterior
				File archivoFotoAnterior = rutaFotoAnterior.toFile();

				// Establecimiento de condiciones para ver si hay un archivo existente, si es así lo elimina
				if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}

			// Modificamos la foto para pasarle la nueva foto
			product.setFoto(nombreArchivo);

			// Añadimos el nuevo producto, con la foto actualizada
			productService.addProduct(product);

			response.put("user", product);
			response.put("mensaje", "Imagen subida : " + nombreArchivo);

		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	/**
	 * Método para ver la foto correspondiente a cada producto
	 * @param nombreFoto
	 * @return
	 */
	@GetMapping("/upload/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {

		// Almacenamiento en una variable de tipo Path, del nombre de la foto
		Path rutaArchivo = Paths.get("upload").resolve(nombreFoto).toAbsolutePath();

		Resource recurso = null;

		try {
			// Asignación del nombre del archivo a una variable de tipo Resource llamada recurso
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		// Establecimiento de condiciones para ver si la foto existe
		if (!recurso.exists() && !recurso.isReadable()) {
			// Si no existe, muestra la imagen por defecto
			rutaArchivo = Paths.get("src/main/resources/static/images").resolve("cruz.jpg").toAbsolutePath();

			try {
				recurso = new UrlResource(rutaArchivo.toUri());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		// Configuración de la cabecera que es necesaria para enviar el resultado
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);

	}
	
}
