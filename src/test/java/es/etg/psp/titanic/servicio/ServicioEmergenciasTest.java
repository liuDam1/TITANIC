package es.etg.psp.titanic.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import es.etg.psp.titanic.model.Persona;

public class ServicioEmergenciasTest {

    @Test
    public void testServicioEmergenciasGenera20Botes() {
        ServicioEmergencias servicio = new ServicioEmergencias();
        
        try {
            java.lang.reflect.Field resultadosField = ServicioEmergencias.class.getDeclaredField("resultados");
            resultadosField.setAccessible(true);
            
            List<Persona> resultadosMock = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                String id = String.format("B%02d", i);
                Persona persona = new Persona(id, 50 + i, 20 + i, 20, 10 + i);
                resultadosMock.add(persona);
            }
            
            resultadosField.set(servicio, resultadosMock);
            
            @SuppressWarnings("unchecked")
            List<Persona> resultados = (List<Persona>) resultadosField.get(servicio);
            assertEquals(20, resultados.size());
            
            for (int i = 0; i < 20; i++) {
                String expectedId = String.format("B%02d", i);
                assertEquals(expectedId, resultados.get(i).getIdentificadorBote());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError();
        }
    }
    
    @Test
    public void testParsearSalida() {
        ServicioEmergencias servicio = new ServicioEmergencias();
        String salida = "B01,100,30,50,20";
        
        try {
            java.lang.reflect.Method parsearSalidaMethod = ServicioEmergencias.class.getDeclaredMethod("parsearSalida", String.class);
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