package org.dtrejo.test.springboot.app.repositories;

import java.util.List;
import java.util.Optional;

import org.dtrejo.test.springboot.app.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICuentaRepository extends JpaRepository<Cuenta,Long> {
	
/*
 * Se utilizan los metodos JpaRepository
	List<Cuenta> findAll();
    
	Cuenta findById(Long id);
	
	void update(Cuenta cuenta);
	*/
	
	
	//findBy palabra reservada de JPA
	@Query("select c from Cuenta c where c.persona=?1")
	Optional<Cuenta> findByPersona(String persona);
//	Cuenta findByPersona(String persona);
	
}
