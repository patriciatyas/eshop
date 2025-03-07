package id.ac.ui.cs.advprog.eshop.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorServiceTest {

    private final IdGeneratorService idGeneratorService = new IdGeneratorService();

    @Test
    void testGenerateId() {
        String generatedId = idGeneratorService.generateId();

        assertNotNull(generatedId, "Generated ID should not be null");
        assertFalse(generatedId.isEmpty(), "Generated ID should not be empty");

        // Ensure the ID is a valid UUID format
        assertTrue(generatedId.matches("^[a-f0-9\\-]{36}$"), "Generated ID should be a valid UUID format");
    }
}
