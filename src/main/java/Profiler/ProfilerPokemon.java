package Profiler;

import org.example.GestorPokemon;
import org.example.Pokemon;

import java.io.IOException;

public class ProfilerPokemon {
    public static void main(String[] args) {
        // Ruta al archivo CSV - ajusta según tu entorno
        // IMPORTANTE: Ajusta esta ruta a donde realmente tengas el archivo Pokemon.csv
        String rutaArchivo = "C:\\Users\\ariel\\OneDrive\\Desktop\\interprete1.3\\Pokemon\\src\\main\\java\\Pokemon.csv";

        System.out.println("PROFILER PARA GESTOR POKEMON");
        System.out.println("============================");
        System.out.println("Midiendo rendimiento de la operación #4: Mostrar todos los Pokémon ordenados por tipo primario");
        System.out.println("Para cada implementación de Map: HashMap, TreeMap y LinkedHashMap");

        try {
            // Probar con HashMap
            System.out.println("\nPrueba con HashMap:");
            long tiempoHashMap = medirRendimiento(1, rutaArchivo);

            // Probar con TreeMap
            System.out.println("\nPrueba con TreeMap:");
            long tiempoTreeMap = medirRendimiento(2, rutaArchivo);

            // Probar con LinkedHashMap
            System.out.println("\nPrueba con LinkedHashMap:");
            long tiempoLinkedHashMap = medirRendimiento(3, rutaArchivo);

            // Mostrar resultados
            System.out.println("\n----- RESULTADOS FINALES -----");
            System.out.println("HashMap: " + tiempoHashMap + " ms");
            System.out.println("TreeMap: " + tiempoTreeMap + " ms");
            System.out.println("LinkedHashMap: " + tiempoLinkedHashMap + " ms");

            // Mostrar la mejor implementación
            long tiempoMinimo = Math.min(tiempoHashMap, Math.min(tiempoTreeMap, tiempoLinkedHashMap));

            System.out.println("\nLa implementación más eficiente es:");
            if (tiempoMinimo == tiempoHashMap) {
                System.out.println("HashMap");
                double vsTM = (double)tiempoTreeMap / tiempoHashMap;
                double vsLHM = (double)tiempoLinkedHashMap / tiempoHashMap;
                System.out.println(String.format("Es %.2f veces más rápido que TreeMap", vsTM));
                System.out.println(String.format("Es %.2f veces más rápido que LinkedHashMap", vsLHM));
            } else if (tiempoMinimo == tiempoTreeMap) {
                System.out.println("TreeMap");
                double vsHM = (double)tiempoHashMap / tiempoTreeMap;
                double vsLHM = (double)tiempoLinkedHashMap / tiempoTreeMap;
                System.out.println(String.format("Es %.2f veces más rápido que HashMap", vsHM));
                System.out.println(String.format("Es %.2f veces más rápido que LinkedHashMap", vsLHM));
            } else {
                System.out.println("LinkedHashMap");
                double vsHM = (double)tiempoHashMap / tiempoLinkedHashMap;
                double vsTM = (double)tiempoTreeMap / tiempoLinkedHashMap;
                System.out.println(String.format("Es %.2f veces más rápido que HashMap", vsHM));
                System.out.println(String.format("Es %.2f veces más rápido que TreeMap", vsTM));
            }
        } catch (Exception e) {
            System.err.println("Error durante la ejecución del profiler: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static long medirRendimiento(int tipoMap, String rutaArchivo) {
        try {
            System.out.println("Inicializando GestorPokemon con tipo de mapa: " + tipoMap);
            GestorPokemon gestor = new GestorPokemon();
            gestor.setMapImplementacion(tipoMap);

            System.out.println("Cargando datos desde: " + rutaArchivo);
            gestor.cargarPokemonsDesdeCSV(rutaArchivo);
            System.out.println("Datos cargados correctamente.");

            // Crear una versión modificada de la función para evitar imprimir en consola durante la medición
            CustomGestorPokemon customGestor = new CustomGestorPokemon(gestor);

            // Realizar algunas corridas de calentamiento
            System.out.println("Realizando calentamiento...");
            for (int i = 0; i < 5; i++) {
                customGestor.medirTodosPokemonPorTipo();
            }

            // Medir el tiempo real
            System.out.println("Iniciando medición...");
            long tiempoTotal = 0;
            int repeticiones = 20;

            for (int i = 0; i < repeticiones; i++) {
                long inicio = System.currentTimeMillis();
                customGestor.medirTodosPokemonPorTipo();
                long fin = System.currentTimeMillis();
                tiempoTotal += (fin - inicio);
            }

            long tiempoPromedio = tiempoTotal / repeticiones;
            System.out.println("Tiempo promedio: " + tiempoPromedio + " ms");

            return tiempoPromedio;

        } catch (IOException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    // Clase auxiliar para evitar impresiones durante la medición
    private static class CustomGestorPokemon {
        private GestorPokemon gestor;

        public CustomGestorPokemon(GestorPokemon gestor) {
            this.gestor = gestor;
        }

        public void medirTodosPokemonPorTipo() {
            // Similar a mostrarTodosPokemonPorTipo pero sin imprimir
            gestor.todosPokemon.values().stream()
                    .sorted(java.util.Comparator.comparing(Pokemon::getTipo1))
                    .forEach(p -> {
                        // No imprimir nada, solo procesar
                        String nombre = p.getNombre();
                        String tipo = p.getTipo1();
                    });
        }
    }
}