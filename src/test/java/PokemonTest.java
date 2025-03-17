import org.example.GestorPokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {
    @Test
    void testToString() {
        // Create a test Pokemon
        org.example.Pokemon pokemon = new org.example.Pokemon(
                "Pikachu", 25, "Electric", "None", 320, 35, 55, 40,
                50, 50, 90, 1, false, "Static");

        // Test toString method
        String result = pokemon.toString();
        assertTrue(result.contains("Pokémon: Pikachu"));
        assertTrue(result.contains("Número: 25"));
        assertTrue(result.contains("Tipo 1: Electric"));
        assertTrue(result.contains("Tipo 2: None"));
    }

    @Test
    void testGetters() {
        // Create a test Pokemon with specific values
        org.example.Pokemon pokemon = new org.example.Pokemon(
                "Charizard", 6, "Fire", "Flying", 534, 78, 84, 78,
                109, 85, 100, 1, false, "Blaze");

        // Test all getters
        assertEquals("Charizard", pokemon.getNombre());
        assertEquals(6, pokemon.getNumero());
        assertEquals("Fire", pokemon.getTipo1());
        assertEquals("Flying", pokemon.getTipo2());
        assertEquals(534, pokemon.getTotal());
        assertEquals(78, pokemon.getHp());
        assertEquals(84, pokemon.getAtaque());
        assertEquals(78, pokemon.getDefensa());
        assertEquals(109, pokemon.getAtaqueEspecial());
        assertEquals(85, pokemon.getDefensaEspecial());
        assertEquals(100, pokemon.getVelocidad());
        assertEquals(1, pokemon.getGeneracion());
        assertFalse(pokemon.isLegendario());
        assertEquals("Blaze", pokemon.getHabilidad());
    }

    @Test
    void testSerializability() throws IOException, ClassNotFoundException {
        // Create a test Pokemon
        org.example.Pokemon original = new org.example.Pokemon(
                "Mewtwo", 150, "Psychic", "None", 680, 106, 110, 90,
                154, 90, 130, 1, true, "Pressure");

        // Serialize and deserialize
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(original);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        org.example.Pokemon deserialized = (org.example.Pokemon) ois.readObject();

        // Verify deserialized object
        assertEquals(original.getNombre(), deserialized.getNombre());
        assertEquals(original.getNumero(), deserialized.getNumero());
        assertEquals(original.getTipo1(), deserialized.getTipo1());
        assertEquals(original.isLegendario(), deserialized.isLegendario());
    }
}