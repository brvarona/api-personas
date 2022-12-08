package com.brvarona.personas.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.brvarona.personas.exception.ResourceNotFoundException;
import com.brvarona.personas.model.Pais;
import com.brvarona.personas.model.Persona;
import com.brvarona.personas.model.TipoDocumento;
import com.brvarona.personas.payload.ActualizarPersonaRequest;
import com.brvarona.personas.service.PersonaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class PersonaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersonaService personaService;
	
	private List<Persona> personas;

	@BeforeEach
	public void setUp() {
		var pais = new Pais();
    	pais.setId(1L);
    	pais.setNombre("Argentina");
    	pais.setCodigo("ARG");
    	
    	var tipoDocumento = new TipoDocumento();
    	tipoDocumento.setId(1L);
    	tipoDocumento.setTipo("DU");
    	tipoDocumento.setDescripcion("Documento unico");
    	
        var persona1 = new Persona();
        persona1.setId(1L);
        persona1.setNombre("Pepe");
        persona1.setApellido("Grillo");
        persona1.setTipoDocumento(tipoDocumento);
        persona1.setNroDocumento("45666777");
        persona1.setPais(null);
        persona1.setEdad(20);
        persona1.setEmail("pepito.grillo@mail.com");
        
        var persona2 = new Persona();
        persona2.setId(2L);
        persona2.setNombre("Juan");
        persona2.setApellido("Gomez");
        persona2.setTipoDocumento(tipoDocumento);
        persona2.setNroDocumento("1234567");
        persona2.setPais(null);
        persona2.setEdad(50);
        persona2.setEmail("jgomez@mail.com");       

        personas = List.of(persona1, persona2);
	}


	@Test
	void getAllPersonasTest_200_Ok() throws Exception {
		
		when(personaService.getAllPersonas(any())).thenReturn(new PageImpl<>(personas));

		mockMvc.perform(get("/api/v1/personas"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content", hasSize(2)))
				.andExpect(jsonPath("$.content[0].id", is(1)))
				.andExpect(jsonPath("$.content[0].nroDocumento", is("45666777")))
				.andExpect(jsonPath("$.content[0].email", is("pepito.grillo@mail.com")))
				.andExpect(jsonPath("$.content[1].id", is(2)))
				.andExpect(jsonPath("$.content[1].nroDocumento", is("1234567")))
				.andExpect(jsonPath("$.content[1].email", is("jgomez@mail.com")));

		verify(personaService, times(1)).getAllPersonas(any());
		verifyNoMoreInteractions(personaService);
	}

	@Test
	void getPersonaByIdTest_200_Ok() throws Exception {

		when(personaService.getPersona(anyLong())).thenReturn(personas.get(0));

		mockMvc.perform(get("/api/v1/personas/{id}", 1))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.nroDocumento", is("45666777")))
				.andExpect(jsonPath("$.email", is("pepito.grillo@mail.com")));

		verify(personaService, times(1)).getPersona(anyLong());
		verifyNoMoreInteractions(personaService);
	}

	@Test
	void getPersonaByIdTest_400_BadRequest() throws Exception {
		mockMvc.perform(get("/api/v1/personas/{id}", "abc")).andExpect(status().isBadRequest());
	}

	@Test
	void getPersonaByIdTest_404_NotFound() throws Exception {
		when(personaService.getPersona(anyLong())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get("/api/v1/personas/{id}", 1)).andExpect(status().isNotFound());

		verify(personaService, times(1)).getPersona(anyLong());
		verifyNoMoreInteractions(personaService);
	}

	@Test
	void updatePersonaTest_200_Ok() throws Exception {
		ActualizarPersonaRequest personaRequest = new ActualizarPersonaRequest();
		personaRequest.setEmail("pepito.grillo@mail.com");

		when(personaService.updatePersona(anyLong(), any(ActualizarPersonaRequest.class))).thenReturn(personas.get(0));

		mockMvc.perform(put("/api/v1/personas/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(personaRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.nroDocumento", is("45666777")))
				.andExpect(jsonPath("$.email", is("pepito.grillo@mail.com")));

		verify(personaService, times(1)).updatePersona(anyLong(), any(ActualizarPersonaRequest.class));
		verifyNoMoreInteractions(personaService);
	}
    

	@Test
	void updatePersonaTest_404_NotFound() throws Exception {
		ActualizarPersonaRequest personaRequest = new ActualizarPersonaRequest();
		personaRequest.setEmail("pepito.grillo@mail.com");

		doThrow(ResourceNotFoundException.class).when(personaService).updatePersona(anyLong(),
				any(ActualizarPersonaRequest.class));
		mockMvc.perform(put("/api/v1/personas/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(personaRequest)))
				.andExpect(status().isNotFound());

		verify(personaService, times(1)).updatePersona(anyLong(), any(ActualizarPersonaRequest.class));
		verifyNoMoreInteractions(personaService);
	}


	@Test
	void deletePersonaTest_200_Ok() throws Exception {

		doNothing().when(personaService).deletePersona(anyLong());
		mockMvc.perform(delete("/api/v1/personas/{id}", 1L))
				.andExpect(status().isNoContent());

		verify(personaService, times(1)).deletePersona(anyLong());
		verifyNoMoreInteractions(personaService);
	}
    

	@Test
	void deletePersonaTest_404_NotFound() throws Exception {

		doThrow(ResourceNotFoundException.class).when(personaService).deletePersona(anyLong());
		mockMvc.perform(delete("/api/v1/personas/{id}", 1L))
				.andExpect(status().isNotFound());

		verify(personaService, times(1)).deletePersona(anyLong());
		verifyNoMoreInteractions(personaService);
	}

}
