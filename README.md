Battleship is a war game played on ocean by two players. Each player gets the same
number of battleships of a particular type placed on the battle area. Note: One player
cannot see the other player’s battleship locations.
The player who destroys all the ships of the other player is the winner. If there is no
winner, the players declare peace.
Eg. Let’s consider each player gets 2 ships of size 2x2 and 1x4. First ship is placed
between D1, D2, E1 and E2 cells and the second ship, between F3, F4, F5 and F6 cells.
Similarly Player-2 can place his/her set of ships in different positions in his/her own
battle area.
Player-1 battle Area
1 2 3 4 5 6
A
B
C
D
E

F

Player-2 battle Area
1 2 3 4 5 6
A
B
C
D
E

F

Both players will get a chance to launch missiles one by one. Eg. If Player-1 fires a
missile into Player-2’s battle area, targeting some location (eg. E1), and the missile hits,
Player-2 needs to communicate to Player-1 that there was a hit.
In the example above, the missile hits the ship on E1. In this case, Player-1 gets another
chance of firing as he successfully hit Player-2’s ship. The game continues like this. If the
missile misses, then Player-2 gets to fire. Players may have different number of missiles.
If a ship is hit in all the cells, then that ship is considered destroyed. Eg. if E1, E2, E3 and
E4 from Player-2’s battle area are hit by the Player-1, then that ship is considered
destroyed. Note that only upon hitting a live cell, a player will get another chance. Ships
are classified as type P or Q. Type P ships are weaker than type Q ships. Each cell of a
Type-Q ship requires 2 accurate missile hits to be destroyed whereas Type-P ship cells
will be destroyed only by 1 missile hit.

Input:
Inputs contain dimensions of the battle area (x & y for bottom right corner), battleship

type, dimensions (Width X Height) & positions (x & y) for Player-1 and then for Player-
2. Finally, Player-1’s sequence of missile target cells (x & y) and Player-2’s sequence.

Sample Input:
Enter area boundaries: 5 E
Type for battleship 1: Q
Dimension for battleship 1: 1 1
Location of battleship 1 for player A: A1
Location of battleship 1 for player B: B2
Type for battleship 2: P
Dimension for battleship 2: 2 1
Location of battleship 2 for player A: D4
Location of battleship 2 for player B: C3
Missile targets for player A: A1 B2 B2 B3
Missile targets for player B: A1 B2 B3 A1 D1 E1 D4 D4 D5 D5
Player-1 battle Area
1 2 3 4 5
A Q
B
C
D P P
E

Player-2 battle Area
1 2 3 4 5
A
B Q
C P P
D
E

Sample Output:
Player-1 fires a missile with target A1 which missed
Player-2 fires a missile with target A1 which hit
Player-2 fires a missile with target B2 which missed
Player-1 fires a missile with target B2 which hit
Player-1 fires a missile with target B2 which hit
Player-1 fires a missile with target B3 which missed
Player-2 fires a missile with target B3 which missed
Player-1 has no more missiles left
Player-2 fires a missile with target A1 which hit
Player-2 fires a missile with target D1 which missed
Player-1 has no more missiles left
Player-2 fires a missile with target E1 which missed

Player-1 has no more missiles left
Player-2 fires a missile with target D4 which hit
Player-2 fires a missile with target D4 which missed
Player-1 no more missiles left
Player-2 fires a missile with target D5 which hit
Player-2 won the battle

Constraints:
1 <= Width of Battle area (M’) <= 9,
A <= Height of Battle area (N’) <= Z
1 <= Number of battleships <= M’ * N’
Type of ship = {‘P’, ‘Q’}
1 <= Width of battleship <= M’
A <= Height of battleship <= N’
1 <= X coordinate of ship <= M’
A <= Y coordinate of ship <= N’
