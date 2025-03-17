package org.example;



import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GestorPokemon {
    public Map<String, Pokemon> todosPokemon;
    private List<Pokemon> coleccionUsuario;
    private int tipoMap;

    public GestorPokemon() {
        this.tipoMap = 1; // Por defecto, HashMap
        this.todosPokemon = MapFactory.crearMap(tipoMap);
        this.coleccionUsuario = new ArrayList<>();
    }

    public void setMapImplementacion(int tipo) {
        this.tipoMap = tipo;
        Map<String, Pokemon> nuevoMap = MapFactory.crearMap(tipo);
        nuevoMap.putAll(todosPokemon);
        this.todosPokemon = nuevoMap;
    }

    public void cargarPokemonsDesdeCSV(String rutaArchivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue; // Saltar la línea de encabezados
                }

                // Manejo especial para separar considerando las comillas
                List<String> campos = parseCsvLine(linea);

                if (campos.size() >= 10) {
                    String nombre = campos.get(0);
                    int numero = Integer.parseInt(campos.get(1));
                    String tipo1 = campos.get(2);
                    String tipo2 = campos.get(3).isEmpty() ? "None" : campos.get(3);

                    // Valores por defecto para los campos que no están en el CSV
                    int total = 0;
                    int hp = 0;
                    int ataque = 0;
                    int defensa = 0;
                    int ataqueEspecial = 0;
                    int defensaEspecial = 0;
                    int velocidad = 0;

                    int generacion = Integer.parseInt(campos.get(8));
                    boolean legendario = campos.get(9).equals("Yes");

                    // Extraer la primera habilidad si hay varias
                    String habilidadesTexto = campos.get(7);
                    String habilidad = habilidadesTexto.split(",")[0].trim();
                    if (habilidad.startsWith("\"")) {
                        habilidad = habilidad.substring(1);
                    }
                    if (habilidad.endsWith("\"")) {
                        habilidad = habilidad.substring(0, habilidad.length() - 1);
                    }

                    Pokemon pokemon = new Pokemon(nombre, numero, tipo1, tipo2, total, hp, ataque, defensa,
                            ataqueEspecial, defensaEspecial, velocidad, generacion, legendario, habilidad);
                    todosPokemon.put(nombre, pokemon);
                }
            }
        }
    }

    private List<String> parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(field.toString());
                field = new StringBuilder();
            } else {
                field.append(c);
            }
        }
        result.add(field.toString());
        return result;
    }

    public void agregarPokemonAColeccion(String nombre) {
        if (todosPokemon.containsKey(nombre)) {
            Pokemon pokemon = todosPokemon.get(nombre);
            boolean existeEnColeccion = coleccionUsuario.stream()
                    .anyMatch(p -> p.getNombre().equals(nombre));

            if (existeEnColeccion) {
                System.out.println("El Pokémon '" + nombre + "' ya existe en tu colección.");
            } else {
                coleccionUsuario.add(pokemon);
                System.out.println("Pokémon '" + nombre + "' agregado a tu colección.");
            }
        } else {
            System.out.println("Error: El Pokémon '" + nombre + "' no existe en la base de datos.");
        }
    }

    public void mostrarDatosPokemon(String nombre) {
        if (todosPokemon.containsKey(nombre)) {
            Pokemon pokemon = todosPokemon.get(nombre);
            System.out.println("\nDatos del Pokémon:");
            System.out.println(pokemon);
        } else {
            System.out.println("Error: El Pokémon '" + nombre + "' no existe en la base de datos.");
        }
    }

    public void mostrarColeccionPorTipo() {
        if (coleccionUsuario.isEmpty()) {
            System.out.println("Tu colección está vacía.");
            return;
        }

        System.out.println("\nPokémon en tu colección ordenados por tipo primario:");
        coleccionUsuario.stream()
                .sorted(Comparator.comparing(Pokemon::getTipo1))
                .forEach(p -> System.out.println(p.getNombre() + " - Tipo: " + p.getTipo1()));
    }

    public void mostrarTodosPokemonPorTipo() {
        System.out.println("\nTodos los Pokémon ordenados por tipo primario:");
        todosPokemon.values().stream()
                .sorted(Comparator.comparing(Pokemon::getTipo1))
                .forEach(p -> System.out.println(p.getNombre() + " - Tipo: " + p.getTipo1()));
    }

    public void buscarPokemonPorHabilidad(String habilidad) {
        System.out.println("\nPokémon con la habilidad '" + habilidad + "':");
        List<Pokemon> pokemonConHabilidad = todosPokemon.values().stream()
                .filter(p -> p.getHabilidad().toLowerCase().contains(habilidad.toLowerCase()))
                .collect(Collectors.toList());

        if (pokemonConHabilidad.isEmpty()) {
            System.out.println("No se encontraron Pokémon con esa habilidad.");
        } else {
            pokemonConHabilidad.forEach(p -> System.out.println(p.getNombre()));
        }
    }

    public void guardarColeccion(String nombreArchivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            out.writeObject(coleccionUsuario);
            System.out.println("Colección guardada exitosamente en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar la colección: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void cargarColeccion(String nombreArchivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            coleccionUsuario = (List<Pokemon>) in.readObject();
            System.out.println("Colección cargada exitosamente desde " + nombreArchivo);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar la colección: " + e.getMessage());
        }
    }
}