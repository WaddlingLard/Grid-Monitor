import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * This program allows a user to take in a sheet of cell values allowing them to see several variations of it interpreted. It also includes a danger grid letting you know if
 * its going to blow. Speaking of which, GET DOWN!
 * @author brianwu
 *
 */
public class GridMonitor implements GridMonitorInterface {

	private double[][] grid;
	private File file;

	/**
	 * This is the default constructor that creates the grid of cell values
	 * @param filename Name of the file to scan
	 * @throws FileNotFoundException if it doesn't find the file it throws this
	 */
	public GridMonitor(String filename) throws FileNotFoundException{
		file = new File(filename);

		Scanner lineReader = new Scanner(file);
		int width = 0;
		int height = 0;

		String line = lineReader.nextLine();
		Scanner lineScanner = new Scanner(line);

		if(lineScanner.hasNextDouble()) { //This is a way of outputting an error if there is a dimension missing
			width = (int)lineScanner.nextDouble();
			if(lineScanner.hasNextDouble()) {
				height = (int)lineScanner.nextDouble();
			}else {
				System.out.println("ERROR! MISSING HEIGHT DIMENSION VALUE!");
			}
		}else {
			System.out.println("ERROR! MISSING WIDTH DIMENSION VALUE!");
		}

		grid = new double[height][width];
		double tempNum;

		for(int i = 0; i < width; i++) {//This is a way to output whether the values are missing, impractical, or wrong type
			line = lineReader.nextLine();
			Scanner numberScanner = new Scanner(line);
			for(int j = 0; j < height; j++) {
				if(numberScanner.hasNextDouble()) {
					tempNum = numberScanner.nextDouble();
					if(tempNum > 10000 || tempNum < -10000) {
						System.out.println("ERROR! DATA ISN'T PRACTICAL! OUTPUT ISN'T USEFUL!");
					}
					grid[j][i] = tempNum;
				}else if(numberScanner.hasNext()) {
					System.out.println("ERROR! INVALID DATA TYPE! OUTPUT IS QUESTIONABLE!");
					String whatIsThis = numberScanner.next();
				}else {
					System.out.println("ERROR! MISSING DATA! OUTPUT IS QUESTIONABLE!");
				}
			}
			numberScanner.close();
		}	
		lineScanner.close();
		lineReader.close();
	}

	/**
	 * This method creates a grid from the original values off of the data sheet
	 * @return A 2d array full of double values
	 */
	@Override
	public double[][] getBaseGrid() {
		double[][] baseGrid = new double[grid[0].length][grid.length];
		for(int i = 0; i < grid[0].length; i++) {
			for(int j = 0; j < grid.length; j++) {
				baseGrid[i][j] = grid[j][i];
			}
		}
		return baseGrid;
	}

	/**
	 * This method creates a grid full of sums from neighboring values and accounts for edge values
	 * @return A 2d array full of double values
	 */
	@Override
	public double[][] getSurroundingSumGrid() { //(x-1)(x+1)(y-1)(y+1)
		double[][] sumGrid = new double[grid[0].length][grid.length];
		int currentX;
		int currentY;
		double currentTotal;

		for(int i = 0; i < grid[0].length; i++) { //These loops and if statements check weather points go out of bounds if trying to add neighboring cells
			for(int j = 0; j < grid.length; j++) {
				currentX = j;
				currentY = i;
				currentTotal = 0;

				if(currentX - 1 < 0) {
					currentTotal += grid[j][i];
				}else {
					currentTotal += grid[j - 1][i];
				}

				if(currentX + 1 >= grid.length) {
					currentTotal += grid[j][i];
				}else {
					currentTotal += grid[j + 1][i];
				}

				if(currentY - 1 < 0) {
					currentTotal += grid[j][i];
				}else {
					currentTotal += grid[j][i - 1];
				}

				if(currentY + 1 >= grid[0].length) {
					currentTotal += grid[j][i];
				}else {
					currentTotal += grid[j][i + 1];
				}

				sumGrid[i][j] = currentTotal;
			}
		}
		return sumGrid;
	}

