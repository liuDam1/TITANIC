package es.etg.psp.titanic.informe;

import es.etg.psp.titanic.informe.TipoInforme;

public class FactoriaInforme {
    // Constantes para mensajes de error
    private static final String ERROR_TIPO_INFORME = "Tipo de informe no reconocido: ";

    public static Informe crearInforme(TipoInforme tipo) {
        switch (tipo) {
            case MARKDOWN: return new InformeMarkdown();
            case CONSOLA: return new InformeConsola();
            default: throw new IllegalArgumentException(ERROR_TIPO_INFORME + tipo);
        }
    }
}