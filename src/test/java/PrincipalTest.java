

import org.example.Principal;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class PrincipalTest {

    @Test
    void testMenuFlow() throws Exception {
        // Create a test CSV
        String testCSV = "Name,Pokedex Number,Type1,Type2,Classification,Height (m),Weight (kg),Abilities,Generation,Legendary Status\n" +
                "Umbreon,197,Dark,,Moonlight Pokémon,1,27,\"Synchronize, Inner-focus\",2,No";

        Path tempFile = Files.createTempFile("test-pokemon", ".csv");
        Files.writeString(tempFile, testCSV);

        // Looking at the error, it seems Principal is expecting an integer input first
        // Let's modify the input to provide expected format
        String input = "1\n" +       // Select HashMap or whatever first option is expected
                "2\n" +       // Show Pokemon data option
                "Umbreon\n" + // Pokemon name
                "0\n";        // Exit option

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        try {
            // We need to modify the CSV path in the Principal class
            // First, let's create a modified version of Principal.java just for testing
            String originalContent = Files.readString(Path.of("src/main/java/org/example/Principal.java"));
            String modifiedContent = originalContent.replaceAll(
                    "\"[^\"]*\\.csv\"", // Find any string that ends with .csv
                    "\"" + tempFile.toString().replace("\\", "\\\\") + "\"" // Replace with our temp file path
            );

            // Write the modified file to a temp location
            Path tempPrincipal = Files.createTempFile("Principal", ".java");
            Files.writeString(tempPrincipal, modifiedContent);

            // Compile the modified Principal class
            // Note: This approach is limited and may not work in all environments
            // A better solution would be to use a system property or environment variable

            System.setIn(new ByteArrayInputStream(input.getBytes()));
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Run the main method
            Principal.main(new String[]{tempFile.toString()});

            String output = outContent.toString();
            assertTrue(output.contains("Pokémon: Umbreon"));

        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
            Files.delete(tempFile);
        }
    }
}