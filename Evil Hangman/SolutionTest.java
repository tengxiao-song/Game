import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SolutionTest {
    @Test
    public void testSolutionConstructor() {
        int length = 5;
        Solution solution = new Solution(length);
        assertFalse(solution.isSolved());
        assertEquals(length, solution.getPartialSolution().size());
        assertEquals(length, solution.getMissingChars());
        for (char c : solution.getPartialSolution()) {
            assertEquals('_', c);
        }
    }

    @Test
    public void testUpdatePartialSolution() {
        int length = 5;
        Solution solution = new Solution(length);
        String newPattern = "----y";
        int count = solution.updatePartialSolution(newPattern);
        assertEquals(1, count);
        assertEquals(4, solution.getMissingChars());
        assertTrue(solution.getPartialSolution().contains('y'));
        assertTrue(solution.getPartialSolution().contains('_'));
        assertFalse(solution.getPartialSolution().contains('-'));
        assertFalse(solution.getPartialSolution().contains('Y'));
        assertFalse(solution.isSolved());
        newPattern = "dummy";
        solution.updatePartialSolution(newPattern);
        assertEquals(0, solution.getMissingChars());
        assertTrue(solution.isSolved());
    }
}
