package com.recursosformacion.lcs.util.constraint.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.recursosformacion.lcs.service.EntradaService;
import jakarta.validation.ConstraintValidatorContext;


public class CheckEntradaValidatorTest {

    @Mock
    private EntradaService entradaService;

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private CheckEntradaValidator checkEntradaValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsValid() {
        Long entradaId = 1L;

        when(entradaService.existsById(entradaId)).thenReturn(true);

        boolean result = checkEntradaValidator.isValid(entradaId, context);

        assertTrue(result);
        verify(entradaService).existsById(entradaId);
    }

    @Test
    public void testIsValidWithNullEntrada() {
        Long entradaId = null;

        boolean result = checkEntradaValidator.isValid(entradaId, context);

        assertTrue(result);
        verify(entradaService, never()).existsById(anyLong());
    }
}

