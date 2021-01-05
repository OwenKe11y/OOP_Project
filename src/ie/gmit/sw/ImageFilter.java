package ie.gmit.sw;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageFilter {

	private double[][] filter = new double[3][3];
	private BufferedImage image = null;
	private String fullPath;

	public ImageFilter(double[][] filter, BufferedImage image, String fullPath) {
		super();
		this.filter = filter;
		this.image = image;
		this.fullPath = fullPath;

	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void process() {

		System.out.println(image); // This writes out a lot of useful meta-data about the image.

		for (int y = 0; y < image.getHeight(); y++) { // Loop over the 2D image pixel-by-pixel
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = image.getRGB(x, y); // Get the pixel at an (x, y) coordinate
				// image.setRGB(x, y, 0x00FF0000); // Set the pixel colour at (x, y) to red

				int newPixel = apply(pixel, x, y);

				// We can get the RGB colour channels out of a 32-bit int as follows:
				int red = (newPixel >> 16) & 0xff;
				int green = (newPixel >> 8) & 0xff;
				int blue = newPixel & 0xff;
				// We can re-create a 32-bit RGB pixel from the channels as follows;
				int rgb = 0;
				rgb = rgb | (red << 16);
				rgb = rgb | (green << 8);
				rgb = rgb | blue;

				image.setRGB(red, green, blue);
			}
		}

		try {
			ImageIO.write(image, "png", new File(fullPath + "output.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int apply(int pixel, int x, int y) {
		int element = pixel;
		int newElement = 0;
		for (int row = 0; row < filter.length; row++) {
			for (int col = 0; col < filter[row].length; col++) {
				try {
					// This will cause an exception if we overrun the edges of the image
					element = image.getRGB(x + col, y + row);// We can get the RGB colour channels out of a 32-bit int
																// as follows:
					int red = (element >> 16) & 0xff;
					int green = (element >> 8) & 0xff;
					int blue = element & 0xff;
					
					red = (int) (red * filter[col][row]);
					green = (int) (green * filter[col][row]);
					blue = (int) (blue * filter[col][row]);
					// We can re-create a 32-bit RGB pixel from the channels as follows;
					int rgb = 0;
					rgb = rgb | (red << 16);
					rgb = rgb | (green << 8);
					rgb = rgb | blue;
					
					newElement += rgb;
					//newElement += element * filter[col][row];
				} catch (Exception e) {
					continue; // Ignore any exception and keep going. It’s good enough J
				}
			}
		}
		element = newElement;
		return element;
	}

}
