INSTALLATION INSTRUCTIONS:
use IMPORT. . . from the FILE menu in Eclipse. Next, select ARCHIVE FILE from the GENERAL category. 
Select BROWSE from the FROM ARCHIVE FILE and select the Spectrangle zip file. BROWSE for a directory folder and press finish.

TO RUN LOCAL GAME:
Open Spectrangle.java class from 'game' package (game.Spectrangle)
Choose the number of human/computer players (up to 4) by assigning a new variable s0, s1, s2, s3.

FORMAT: 
'Player (variable) = new HumanPlayer/NaiveComputer("NAME");
Game s = new Game(s0, s1, s2, s3) (IF PLAYER IS NOT PRESENT, PUT null INSTEAD OF VARIABLE).

EXAMPLE: 
Player s0 = new HumanPlayer("Jack");
Player s1 = new HumanPlayer("Jill");
Player s2 = new NaiveComputer("John");

Game s = new Game(s0, s1, s2, null);


Finally, press Run Class (f11)


TO RUN SERVER:
Open SpectrangleServer.java from 'networking' package (networking.SpectrangleServer)
Press Run Class (f11) and follow instructions on Console

TO RUN CLIENT:
Open SpectrangleClient.java from 'networking' package (networking.SpectrangleClient)
Press Run Class (f11) and follow instructions on Console

