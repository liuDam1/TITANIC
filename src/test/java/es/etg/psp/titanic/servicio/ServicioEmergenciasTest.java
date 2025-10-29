package es.etg.psp.titanic.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import es.etg.psp.titanic.model.Persona;

public class ServicioEmergenciasTest {

    @Test
    public void testParsearSalida() {
        ServicioEmergencias servicio = new ServicioEmergencias();
        String salida = "B01;100;30;50;20";

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
            fail("Se produjo una excepción al probar el método parsearSalida: " + e.getMessage());
        }
    }

    @Test
    public void testGenerarIdentificadorBote() {
        ServicioEmergencias servicio = new ServicioEmergencias();

        try {
            java.lang.reflect.Method method = ServicioEmergencias.class.getDeclaredMethod("generarIdentificadorBote",
                    int.class);
            method.setAccessible(true);

            assertEquals("B00", method.invoke(servicio, 0));
            assertEquals("B01", method.invoke(servicio, 1));
            assertEquals("B19", method.invoke(servicio, 19));

        } catch (Exception e) {
            e.printStackTrace();
            fail("Se produjo una excepción al probar el método generarIdentificadorBote: " + e.getMessage());
        }
    }
}