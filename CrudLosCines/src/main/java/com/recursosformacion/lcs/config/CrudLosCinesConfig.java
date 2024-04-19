package com.recursosformacion.lcs.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrudLosCinesConfig {

	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
