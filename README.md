# AndroidCustomWidgets
This repository is just a storage space for a number of custom Views that I have made.
Some of the Views were made for specific apps and some were made to be used in any future apps.

RadialMenu:
In the XML, the Radial Menu requires square dimensions and a positive number between 1 and 12 in the custom numberOfOptions field. It also requires names for the menu items
input in the custom optionsText field. These names must be separated by '/' characters and there must be a number of them matching the numberOfFields number. They can be 
empty, but there must be numberOfFields-1 '/' characters. 
The RadialMenuView should be inside of a RadialMenuSurface View. The RadialMenuSurface is what detects the initial touch and causes the RadiamMenuView to appear.
The actions of the individual menu items should be specified in the calling Activity by calling the RadialMenuView.setActionExecutor() function and passing it an instance
of the RadialMenuView.ActionExecutor class with the performMenuAction(int menuItem) function overridden. This function takes positive integer values up thru the number
of options specified in the XML. The number 0 is passed when the Radial Menu is brought up, but no action is selected.
In its current state, the Radial Menu is functional, but lacks polish and a great deal of styling, which could be altered for specific use by modification of the onDraw
function of the RadialMenuView class. 

StepProgress:
In the XML, the step-progress bar requires the custom fields numberOfSteps and progressLevel. The numberOfSteps attribute must be and integer greater than 0.
The progressLevel attribute should be between 0 and numberOfSteps, but will default to either the maximum if its set greater than numberOfSteps or the minimum if its
set less than 0.
Labels for each step must be created separately. 
This View requires custom colors called "progressComplete" and "progressIncomplete" as well as custom attributes called "numberOfSteps" and "progressLevel".

DoubleSeekbar:
In the XML, the DoubleSeekbar requires the custom fields minimumValue, maximumValue, and stepSize. The maximumValue attribute must be greater
than the minimumValue attribute and both must be integers. This View requires custom colors called "doubleSeekbarUpper", "doubleSeekbarLower", "doubleSeekbarBar",
and "doubleSeekbarHighlight".
To fully implement, the actions to be taken when Seekbar is changed should be defined in the calling Activity by calling the DoubleSeekBar.setActionExecutor() function 
and passing it an instance of the DoubleSeekBar.ActionExecutor class with the touchMove() and touchUp() functions overriden. These functions can access the two values 
of the Seekbar using the getLowerValue() and getUpperValue() functions.

FileExplorer:
To fully implement, the MainActivity member variable and corresponding bit of the constructor should be changed to match the Activity type that is calling and using
this class. This class also requires external read permissions and as such a permissions request listener should be implemented in the calling Activity. This class does
not implement any View. It only holds and manages the data. As such, it should be paired with a ListView, RecyclerView or something similar. The example uses a very simple
ListView.