Missionaries and Cannibals problem using A* algorithm with closet set.

In the missionaries and cannibals problem, three missionaries and three cannibals must cross a 
river using a boat which can carry at most two people, under the constraint that, for both banks, 
if there are missionaries present on the bank, they cannot be outnumbered by cannibals 
(if they were, the cannibals would eat the missionaries). The boat cannot cross the river by itself
with no people on board.

We generalize the problem and we assume that in the initial state there are N cannibals on a bank and 
N missionaries on the same bank,  where N is a parameter defined when program is called.
The maximum capacity of the boat is M, where M is also defined when program is called.
Finally, let K be the maximum number of river crossings.

If M > 2 and there is at least one missionary in the boat, we assume that in the boat the 
number of missionaries must be greater than or equal to the number of cannibals.

The program should find the optimum solution that does not exceed 
K crossings, if such a solution exists.

More about the problem: https://en.wikipedia.org/wiki/Missionaries_and_cannibals_problem

When calling the program, the following parameters should be specified, separated by a space:
	-> N: number  of cannibals /  number of missionaries
	-> Μ: maximum capacity of boat
	-> Κ: maximum river crossings