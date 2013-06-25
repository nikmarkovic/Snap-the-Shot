package rs.ac.metropolitan.cs330.znamenitosti.model;

import java.util.Collections;
import java.util.List;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import rs.ac.metropolitan.cs330.znamenitosti.adapter.city.CityDrawerCanBeUnlocked;
import rs.ac.metropolitan.cs330.znamenitosti.adapter.city.CityDrawerComleated;
import rs.ac.metropolitan.cs330.znamenitosti.adapter.city.CityDrawerInProgress;
import rs.ac.metropolitan.cs330.znamenitosti.adapter.city.CityDrawerLocked;
import rs.ac.metropolitan.cs330.znamenitosti.adapter.ListItemDrawer;
import rs.ac.metropolitan.cs330.znamenitosti.service.Gameplay;

/**
 *
 * @author nikola
 */
@Table(name = "City")
public class City extends Model implements Comparable<City> {

    @Column(name = "name", unique = true)
    public String name;
    @Column(name = "unlocked")
    public boolean unlocked;
    @Column(name = "helps")
    public int helps;

    public static City create(String name) {
        City city = new City();
        city.name = name;
        city.unlocked = false;
        city.helps = 0;
        city.save();
        return city;
    }

    public static List<City> all() {
        return new Select()
                .from(City.class)
                .execute();
    }

    public static List<City> unlocked() {
        return new Select()
                .from(City.class)
                .where("unlocked = ?", 1)
                .execute();
    }

    public static City byName(String name) {
        return new Select()
                .from(City.class)
                .where("name = ?", name)
                .executeSingle();
    }

    public static int count() {
        return all().size();
    }

    public static int countUnlocked() {
        return unlocked().size();
    }

    public City() {
        super();
        unlocked = false;
        helps = 0;
    }

    public List<Sight> sights() {
        try {
            return getMany(Sight.class, "city");
        } catch (Exception ex) {
            return Collections.EMPTY_LIST;
        }
    }

    public List<Sight> solvedSights() {
        return new Select()
                .from(Sight.class)
                .where("solved = ? AND city = ?", 1, getId())
                .execute();
    }

    public int countSights() {
        return sights().size();
    }

    public int countSolvedSights() {
        return solvedSights().size();
    }

    public int getProgress() {
        int countSight = countSights();
        if (countSight == 0) {
            return 0;
        }
        return Math.round(100f * countSolvedSights() / countSight);
    }

    public boolean isCompleted() {
        return getProgress() == 100;
    }

    public int remainingHelps() {
        int repainingHelps = countSights() / 4 - helps;
        return repainingHelps < 0 ? 0 : repainingHelps;
    }

    public ListItemDrawer getListItemDrawer() {
        if (!unlocked && Gameplay.INSTANCE.unlockPoints() > 0) {
            return new CityDrawerCanBeUnlocked(this);
        }
        if (!unlocked) {
            return new CityDrawerLocked(this);
        }
        if (isCompleted()) {
            return new CityDrawerComleated(this);
        }
        return new CityDrawerInProgress(this);
    }

    public int compareTo(City another) {
        if (this == another) {
            return 0;
        }
        if (unlocked && !another.unlocked) {
            return -1;
        }
        if (unlocked && another.unlocked && getProgress() != another.getProgress()) {
            return Double.compare(another.getProgress(), getProgress());
        }
        return name.toLowerCase().compareTo(another.name.toLowerCase());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        final City other = (City) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "City: " + name;
    }
}
