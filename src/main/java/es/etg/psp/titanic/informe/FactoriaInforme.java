package es.etg.psp.titanic.informe;

public class FactoriaInforme {
    private static final String ERROR_TIPO_INFORME = "Tipo de informe no reconocido: ";

    public static Informe crearInforme(TipoInforme tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException(ERROR_TIPO_INFORME + tipo);
        }
        
        switch (tipo) {
            case MARKDOWN:
                return new InformeMarkdown();
            case CONSOLA:
                return new InformeConsola();
            default:
                throw new IllegalArgumentException(ERROR_TIPO_INFORME + tipo);
        }
    }
}