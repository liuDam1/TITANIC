package es.etg.psp.titanic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import org.junit.jupiter.api.Test;

public class PersonaTest {

    @Test
    public void testPersonaCreacion() {
        String idBote = "B01";
        int totalPersonas = 100;
        int mujeres = 40;
        int varones = 50;
        int niños = 10;
        
        Persona persona = new Persona(idBote, totalPersonas, mujeres, varones, niños);
        
        assertEquals(idBote, persona.getIdentificadorBote());
        assertEquals(totalPersonas, persona.getTotalPersonas());
        assertEquals(mujeres, persona.getMujeres());
        assertEquals(varones, persona.getVarones());
        assertEquals(niños, persona.getNiños());
    }
    
    @Test
    public void testPersonaSumaCorrecta() {
        int mujeres = 30;
        int varones = 40;
        int niños = 30;
        int totalPersonas = mujeres + varones + niños;
        
        Persona persona = new Persona("B02", totalPersonas, mujeres, varones, niños);
        
        assertEquals(totalPersonas, persona.getMujeres() + persona.getVarones() + persona.getNiños());
    }
    
    @Test
    public void testPersonaConValoresMinimos() {
        Persona persona = new Persona("B03", 1, 0, 0, 1);
        
        assertEquals(1, persona.getTotalPersonas());
        assertEquals(0, persona.getMujeres());
        assertEquals(0, persona.getVarones());
        assertEquals(1, persona.getNiños());
    }
    
    @Test
    public void testPersonaConMockito() {
        Persona personaMock = mock(Persona.class);
        
        when(personaMock.getIdentificadorBote()).thenReturn("B04");
        when(personaMock.getTotalPersonas()).thenReturn(150);
        when(personaMock.getMujeres()).thenReturn(50);
        when(personaMock.getVarones()).thenReturn(70);
        when(personaMock.getNiños()).thenReturn(30);
        
        assertEquals("B04", personaMock.getIdentificadorBote());
        assertEquals(150, personaMock.getTotalPersonas());
        assertEquals(50, personaMock.getMujeres());
        assertEquals(70, personaMock.getVarones());
        assertEquals(30, personaMock.getNiños());
        
        verify(personaMock).getIdentificadorBote();
        verify(personaMock).getTotalPersonas();
        verify(personaMock).getMujeres();
        verify(personaMock).getVarones();
        verify(personaMock).getNiños();
    }
    
    @Test
    public void testVerificacionLlamadas() {
        Persona personaMock = mock(Persona.class);
        
        personaMock.getIdentificadorBote();
        personaMock.getTotalPersonas();
        personaMock.getMujeres();
        
        verify(personaMock).getIdentificadorBote();
        verify(personaMock).getTotalPersonas();
        verify(personaMock).getMujeres();
        
        verify(personaMock, never()).getVarones();
        verify(personaMock, never()).getNiños();
    }
}