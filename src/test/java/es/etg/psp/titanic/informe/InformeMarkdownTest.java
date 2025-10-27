package es.etg.psp.titanic.informe;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import es.etg.psp.titanic.model.Persona;

public class InformeMarkdownTest {

    @Test
    public void testGenerarInformeMarkdown() {
        List<Persona> datosPrueba = Arrays.asList(
                new Persona("B01", 50, 20, 25, 5),
                new Persona("B02", 30, 15, 10, 5));

        try {
            InformeMarkdown informeMarkdown = new InformeMarkdown();
            informeMarkdown.generarInforme(datosPrueba);

            File archivoInforme = new File("src/main/resources/Informe.md");
            assertTrue(archivoInforme.exists());

            String contenido = new String(Files.readAllBytes(archivoInforme.toPath()));

            assertTrue(contenido.contains("# SERVICIO DE EMERGENCIAS"));
            assertTrue(contenido.contains("## Bote B01"));
            assertTrue(contenido.contains("## Bote B02"));
            assertTrue(contenido.contains("## Total"));
            assertTrue(contenido.contains("- Total Salvados 80"));
            assertTrue(contenido.contains("B01"));
            assertTrue(contenido.contains("B02"));

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}