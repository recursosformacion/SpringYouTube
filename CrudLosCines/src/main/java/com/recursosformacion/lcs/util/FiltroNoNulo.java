package com.recursosformacion.lcs.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@Component
@Validated
public class FiltroNoNulo {
	public int validateNotNull(@NotNull String data) {
        return data.length();
    }

}
