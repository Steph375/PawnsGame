## Level 0: High Contrast View
* We implemented level 0 by creating a new ColorScheme interface used to represent the color scheme
  to use when creating the GUI. This interface provides public methods to get colors such as
  getLineColor and getBackgroundColor that return Color objects.
* When visualizing the board via the BoardPanel, the board panel takes in a color scheme as
  a parameter, and uses these methods to access the colors used for drawing.
* The specific ColorScheme implementation (either DefaultColor or HighContrast, 
  classes we implemented) needs to be passed
  in to the modified nePawnsFrame when the view is constructed in the main.

## Level 1: cards with new influence
* To implement level one, we created the following classes:
  * EnhancedCell, which extends the existing Cell implementation and tracks the upgrade and devalue 
    effects applied to a cell
  * EnhancedPawnsGame, which extends the existing PawnsGameModel implementation that handles the
    scoring and application of the new types of influence to a board made of EnhancedCells
  * We changed the Card interface to have methods that return the positions the card has influence
    over, upgrades added to, and devalues added to separately
    * in the regular PawnsCard, getUpgrades and getDevalues return empty lists
  * Changed the PawnCard hashmap of influence from <InfluencePosition, boolean> to 
    <InfluencePosition, InfluenceType> with influence type being a new enum
  * created a InfluencePawnCard that extends PawnCard and handles all three types of influence
  * we created a new EnhancedDeckReader to create new decks of InfluencePawnCards when reading text
    files that represent the new types of influence
  * Created a new EnhancedTextualView to visualize the new types of influence on the board
* We created test classes to test the behavior of the new model, cell, card, and textual view,
  as well as the ColorSchemes

## Level 2: visualizing the new influence types in the GUI
* We implemented level 2 by creating an EnhancedBoardPanel, EnhancedHandPanel and EnhancedPawnsFrame
* The EnhancedBoardPanel simply draws the upgrade/devalue as text on the bottom of the cell adding 
  to the parent logic from BoardPanel.
* We modified HandPanel's drawCard separating part of it to a helper drawCardText
  * This way in our new EnhancedHandPanel we could override draw card but only call super on its 
    helper drawCardText. That way we could create a new influence drawing method in our enhanced 
    class that visualizes upgrade and devalue in the influence grid with their designated colors.