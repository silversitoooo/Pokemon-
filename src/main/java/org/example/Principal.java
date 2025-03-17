package org.example;



import java.io.IOException;
import java.util.*;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ruta fija del archivo CSV
        String rutaArchivo = "C:\\Users\\ariel\\OneDrive\\Desktop\\interprete1.3\\Pokemon\\src\\main\\java\\Pokemon.csv";

        // Leer el archivo CSV
        GestorPokemon gestor = new GestorPokemon();

        try {
            System.out.println("Cargando archivo desde: " + rutaArchivo);
            gestor.cargarPokemonsDesdeCSV(rutaArchivo);
            System.out.println("Archivo cargado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
            return;
        }

        // Seleccionar implementación del Map
        System.out.println("\nSeleccione la implementación de Map a utilizar:");
        System.out.println("1) HashMap");
        System.out.println("2) TreeMap");
        System.out.println("3) LinkedHashMap");
        System.out.print("Opción: ");
        int opcionMap = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        gestor.setMapImplementacion(opcionMap);

        // Menú principal
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Agregar un Pokémon a tu colección");
            System.out.println("2. Mostrar datos de un Pokémon");
            System.out.println("3. Mostrar Pokémon de tu colección ordenados por tipo primario");
            System.out.println("4. Mostrar todos los Pokémon ordenados por tipo primario");
            System.out.println("5. Buscar Pokémon por habilidad");
            System.out.println("6. Guardar colección");
            System.out.println("7. Cargar colección");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del Pokémon a agregar: ");
                    String nombrePokemon = scanner.nextLine();
                    gestor.agregarPokemonAColeccion(nombrePokemon);
                    break;
                case 2:
                    System.out.print("Ingrese el nombre del Pokémon: ");
                    String nombrePokemonInfo = scanner.nextLine();
                    gestor.mostrarDatosPokemon(nombrePokemonInfo);
                    break;
                case 3:
                    gestor.mostrarColeccionPorTipo();
                    break;
                case 4:
                    gestor.mostrarTodosPokemonPorTipo();
                    break;
                case 5:
                    System.out.print("Ingrese la habilidad a buscar: ");
                    String habilidad = scanner.nextLine();
                    gestor.buscarPokemonPorHabilidad(habilidad);
                    break;
                case 6:
                    System.out.print("Ingrese nombre del archivo para guardar: ");
                    String archivoGuardar = scanner.nextLine();
                    gestor.guardarColeccion(archivoGuardar);
                    break;
                case 7:
                    System.out.print("Ingrese nombre del archivo para cargar: ");
                    String archivoCargar = scanner.nextLine();
                    gestor.cargarColeccion(archivoCargar);
                    break;
                case 0:
                    continuar = false;
                    System.out.println("¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        scanner.close();
    }
}