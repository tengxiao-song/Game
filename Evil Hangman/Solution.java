import java.util.*;

public class Solution {
    private ArrayList<Character> partialSolution;
    private int missingChars;

    public Solution(int length) {
        partialSolution = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            partialSolution.add('_');
        }
        missingChars = length;
    }

    public boolean isSolved() {
        return missingChars == 0;
    }

    public void printProgress() {
        for (char c : partialSolution) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public ArrayList<Character> getPartialSolution() {
        return partialSolution;
    }

    public int updatePartialSolution(String newSolution) {
        int count = 0;
        for (int i = 0; i < partialSolution.size(); i++) {
            char temp = newSolution.charAt(i);
            if (partialSolution.get(i) != temp && temp != '-') {
                partialSolution.set(i, temp);
                missingChars -= 1;
                count++;
            }
        }
        return count;
    }
    public int getMissingChars(){
        return missingChars;
    }
}
