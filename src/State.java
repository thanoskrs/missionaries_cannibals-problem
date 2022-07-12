import java.util.ArrayList;

public class State implements Comparable<State> {

    private int[] sides;
    private int N;
    private int missionaries;
    private int cannibals;
    private int boatSide;           // if boat=left : value=1 ? value=0
    private int maxPeople;          // maximum number of people in the boat
    private int maxMoves;           // maximum number of total moves
    private int g;                  // total cost from root to current state
    private int h;
    private State parent;
    int heuristic;

    public State() {
        this.N = -1;
        this.maxPeople = -1;
        this.maxMoves = -1;
        this.missionaries = -1;
        this.cannibals = -1;

    }

    public State(int N, int maxPeople , int maxMoves, int boatSide, int heuristic) {
        this.N = N;
        this.maxPeople = maxPeople;
        this.maxMoves = maxMoves;
        this.missionaries = N;
        this.cannibals = N;
        this.boatSide = boatSide;
        this.heuristic = heuristic;
        g = 0;

        initTable();

        if (boatSide == 1)
            h = heuristic();
        else
            h = heuristic(1) + 1;
    }

    public State(int N, int cannibals, int missionaries, int maxPeople , int maxMoves, int boatSide, int heuristic) {
        this.N = N;
        this.maxPeople = maxPeople;
        this.maxMoves = maxMoves;
        this.missionaries = missionaries;
        this.cannibals = cannibals;
        this.boatSide = boatSide;
        this.heuristic = heuristic;
        g = 0;

        initTable();

        if (boatSide == 1)
            if (heuristic == 1)
                h = heuristic();
            else
                h = heuristic2();
        else
            if (heuristic == 1)
                h = heuristic(1) + 1;
            else
                h = heuristic2(1) + 1;
    }


    private void initTable() {
        sides = new int[5];
        sides[0] = cannibals;
        sides[1] = missionaries;
        sides[2] = boatSide;
        sides[3] = N - sides[0];
        sides[4] = N - sides[1];

    }

    public int[] getSides() {
        return sides;
    }

    public void setSides(int[] sides) {
        this.sides = sides;
    }

    public int getMissionaries() {
        return missionaries;
    }

    public void setMissionaries(int missionaries) {
        this.missionaries = missionaries;
    }

    public int getCannibals() {
        return cannibals;
    }

    public void setCannibals(int cannibals) {
        this.cannibals = cannibals;
    }

    public int getBoatSide() {
        return boatSide;
    }

