****************
* PROJECT1/GridMonitor
* CS 221
* 1/21/2022
* Brian Wu
**************** 

OVERVIEW:

 The GridMonitor is a class that takes cell values from a text file and scans them.
 It then outputs several grids that provide information on sums, averages, deltas, and danger levels.


INCLUDED FILES:

 GridMonitor.java - source file
 GridMonitorTest.java - source file
 GridMointorInterface.java - source file
 README - this file


BUILDING AND RUNNING:

 When you are in folder with all the contents use this command
 to compile the code:
 $ javac GridMonitor.java

 To run the program after compiling use this command:
 $ java GridMonitor

 Console output will report the data from the GridMonitor.
 

PROGRAM DESIGN:

 The GridMonitor class implements the GridMonitorInterface so most of the
 documentation is established in the interface. The program is relatively simple,
 with a constructor, several methods, a toString, and a main class built in.
 
 As a recap, this program as several methods that interpret cell data to 
 get sums and averages. The 2d arrays are all moving in one machine from one method
 to another allowing results to be calculated.
 
 The main algorithms that take up this program are the data reading at the start in 
 the constructor. This allows the program to take in values and then can be used 
 throughout the program. Another place that I find critical is the surrounding sums
 method as it accounts for location of X and Y values to correctly associate sums.
 
 I feel in a program like this using the least amount of logic gets you the best results.
 Its like how cooking is. Sometimes less is more and that applies here. The
 program's idea is simple so the code should be simple. That isn't to say that this took 
 quite some time to think about. One thing that I would like to improve is the 
 safeguards to make this program more user friendly to less educated folk, even if they 
 wouldn't ever need to use it.
 

TESTING:

 Making testing files was the key to producing a consistent output that is 
 precise and accurate. This program does not take into a complete account
 that the testing file is bad. What it does though is it generally outputs 
 grids that are accurate with the information provided.
 
 Some stuff that it is capable of when the data is bad:
 Missing width/height values (The program Won't be able to run however)
 Accounting for missing values
 Incorrect data types
 Impractical values
 
 There is definitely more stuff to polish out, but with what is currently 
 provided it does a great job at notifying the user on errors that are most
 likely to happen. The current safeguards target data imperfections and 
 there is more to fix, and that will happen in the near future.


DISCUSSION:
 
 I'm somewhat accustomed to test-driven development, but in CS221 it
 makes projects a lot more in depth requiring more than the bare minimum.
 I think I ran into most issues when trying to develop safeguards and idiot-proof
 techniques. There is just so much to account for and I feel rather satisfied in what
 I accomplished. Another bump on the road I ran into was the conditionals in for loops
 and if statements. It can be frustrating when you have all the logic right, but an 'i' 
 or 'j' is just in the wrong spot.

 