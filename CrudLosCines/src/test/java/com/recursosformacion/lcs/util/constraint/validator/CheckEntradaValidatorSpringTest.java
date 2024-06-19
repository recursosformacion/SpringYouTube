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
import com.recursosformacion.lcs.service.EntradaService;
import jakarta.validation.ConstraintValidatorContext;

@ExtendWith(MockitoExtension.class)
public class CheckEntradaValidatorSpringTest {

	@Mock
	private EntradaService entradaService;

	@MockBean
	private ConstraintValidatorContext context;

	@Test
	public void testIsValidWithValidEntrada() {
		Long entradaId = 1L;

		when(entradaService.existe(entradaId)).thenReturn(true);

		boolean result = probar(entradaId);
		assertTrue(result);
		verify(entradaService).existe(entradaId);
	}

	@Test
	public void testIsValidWithNullEntrada() {
		Long entradaId = null;

		boolean result = probar(entradaId);
		assertTrue(result);
		verify(entradaService, never()).existe(anyLong());
	}

	@Test
	public void testNotValidWidthInvalidData() {
		Long entradaId = 1L;

		when(entradaService.existe(entradaId)).thenReturn(false);

		boolean result = probar(entradaId);
		assertFalse(result);
		verify(entradaService).existe(entradaId);
	}

	boolean probar(Long entradaId) {
		return new CheckEntradaValidator(entradaService).isValid(entradaId, context);
	}
}
