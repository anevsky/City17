/**
 * 
 */
package com.alexnevsky.web.util;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.media.jai.JAI;

import org.apache.log4j.Logger;
import org.imgscalr.Scalr;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.SeekableStream;

/**
 * @author Alex Nevsky
 * 
 */
public class ImageUtil {

	private static final Logger logger = Logger.getLogger(ImageUtil.class);

	/**
	 * Returns buffered image data.
	 * 
	 * @param inputImageFile
	 *            Path to image.
	 * @return buffered image data or null if wrong path or something else
	 */
	public static BufferedImage getBufferedImage(File inputImageFile) {
		BufferedImage bufferedImage = null;

		String extension = FileUtil.getPathExtension(inputImageFile.getName());

		// JAI
		// see http://stackoverflow.com/questions/4470958/
		try {
			SeekableStream seekableStream = new FileSeekableStream(inputImageFile);
			ParameterBlock pb = new ParameterBlock();
			pb.add(seekableStream);

			if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) {
				bufferedImage = JAI.create("jpeg", pb).getAsBufferedImage();
			} else if (extension.equalsIgnoreCase("png")) {
				bufferedImage = JAI.create("png", pb).getAsBufferedImage();
			} else if (extension.equalsIgnoreCase("gif")) {
				bufferedImage = JAI.create("gif", pb).getAsBufferedImage();
			} else {
				return null;
			}
		} catch (final IOException e) {
			logger.error("Oops!.. Something wrong..." + e, e);
		}

		return bufferedImage;
	}

	/**
	 * Resizes image by width (ignoring height, leaves the original src image unmodified.).
	 * 
	 * @param inputImageFile
	 *            input image file to thumb
	 * @param outputImageFile
	 *            file for resized image
	 * @param targetWidth
	 *            target image width
	 * @return true if success
	 */
	public static boolean resizeImageByWidth(File inputImageFile, File outputImageFile, final int targetWidth) {
		boolean isSuccess = false;

		final int DOES_NOT_MATTER = 200;

		BufferedImage inputImage = null;
		try {
			if (inputImageFile.exists()) {
				// java.awt.color.CMMException: Invalid image format could be there
				// inputImage = ImageIO.read(inputImageFile);

				String extension = FileUtil.getPathExtension(inputImageFile.getName());

				inputImage = getBufferedImage(inputImageFile);

				if (extension != null && inputImage != null) {
					if (!outputImageFile.isDirectory()) {
						FileUtil.createDir(outputImageFile.getParentFile());
					}

					final BufferedImage outputImage = Scalr.resize(inputImage, Scalr.Method.BALANCED,
							Scalr.Mode.FIT_TO_WIDTH, targetWidth, DOES_NOT_MATTER, Scalr.OP_ANTIALIAS);

					ImageIO.write(outputImage, extension, outputImageFile);

					isSuccess = true;
				}
			} else {
				logger.error("Oops!.. " + inputImageFile.toString() + " is not exists!");
			}
		} catch (final IOException e) {
			logger.error("Oops!.. Something wrong..." + e, e);
		}

		return isSuccess;
	}

	/**
	 * Returns image dimension.
	 * 
	 * @param path
	 *            File path.
	 * @return image dimension or null if wrong path or something else
	 */
	public static Dimension getImageDimension(File path) {
		Dimension result = null;

		String suffix = FileUtil.getPathExtension(path.toString());

		if (suffix != null) {
			Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);

			if (iter.hasNext()) {
				ImageReader reader = iter.next();
				try {
					ImageInputStream stream = new FileImageInputStream(path);
					reader.setInput(stream);
					int width = reader.getWidth(reader.getMinIndex());
					int height = reader.getHeight(reader.getMinIndex());
					result = new Dimension(width, height);
				} catch (IOException e) {
					logger.error(e, e);
				} finally {
					reader.dispose();
				}
			}
		}

		return result;
	}

	/**
	 * Compares two images. Not fully, but in many ways. Good performance.
	 * 
	 * @param first
	 *            Path to the first image.
	 * @param second
	 *            Path to the second image.
	 * 
	 * @return true if the same (not necessarily fully)
	 * @see http://stackoverflow.com/questions/2362008/
	 */
	public static boolean isEquals(File first, File second) {
		boolean isEquals = false;

		if (FileUtil.getFileSize(first) == FileUtil.getFileSize(second)) {

			BufferedImage firstBufferedImage = null;
			BufferedImage secondBufferedImage = null;

			firstBufferedImage = getBufferedImage(first);
			secondBufferedImage = getBufferedImage(second);

			if ((firstBufferedImage != null) && (secondBufferedImage != null)) {

				int firstWidth = firstBufferedImage.getWidth();
				int firstHeight = firstBufferedImage.getHeight();

				int secondWidth = secondBufferedImage.getWidth();
				int secondHeight = secondBufferedImage.getHeight();

				if ((firstWidth == secondWidth) && (firstHeight == secondHeight)) {
					boolean isSame = true;

					// compare the color of the pixel at the specified interval
					final int COMPARE_INTERVAL = 10;
					int height = 0;

					while ((height < firstHeight) && (isSame == true)) {
						int width = 0;

						while ((width < firstWidth) && (isSame == true)) {
							int firstImageRGB = firstBufferedImage.getRGB(width, height);
							int secondImageRGB = secondBufferedImage.getRGB(width, height);

							if (firstImageRGB != secondImageRGB) {
								isSame = false;
							}

							width += COMPARE_INTERVAL;
						}

						height += COMPARE_INTERVAL;
					}

					isEquals = isSame;
				}
			}
		}

		return isEquals;
	}
}
