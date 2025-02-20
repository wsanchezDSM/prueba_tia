package com.tia.principal.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tia.principal.entities.TblUsuarios;
import com.tia.principal.entities.TblUsuariosToken;
import com.tia.principal.models.HistorialResponse;
import com.tia.principal.models.RegistroResponse;
import com.tia.principal.repository.TblUsuariosRepository;
import com.tia.principal.repository.TblUsuariosTokenRepository;
import com.tia.principal.utils.Utileria;

@Service
public class PrincipalServicio {

	@Autowired
	private Utileria util;
	
	@Autowired
	private TblUsuariosRepository tblUsuariosRepository;
	
	@Autowired
	private TblUsuariosTokenRepository tblUsuariosTokenRepository;
	
	public RegistroResponse generarToken(String cliente) {
		TblUsuarios usuarioEncontrado = tblUsuariosRepository.findByEmailAndEnUsoTrue(cliente)
                .orElseThrow(() -> new RuntimeException("El usuario no se encuentra en la lista de usuarios del sistema"));

        LocalDateTime now = util.obtenerTiempoActual();
        Optional<TblUsuariosToken> tokenActivo = tblUsuariosTokenRepository.findFirstByTblUsuarios_IdAndEnUsoFalseAndTiempoExpiracionAfter(usuarioEncontrado.getId(), now);

        if (tokenActivo.isPresent()) {
        	TblUsuariosToken t = tokenActivo.get();
            return new RegistroResponse(t.getTk(), ChronoUnit.SECONDS.between(now, t.getTiempoExpiracion()));
        }

        String generacionToken = util.generaTokenVirtual();
        LocalDateTime expiracion = now.plusSeconds(60);
        TblUsuariosToken token = TblUsuariosToken.builder()
        		.enUso(Boolean.FALSE)
        		.fechaCreacion(now)
        		.tblUsuarios(usuarioEncontrado)
        		.tiempoExpiracion(expiracion)
        		.tk(generacionToken)
        		.build();		
        		
        tblUsuariosTokenRepository.save(token);
        return new RegistroResponse(generacionToken, 60L);
	}
	
	public RegistroResponse obtenerToken(String cliente) {
		TblUsuarios usuarioEncontrado = tblUsuariosRepository.findByEmailAndEnUsoTrue(cliente)
                .orElseThrow(() -> new RuntimeException("El usuario no se encuentra en la lista de usuarios del sistema"));

        LocalDateTime now = util.obtenerTiempoActual();
        Optional<TblUsuariosToken> tokenActivo = tblUsuariosTokenRepository.findLastByTblUsuarios_IdAndEnUsoFalseAndTiempoExpiracionAfter(usuarioEncontrado.getId(), now);

        if (tokenActivo.isPresent()) {
        	TblUsuariosToken t = tokenActivo.get();
            return new RegistroResponse(t.getTk(), ChronoUnit.SECONDS.between(now, t.getTiempoExpiracion()));
        }

        String generacionToken = util.generaTokenVirtual();
        return new RegistroResponse(generacionToken, 60L);
	}
	
	
	public boolean usarToken(String cliente, String tokenIngresado) {
		TblUsuarios usuarioEncontrado = tblUsuariosRepository.findByEmailAndEnUsoTrue(cliente)
                .orElseThrow(() -> new RuntimeException("El usuario no se encuentra en la lista de usuarios del sistema"));
        LocalDateTime now = util.obtenerTiempoActual();
        Optional<TblUsuariosToken> tokenValido = tblUsuariosTokenRepository.findByTblUsuarios_IdAndTkAndAndTiempoExpiracionAfterAndEnUsoFalse(usuarioEncontrado.getId(), tokenIngresado, now);

        if (tokenValido.isPresent()) {
        	TblUsuariosToken token = tokenValido.get();
            token.setEnUso(Boolean.TRUE);
            tblUsuariosTokenRepository.save(token);
            return true;
        }

        return false;
    }
	
	public List<HistorialResponse> historial(/*String cliente*/) {
		List<HistorialResponse> lsHistorial=tblUsuariosTokenRepository.findAll().stream().map(y->{
			HistorialResponse ele=new HistorialResponse();
			ele.setEmail(y.getTblUsuarios().getEmail());
			ele.setEnUso(y.getEnUso());
			ele.setFechaCreacion(y.getFechaCreacion());
			ele.setId(y.getId());
			ele.setNombres(y.getTblUsuarios().getNombres());
			ele.setTiempoExpiracion(y.getTiempoExpiracion());
			ele.setTk(y.getTk());
			return ele;
		}).collect(Collectors.toList());
		return lsHistorial;
	}
	
	@Scheduled(fixedRate = 60000) 
    public void generarTokenCada60Segundos(){
        tblUsuariosRepository.findAll().stream().forEach(n->{
        	this.generarToken(n.getEmail());
        });

    }
}
