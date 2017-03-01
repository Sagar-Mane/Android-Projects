Conway’s Game of Life
UI Design
1. Created two fragments on main activity layout.
2. One for grid section and other for buttons section.
3. Used 2d graphics for drawing grid and filling cells with solid circles.
4. Next button changed the grid to its next generation
5. Reset button clears the grid and prompt the user an alert before clearing the grid.
Implementation Details
* Used 2d Boolean matrix for storing grid values i.e. false or true- live cell being true and dead cell being false.
* Depending upon the values of each cell inside grid, when onDraw() method is called cells with true values i.e. live cells are filled with solid red circle.
* Used rules of Conway’s game of life as mentioned along with the border conditions.
*  Game works both in portrait mode and landscape mode.
