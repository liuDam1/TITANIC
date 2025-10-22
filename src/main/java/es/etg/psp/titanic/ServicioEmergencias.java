package es.etg.psp.titanic;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class ServicioEmergencias {

    // Constantes para mensajes y valores
    public static final String MSG_ERROR = "Se ha producido un error al ejecutar el comando";
    public static final String MSG_BOTE_COMPLETADO = "Bote %s completado con %d personas a bordo";
    public static final String MSG_INICIANDO_SIMULACION = "Iniciando simulación de rescate...\n";
    public static final String MSG_GENERANDO_INFORMES = "\nGenerando informes finales...";
    public static final String RUTA_CLASES = "target/classes";
    public static final int NUMERO_BOTES = 20;
    public static final int TAMANO_POOL_HILOS = 10;
    public static final String FORMATO_ID_BOTE = "B%02d";

    private final List<Persona> resultados = Collections.synchronizedList(new ArrayList<>());
    private int botesCompletados = 0;
    private final Object lock = new Object();

    public void iniciarSimulacion() {
        System.out.println(MSG_INICIANDO_SIMULACION);
        ExecutorService executor = Executors.newFixedThreadPool(TAMANO_POOL_HILOS); // Usar un pool de hilos

        for (int i = 0; i < NUMERO_BOTES; i++) {
            final String id = String.format(FORMATO_ID_BOTE, i);
            
            // Enviar cada bote a ejecutarse en un hilo separado
            executor.submit(() -> {
                // Comando para lanzar otro proceso Java
                String[] comando = {
                    "java",
                    "-cp",
                    RUTA_CLASES, // carpeta donde Maven coloca los .class
                    "es.etg.psp.titanic.Bote",
                    id
                };

                try {
                    Process process = Runtime.getRuntime().exec(comando);
                    StringBuilder output = new StringBuilder();

                    // Capturar salida estándar
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }

                    int exitVal = process.waitFor();

                    if (exitVal == 0) {
                        String salida = output.toString().trim();
                        if (!salida.isEmpty()) {
                            Persona stats = parsearSalida(salida);
                            
                            // Mostrar información del bote inmediatamente
                            System.out.printf(MSG_BOTE_COMPLETADO + "\n", stats.getIdentificadorBote(), stats.getTotalPersonas());
                            
                            // Añadir a resultados de forma sincronizada
                            synchronized (resultados) {
                                resultados.add(stats);
                            }
                            
                            // Verificar si todos los botes han completado
                            synchronized (lock) {
                                botesCompletados++;
                                if (botesCompletados == NUMERO_BOTES) {
                                    // Todos los botes han completado, generar informes
                                    generarInformes();
                                }
                            }
                        }
                    } else {
                        System.err.println(MSG_ERROR + " en bote " + id);
                    }

                } catch (IOException | InterruptedException e) {
                    System.err.println(MSG_ERROR + " en bote " + id);
                }
            });
        }
        
        executor.shutdown(); // Apagar el executor después de enviar todas las tareas
    }

    private Persona parsearSalida(String linea) {
        // formato: id,total,mujeres,varones,niños
        String[] partes = linea.split(",");
        return new Persona(
                partes[0],
                Integer.parseInt(partes[1]),
                Integer.parseInt(partes[2]),
                Integer.parseInt(partes[3]),
                Integer.parseInt(partes[4])
        );
    }

    private void generarInformes() {
        System.out.println(MSG_GENERANDO_INFORMES);
        
        // Constantes para tipos de informes
        final String TIPO_INFORME_CONSOLA = "consola";
        final String TIPO_INFORME_MARKDOWN = "markdown";
        
        // Ordenar los resultados por ID del bote para un informe más legible
        synchronized (resultados) {
            resultados.sort(Comparator.comparingInt(s -> Integer.parseInt(s.getIdentificadorBote().substring(1))));
        }
        
        Informe consola = FactoriaInforme.crearInforme(TIPO_INFORME_CONSOLA);
        Informe markdown = FactoriaInforme.crearInforme(TIPO_INFORME_MARKDOWN);

        consola.generarInforme(resultados);
        markdown.generarInforme(resultados);
    }
}
