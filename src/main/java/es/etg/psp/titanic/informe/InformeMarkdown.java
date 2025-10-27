package es.etg.psp.titanic.informe;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import es.etg.psp.titanic.model.Persona;

public class InformeMarkdown implements Informe {
    private static final String TITULO_INFORME = "# SERVICIO DE EMERGENCIAS\n\n";
    private static final String FORMATO_FECHA = "dd/MM/yyyy 'a las' HH:mm:ss";
    private static final String TEXTO_FECHA = "Ejecucion realizada el dia ";
    private static final String SEPARADOR = "\n\n";
    private static final String TITULO_BOTE = "## Bote ";
    private static final String TITULO_TOTAL = "## Total\n";
    private static final String TOTAL_SALVADOS = "- Total Salvados ";
    private static final String MUJERES = "  - Mujeres ";
    private static final String VARONES = "  - Varones ";
    private static final String NINOS = "  - Ninos ";
    private static final String ARCHIVO_INFORME = "src/main/resources/Informe.md";
    private static final String MSG_EXITO_GENERACION = "\nInforme Markdown generado correctamente";
    private static final String MSG_ERROR_GENERACION = "Error al escribir el informe Markdown";

    public InformeMarkdown() {
    }

    @Override
    public void generarInforme(List<Persona> resultados) {
        ContadoresTotales contadores = new ContadoresTotales();

        StringBuilder contenido = new StringBuilder();
        agregarCabeceraInforme(contenido);
        agregarSeccionesBotes(contenido, resultados, contadores);
        agregarSeccionTotal(contenido, contadores);

        escribirInformeAArchivo(contenido.toString());
    }

    private void agregarCabeceraInforme(StringBuilder sb) {
        sb.append(TITULO_INFORME);
        sb.append(TEXTO_FECHA)
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMATO_FECHA)))
                .append(SEPARADOR);
    }

    private void agregarSeccionesBotes(StringBuilder sb, List<Persona> resultados, ContadoresTotales contadores) {
        for (Persona persona : resultados) {
            agregarSeccionBote(sb, persona);
            actualizarContadores(contadores, persona);
        }
    }

    private void agregarSeccionBote(StringBuilder sb, Persona persona) {
        sb.append(TITULO_BOTE).append(persona.getIdentificadorBote()).append(SEPARADOR)
                .append(TOTAL_SALVADOS).append(persona.getTotalPersonas()).append("\n")
                .append(MUJERES).append(persona.getMujeres()).append("\n")
                .append(VARONES).append(persona.getVarones()).append("\n")
                .append(NINOS).append(persona.getNiños()).append(SEPARADOR);
    }

    private void actualizarContadores(ContadoresTotales contadores, Persona persona) {
        contadores.totalPersonas += persona.getTotalPersonas();
        contadores.totalMujeres += persona.getMujeres();
        contadores.totalVarones += persona.getVarones();
        contadores.totalNinos += persona.getNiños();
    }

    private void agregarSeccionTotal(StringBuilder sb, ContadoresTotales contadores) {
        sb.append(TITULO_TOTAL)
                .append(TOTAL_SALVADOS).append(contadores.totalPersonas).append("\n")
                .append(MUJERES).append(contadores.totalMujeres).append("\n")
                .append(VARONES).append(contadores.totalVarones).append("\n")
                .append(NINOS).append(contadores.totalNinos).append("\n");
    }

    private void escribirInformeAArchivo(String contenido) {
        try (FileWriter fw = new FileWriter(ARCHIVO_INFORME)) {
            fw.write(contenido);
            System.out.println(MSG_EXITO_GENERACION);
        } catch (IOException e) {
            System.err.println(MSG_ERROR_GENERACION);
        }
    }

    private static class ContadoresTotales {
        int totalPersonas = 0;
        int totalMujeres = 0;
        int totalVarones = 0;
        int totalNinos = 0;
    }
}