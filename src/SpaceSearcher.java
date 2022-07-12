import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class SpaceSearcher {

    private ArrayList<State> states;
    private HashSet<State> closedSet;

    public SpaceSearcher() {
        states = new ArrayList<State>();
        closedSet = new HashSet<State>();
    }

    public State aStar(State initialState) {

        states.add(initialState);

        while(states.size() > 0) {
            State currentState = states.remove(0);
            if (currentState.isTerminal()) {
                return currentState;
            }
            if (!closedSet.contains(currentState))
            {
                closedSet.add(currentState);
                states.addAll(currentState.getChildren());
                Collections.sort(states);
            }
        }
        return null;
    }
}
