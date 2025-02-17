package com.tia.principal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.tia.principal.entities.TblUsuarios;

@Repository
public interface TblUsuariosRepository extends JpaRepository<TblUsuarios,Long>,JpaSpecificationExecutor<TblUsuarios> {
	
	Optional<TblUsuarios> findByEmailAndEnUsoTrue(String email);

}
