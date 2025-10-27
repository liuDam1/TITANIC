package es.etg.psp.titanic.informe;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import es.etg.psp.titanic.model.Persona;

public class InformeConsolaTest {

    @Test
    public void testGenerarInformeConsola() {
        List<Persona> datosPrueba = Arrays.asList(
            new Persona("B01", 50, 20, 25, 5),
            new Persona("B02", 30, 15, 10, 5)
        );
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        try {
            InformeConsola informeConsola = new InformeConsola();
            informeConsola.generarInforme(datosPrueba);
            
            String output = outputStream.toString();
            
            assertTrue(output.contains("===== INFORME EN CONSOLA ====="));
            assertTrue(output.contains("Total salvados en B01: 50"));
            assertTrue(output.contains("Total salvados en B02: 30"));
            assertTrue(output.contains("TOTAL SALVADOS: 80"));
            
        } finally {
            System.setOut(originalOut);
        }
    }
    
    @Test
    public void testGenerarInformeConPersonasMock() {
        Persona personaMock1 = mock(Persona.class);
        Persona personaMock2 = mock(Persona.class);
        
        when(personaMock1.getIdentificadorBote()).thenReturn("B03");
        when(personaMock1.getTotalPersonas()).thenReturn(75);
        when(personaMock1.getMujeres()).thenReturn(30);
        when(personaMock1.getVarones()).thenReturn(35);
        when(personaMock1.getNiños()).thenReturn(10);
        
        when(personaMock2.getIdentificadorBote()).thenReturn("B04");
        when(personaMock2.getTotalPersonas()).thenReturn(60);
        when(personaMock2.getMujeres()).thenReturn(25);
        when(personaMock2.getVarones()).thenReturn(25);
        when(personaMock2.getNiños()).thenReturn(10);
        
        List<Persona> datosMock = new ArrayList<>();
        datosMock.add(personaMock1);
        datosMock.add(personaMock2);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        try {
            InformeConsola informeConsola = new InformeConsola();
            informeConsola.generarInforme(datosMock);
            
            verify(personaMock1).getIdentificadorBote();
            verify(personaMock1, times(2)).getTotalPersonas();
            verify(personaMock2).getIdentificadorBote();
            verify(personaMock2, times(2)).getTotalPersonas();
            
        } finally {
            System.setOut(System.out);
        }
    }
    
    @Test
    public void testGenerarInformeConListaVacia() {
        List<Persona> listaVacia = new ArrayList<>();
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        try {
            InformeConsola informeConsola = new InformeConsola();
            informeConsola.generarInforme(listaVacia);
            
        } finally {
            System.setOut(System.out);
        }
    }
}