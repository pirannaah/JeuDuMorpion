package tictactoe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaseOccupeeExceptionTest {

    @Test
    void testExceptionCreation() {
        CaseOccupeeException ex = new CaseOccupeeException();
        assertNotNull(ex);
    }
}
