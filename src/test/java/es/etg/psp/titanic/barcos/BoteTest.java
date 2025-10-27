package es.etg.psp.titanic.barcos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class BoteTest {

    @Test
    public void testGenerarSalidaRescate() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt())).thenReturn(50, 20, 20);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            java.lang.reflect.Method method = Bote.class.getDeclaredMethod("generarSalidaRescate", String.class,
                    Random.class);
            method.setAccessible(true);
            method.invoke(null, "B01", mockRandom);

            String output = outputStream.toString().trim();
            String[] partes = output.split(",");
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
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testCalcularTiempo() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(5)).thenReturn(3);

        try {
            java.lang.reflect.Method method = Bote.class.getDeclaredMethod("calcularTiempo", Random.class);
            method.setAccessible(true);
            int tiempo = (int) method.invoke(null, mockRandom);

            assertEquals(5000, tiempo);
            verify(mockRandom).nextInt(5);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}