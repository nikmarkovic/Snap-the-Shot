package rs.ac.metropolitan.cs330.znamenitosti.util;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_legacy.*;

/**
 * Helper class that simplifies usage of `cvCalcHist` function for single
 * channel images.
 *
 * @author nikola
 */
public class Histogram1D {

    private static final int numberOfBins = 256;
    private float minRange = 0.0f;
    private float maxRange = 255.0f;

    public Histogram1D() {
    }

    public void setRanges(float minRange, float maxRange) {
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    /**
     * Computes histogram of an image.
     *
     * @param image input image.
     * @param mask optional mask.
     * @return histogram object.
     */
    public CvHistogram getHistogram(IplImage image, IplImage mask) {
        // Allocate histogram object
        int[] sizes = {numberOfBins};
        float[][] ranges = {{minRange, maxRange}};
        CvHistogram histogram = cvCreateHist(1, sizes, CV_HIST_ARRAY, ranges, 1);
        // Compute histogram
        IplImage[] images = {image};
        cvCalcHist(images, histogram, 1, mask);
        return histogram;
    }

    /**
     * Computes histogram of an image.
     *
     * @param image input image.
     * @return histogram represented as an array.
     */
    public float[] getHistogramAsArray(IplImage image) {
        // Create and calculate histogram object
        CvHistogram histogram = getHistogram(image, null);
        // Extract values to an array
        float[] result = new float[numberOfBins];
        for (int i = 0; i < numberOfBins; ++i) {
            result[i] = cvQueryHistValue_1D(histogram, i);
        }
        // Release the memory allocated for histogram
        cvReleaseHist(histogram);
        return result;
    }

    /**
     * Apply a look-up table to an image. It is a wrapper for function `cvLUT`.
     *
     * @param image input image
     * @param lookUpTable look-up table
     * @return new image
     */
    public static IplImage applyLookUp(IplImage image, CvMat lookUpTable) {
        IplImage result = IplImage.create(cvGetSize(image), image.depth(), image.nChannels());
        cvLUT(image, result, lookUpTable);
        return result;
    }

    /**
     * Equalize histogram of an image. The algorithm normalizes the brightness
     * and increases the contrast of the image. It is a wrapper for function
     * `cvEqualizeHist`.
     *
     * @param image input image
     * @return new image
     */
    public static IplImage equalize(IplImage image) {
        // Create out put image of the same size and type as input
        IplImage result = IplImage.create(cvGetSize(image), image.depth(), image.nChannels());
        // Equalize histogram
        cvEqualizeHist(image, result);
        return result;
    }
}
