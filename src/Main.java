import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {

        State initialState;

        if (args.length != 3) {
            initialState = new State(3, 2, 20, 1, 1);

        } else {

            int N = Integer.parseInt(args[0]);
            int maxPeople = Integer.parseInt(args[1]);
            int maxMoves = Integer.parseInt(args[2]);

            if (N > 0 && maxPeople > 0 && maxMoves > 0)
                initialState = new State(N, maxPeople, maxMoves, 1, 1);
            else
                initialState = new State(3, 2, 20, 1, 1);
        }

        System.out.println("// C = Cannibals \n// M = Missionaries \n");

        System.out.println("Initial State:");
        System.out.println("-------------------------------------");
        initialState.print();
        SpaceSearcher spaceSearcher = new SpaceSearcher();
        State terminalState = null;

        long start = System.currentTimeMillis();
        terminalState = spaceSearcher.aStar(initialState);
        long end = System.currentTimeMillis();

        if(terminalState == null)
        {
            System.out.println("\nCould not find solution");
        }
        else
        {
            State temp = terminalState;
            ArrayList<State> path = new ArrayList<State>();
            path.add(terminalState);
            while(temp.getParent()!=null)
            {
                path.add(temp.getParent());
                temp = temp.getParent();
            }
            Collections.reverse(path);
            System.out.println("\nFinished in "+(path.size() - 1)+" steps! Next Steps:");
            System.out.println("-------------------------------------");
            for(State item : path)
            {
                item.print();
            }
        }

        System.out.println("\nA-Star algorithm (A*) with closed set search time: " + (double)(end - start) / 1000 + " sec.");

    }
}
