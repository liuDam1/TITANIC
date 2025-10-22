package es.etg.psp.titanic;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InformeMarkdown implements Informe {
    // Constantes para el informe
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

    @Override
    public void generarInforme(List<Persona> resultados) {
        int totalPersonas = 0, totalMujeres = 0, totalVarones = 0, totalNinos = 0;

        StringBuilder sb = new StringBuilder();
        sb.append(TITULO_INFORME);
        sb.append(TEXTO_FECHA)
          .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMATO_FECHA)))
          .append(SEPARADOR);

        for (Persona persona : resultados) {
            sb.append(TITULO_BOTE).append(persona.getIdentificadorBote()).append(SEPARADOR)
              .append(TOTAL_SALVADOS).append(persona.getTotalPersonas()).append("\n")
              .append(MUJERES).append(persona.getMujeres()).append("\n")
              .append(VARONES).append(persona.getVarones()).append("\n")
              .append(NINOS).append(persona.getNiños()).append(SEPARADOR);

            totalPersonas += persona.getTotalPersonas();
            totalMujeres += persona.getMujeres();
            totalVarones += persona.getVarones();
            totalNinos += persona.getNiños();
        }

        sb.append(TITULO_TOTAL)
          .append(TOTAL_SALVADOS).append(totalPersonas).append("\n")
          .append(MUJERES).append(totalMujeres).append("\n")
          .append(VARONES).append(totalVarones).append("\n")
          .append(NINOS).append(totalNinos).append("\n");

        try (FileWriter fw = new FileWriter(ARCHIVO_INFORME)) {
            fw.write(sb.toString());
            System.out.println(MSG_EXITO_GENERACION);
        } catch (IOException e) {
            System.err.println(MSG_ERROR_GENERACION);
        }
    }
}

