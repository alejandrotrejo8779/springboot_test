package org.dtrejo.test.springboot.app.springboot_test;

import static org.dtrejo.test.springboot.app.Datos.*;
import org.dtrejo.test.springboot.app.exceptions.DineroInsuficienteException;
import org.dtrejo.test.springboot.app.models.Banco;
import org.dtrejo.test.springboot.app.models.Cuenta;
import org.dtrejo.test.springboot.app.repositories.IBancoRepository;
import org.dtrejo.test.springboot.app.repositories.ICuentaRepository;
import org.dtrejo.test.springboot.app.services.IService;
import org.dtrejo.test.springboot.app.services.impl.ServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.invocation.finder.VerifiableInvocationsFinder;

//import org.mockito.Mockito;
import static  org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SpringbootTestApplicationTests {
	
	//@Mock
	@MockBean //Integracion de spring
	ICuentaRepository cuentaRepository;
	//@Mock
	@MockBean //Integracion de spring
	IBancoRepository bancoRepository;
	
//	@InjectMocks //Deve ser con la implementacion
	@Autowired //Se hace esto mediante la integracion de spring
	ServiceImpl service;
//	IService service;
	
	
	
	@BeforeEach
	void setUp() {
		
//		cuentaRepository = mock(ICuentaRepository.class);
//		bancoRepository = mock(IBancoRepository.class);
//		service = new ServiceImpl(cuentaRepository, bancoRepository);
		
		
		//Se reinician los datos para cada metodo
		//Se declaran metodos static en Datos para que se cree una nueva instancia en cada llamado
//		Datos.CUENTA_001.setSaldo(new BigDecimal("1000"));
//		Datos.CUENTA_002.setSaldo(new BigDecimal("2000"));
//		
//		Datos.BANCO_001.setTotalTransferencias(0);
		
		
	}

	@Test
	void contextLoads() {
		
		//Given: Dado algun contexto
		when(cuentaRepository.findById(1L)).thenReturn(cuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(cuenta002());
		when(bancoRepository.findById(1L)).thenReturn(banco001());
		
		
		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		BigDecimal saldoDestino = service.revisarSaldo(2L);
		
	     assertEquals("1000",saldoOrigen.toPlainString());
	     assertEquals("2000",saldoDestino.toPlainString());
	     
	     
	     service.transferir(1L, 2L, new BigDecimal("100"), 1L);
	     
	      saldoOrigen = service.revisarSaldo(1L);
	      saldoDestino = service.revisarSaldo(2L);
	      
	      assertEquals("900",saldoOrigen.toPlainString());
		  assertEquals("2100",saldoDestino.toPlainString());
		  
		  
		//  verify(cuentaRepository).findById(1L);		
		 // verify(cuentaRepository).findById(2L);	
		  
		  int total=service.revisarTotalTransferencias(1L);
		  assertEquals(1, total);
		  
		  //times :se manda como parametro el mnumero de veces que se ejecuta en el flujo actual el metodo
		  verify(cuentaRepository,times(3)).findById(1L);		
		  verify(cuentaRepository,times(3)).findById(2L);	  
		//  verify(cuentaRepository,times(2)).update(any(Cuenta.class));
		  verify(cuentaRepository,times(2)).save(any(Cuenta.class)); //JPA
	  
		  
		  
		  verify(bancoRepository,times(2)).findById(1L);
		 // verify(bancoRepository).update(any(Banco.class));
		  verify(bancoRepository).save(any(Banco.class)); //JPA
		  
		  
		  verify(cuentaRepository,times(6)).findById(anyLong());
		  verify(cuentaRepository,never()).findAll();
	}
	
	@Test
	void contextLoads2() {
		
		//Given: Dado algun contexto
		when(cuentaRepository.findById(1L)).thenReturn(cuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(cuenta002());
		when(bancoRepository.findById(1L)).thenReturn(banco001());
		
		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		BigDecimal saldoDestino = service.revisarSaldo(2L);
		
	     assertEquals("1000",saldoOrigen.toPlainString());
	     assertEquals("2000",saldoDestino.toPlainString());
	     
	     
	     assertThrows(DineroInsuficienteException.class, () -> {
	    	 
	    	 service.transferir(1L,2L, new BigDecimal("1200"), 1L);
	     });
	     
	     

	     
	      saldoOrigen = service.revisarSaldo(1L);
	      saldoDestino = service.revisarSaldo(2L);
	      
	      assertEquals("1000",saldoOrigen.toPlainString());
		  assertEquals("2000",saldoDestino.toPlainString());
		  
		  
		//  verify(cuentaRepository).findById(1L);		
		 // verify(cuentaRepository).findById(2L);	
		  
		  int total=service.revisarTotalTransferencias(1L);
		  assertEquals(0, total);
		  
		  //times :se manda como parametro el mnumero de veces que se ejecuta en el flujo actual el metodo
		  verify(cuentaRepository,times(3)).findById(1L);		
		  verify(cuentaRepository,times(2)).findById(2L);	  
		 // verify(cuentaRepository,never()).update(any(Cuenta.class));
		  verify(cuentaRepository,never()).save(any(Cuenta.class)); //JPA
		  
		  
		  verify(bancoRepository,times(1)).findById(1L);
		 //  verify(bancoRepository,never()).update(any(Banco.class));
		  verify(bancoRepository,never()).save(any(Banco.class)); //JPA
		  
		  
		  verify(cuentaRepository,times(5)).findById(anyLong());
		  verify(cuentaRepository,never()).findAll();
	}
	
	
	
	
	@Test
	void contextLoad3() {
		
		when(cuentaRepository.findById(1L)).thenReturn(cuenta001());
		
		
		Cuenta cuenta1= service.findById(1l);
		Cuenta cuenta2= service.findById(1l);
		
		//Compara que sean  las mismas instancias
		assertSame(cuenta1, cuenta2);
		assertTrue(cuenta1 == cuenta2);
		assertEquals("Andres", cuenta1.getPersona());
		assertEquals("Andres", cuenta2.getPersona());
		
		verify(cuentaRepository,times(2)).findById(1L);
	}
	

	@Test
	void testFindAll(){
    
      //Given
	  List<Cuenta> datos = Arrays.asList(cuenta001().orElseThrow(),
	  cuenta002().orElseThrow()
	   );
	   when(cuentaRepository.findAll()).thenReturn(datos);


     //When
      List<Cuenta> cuentas = service.findAll();

	  assertFalse(cuentas.isEmpty());
	  assertEquals(2, cuentas.size());
	  //Compara los objetos con el metodo equals
	  assertTrue(cuentas.contains(cuenta002().orElseThrow()));

	  verify(cuentaRepository).findAll();

	}


	@Test
	void testSave(){

		//Given
		Cuenta cuentaPepe= new Cuenta(null,"Pepe" , new BigDecimal("3000"));

		 // when(cuentaService.save(any())).thenReturn(cuenta);


         //Como estamos probando el controlador y no el motor de base de datos se simula el id de la siguiente manera
         //Cuento se invoque el metodo save, con cualquier objeto cuenta(any) se le asigna lo que esta en la expresion lamba
         when(cuentaRepository.save(any())).then(invocation -> {

            Cuenta c= invocation.getArgument(0);
            c.setId(3L);

            return c;

         }); 
		 //Then
		Cuenta cuenta= service.save(cuentaPepe);

		 assertEquals(3, cuenta.getId());
		 assertEquals("Pepe", cuenta.getPersona());

		 assertEquals("3000", cuenta.getSaldo().toPlainString());

		 verify(cuentaRepository).save(any());
	}



}
