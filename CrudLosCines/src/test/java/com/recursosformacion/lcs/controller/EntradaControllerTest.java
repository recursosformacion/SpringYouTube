package com.recursosformacion.lcs.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class EntradaControllerTest {

private static MockMvc mockMvc;
    
    @BeforeAll
    public static void setup(){
        mockMvc = MockMvcBuilders
          .standaloneSetup(new EntradaController()).build();
    }
    
	@Test
	void test() {
		fail("Not yet implemented");
	}

//	@Test
//	public void givenPhonePageUri_whenMockMvc_thenReturnsPhonePage() {
//		this.mockMvc.perform(get("/validatePhone")).andExpect(view().name("phoneHome"));
//	}

	@Test
	public void 
	  comprobarDeteccionDniErroneo() throws Exception {
	 
	    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/entrada")
	    		.accept(MediaType.TEXT_HTML)
	            .param("ent_fecha", "13-03-2023")
	            .param("ent_numero", "10")
	            .param("ent_cine", "5954")
	            .param("ent_cantidad", "1")
	            .param("idCliente", "23.234.432"))
	//            .andExpect(model().errorCount(0))
	            .andExpect(status().is4xxClientError());
	}
	
	@Test
	public void 
	  comprobarDeteccionDniCorrecto() throws Exception {
	 
	    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/entrada")
	    		.accept(MediaType.TEXT_HTML)
	            .param("ent_fecha", "13-03-2023")
	            .param("ent_numero", "10")
	            .param("ent_cine", "5954")
	            .param("ent_cantidad", "1")
	            .param("idCliente", "38.474.364-X"))
	//            .andExpect(model().errorCount(0))
	            .andExpect(status().isOk());
	}

}
