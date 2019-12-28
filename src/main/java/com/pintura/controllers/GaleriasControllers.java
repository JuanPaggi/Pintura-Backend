package com.pintura.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pintura.controllers.dto.GaleriaItem;
import com.pintura.exceptions.ApiException;
import com.pintura.services.GaleriaServices;
import com.pintura.utils.ModelApiResponse;

/**
 * @author Juan Paggi. Controlador de galerias con get, post, put, y delete. Tenemos
 *         dos Get, uno para devolver un galeria seleccionado por su id y otro get
 *         para devolver todos los galerias.
 */

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET })
@RequestMapping("/galerias")
public class GaleriasControllers {

	public static final Logger logger = LoggerFactory.getLogger(GaleriasControllers.class);

	@Autowired
	GaleriaServices galeriaServices;

	@GetMapping(path = "/{idGaleria}")
	public @ResponseBody ResponseEntity<GaleriaItem> getGaleriaByID(@PathVariable("idGaleria") String idGaleria) {
		try {
			return new ResponseEntity<GaleriaItem>(galeriaServices.getGaleria(Integer.parseInt(idGaleria)),
					HttpStatus.OK);
		} catch (ApiException e) {
			if (e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				return new ResponseEntity<GaleriaItem>(HttpStatus.NOT_FOUND);
			} else {
				logger.error(e.getMessage(), e);
				return new ResponseEntity<GaleriaItem>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<GaleriaItem>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "")
	public @ResponseBody ResponseEntity<List<GaleriaItem>> getGalerias() {
		try {
			return new ResponseEntity<List<GaleriaItem>>(galeriaServices.getAllGalerias(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "")
	public @ResponseBody ResponseEntity<ModelApiResponse> addUsuarios(@RequestBody GaleriaItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			galeriaServices.addGaleria(body);
			respuesta.codigo("OK");
			respuesta.descripcion("Galeria agregada correctamente");
			return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.OK);
		} catch (ApiException e) {
			if (e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.NOT_FOUND);
			} else {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			respuesta.codigo("ERROR");
			respuesta.descripcion(e.getMessage());
			return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/{idGaleria}")
	public @ResponseBody ResponseEntity<ModelApiResponse> removeUsuario(@PathVariable("idGaleria") String idGaleria) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			galeriaServices.removeGaleria(Integer.parseInt(idGaleria));
			respuesta.codigo("OK");
			respuesta.descripcion("Galeria borrada correctamente");
			return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.OK);
		} catch (ApiException e) {
			if (e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.NOT_FOUND);
			} else {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			respuesta.codigo("ERROR");
			respuesta.descripcion(e.getMessage());
			return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/{idGaleria}")
	public @ResponseBody ResponseEntity<ModelApiResponse> editUsuario(@PathVariable("idGaleria") String idGaleria,
			@RequestBody GaleriaItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			galeriaServices.editGaleria(Integer.parseInt(idGaleria), body);
			respuesta.codigo("OK");
			respuesta.descripcion("Galeria editada correctamente");
			return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.OK);
		} catch (ApiException e) {
			if (e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.NOT_FOUND);
			} else {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			respuesta.codigo("ERROR");
			respuesta.descripcion(e.getMessage());
			return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
