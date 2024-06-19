package com.recursosformacion.lcs.util.constraint.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DniConstraintValidationTest {

	 static  String DNI_OK;
	    static  String DNI_ERR;
	    
	    DniConstraintValidator validator = new DniConstraintValidator();
	    
	@BeforeAll
	static void init() {
		System.out.println("Inicio de las pruebas******************************");
		Random random = new Random();
        int numeroDni = random.nextInt(100000000);
        char letraDniOk = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numeroDni % 23);
        char letraDniErr = "TRWAGMYFPDXBNJZSQVHLCKE".charAt((numeroDni + 1) % 23);
        DNI_OK = String.format("%,d", numeroDni) + "-" + letraDniOk;
		if (numeroDni < 10000000) {
			DNI_OK = "0" + DNI_OK;
		}
        DNI_ERR = String.format("%,d", numeroDni) + "-" + letraDniErr;
        
        System.out.println("DNI:    " + DNI_OK);
        System.out.println("DNI_Err:" + DNI_ERR);
        
    	System.out.println("**************************************");
	}
	
    	@Test
    	void testDniOk() {
    		assertTrue(validator.isValid(DNI_OK, null));
    	}
    	
    	@Test
		void testDniErr() {
			assertFalse(validator.isValid(DNI_ERR, null));
		}
    	
    	
    	
	
}
