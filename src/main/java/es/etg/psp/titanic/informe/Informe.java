package es.etg.psp.titanic.informe;

import java.util.List;

import es.etg.psp.titanic.model.Persona;

public interface Informe {
    void generarInforme(List<Persona> resultados);
}