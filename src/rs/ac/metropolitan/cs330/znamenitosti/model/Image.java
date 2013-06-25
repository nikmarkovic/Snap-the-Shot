package rs.ac.metropolitan.cs330.znamenitosti.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import com.googlecode.javacpp.BytePointer;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import rs.ac.metropolitan.cs330.znamenitosti.util.ImageComparator;

/**
 *
 * @author nikola
 */
@Table(name = "Image")
public class Image extends Model implements Comparable<Image> {

    @Column(name = "name", unique = true)
    public String name;
    @Column(name = "data")
    public byte[] data;

    public static Image create(byte[] data) {
        Image image = new Image();
        image.name = getGenericName();
        image.data = data;
        image.save();
        return image;
    }

    public static Image create(String name, byte[] data) {
        Image image = new Image();
        String imageName = (name == null || name.length() == 0) ? getGenericName() : name;
        image.name = imageName;
        image.data = data;
        image.save();
        return image;
    }

    public static Image byName(String name) {
        return new Select()
                .from(Image.class)
                .where("name = ?", name)
                .executeSingle();
    }

    public Image() {
        super();
    }

    public int compareTo(Image another) {
        IplImage anotherImage = cvDecodeImage(cvMat(1, another.data.length, CV_8UC1, new BytePointer(another.data)));
        IplImage thisImage = cvDecodeImage(cvMat(1, data.length, CV_8UC1, new BytePointer(data)));
        ImageComparator comparator = new ImageComparator(anotherImage);
        int imageSize = thisImage.width() * thisImage.height();
        return (int) (100 * comparator.compare(thisImage) / imageSize);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Image other = (Image) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Image: " + name;
    }

    private static String getGenericName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "IMG_" + timeStamp + ".jpg";
    }
}
