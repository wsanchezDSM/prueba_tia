package com.tia.principal.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="token_tbl_usuarios", schema = "testtia")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TblUsuariosToken {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name="tk")
	private String tk;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
	private TblUsuarios tblUsuarios;
	
	@Column(name="fecha_creacion")
	private LocalDateTime fechaCreacion;
	
	@Column(name="tiempo_expiracion")
    private LocalDateTime tiempoExpiracion;
	
	@Column(name="en_uso")
    private Boolean enUso;
}
