# OandX

## Compiling Java files using Eclipse IDE

1. Download this repository as ZIP
2. Create new `Java Project` in `Eclipse`
3. Right click on your `Java Project` --> `Import`
4. Choose `General` --> `Archive File`
5. Put directory where you downloaded ZIP in `From archive file`
6. Put `ProjectName/src` in `Into folder`
7. Click `Finish`

### Linking the UI Library

8. Right click on your `Java Project` --> `Build Path` --> `Add External Archives`
9. Select `ecs100.jar` and link it to the project. That JAR will be in the directory where you downloaded ZIP

## Running the program

1. Right click on your `Java Project` --> `Run As` --> `Run Configurations`
2. See `Arguments` tab. In `Program Arguments` put `#RowsAndColumns <SPACE> #CellsToWin`. e.g. For 10x10 board with 5 neighbouring/consecutive cells to win put: `10 5`
3. Click `Apply`. Then `Run` the game
