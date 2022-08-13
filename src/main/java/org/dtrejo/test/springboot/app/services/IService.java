package org.dtrejo.test.springboot.app.services;

import java.math.BigDecimal;

import org.dtrejo.test.springboot.app.models.Cuenta;

public interface IService {
	
	
	Cuenta findById(Long id );
   
	int revisarTotalTransferencias(Long bancoId);
	
	BigDecimal revisarSaldo(Long cuentId);
	
	void transferir(Long numCuentaOrigen,Long numCuentaDestino,BigDecimal monto,Long bancoId);
}
