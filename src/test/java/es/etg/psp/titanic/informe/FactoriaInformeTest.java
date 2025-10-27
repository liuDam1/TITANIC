package es.etg.psp.titanic.informe;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FactoriaInformeTest {

    @Test
    public void testCrearInformeConsola() {
        Informe informe = FactoriaInforme.crearInforme(TipoInforme.CONSOLA);
        assertTrue(informe instanceof InformeConsola);
    }
    
    @Test
    public void testCrearInformeMarkdown() {
        Informe informe = FactoriaInforme.crearInforme(TipoInforme.MARKDOWN);
        assertTrue(informe instanceof InformeMarkdown);
    }
    
    @Test
    public void testCrearInformeConTipoNoValido() {
        assertThrows(IllegalArgumentException.class, () -> {
            FactoriaInforme.crearInforme(null);
        });
    }
}