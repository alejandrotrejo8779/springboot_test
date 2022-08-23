package org.dtrejo.test.springboot.app.controllers;


import static org.mockito.Mockito.*;

import static org.dtrejo.test.springboot.app.Datos.*;

import org.aopalliance.intercept.Invocation;
import org.dtrejo.test.springboot.app.models.Cuenta;
import org.dtrejo.test.springboot.app.models.TransaccionDto;
import org.dtrejo.test.springboot.app.services.IService;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CuentaController.class)
public class CuentaControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private IService cuentaService;

    ObjectMapper objectMapper;

    @BeforeEach
     void setUp(){
        objectMapper= new ObjectMapper();

     }    


    @Test
    void testDetalle() throws Exception {
        //Given Dado.  mockeamos el controller con el metodo cuenta001
        when(cuentaService.findById(1L)).thenReturn(cuenta001().orElseThrow());

          
        //When Cuando
        mvc.perform(get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))
        
        
        //Then Despues  
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.persona").value("Andres"))
        .andExpect(jsonPath("$.saldo").value("1000"));

        verify(cuentaService).findById(1L);
    }

    @Test
    void testTransferir() throws JsonProcessingException, Exception {
           
//Given
//Simulamos la entrada de los datos json llenando el objeto
          TransaccionDto dto= new TransaccionDto();
          dto.setCuentaOrigenId(1L);
          dto.setCuentaDestinoId(2L);
          dto.setMonto(new BigDecimal("100"));
          dto.setBancoId(1L);

        
          Map<String ,Object> response = new HashMap<>();
         response.put("date", LocalDate.now().toString());
         response.put("Status","OK");
         response.put("mensaje","Transferencia realizada con exito!");
         response.put("transaccion", dto);
           
         System.out.println(objectMapper.writeValueAsString(response));

          //When
          mvc.perform(post("/api/cuentas/transferir")
                    .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))

          //Then
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
          .andExpect(jsonPath("$.mensaje").value("Transferencia realizada con exito!"))
          .andExpect(jsonPath("$.transaccion.cuentaOrigenId").value(1L))
          .andExpect(content().json(objectMapper.writeValueAsString(response)));


    }


    @Test
    void testListar() throws JsonProcessingException, Exception{
   //Given
        List<Cuenta> cuentas = Arrays.asList(cuenta001().orElseThrow(),
       cuenta002().orElseThrow()
        );

        when(cuentaService.findAll()).thenReturn(cuentas);

        //When
        mvc.perform(get("/api/cuentas").contentType(MediaType.APPLICATION_JSON))
        
        //Then
        .andExpect(jsonPath("$[0].persona").value("Andres"))
        .andExpect(jsonPath("$[1].persona").value("Jhon"))
        .andExpect(jsonPath("$[0].saldo").value("1000"))
        .andExpect(jsonPath("$[1].saldo").value("2000"))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(content().json(objectMapper.writeValueAsString(cuentas))
        );


        verify(cuentaService).findAll();
        

    }

    @Test
    void testSave() throws JsonProcessingException, Exception{

         //Given
         Cuenta cuenta= new Cuenta(null,"Pepe" , new BigDecimal("3000"));
         // when(cuentaService.save(any())).thenReturn(cuenta);


         //Como estamos probando el controlador y no el motor de base de datos se simula el id de la siguiente manera
         //Cuento se invoque el metodo save, con cualquier objeto cuenta(any) se le asigna lo que esta en la expresion lamba
         when(cuentaService.save(any())).then(invocation -> {

            Cuenta c= invocation.getArgument(0);
            c.setId(3L);

            return c;

         });

         //When
          mvc.perform(post("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(cuenta)))
          .andExpect(status().isCreated())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.id", is(3)))
          .andExpect(jsonPath("$.persona", is("Pepe")))
          .andExpect(jsonPath("$.saldo", is(3000)));

          verify(cuentaService).save(any());

    }

}
