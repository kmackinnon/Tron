package database;

import java.util.BitSet;

/**
 * Development class for generating Maps files in Binary format.
 *
 * @author Michael Williams
 */
public class MapGenerator {

    private static final DatabaseInterface db = new SQLiteInterface(".lightracer.db");
    private BitSet basic1;
    private BitSet basic2;
    private BitSet basic3;

    public MapGenerator() {
    }

    /**
     * Default size of the map.
     */
    public void makeMap1() {
        basic1 = new BitSet(75 * 50);
    }

    /**
     * Save the default map in the database.
     */
    public void saveMap1() {
        db.addMap("BasicMap1", basic1.toByteArray(), 75, 50);
    }

    /**
     * Makes the second map with two obstacles.
     */
    public void makeMap2() {
        basic2 = new BitSet(75 * 50);
        for (int i = 15; i < 25; i++) {
            for (int j = 20; j < 30; j++) {
                basic2.set(i - 1 + (j - 1) * 75);
            }
        }
        for (int i = 50; i < 60; i++) {
            for (int j = 20; j < 30; j++) {
                basic2.set(i - 1 + (j - 1) * 75);
            }
        }
    }

    /**
     * Saves the second map in the database.
     */
    public void saveMap2() {
        db.addMap("BasicMap2", basic2.toByteArray(), 75, 50);
    }

    /**
     * Makes the third (hardest) map in the database with three obstacles.
     */
    public void makeMap3() {
        basic3 = new BitSet(75 * 50);
        for (int i = 30; i < 45; i++) {
            for (int j = 20; j < 30; j++) {
                basic3.set(i - 1 + (j - 1) * 75);
            }
        }
        for (int i = 5; i < 25; i++) {
            for (int j = 25; j < 45; j++) {
                basic3.set(i - 1 + (j - 1) * 75);
            }
        }
        for (int i = 50; i < 70; i++) {
            for (int j = 5; j < 25; j++) {
                basic3.set(i - 1 + (j - 1) * 75);
            }
        }
    }

    /**
     * Saves the third map to the database.
     */
    public void saveMap3() {
        db.addMap("BasicMap3", basic3.toByteArray(), 75, 50);
    }

    /**
     * Sets up all three maps and saves them all at once.
     */
    public void makeAllAndInstall() {
        makeMap1();
        saveMap1();
        makeMap2();
        saveMap2();
        makeMap3();
        saveMap3();
    }
}