    public void setBoatSide(int boatSide) {
        this.boatSide = boatSide;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public int getMaxMoves() {
        return maxMoves;
    }

    public void setMaxMoves(int maxMoves) {
        this.maxMoves = maxMoves;
    }

    public ArrayList<State> getChildren() {

        ArrayList<State> children = new ArrayList<State>();

        if (g < maxMoves) {

            if (sides[2] == 0) {    // correct side of boat
                for (int can = 0; can <= sides[3]; can++)
                {
                    for (int miss = 0; miss <= sides[4]; miss++)
                    {

                        // if there are missionaries at the boat, cannibals can't be more than them.
                        if (can > miss && miss != 0) {
                            continue;
                        }

                        // the boat needs at least one person to move
                        if (can == 0 && miss == 0)
                            continue;

                        boolean canTravel = false;

                        if (can + miss <= maxPeople) {
                            if (sides[4] - miss != 0) {
                                if (sides[3] - can <= sides[4] - miss) {
                                    canTravel = true;
                                }
                            } else {
                                canTravel = true;
                            }
                        }

                        if (canTravel) {
                            canTravel = false;
                            if (sides[1] + miss != 0) {
                                if (sides[0] + can <= sides[1] + miss) {
                                    canTravel = true;
                                }
                            } else {
                                canTravel = true;
                            }
                        } else {
                            continue;
                        }

                        if (canTravel) {
                            State child = new State(N, sides[0] + can, sides[1] + miss, maxPeople, maxMoves, 1, heuristic);
                            child.parent = this;
                            child.g = g + 1;
                            //child.h = child.heuristic() + 1;  // plus one, because we need an extra move to move people from right to left.

                            children.add(child);
                        } else {
                            continue;
                        }

                    }
                }


            }
            else {      // wrong side of boat
                for (int can = 0; can <= sides[0]; can++)
                {
                    for (int miss = 0; miss <= sides[1]; miss++)
                    {

                        // if there are missionaries at the boat, cannibals can't be more than them.
                        if (can > miss && miss != 0) {
                            continue;
                        }

                        // the boat needs at least one person to move
                        if (can == 0 && miss == 0)
                            continue;

                        boolean canTravel = false;

                        if (can + miss <= maxPeople) {
                            if (sides[1] - miss != 0) {
                                if (sides[0] - can <= sides[1] - miss) {
                                    canTravel = true;
                                }
                            } else {
                                canTravel = true;
                            }
                        }

                        if (canTravel) {
                            canTravel = false;
                            if (sides[4] + miss != 0) {
                                if (sides[3] + can <= sides[4] + miss) {
                                    canTravel = true;
                                }
                            } else {
                                canTravel = true;
                            }
                        } else {
                            continue;
                        }

                        if (canTravel) {
                            State child = new State(N, sides[0] - can, sides[1] - miss, maxPeople, maxMoves, 0, heuristic);
                            child.parent = this;
                            child.g = g + 1;

                            children.add(child);
                        } else {
                            continue;
                        }

                    }
                }

            }
        }


        return children;
    }

    public int heuristic() {
        int total_people = sides[0] + sides[1];
        int steps = 0;
        for (int i = total_people - maxPeople; i > 0; i = i - maxPeople + 1) {
            steps+=2;
        }
        steps++;

        return steps;
    }

    public int heuristic(int n) {
        int total_people = sides[0] + sides[1] + n;
        int steps = 0;
        for (int i = total_people - maxPeople; i > 0; i = i - maxPeople + 1) {
            steps+=2;
        }
        steps++;

        return steps;
    }

    public int heuristic2() {
        int total_people = sides[0] + sides[1];
        int step = Math.abs(-maxPeople + 1);
        if (step !=0 )
            return (int) Math.ceil(total_people/step) + 1;
        else
            return 1;

    }

    public int heuristic2(int n) {
        int total_people = sides[0] + sides[1] + n;
        int step = Math.abs(- maxPeople + 1);
        if (step !=0 )
            return (int) Math.ceil(total_people/step) + 1;
        else
            return 1;
    }

    public boolean isTerminal() {
        if (sides[0] == 0 && sides[1] == 0 && sides[2] == 0 && sides[3] == N && sides[4] == N)
            return true;

        return false;
    }

    @Override
    public boolean equals(Object obj) {

        if (N != ((State) obj).N)
            return false;

        for (int i = 0; i < sides.length; i++) {
            if (sides[i] != ((State) obj).sides[i])
                return false;
        }
        return true;
    }

    public void print() {
        if (boatSide == 1)
            System.out.println("<" + String.valueOf(sides[0]) + "C, " + String.valueOf(sides[1]) + "M>"
                    + "  \\_____/\t\t\t" +
                    "<" + String.valueOf(sides[3]) + "C, " + String.valueOf(sides[4]) + "M>");
        else
            System.out.println("<" + String.valueOf(sides[0]) + "C, " + String.valueOf(sides[1]) + "M>"
                    + "\t\t\t\\_____/ " +
                    "<" + String.valueOf(sides[3]) + "C, " + String.valueOf(sides[4]) + "M>");

        System.out.println("-------------------------------------");
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(String.valueOf(sides[0])  + String.valueOf(sides[1]) + String.valueOf(sides[2]));
    }

    @Override
    public int compareTo(State s) {
        return Integer.compare(g + h, s.g + s.h);
    }

    public State getParent() {
        return parent;
    }

    public void setParent(State parent) {
        this.parent = parent;
    }
}