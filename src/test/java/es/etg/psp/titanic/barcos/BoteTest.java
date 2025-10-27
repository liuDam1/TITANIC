package es.etg.psp.titanic.barcos;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class BoteTest {

    @Test
    public void testGenerarSalidaRescate() {
        // Mockear Random para controlar los resultados
        Random mockRandom = Mockito.mock(Random.class);
        // Configurar respuestas predecibles
        Mockito.when(mockRandom.nextInt(Mockito.anyInt())).thenReturn(50, 20, 20);
        
        // Redirigir la salida estándar para capturarla
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        try {
            // Llamar al método privado usando reflexión
            java.lang.reflect.Method method = Bote.class.getDeclaredMethod("generarSalidaRescate", String.class, Random.class);
            method.setAccessible(true);
            method.invoke(null, "B01", mockRandom);
            
            // Obtener la salida capturada
            String output = outputStream.toString().trim();
            
            // Verificar que la salida tiene el formato correcto
            String[] partes = output.split(",");
            assertEquals(5, partes.length, "La salida debe tener 5 campos separados por comas");
            assertEquals("B01", partes[0], "El ID del bote debe ser B01");
            
            // Convertir a enteros para verificar los valores
            int total = Integer.parseInt(partes[1]);
            int mujeres = Integer.parseInt(partes[2]);
            int varones = Integer.parseInt(partes[3]);
            int niños = Integer.parseInt(partes[4]);
            
            // Verificar que la suma de mujeres, varones y niños es igual al total
            assertEquals(total, mujeres + varones + niños, "La suma de mujeres, varones y niños debe ser igual al total");
            
            // Verificar que los valores están dentro de los límites
            assertTrue(total >= 1 && total <= 100, "El total debe estar entre 1 y 100");
            assertTrue(mujeres >= 0, "El número de mujeres no puede ser negativo");
            assertTrue(varones >= 0, "El número de varones no puede ser negativo");
            assertTrue(niños >= 0, "El número de niños no puede ser negativo");
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al probar el método generarSalidaRescate: " + e.getMessage());
        } finally {
            // Restaurar la salida estándar original
            System.setOut(originalOut);
        }
    }
    
    @Test
    public void testCalcularTiempo() {
        // Mockear Random para controlar el resultado
        Random mockRandom = Mockito.mock(Random.class);
        Mockito.when(mockRandom.nextInt(5)).thenReturn(3); // 3 + 2 = 5 segundos
        
        try {
            // Llamar al método privado usando reflexión
            java.lang.reflect.Method method = Bote.class.getDeclaredMethod("calcularTiempo", Random.class);
            method.setAccessible(true);
            int tiempo = (int) method.invoke(null, mockRandom);
            
            // Verificar que el tiempo calculado es 5000 milisegundos (5 segundos)
            assertEquals(5000, tiempo, "El tiempo debe ser 5000 milisegundos");
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al probar el método calcularTiempo: " + e.getMessage());
        }
    }
}
