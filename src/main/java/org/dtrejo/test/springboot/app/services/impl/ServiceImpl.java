package org.dtrejo.test.springboot.app.services.impl;

import java.math.BigDecimal;

import org.dtrejo.test.springboot.app.models.Banco;
import org.dtrejo.test.springboot.app.models.Cuenta;
import org.dtrejo.test.springboot.app.repositories.IBancoRepository;
import org.dtrejo.test.springboot.app.repositories.ICuentaRepository;
import org.dtrejo.test.springboot.app.services.IService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceImpl implements IService {

	private ICuentaRepository cuentaRepository;
	private IBancoRepository bancoRepository;

	public ServiceImpl(ICuentaRepository cuentaRepository, IBancoRepository bancoRepository) {
		super();
		this.cuentaRepository = cuentaRepository;
		this.bancoRepository = bancoRepository;
	}

	@Override
	@Transactional(readOnly= true) // @Transactional sirve para saber que es por medio de flujo http
	public Cuenta findById(Long id) {

		// return cuentaRepository.findById(id);

		return cuentaRepository.findById(id).orElseThrow(); // JPA
	}

	@Override
	@Transactional(readOnly= true)
	public int revisarTotalTransferencias(Long bancoId) {

		// Banco banco = bancoRepository.findById(bancoId);
		Banco banco = bancoRepository.findById(bancoId).orElseThrow(); // JPA

		return banco.getTotalTransferencias();
	}

	@Override
	@Transactional(readOnly= true)
	public BigDecimal revisarSaldo(Long cuentId) {

		Cuenta cuenta = cuentaRepository.findById(cuentId).orElseThrow(); // JPA

		return cuenta.getSaldo();
	}

	@Override
	@Transactional()
	public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {

//		Banco banco= bancoRepository.findById(1L);
//		int totalTransferencia=banco.getTotalTransferencias();
//		banco.setTotalTransferencias(++totalTransferencia);
//		bancoRepository.update(banco);

		Cuenta cuentaOrigen = cuentaRepository.findById(numCuentaOrigen).orElseThrow();// JPA
		cuentaOrigen.debito(monto);
		// cuentaRepository.update(cuentaOrigen);
		cuentaRepository.save(cuentaOrigen); // JPA

		Cuenta cuentaDestino = cuentaRepository.findById(numCuentaDestino).orElseThrow();// JPA
		cuentaDestino.credito(monto);
		// cuentaRepository.update(cuentaDestino);
		cuentaRepository.save(cuentaDestino); // JPA

		// Se cambia para que se haga la sumatoria de transferencia al final
		// Banco banco= bancoRepository.findById(1L);
		Banco banco = bancoRepository.findById(1L).orElseThrow(); // JPA
		int totalTransferencia = banco.getTotalTransferencias();
		banco.setTotalTransferencias(++totalTransferencia);
		// bancoRepository.update(banco);
		bancoRepository.save(banco); // JPA

	}

}
