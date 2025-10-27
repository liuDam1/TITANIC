package es.etg.psp.titanic.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import es.etg.psp.titanic.model.Persona;

public class ServicioEmergenciasTest {

    @Test
    public void testParsearSalida() {
        ServicioEmergencias servicio = new ServicioEmergencias();
        String salida = "B01,100,30,50,20";

        try {
            java.lang.reflect.Method parsearSalidaMethod = ServicioEmergencias.class.getDeclaredMethod("parsearSalida",
                    String.class);
            parsearSalidaMethod.setAccessible(true);

            Persona persona = (Persona) parsearSalidaMethod.invoke(servicio, salida);

            assertEquals("B01", persona.getIdentificadorBote());
            assertEquals(100, persona.getTotalPersonas());
            assertEquals(30, persona.getMujeres());
            assertEquals(50, persona.getVarones());
            assertEquals(20, persona.getNiños());

        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError();
        }
    }
}