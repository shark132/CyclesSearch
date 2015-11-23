import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by shark on 22.11.2015.
 */
public class CycleSearch {

    //Cycles included into the input file
    private ArrayList<ArrayList<Integer>> cycles = new ArrayList<ArrayList<Integer>>();
    //Map for storing input pairs : id -> dependencies
    private Map<Integer,HashSet<Integer>> map = new HashMap<Integer,HashSet<Integer>>();
    //Set of visited vertexes
    private HashSet<Integer> globallySearchedId = new HashSet<Integer>();
    //Set of visited vertexes in the inner cycleSearch
    private HashSet<Integer> deepSearchedId = new HashSet<Integer>();
    //List of the made path
    private ArrayList<Integer> innerPath = new ArrayList<Integer>();


    public CycleSearch(BufferedReader reader) {
        String s;
        try {
            while ((s = reader.readLine()) != null) {
                correctSpaces(s);
            }
        } catch (IOException e) {
            System.out.println("File reading error");
        }

    }

    /**
     *
     * @param s - String that is to be freed from extra spaces
     * Deletes all extra spaces
     */
    public void correctSpaces(String s) {
        Integer firstNumber = null;
        Integer secondNumber = null;
        String[] separatedNumbers = s.split(" ");


        for (int i = 0; i < separatedNumbers.length; i++) {
            if (firstNumber == null) {
                try {
                    firstNumber = Integer.parseInt(separatedNumbers[i]);
                } catch (NumberFormatException e) {
                    continue;
                }
            }

            else{
                try {
                    secondNumber = Integer.parseInt(separatedNumbers[i]);
                } catch (NumberFormatException e) {
                    continue;
                }

            }
        }
        addToMap(firstNumber, secondNumber);
    }

    /**
     *
     * @param id - a key for the map (first string value)
     * @param dependency - a value for the map(second string value)
     */
    public void addToMap(Integer id, Integer dependency) {
        //Checks whether the given id is already represents in the map
        if (!map.containsKey(id)) {
            HashSet<Integer> set = new HashSet<Integer>();
            set.add(dependency);
            map.put(id,set);
        }
        else {
            // Widen the number of dependencies of the key
            map.get(id).add(dependency);
        }

    }

    /**
     * Global search for a particular vertex's dependecies
     */
    public void searchCycles() {
        for (Map.Entry<Integer, HashSet<Integer>> pair : map.entrySet())
        {
            int id = pair.getKey();
            if (!globallySearchedId.contains(id)) {
                //Search for the vertexs dependencies
                deepSearch(id);
            }
        }
    }

    /**
     * Search for the given vertex's dependencies
     * @param id
     */
    public void deepSearch(int id) {
        if (!map.containsKey(id)) {
            //This case means that the given vertex is not a key element and has no dependencies
            return;
        }
        else {
            deepSearchedId.add(id);
            innerPath.add(id);
            globallySearchedId.add(id);

            for (Integer tmpId : map.get(id)) {
                //True - means that one of the cycles for the vertex has been found
                if (deepSearchedId.contains(tmpId)) {
                    int index = innerPath.indexOf(tmpId);
                    ArrayList<Integer> tmpCycle = new ArrayList<Integer>();

                    for (int i = index; i < innerPath.size() - index; i++) {
                        tmpCycle.add(innerPath.get(i));
                    }
                    //Adding the vertex to the end of the cycle in order to finish it
                    tmpCycle.add(tmpId);
                    cycles.add(tmpCycle);
                }
                else {
                    deepSearch(tmpId);
                }
            }
            deepSearchedId.remove(id);
            innerPath.remove(innerPath.size() - 1);
        }
    }

    public void printList() {
        for (ArrayList<Integer> list : cycles) {
            for (Integer number : list) {
                System.out.print(number + " ");
            }
            System.out.println();
        }
    }
}
