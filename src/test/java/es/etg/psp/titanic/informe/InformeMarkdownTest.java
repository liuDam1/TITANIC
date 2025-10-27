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
            assertTrue(archivoInforme.exists(), "El archivo src/main/resources/Informe.md debe existir");

            String contenido = new String(Files.readAllBytes(archivoInforme.toPath()));

            assertTrue(contenido.contains("# SERVICIO DE EMERGENCIAS"), "Debe contener el título principal");
            assertTrue(contenido.contains("## Bote B01"), "Debe contener la sección para el bote B01");
            assertTrue(contenido.contains("## Bote B02"), "Debe contener la sección para el bote B02");
            assertTrue(contenido.contains("## Total"), "Debe contener la sección de total");
            assertTrue(contenido.contains("- Total Salvados 80"), "Debe contener el total correcto de 80 personas");
            assertTrue(contenido.contains("B01"), "Debe contener el identificador del bote B01");
            assertTrue(contenido.contains("B02"), "Debe contener el identificador del bote B02");

        } catch (IOException e) {
            e.printStackTrace();
            fail("Error al leer el archivo de informe: " + e.getMessage());
        }
    }
}