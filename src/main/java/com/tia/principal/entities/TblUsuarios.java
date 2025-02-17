package com.tia.principal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="tbl_usuarios", schema = "testtia")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TblUsuarios {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name="nombres")
	private String nombres;
	
	@Column(name="email")
	private String email;
	
	@Column(name="en_uso")
    private Boolean enUso;
}