	/**
	 * This method creates a grid full of average values from the sum grid 
	 * @return A 2d array full of double values
	 */
	@Override
	public double[][] getSurroundingAvgGrid() {
		double[][] avgGrid = new double[grid[0].length][grid.length];
		double[][] comparison = getSurroundingSumGrid();
		for(int i = 0; i < grid[0].length; i++)
			for(int j = 0; j < grid.length; j++)
				avgGrid[i][j] = comparison[i][j] / (4 * 1.0);

		return avgGrid;
	}

	/**
	 * This method creates a grid full of double values that set a delta on the averages to find out what isnt within the range of safe values
	 * @return A 2d array full of double values
	 */
	@Override
	public double[][] getDeltaGrid() {
		double[][] deltaGrid = new double[grid[0].length][grid.length];
		double[][] comparison = getSurroundingAvgGrid();
		for(int i = 0; i < grid.length; i++)
			for(int j = 0; j < grid[0].length; j++)
				deltaGrid[j][i] = Math.abs(comparison[j][i] / (2 * 1.0));

		return deltaGrid;
	}

	/**
	 * This method creates a danger grid full of boolean values declaring what is and isn't dangerous
	 * @return A 2d array full of boolean values
	 */
	@Override
	public boolean[][] getDangerGrid() {
		boolean[][] dangerGrid = new boolean[grid[0].length][grid.length];
		double[][] deltaGrid = getDeltaGrid();
		double[][] avgGrid = getSurroundingAvgGrid();
		double[][] comparison = getBaseGrid();
		double lowerRange;
		double higherRange;
		for(int i = 0; i < grid.length; i++)
			for(int j = 0; j < grid[0].length; j++) {
				lowerRange = avgGrid[j][i] - deltaGrid[j][i];
				higherRange = avgGrid[j][i] + deltaGrid[j][i];
				if(comparison[j][i] < lowerRange || comparison[j][i] > higherRange) {
					dangerGrid[j][i] = true;
				}
			}
		return dangerGrid;
	}

	/**
	 * This is a method that outputs a grid full of double values to a string
	 * @param laPrinta The 2d array filled with double values
	 * @return A literal string with all the numbers of the value grids
	 */
	public String printNumberGrid(double[][] laPrinta) {
		String addon = "";
		for(int i = 0; i < laPrinta.length; i++) {
			addon += "\n";
			for(int j = 0; j < laPrinta[0].length; j++) {
				addon += (laPrinta[i][j] + " ");
			}
		}
		return addon;
	}

	/**
	 * This is a method that outputs a grid full of boolean values to a string
	 * @param laPrinta The 2d array filled with boolean values
	 * @return A literal string with all the trues and falses of the danger grid
	 */
	public String printBooleanGrid(boolean[][] laPrinta) { 
		String addon = "";
		for(int i = 0; i < laPrinta.length; i++) {
			addon += "\n";
			for(int j = 0; j < laPrinta[0].length; j++) {
				addon += (laPrinta[i][j] + " ");
			}
		}
		return addon;
	}
	/**
	 * This the the toString() method that returns the name of the file being scanned.
	 * @return String of the filename
	 */
	public String toString() {
		return file + "";
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scnr = new Scanner(System.in);
		System.out.println("What grid do you wanna scan?");
		System.out.print("Input filename: ");
		String gridName = scnr.nextLine();

		GridMonitor gred = new GridMonitor(gridName);
		System.out.println("---------------------------");
		System.out.println("The base grid values:");
		System.out.println(gred.printNumberGrid(gred.getBaseGrid()));
		System.out.println("---------------------------");
		System.out.println("The surrounding sum values:");
		System.out.println(gred.printNumberGrid(gred.getSurroundingSumGrid()));
		System.out.println("---------------------------");
		System.out.println("The surrounding average values:");
		System.out.println(gred.printNumberGrid(gred.getSurroundingAvgGrid()));
		System.out.println("---------------------------");
		System.out.println("The delta values:");
		System.out.println(gred.printNumberGrid(gred.getDeltaGrid()));
		System.out.println("---------------------------");
		System.out.println("The danger grid values:");
		System.out.println(gred.printBooleanGrid(gred.getDangerGrid()));
		System.out.println("---------------------------");
		scnr.close();
	}
}
