package es.etg.psp.titanic.barcos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class BoteTest {

    @Test
    public void testGenerarDatosRescate() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt())).thenReturn(50, 20, 20);

        Bote bote = new Bote("B01");

        try {
            java.lang.reflect.Field field = Bote.class.getDeclaredField("generador");
            field.setAccessible(true);
            field.set(bote, mockRandom);

            java.lang.reflect.Method method = Bote.class.getDeclaredMethod("generarDatosRescate");
            method.setAccessible(true);
            String output = (String) method.invoke(bote);

            String[] partes = output.split(";");
            assertEquals(5, partes.length);
            assertEquals("B01", partes[0]);

            int total = Integer.parseInt(partes[1]);
            int mujeres = Integer.parseInt(partes[2]);
            int varones = Integer.parseInt(partes[3]);
            int niños = Integer.parseInt(partes[4]);

            assertEquals(total, mujeres + varones + niños);
            assertTrue(total >= 1 && total <= 100);
            assertTrue(mujeres >= 0);
            assertTrue(varones >= 0);
            assertTrue(niños >= 0);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testCalcularTiempoDemora() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(5)).thenReturn(3);

        Bote bote = new Bote("B01");

        try {
            java.lang.reflect.Field field = Bote.class.getDeclaredField("generador");
            field.setAccessible(true);
            field.set(bote, mockRandom);

            java.lang.reflect.Method method = Bote.class.getDeclaredMethod("calcularTiempoDemora");
            method.setAccessible(true);
            int tiempo = (int) method.invoke(bote);

            assertEquals(5000, tiempo);
            verify(mockRandom).nextInt(5);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testEjecutarRescate() throws Exception {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt())).thenReturn(0, 50, 20, 20);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            Bote bote = new Bote("B01");
            java.lang.reflect.Field field = Bote.class.getDeclaredField("generador");
            field.setAccessible(true);
            field.set(bote, mockRandom);

            bote.ejecutarRescate();

            String output = outputStream.toString().trim();
            String[] partes = output.split(";");
            assertEquals("B01", partes[0]);

        } finally {
            System.setOut(originalOut);
        }
    }
}