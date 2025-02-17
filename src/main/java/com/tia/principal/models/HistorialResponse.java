package com.tia.principal.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialResponse {

	 private Long id;
	 private String tk;
	 private String nombres;
	 private String email;
	 private LocalDateTime fechaCreacion;
	 private LocalDateTime tiempoExpiracion;
	 private Boolean enUso;
	
}
