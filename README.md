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