package com.tia.principal.utils;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utileria {

	public String generaTokenVirtual() {
	    return String.format("%06d", new Random().nextInt(999999));
	}
	
	public LocalDateTime obtenerTiempoActual() {
		return LocalDateTime.now();
	}
}
