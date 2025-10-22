package es.etg.psp.titanic;

public class FactoriaInforme {
    // Constantes para tipos de informes
    private static final String TIPO_MARKDOWN = "markdown";
    private static final String TIPO_CONSOLA = "consola";
    private static final String ERROR_TIPO_INFORME = "Tipo de informe no reconocido: ";

    public static Informe crearInforme(String tipo) {
        switch (tipo.toLowerCase()) {
            case TIPO_MARKDOWN: return new InformeMarkdown();
            case TIPO_CONSOLA: return new InformeConsola();
            default: throw new IllegalArgumentException(ERROR_TIPO_INFORME + tipo);
        }
    }
}
