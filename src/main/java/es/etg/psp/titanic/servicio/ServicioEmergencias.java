package es.etg.psp.titanic.servicio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import es.etg.psp.titanic.informe.FactoriaInforme;
import es.etg.psp.titanic.informe.Informe;
import es.etg.psp.titanic.informe.TipoInforme;
import es.etg.psp.titanic.model.Persona;

public class ServicioEmergencias {
    private static final String MSG_ERROR = "Se ha producido un error al ejecutar el comando";
    private static final String MSG_BOTE_COMPLETADO = "Bote %s completado con %d personas a bordo";
    private static final String MSG_INICIANDO_SIMULACION = "Iniciando simulación de rescate...\n";
    private static final String MSG_GENERANDO_INFORMES = "\nGenerando informes finales...";
    private static final String RUTA_CLASES = "target/classes";
    private static final int NUMERO_BOTES = 20;
    private static final String FORMATO_ID_BOTE = "B%02d";
    private static final String COMANDO_JAVA = "java";
    private static final String PARAMETRO_CLASSPATH = "-cp";
    private static final String CLASE_BOTE = "es.etg.psp.titanic.barcos.Bote";
    private static final String SEPARADOR_CSV = ",";
    private static final String SALTO_LINEA = "\n";
    private static final String ESPACIO_BOTE = " en bote ";
    private static final String FORMATO_MENSAJE = "%s\n";

    private final List<Persona> resultados;

    public ServicioEmergencias() {
        this.resultados = new ArrayList<>();
    }

    public void iniciarSimulacion() {
        System.out.println(MSG_INICIANDO_SIMULACION);

        resultados.clear();

        for (int i = 0; i < NUMERO_BOTES; i++) {
            final String id = generarIdentificadorBote(i);
            procesarBote(id);
        }

        generarInformes();
    }

    private String generarIdentificadorBote(int indice) {
        return String.format(FORMATO_ID_BOTE, indice);
    }

    private void procesarBote(String id) {
        try {
            String[] comando = construirComandoBote(id);
            Process process = Runtime.getRuntime().exec(comando);

            String salida = leerSalidaProceso(process);
            int exitVal = process.waitFor();

            if (exitVal == 0 && !salida.isEmpty()) {
                procesarSalidaExitosa(id, salida);
            } else {
                manejarErrorProceso(id);
            }
        } catch (IOException | InterruptedException e) {
            manejarErrorProceso(id);
        }
    }

    private String[] construirComandoBote(String id) {
        return new String[] {
                COMANDO_JAVA,
                PARAMETRO_CLASSPATH,
                RUTA_CLASES,
                CLASE_BOTE,
                id
        };
    }

    private String leerSalidaProceso(Process process) throws IOException {
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append(SALTO_LINEA);
        }

        return output.toString().trim();
    }

    private void procesarSalidaExitosa(String id, String salida) {
        Persona stats = parsearSalida(salida);

        System.out.printf(FORMATO_MENSAJE,
                String.format(MSG_BOTE_COMPLETADO, stats.getIdentificadorBote(),
                        stats.getTotalPersonas()));

        resultados.add(stats);
    }

    private void manejarErrorProceso(String id) {
        System.err.println(MSG_ERROR + ESPACIO_BOTE + id);
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
