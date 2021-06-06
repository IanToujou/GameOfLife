# GameOfLife

## What is Game Of Life?

*Wikipedia Article: https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life*

Game of life is a zero player input game, so basically a simulation that runs on its own.
It has been created by John Horton Conway in 1970.
It simulates a population and its changes over generations by applying the rules down below.
The simulation consists of a grid with cells, that are either alive or dead.

![Example](https://upload.wikimedia.org/wikipedia/commons/e/e5/Gospers_glider_gun.gif)

The rules are the following:
* If a cell has `> 3` neighbors, it *dies* due to overpopulation.
* If a cell has `< 2` neighbors, it *dies* due to underpopulation.
* If a cell has `2` or `3` neighbors, it will *stay alive*.
* If a dead cell has `3` neighbors, it will become *alive* due to reproduction.

## Features

![Simulation](https://user-images.githubusercontent.com/44029196/120933413-5bd8f180-c6fa-11eb-8962-e7b12aeae53b.gif)

* A simulation of the game of life.
* Custom generation amount and grid size.
* `GIF` and `PNG` sequence export.

## Guides
### Install

1. Download this software. 
2. Execute it with `start.bat` on windows or the `start.sh` script on linux.
3. Specify the size of the grid, amount of generations and the resolution in the terminal.
4. Watch the random generated simulation go!

### Custom grid input

Custom input hasn't been added yet. However, I plan on doing so in the near future. Sorry :c
