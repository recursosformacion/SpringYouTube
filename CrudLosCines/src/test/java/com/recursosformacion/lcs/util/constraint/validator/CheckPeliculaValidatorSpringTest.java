package com.recursosformacion.lcs.util.constraint.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.recursosformacion.lcs.service.PeliculaService;
import jakarta.validation.ConstraintValidatorContext;

@ExtendWith(MockitoExtension.class)
public class CheckPeliculaValidatorSpringTest {

	@Mock
	private PeliculaService peliculaService;

	@MockBean
	private ConstraintValidatorContext context;

	@Test
	public void testIsValidWithValidPelicula() {
		Long peliculaId = 1L;

		when(peliculaService.existe(peliculaId)).thenReturn(true);

		boolean result = probar(peliculaId);
		assertTrue(result);
		verify(peliculaService).existe(peliculaId);
	}

	@Test
	public void testIsValidWithNullPelicula() {
		Long peliculaId = null;

		boolean result = probar(peliculaId);
		assertTrue(result);
		verify(peliculaService, never()).existe(anyLong());
	}

	@Test
	public void testNotValidWidthInvalidData() {
		Long peliculaId = 1L;

		when(peliculaService.existe(peliculaId)).thenReturn(false);

		boolean result = probar(peliculaId);
		assertFalse(result);
		verify(peliculaService).existe(peliculaId);
	}

	boolean probar(Long peliculaId) {
		return new CheckPeliculaValidator(peliculaService).isValid(peliculaId, context);
	}
}
