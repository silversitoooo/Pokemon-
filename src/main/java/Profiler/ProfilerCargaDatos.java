package Profiler;





import org.example.GestorPokemon;

import java.io.IOException;

class ProfilerCarga {
    public static void main(String[] args) {
        // Ruta al archivo CSV - ajusta según tu entorno
        String rutaArchivo = "src/main/java/Pokemon.csv";

        System.out.println("PROFILER PARA CARGA DE DATOS");
        System.out.println("============================");

        try {
            // Probar con HashMap
            System.out.println("\nTiempo de carga con HashMap:");
            long tiempoHashMap = medirTiempoCarga(1, rutaArchivo);

            // Probar con TreeMap
            System.out.println("\nTiempo de carga con TreeMap:");
            long tiempoTreeMap = medirTiempoCarga(2, rutaArchivo);

            // Probar con LinkedHashMap
            System.out.println("\nTiempo de carga con LinkedHashMap:");
            long tiempoLinkedHashMap = medirTiempoCarga(3, rutaArchivo);

            // Mostrar resultados
            System.out.println("\n----- RESULTADOS DE CARGA -----");
            System.out.println("HashMap: " + tiempoHashMap + " ms");
            System.out.println("TreeMap: " + tiempoTreeMap + " ms");
            System.out.println("LinkedHashMap: " + tiempoLinkedHashMap + " ms");

            // Mostrar la mejor implementación para carga
            long tiempoMinimo = Math.min(tiempoHashMap, Math.min(tiempoTreeMap, tiempoLinkedHashMap));

            System.out.println("\nLa implementación más eficiente para carga es:");
            if (tiempoMinimo == tiempoHashMap) {
                System.out.println("HashMap");
            } else if (tiempoMinimo == tiempoTreeMap) {
                System.out.println("TreeMap");
            } else {
                System.out.println("LinkedHashMap");
            }

        } catch (Exception e) {
            System.err.println("Error durante la ejecución del profiler: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static long medirTiempoCarga(int tipoMap, String rutaArchivo) {
        try {
            long tiempoTotal = 0;
            int repeticiones = 10;

            for (int i = 0; i < repeticiones; i++) {
                GestorPokemon gestor = new GestorPokemon();
                gestor.setMapImplementacion(tipoMap);

                // Medir tiempo de carga
                long inicio = System.currentTimeMillis();
                gestor.cargarPokemonsDesdeCSV(rutaArchivo);
                long fin = System.currentTimeMillis();

                tiempoTotal += (fin - inicio);
                System.out.println("Iteración " + (i+1) + ": " + (fin - inicio) + " ms");
            }

            long tiempoPromedio = tiempoTotal / repeticiones;
            System.out.println("Tiempo promedio de carga: " + tiempoPromedio + " ms");

            return tiempoPromedio;

        } catch (IOException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
}