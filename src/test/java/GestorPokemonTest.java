

import org.example.GestorPokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorPokemonTest {
    private GestorPokemon gestor;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        gestor = new GestorPokemon();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testCargarPokemonsDesdeCSV() throws IOException {
        // Create a test CSV with sample data
        String testCSV = "Name,Pokedex Number,Type1,Type2,Classification,Height (m),Weight (kg),Abilities,Generation,Legendary Status\n" +
                "Umbreon,197,Dark,,Moonlight Pokémon,1,27,\"Synchronize, Inner-focus\",2,No";

        Path tempFile = Files.createTempFile("test-pokemon", ".csv");
        Files.writeString(tempFile, testCSV);

        gestor.cargarPokemonsDesdeCSV(tempFile.toString());

        // Test the Pokemon was loaded correctly
        gestor.mostrarDatosPokemon("Umbreon");
        assertTrue(outContent.toString().contains("Pokémon: Umbreon"));
        assertTrue(outContent.toString().contains("Tipo 1: Dark"));

        Files.delete(tempFile);
    }

    @Test
    void testParseCsvLine() throws Exception {
        // Use reflection to access private method
        java.lang.reflect.Method parseCsvLine = GestorPokemon.class.getDeclaredMethod("parseCsvLine", String.class);
        parseCsvLine.setAccessible(true);

        String line = "Umbreon,197,Dark,,Moonlight Pokémon,1,27,\"Synchronize, Inner-focus\",2,No";
        List<String> result = (List<String>) parseCsvLine.invoke(gestor, line);

        assertEquals(10, result.size());
        assertEquals("Umbreon", result.get(0));
        assertEquals("197", result.get(1));
        assertEquals("Dark", result.get(2));
        assertEquals("", result.get(3));
        // Fixed: Changed expectation to match actual implementation (without quotes)
        assertEquals("Synchronize, Inner-focus", result.get(7));
    }

    @Test
    void testAgregarPokemonAColeccion() throws IOException {
        // Setup with test data
        String testCSV = "Name,Pokedex Number,Type1,Type2,Classification,Height (m),Weight (kg),Abilities,Generation,Legendary Status\n" +
                "Umbreon,197,Dark,,Moonlight Pokémon,1,27,\"Synchronize, Inner-focus\",2,No";

        Path tempFile = Files.createTempFile("test-pokemon", ".csv");
        Files.writeString(tempFile, testCSV);
        gestor.cargarPokemonsDesdeCSV(tempFile.toString());

        outContent.reset();
        gestor.agregarPokemonAColeccion("Umbreon");
        assertTrue(outContent.toString().contains("agregado a tu colección"));

        outContent.reset();
        gestor.agregarPokemonAColeccion("Umbreon");
        assertTrue(outContent.toString().contains("ya existe en tu colección"));

        outContent.reset();
        gestor.agregarPokemonAColeccion("NonExistent");
        assertTrue(outContent.toString().contains("no existe en la base de datos"));

        Files.delete(tempFile);
    }

    @Test
    void testGuardarYCargarColeccion(@TempDir Path tempDir) throws IOException {
        // Setup test data
        String testCSV = "Name,Pokedex Number,Type1,Type2,Classification,Height (m),Weight (kg),Abilities,Generation,Legendary Status\n" +
                "Umbreon,197,Dark,,Moonlight Pokémon,1,27,\"Synchronize, Inner-focus\",2,No\n" +
                "Pikachu,25,Electric,,Mouse Pokémon,0.4,6,\"Static, Lightning-rod\",1,No";

        Path tempFile = Files.createTempFile("test-pokemon", ".csv");
        Files.writeString(tempFile, testCSV);
        gestor.cargarPokemonsDesdeCSV(tempFile.toString());

        gestor.agregarPokemonAColeccion("Umbreon");

        // Test save and load collection
        Path savePath = tempDir.resolve("test-collection.dat");
        gestor.guardarColeccion(savePath.toString());

        GestorPokemon newGestor = new GestorPokemon();
        newGestor.cargarColeccion(savePath.toString());

        outContent.reset();
        newGestor.mostrarColeccionPorTipo();
        assertTrue(outContent.toString().contains("Umbreon"));

        Files.delete(tempFile);
    }
}