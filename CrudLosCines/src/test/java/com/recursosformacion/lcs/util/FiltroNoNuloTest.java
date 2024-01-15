package com.recursosformacion.lcs.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class FiltroNoNuloTest {
    @Autowired FiltroNoNulo component;

    @Test
    void givenNull_whenValidate_thenConstraintViolationException() {
        assertThrows(ConstraintViolationException.class, () -> component.validateNotNull(null));
    }
    
    @Test
    void givenValue_whenValidate_thenConstraintViolationException() {
        assertEquals(component.validateNotNull("Hola"), 4);
    }
}
