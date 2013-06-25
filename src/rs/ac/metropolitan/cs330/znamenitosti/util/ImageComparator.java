package rs.ac.metropolitan.cs330.znamenitosti.util;

import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

/**
 * Computes image similarity using `cvCompareHist`.
 *
 * @author nikola
 */
public class ImageComparator {

    private ColorHistogram colorHistogram;
    private CvHistogram referenceHistogram;

    public ImageComparator(IplImage referenceImage) {
        colorHistogram = new ColorHistogram();
        ColorHistogram.colorReduce(referenceImage, 32);
        this.referenceHistogram = colorHistogram.getHistogram(referenceImage);

    }

    /**
     * Compare the reference image with the given input image.
     *
     * @param image input image.
     * @return similarity score
     */
    public double compare(IplImage image) {
        ColorHistogram.colorReduce(image, 32);
        CvHistogram inputHistogram = colorHistogram.getHistogram(image);
        return cvCompareHist(referenceHistogram, inputHistogram, CV_COMP_INTERSECT);
    }
}
