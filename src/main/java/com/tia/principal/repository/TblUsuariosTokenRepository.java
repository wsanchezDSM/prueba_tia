package com.tia.principal.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tia.principal.entities.TblUsuariosToken;

@Repository
public interface TblUsuariosTokenRepository extends JpaRepository<TblUsuariosToken,Long>,JpaSpecificationExecutor<TblUsuariosToken> {
	
	Optional<TblUsuariosToken> findFirstByTblUsuarios_IdAndEnUsoFalseAndTiempoExpiracionAfter(Long idUsuario,LocalDateTime horaActual);
	
	Optional<TblUsuariosToken> findLastByTblUsuarios_IdAndEnUsoFalseAndTiempoExpiracionAfter(Long idUsuario,LocalDateTime horaActual);
	
    Optional<TblUsuariosToken> findByTblUsuarios_IdAndTkAndAndTiempoExpiracionAfterAndEnUsoFalse(Long idUsuario, String token, LocalDateTime horaActual);

    List<TblUsuariosToken> findAllByTblUsuarios_Email(String cliente);
}
