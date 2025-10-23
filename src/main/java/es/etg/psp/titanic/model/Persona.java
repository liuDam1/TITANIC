package es.etg.psp.titanic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Persona {
    private final String identificadorBote;
    private final int totalPersonas;
    private final int mujeres;
    private final int varones;
    private final int niños;
}