package com.tia.principal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tia.principal.models.HistorialResponse;
import com.tia.principal.models.RegistroResponse;
import com.tia.principal.services.PrincipalServicio;

@RestController
@RequestMapping("/api/token_virtual/")
public class PrincipalController {

	@Autowired
	private PrincipalServicio principalServicio;
	
	@GetMapping("generarToken/")
	public ResponseEntity<?> generarToken(@RequestParam String cliente) {
		RegistroResponse salida = principalServicio.generarToken(cliente);
        return ResponseEntity.ok(salida);
    }
	
	@GetMapping("obtenerToken/")
	public ResponseEntity<?> obtenerToken(@RequestParam String cliente) {
		RegistroResponse salida = principalServicio.obtenerToken(cliente);
        return ResponseEntity.ok(salida);
    }
	
	@GetMapping("usarToken/")
	public ResponseEntity<?> usarToken(@RequestParam String cliente, @RequestParam String token){
		Boolean valido = principalServicio.usarToken(cliente, token);
        return ResponseEntity.ok(Map.of("mensaje",valido?"El token es valido":"El token no es valido"));
	}
	
	@GetMapping("historial/")
	public ResponseEntity<?> historial(/*@RequestParam String cliente*/){
		List<HistorialResponse> lsHistorial= principalServicio.historial(/*cliente*/);
        return ResponseEntity.ok(Map.of("historial",lsHistorial));
	}
}
