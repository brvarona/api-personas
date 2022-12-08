package com.brvarona.personas.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.brvarona.personas.mapper.PersonaMapper;
import com.brvarona.personas.model.Pais;
import com.brvarona.personas.model.Persona;
import com.brvarona.personas.model.TipoDocumento;
import com.brvarona.personas.payload.ActualizarPersonaRequest;
import com.brvarona.personas.payload.CrearPersonaRequest;
import com.brvarona.personas.repository.PaisRepository;
import com.brvarona.personas.repository.PersonaRepository;
import com.brvarona.personas.repository.TipoDocumentoRepository;


@SpringBootTest
class PersonaServiceTest {

	@MockBean
	private PersonaRepository personaRepository;
	
	@MockBean
	private TipoDocumentoRepository tipoDocumentoRepository;
	
	@MockBean
	private PaisRepository paisRepository;
	
	@MockBean
	private PersonaMapper personaMapper;

	@Autowired
	private PersonaService personaService;
	
	private List<Persona> personas;
	
	private Pais pais;
	
	private TipoDocumento tipoDocumento;	

    @BeforeEach
    public void setUp() {
    	pais = new Pais();
    	pais.setId(1L);
    	pais.setNombre("Argentina");
    	pais.setCodigo("ARG");
    	
    	tipoDocumento = new TipoDocumento();
    	tipoDocumento.setId(1L);
    	tipoDocumento.setTipo("DU");
    	tipoDocumento.setDescripcion("Documento unico");
    	
        var persona1 = new Persona();
        persona1.setId(1L);
        persona1.setNombre("Pepe");
        persona1.setApellido("Grillo");
        persona1.setTipoDocumento(tipoDocumento);
        persona1.setNroDocumento("45666777");
        persona1.setPais(pais);
        persona1.setEdad(20);
        persona1.setEmail("pepito.grillo@mail.com");
       
        var persona2 = new Persona();
        persona2.setId(2L);
        persona2.setNombre("Juan");
        persona2.setApellido("Gomez");
        persona2.setTipoDocumento(tipoDocumento);
        persona2.setNroDocumento("1234567");
        persona2.setPais(pais);
        persona2.setEdad(50);
        persona2.setEmail("jgomez@mail.com");       

        personas = List.of(persona1, persona2);
    }

	@Test
	void getAllPersonasTest() {		
		
		when(personaRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(personas));
		
		Page<Persona> result = personaService.getAllPersonas(Pageable.ofSize(20));

		assertEquals(2, result.getNumberOfElements());
		assertThat(result.getContent().get(0).getId()).isEqualTo(1);
		assertThat(result.getContent().get(0).getNroDocumento()).isEqualTo("45666777");
		assertThat(result.getContent().get(0).getApellido()).isEqualTo("Grillo");
		assertThat(result.getContent().get(1).getId()).isEqualTo(2);
		assertThat(result.getContent().get(1).getNroDocumento()).isEqualTo("1234567");
		assertThat(result.getContent().get(1).getApellido()).isEqualTo("Gomez");
		
		verify(personaRepository, times(1)).findAll(any(Pageable.class));
		verifyNoMoreInteractions(personaRepository);
	}

	@Test
	void findPersonaByIdTest() {

		when(personaRepository.findById(anyLong())).thenReturn(Optional.of(personas.get(0)));

		Persona result = personaService.getPersona(1L);
		assertNotNull(result);
		assertThat(result.getId()).isEqualTo(1);
		assertThat(result.getTipoDocumento().getTipo()).isEqualTo("DU");
		assertThat(result.getNroDocumento()).isEqualTo("45666777");
		assertThat(result.getPais().getId()).isEqualTo(1L);
		
		verify(personaRepository, times(1)).findById(anyLong());
		verifyNoMoreInteractions(personaRepository);
	}

	
	@Test
	void createPersonaTest() {
		CrearPersonaRequest personaRequest = new CrearPersonaRequest();
		personaRequest.setNombre("Pepe");
		personaRequest.setApellido("Grillo");
		personaRequest.setTipoDocumentoId(1L);
		personaRequest.setNroDocumento("45666777");
		personaRequest.setPaisId(1L);
		personaRequest.setEdad(20);
		personaRequest.setEmail("pepito.grillo@mail.com");
		
		when(paisRepository.findById(any(Long.class))).thenReturn(Optional.of(pais));
		when(tipoDocumentoRepository.findById(any(Long.class))).thenReturn(Optional.of(tipoDocumento));
		when(personaMapper.fromCreateRequest(any(CrearPersonaRequest.class))).thenReturn(personas.get(0));
		when(personaRepository.findByTipoDocumentoAndNroDocumentoAndPais(any(TipoDocumento.class), any(String.class), any(Pais.class)))
																			.thenReturn(Optional.empty());

		when(personaRepository.save(any(Persona.class))).thenReturn(personas.get(0));

		Persona result = personaService.createPersona(personaRequest);
		assertNotNull(result);
		assertThat(result.getId()).isEqualTo(1);
		assertThat(result.getTipoDocumento().getId()).isEqualTo(1);
		assertThat(result.getPais().getId()).isEqualTo(1);
		assertThat(result.getEmail()).isEqualTo("pepito.grillo@mail.com");
		
		verify(personaRepository, times(1)).findByTipoDocumentoAndNroDocumentoAndPais(any(TipoDocumento.class), any(String.class), any(Pais.class));
		verify(personaRepository, times(1)).save(any(Persona.class));
		verifyNoMoreInteractions(personaRepository);
	}

	
	@Test
	void updatePersonaTest() {
		ActualizarPersonaRequest personaRequest = new ActualizarPersonaRequest();
		personaRequest.setEmail("brvarona@otromail.com");
		
		var personaMap = personas.get(0);
		personaMap.setEmail("brvarona@otromail.com");
		
		when(personaMapper.fromUpdateRequest(any(Persona.class), any(ActualizarPersonaRequest.class))).thenReturn(personaMap);
		when(personaRepository.findById(anyLong())).thenReturn(Optional.of(personas.get(0)));
		when(personaRepository.save(any(Persona.class))).thenReturn(personas.get(0));

		Persona result = personaService.updatePersona(1L, personaRequest);
		assertNotNull(result);
		assertThat(result.getId()).isEqualTo(1);
		assertThat(result.getEmail()).isEqualTo("brvarona@otromail.com");
		
		verify(personaRepository, times(1)).findById(anyLong());
		verify(personaRepository, times(1)).save(any(Persona.class));
		verifyNoMoreInteractions(personaRepository);
	}

	@Test
	void deletePersonaTest() {

		when(personaRepository.findById(anyLong())).thenReturn(Optional.of(personas.get(0)));
		doNothing().when(personaRepository).delete(any(Persona.class));
		
		personaService.deletePersona(1L);
		
		verify(personaRepository, times(1)).findById(anyLong());
		verify(personaRepository, times(1)).delete(any(Persona.class));
		verifyNoMoreInteractions(personaRepository);
	}

}
