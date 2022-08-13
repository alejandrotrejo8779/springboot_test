package org.dtrejo.test.springboot.app.springboot_test;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.dtrejo.test.springboot.app.models.Cuenta;
import org.dtrejo.test.springboot.app.repositories.ICuentaRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//Pruebas unitarias con JPA
@DataJpaTest
public class IntegracionJpaTest {

	@Autowired
	ICuentaRepository cuentaRepository;

	@Test
	void testFindById() {

		Optional<Cuenta> cuenta = cuentaRepository.findById(1L);

		assertTrue(cuenta.isPresent());
		assertEquals("Andres", cuenta.orElseThrow().getPersona());

	}

	@Test
	void testFindByPersona() {

		Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Andres");

		assertTrue(cuenta.isPresent());
		assertEquals("Andres", cuenta.orElseThrow().getPersona());
		assertEquals("1000.00", cuenta.orElseThrow().getSaldo().toPlainString());

	}

	@Test
	void testFindByPersonaThrowException() {

		Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Rod");

//		assertThrows(NoSuchElementException.class, ()-> {
//			
//			cuenta.orElseThrow();
//			
//		});

		assertThrows(NoSuchElementException.class, cuenta :: orElseThrow);
		assertFalse(cuenta.isPresent());

	

//		assertTrue(cuenta.isPresent());
//		assertEquals("Andres", cuenta.orElseThrow().getPersona());
//		assertEquals("1000.00", cuenta.orElseThrow().getSaldo().toPlainString());

	}
	
	
	@Test
	void tesFindAll() {
		
		List<Cuenta> cuentas = cuentaRepository.findAll();
		
		assertFalse(cuentas.isEmpty());
		assertEquals(2, cuentas.size());
	}
	
	
	@Test
	void testSave() {
		
		//Given
		Cuenta cuentaPepe = new Cuenta(null,"Pepe", new BigDecimal("3000"));
		
		
		
		//When
	//	Cuenta cuenta=cuentaRepository.findByPersona("Pepe").orElseThrow();
		
		Cuenta cuenta=cuentaRepository.save(cuentaPepe);
		assertEquals("Pepe", cuenta.getPersona());
		assertEquals("3000", cuenta.getSaldo().toPlainString());
		
	//	assertEquals(3, cuenta.getId()); //Se auto incrementa y puede ser que no tenga el id 3 por que cada metodo se vuelve a crear los datos en bd
	}
	
	
	@Test
	void testUpdate() {
		
		//Given
		Cuenta cuentaPepe = new Cuenta(null,"Pepe", new BigDecimal("3000"));
		
		
		
		//When
	//	Cuenta cuenta=cuentaRepository.findByPersona("Pepe").orElseThrow();
		
		Cuenta cuenta=cuentaRepository.save(cuentaPepe);
		assertEquals("Pepe", cuenta.getPersona());
		assertEquals("3000", cuenta.getSaldo().toPlainString());
		
	//	assertEquals(3, cuenta.getId()); //Se auto incrementa y puede ser que no tenga el id 3 por que cada metodo se vuelve a crear los datos en bd
	
	
		cuenta.setSaldo(new BigDecimal("3800"));
		Cuenta cuentaActualizada = cuentaRepository.save(cuentaPepe);
		
		
		assertEquals("Pepe", cuentaActualizada.getPersona());
		assertEquals("3800", cuentaActualizada.getSaldo().toPlainString());
	
	}
	
	@Test
	void testDelete() {
		Cuenta cuenta = cuentaRepository.findById(2L).orElseThrow();
		
		assertEquals("John", cuenta.getPersona());
		
		cuentaRepository.delete(cuenta);
		
		
		assertThrows(NoSuchElementException.class, ()-> {
		
			cuentaRepository.findByPersona("John").orElseThrow();
		
	});
		
		assertEquals(1, cuentaRepository.findAll().size());
		
		
		
	}
	
	
	
	

}
