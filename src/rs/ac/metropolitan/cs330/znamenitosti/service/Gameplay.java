package rs.ac.metropolitan.cs330.znamenitosti.service;

import rs.ac.metropolitan.cs330.znamenitosti.model.City;
import rs.ac.metropolitan.cs330.znamenitosti.model.Sight;

/**
 *
 * @author nikola
 */
public enum Gameplay {

    INSTANCE;

    public int unlockPoints() {
        return City.countUnlocked() == 0 ? 1 : (Sight.unlockedProgress() / 50);
    }
}
