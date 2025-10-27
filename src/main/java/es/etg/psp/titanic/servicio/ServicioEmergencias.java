package es.etg.psp.titanic.servicio;

import java.io.*;
import java.util.*;

import es.etg.psp.titanic.informe.FactoriaInforme;
import es.etg.psp.titanic.informe.Informe;
import es.etg.psp.titanic.informe.TipoInforme;
import es.etg.psp.titanic.model.Persona;

public class ServicioEmergencias {
    public static final String MSG_ERROR = "Se ha producido un error al ejecutar el comando";
    public static final String MSG_BOTE_COMPLETADO = "Bote %s completado con %d personas a bordo";
    public static final String MSG_INICIANDO_SIMULACION = "Iniciando simulación de rescate...\n";
    public static final String MSG_GENERANDO_INFORMES = "\nGenerando informes finales...";
    public static final String RUTA_CLASES = "target/classes";
    public static final int NUMERO_BOTES = 20;
    public static final String FORMATO_ID_BOTE = "B%02d";
    public static final String COMANDO_JAVA = "java";
    public static final String PARAMETRO_CLASSPATH = "-cp";
    public static final String CLASE_BOTE = "es.etg.psp.titanic.barcos.Bote";
    public static final String SEPARADOR_CSV = ",";
    public static final String SALTO_LINEA = "\n";
    public static final String ESPACIO_BOTE = " en bote ";
    public static final String FORMATO_MENSAJE = "%s\n";
    private final List<Persona> resultados = new ArrayList<>();

    public void iniciarSimulacion() {
        System.out.println(MSG_INICIANDO_SIMULACION);

        for (int i = 0; i < NUMERO_BOTES; i++) {
            final String id = String.format(FORMATO_ID_BOTE, i);

            try {
                String[] comando = {
                        COMANDO_JAVA,
                        PARAMETRO_CLASSPATH,
                        RUTA_CLASES,
                        CLASE_BOTE,
                        id
                };

                Process process = Runtime.getRuntime().exec(comando);

                StringBuilder output = new StringBuilder();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append(SALTO_LINEA);
                }

                int exitVal = process.waitFor();

                if (exitVal == 0) {
                    String salida = output.toString().trim();
                    if (!salida.isEmpty()) {
                        Persona stats = parsearSalida(salida);

                        System.out.printf(FORMATO_MENSAJE,
                                String.format(MSG_BOTE_COMPLETADO, stats.getIdentificadorBote(),
                                        stats.getTotalPersonas()));

                        resultados.add(stats);
                    }
                } else {
                    System.err.println(MSG_ERROR + ESPACIO_BOTE + id);
                }

            } catch (IOException | InterruptedException e) {
                System.err.println(MSG_ERROR + ESPACIO_BOTE + id);
            }
        }

        generarInformes();
    }

    private Persona parsearSalida(String linea) {
        String[] partes = linea.split(SEPARADOR_CSV);
        return new Persona(
                partes[0],
                Integer.parseInt(partes[1]),
                Integer.parseInt(partes[2]),
                Integer.parseInt(partes[3]),
                Integer.parseInt(partes[4]));
    }

    private void generarInformes() {
        System.out.println(MSG_GENERANDO_INFORMES);

        resultados.sort(Comparator.comparingInt(s -> Integer.parseInt(s.getIdentificadorBote().substring(1))));

        Informe consola = FactoriaInforme.crearInforme(TipoInforme.CONSOLA);
        Informe markdown = FactoriaInforme.crearInforme(TipoInforme.MARKDOWN);

        consola.generarInforme(resultados);
        markdown.generarInforme(resultados);
    }
}
