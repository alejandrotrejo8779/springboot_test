package org.dtrejo.test.springboot.app;

import java.math.BigDecimal;
import java.util.Optional;

import org.dtrejo.test.springboot.app.models.Banco;
import org.dtrejo.test.springboot.app.models.Cuenta;

public class Datos {
	
//	public static final Cuenta CUENTA_001=new Cuenta(1L,"Andres", new BigDecimal("1000") );
//	public static final Cuenta CUENTA_002=new Cuenta(2L,"Jhon", new BigDecimal("2000") );
//
//	
//	
//	public static final Banco BANCO_001=new Banco(1L,"El banco Financiero", 0 );
	
	
/*	public static Cuenta cuenta001() {
		
		return new Cuenta(1L,"Andres", new BigDecimal("1000") );
	}

	
	public static Cuenta cuenta002() {
		
		return new Cuenta(2L,"Jhon", new BigDecimal("2000") );
	}

	
	public static Banco banco001() {
		
		return new Banco(1L,"El banco Financiero", 0 );
	} */
	
	public static Optional<Cuenta> cuenta001() {
		
		return Optional.of(new Cuenta(1L,"Andres", new BigDecimal("1000") ));
	}

	
	public static Optional<Cuenta> cuenta002() {
		
		return Optional.of(new Cuenta(2L,"Jhon", new BigDecimal("2000") ));
	}

	
	public static Optional<Banco> banco001() {
		
		return Optional.of(new Banco(1L,"El banco Financiero", 0 ));
	}
}
