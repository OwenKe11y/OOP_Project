package ie.gmit.sw;

import java.util.Scanner;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The Class MenuRun is used to show the main menu of the application
 **/
public class MenuRun {

	private String path;
	private String fullPath;

	/**
	 * Constructor that takes in showMenuOption
	 **/
	public MenuRun() {
		showMenuOptions();// Menu Call
	}

	/**
	 * Method for displaying the Menu and handling the selections
	 **/
	public void showMenuOptions() {

		Scanner choice = new Scanner(System.in);
		String selection;

		// Title Menu
		System.out.println("***************************************************");
		System.out.println("* GMIT - Dept. Computer Science & Applied Physics *");
		System.out.println("*                                                 *");
		System.out.println("*          Image Filtering System V1.0            *");
		System.out.println("*                By Owen Kelly                    *");
		System.out.println("*                                                 *");
		System.out.println("***************************************************");

		System.out.println();
		System.out.println("1)Enter Image Directory");
		System.out.println("2)Select Single Image");
		System.out.println("3)Add Custom Filter");
		System.out.println("4)Quit");
		System.out.println();
		System.out.println("Select Option 1-4> ");
		// option pass in
		selection = choice.nextLine();

		// switch statement for selecting menu item
		switch (selection) {

		case ("1"):
			imgDirectory();// start image directory selector
			break;

		case ("2"):
			imageSelect(path);// start select a single image
			break;

		case ("3"):
			imageReadIn();
			break;

		case ("4"):
			exitProgram();
			break;
		}

		do {
			System.out.println("<Invalid option - please try again>");
			System.out.print("Choice>");
			selection = choice.nextLine();

			switch (selection) {

			case ("1"):
				imgDirectory();
				clearScreen();
				break;

			case ("2"):
				imageSelect(path);
				break;

			case ("3"):
				imageReadIn();
				break;

			case ("4"):
				exitProgram();
				break;

			}

		} while ((selection != ("1")) || (selection != ("2")) || (selection != ("3")) || (selection != ("4")));
		// repeat this function until 1, 2, 3 or 4 is chosen

		choice.close();
	}// showMenuOption

	// Method that starts the image directory selector
	private String imgDirectory() {

		Scanner s = new Scanner(System.in);

		System.out.println("Enter in new Directory");
		System.out.print("Enter Text> ");// F:\Documents\College\Third Year\Semester 1\OOP Project\images\
		path = s.nextLine();

		clearScreen();
		showMenuOptions();
		s.close();

		return path;
	}// imgDirectory

	private String imageSelect(String path) {

		Scanner s = new Scanner(System.in);
		String option;

		String[] pathnames;
		this.path = path;

		File f = new File(path);
		pathnames = f.list();

		if (pathnames != null) {
			for (String pathname : pathnames) {
				// Print the names of files and directories
				System.out.println(pathname);
			}
		} else {
			System.out.println("No directory detected! Check your directory input and try again");
			showMenuOptions();
		}

		System.out.println("Enter the image you would like to select");
		option = s.nextLine();

		fullPath = path + option;
		System.out.println(fullPath);

		clearScreen();
		showMenuOptions();
		s.close();

		return fullPath;
	}

	private void imageReadIn() {
		BufferedImage imageFilter;
		try {
			imageFilter = ImageIO.read(new File(fullPath));
			double[][] kernal = { { -1.0, -1.0, -1.0}, {2, 2, 2}, {-1.0, -1.0, -1.0} };

			ImageFilter filter = new ImageFilter(kernal, imageFilter, fullPath);
			filter.process();
		}catch(

	IOException e)
	{
		e.printStackTrace();
	}

	showMenuOptions();

	}

	// Method for exiting the program
	private void exitProgram() {

		System.out.println("<Exiting Program>");
		System.exit(0);// Exits the program immediately
	}

	public static void clearScreen() {
		System.out.flush();
	}
}// MenuRun class
