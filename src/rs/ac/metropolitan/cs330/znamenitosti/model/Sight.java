package rs.ac.metropolitan.cs330.znamenitosti.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import rs.ac.metropolitan.cs330.znamenitosti.adapter.ListItemDrawer;
import rs.ac.metropolitan.cs330.znamenitosti.adapter.sight.SightDrawerInProgress;
import rs.ac.metropolitan.cs330.znamenitosti.adapter.sight.SightDrawerSolved;

/**
 *
 * @author nikola
 */
@Table(name = "Sight")
public class Sight extends Model implements Comparable<Sight> {

    @Column(name = "name")
    public String name;
    @Column(name = "description")
    public String description;
    @Column(name = "latitude")
    public double latitude;
    @Column(name = "longitude")
    public double longitude;
    @Column(name = "solved")
    public boolean solved;
    @Column(name = "solvedTime")
    public Date solvedTime;
    @Column(name = "image")
    public Image image;
    @Column(name = "city")
    public City city;

    public static List<Sight> all() {
        return new Select()
                .from(Sight.class)
                .execute();
    }

    public static List<Sight> all(City city) {
        return new Select()
                .from(Sight.class)
                .where("city = ?", city.getId())
                .execute();
    }

    public static List<Sight> unlocked() {
        List<Sight> result = new ArrayList<Sight>();
        for (City city : City.unlocked()) {
            result.addAll(city.sights());
        }
        return result;
    }

    public static List<Sight> solved() {
        return new Select()
                .from(Sight.class)
                .where("solved = ?", 1)
                .execute();
    }

    public static Sight byNameAndCity(String name, City city) {
        return new Select()
                .from(Sight.class)
                .where("name = ? AND city = ?", name, city.getId())
                .executeSingle();
    }

    public static Sight byNameAndCity(String name, String cityName) {
        City city = City.byName(name);
        return byNameAndCity(name, city);
    }

    public static int count() {
        return all().size();
    }

    public static int countUnlocked() {
        return unlocked().size();
    }

    public static int countSolved() {
        return solved().size();
    }

    public static int progress() {
        int count = count();
        if (count == 0) {
            return 0;
        }
        return Math.round(100f * countSolved() / count);
    }

    public static int unlockedProgress() {
        int countUnlocked = countUnlocked();
        if (countUnlocked == 0) {
            return 0;
        }
        return Math.round(100f * countSolved() / countUnlocked);
    }

    public static class Creator {

        private String name;
        private String description;
        private double latitude;
        private double longitude;
        private Image image;
        private City city;

        public Creator() {
        }

        public Creator setName(String name) {
            this.name = name;
            return this;
        }

        public Creator setDescription(String description) {
            this.description = description;
            return this;
        }

        public Creator setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Creator setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Creator setImage(Image image) {
            if (Image.byName(name) == null) {
                image.save();
            }
            this.image = image;
            return this;
        }

        public Creator setCity(City city) {
            if (City.byName(city.name) == null) {
                city.save();
            }
            this.city = city;
            return this;
        }

        public Sight create() {
            Sight sight = new Sight();
            sight.name = name;
            sight.description = description;
            sight.latitude = latitude;
            sight.longitude = longitude;
            sight.solved = false;
            sight.image = image;
            sight.city = city;
            sight.save();
            return sight;
        }
    }

    public Sight() {
        super();
    }

    public ListItemDrawer getListItemDrawer() {
        return solved ? new SightDrawerSolved(this) : new SightDrawerInProgress(this);
    }

    public int compareTo(Sight another) {
        if (this == another) {
            return 0;
        }
        if (solved && !another.solved) {
            return -1;
        }
        if (solved && another.solved) {
            return solvedTime.compareTo(another.solvedTime);
        }
        return name.toLowerCase().compareTo(another.name.toLowerCase());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 47 * hash + (this.city != null ? this.city.hashCode() : 0);
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
        final Sight other = (Sight) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.city != other.city && (this.city == null || !this.city.equals(other.city))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sight: " + name;
    }
}
