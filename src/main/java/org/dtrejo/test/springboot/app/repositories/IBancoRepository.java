package org.dtrejo.test.springboot.app.repositories;

import java.util.List;

import org.dtrejo.test.springboot.app.models.Banco;
import org.dtrejo.test.springboot.app.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBancoRepository extends  JpaRepository<Banco,Long>{

	/*
	 * Se usan los metodos de JpaRepository
	List<Banco> findAll();

	Banco findById(Long id);

	void update(Banco banco);
	*/
}
