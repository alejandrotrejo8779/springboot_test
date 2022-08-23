package org.dtrejo.test.springboot.app.controllers;

import java.security.Provider.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dtrejo.test.springboot.app.models.Cuenta;
import org.dtrejo.test.springboot.app.models.TransaccionDto;
import org.dtrejo.test.springboot.app.services.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

	@Autowired
	private IService cuentaService;




	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Cuenta> listar(){

	return cuentaService.findAll();

	
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cuenta save(@RequestBody Cuenta cuenta) {

		return cuentaService.save(cuenta);

	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Cuenta detalle(@PathVariable Long id) {
		// public Cuenta detalle (@PathVariable(name="") Long id) {

		return cuentaService.findById(id);
	}

	@PostMapping("/transferir")
	public ResponseEntity<?> transferir(@RequestBody TransaccionDto dto) {

		cuentaService.transferir(dto.getCuentaOrigenId(),
				dto.getCuentaDestinoId(),
				dto.getMonto(),
				dto.getBancoId());

		Map<String, Object> response = new HashMap<>();
		response.put("date", LocalDate.now().toString());
		response.put("Status", "OK");
		response.put("mensaje", "Transferencia realizada con exito!");
		response.put("transaccion", dto);

		return ResponseEntity.ok(response);

	}

}
