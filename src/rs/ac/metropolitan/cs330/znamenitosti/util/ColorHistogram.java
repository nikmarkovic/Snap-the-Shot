package rs.ac.metropolitan.cs330.znamenitosti.util;

import java.util.ArrayList;
import java.util.List;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

/**
 * Helper class that simplifies usage of `cvCalcHist` function for color images.
 *
 * @author nikola
 */
public class ColorHistogram {

    private static final int numberOfBins = 256;
    private static final float minRange = 0.0f;
    private static final float maxRange = 255.0f;

    public ColorHistogram() {
    }

    /**
     * Computes histogram of an image. Returned CvHistogram object has to be
     * manually deallocated after use using `cvReleaseHist`.
     *
     * @param image input image
     * @return histogram object
     */
    public CvHistogram getHistogram(IplImage image) {
        if (image != null && image.nChannels() == 3) {
            // Allocate histogram object
            int[] sizes = {numberOfBins, numberOfBins, numberOfBins};
            float[] minMax = {minRange, maxRange};
            float[][] ranges = {minMax, minMax, minMax};
            CvHistogram histogram = CvHistogram.create(3, sizes, CV_HIST_SPARSE, ranges, 1);
            // Split bands, as required by `cvCalcHist`
            IplImage channel0 = IplImage.create(cvGetSize(image), image.depth(), 1);
            IplImage channel1 = IplImage.create(cvGetSize(image), image.depth(), 1);
            IplImage channel2 = IplImage.create(cvGetSize(image), image.depth(), 1);
            cvSplit(image, channel0, channel1, channel2, null);
            // Compute histogram
            IplImage[] channels = {channel0, channel1, channel2};
            cvCalcHist(channels, histogram, 0, null);
            return histogram;
        }
        return null;
    }

    /**
     * Convert input image from RGB ro HSV color space and compute histogram of
     * the hue channel.
     *
     * @param image RGB image
     * @param minSaturation minimum saturation of pixels that are used for
     * histogram calculations. Pixels with saturation larger than minimum will
     * be used in histogram computation
     * @return histogram of the hue channel, its range is from 0 to 180.
     */
    public CvHistogram getHueHistogram(IplImage image, int minSaturation) {
        if (image != null && image.nChannels() == 3) {
            // Convert RGB to HSV color space
            IplImage hsvImage = IplImage.create(cvGetSize(image), image.depth(), 3);
            cvCvtColor(image, hsvImage, CV_BGR2HSV);
            // Split the 3 channels into 3 images
            List<IplImage> hsvChannels = splitChannels(hsvImage);
            IplImage saturationMask = null;
            if (minSaturation > 0) {
                saturationMask = IplImage.create(cvGetSize(hsvImage), IPL_DEPTH_8U, 1);
                cvThreshold(hsvChannels.get(1), saturationMask, minSaturation, 255, CV_THRESH_BINARY);
            }
            // Compute histogram of the hue channel
            Histogram1D h1D = new Histogram1D();
            h1D.setRanges(0, 180);
            return h1D.getHistogram(hsvChannels.get(0), saturationMask);
        }
        return null;
    }

    /**
     * Reduce number of colors.
     *
     * @param image input image that will have colors modified after this call.
     * @param factor color reduction factor.
     */
    public static void colorReduce(IplImage image, int factor) {
        CvMat mat = image.asCvMat();
        int elements = mat.rows() * mat.cols() * mat.channels();
        for (int i = 0; i < elements; ++i) {
            int v = (int) mat.get(i);
            v = v / factor * factor + factor / 2;
            mat.put(i, v);
        }
    }

    /**
     * Split channels in a 3 channel image.
     *
     * @param image 3 channel image.
     * @return array of 3 channels.
     */
    public static List<IplImage> splitChannels(IplImage image) {
        if (image != null && image.nChannels() == 3) {
            List<IplImage> result = new ArrayList<IplImage>();

            CvSize size = cvGetSize(image);
            IplImage channel0 = IplImage.create(size, image.depth(), 1);
            IplImage channel1 = IplImage.create(size, image.depth(), 1);
            IplImage channel2 = IplImage.create(size, image.depth(), 1);
            cvSplit(image, channel0, channel1, channel2, null);

            result.add(channel0);
            result.add(channel1);
            result.add(channel2);
            return result;
        }
        return null;
    }
}
