package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;
import org.springframework.boot.SpringApplication;

@SpringBootTest
class EshopApplicationTest {

    @Test
    void contextLoads() {
        // Test if the application context loads successfully
    }

    @Test
    void mainMethodRuns() {
        // Call the main method to ensure it runs without errors
        EshopApplication.main(new String[] {});

        // Since SpringApplication.run() is static, we cannot verify it with Mockito
        // But if there were issues, the test would fail
    }
}
