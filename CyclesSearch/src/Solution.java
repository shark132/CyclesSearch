import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by shark on 22.11.2015.
 * This programme searches for nested cycles in the input file
 */

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        CycleSearch cycleSearch = new CycleSearch(reader);
        cycleSearch.searchCycles();
        cycleSearch.printList();
        reader.close();
    }
}
