package com.woodpeckers.tadoba;

/**
 * Created by vaishaliagarwal on 11/06/16.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

class Constants {
    public static final String FAMILY1 = "Threskiornithidae";
    public static final String FAMILY2 = "Cuculidae";
    public static final String FAMILY3 = "Falconidae";
    public static final String FAMILY4 = "Paridae";
    public static final String FAMILY5 = "Phasianidae";
    public static final String FAMILY6 = "Coraciidae";
    public static final String FAMILY7 = "Muscicapidae";
    public static final String FAMILY8 = "Nectariniidae";
    public static final String FAMILY9 = "Glareolidae";
    public static final String FAMILY10 = "Scolopacidae";
}

public class DummyBirdFactory {

    private static List<BirdFamily> birdFamilyList;
    private static HashMap<String, Bird> birdList;

    static {
        birdFamilyList = new ArrayList<BirdFamily>();
        birdFamilyList.add(new BirdFamily(Constants.FAMILY1, "1"));
        birdFamilyList.add(new BirdFamily(Constants.FAMILY2, "2"));
        birdFamilyList.add(new BirdFamily(Constants.FAMILY3, "3"));
        birdFamilyList.add(new BirdFamily(Constants.FAMILY4, "4"));
        birdFamilyList.add(new BirdFamily(Constants.FAMILY5, "5"));
        birdFamilyList.add(new BirdFamily(Constants.FAMILY6, "6"));
        birdFamilyList.add(new BirdFamily(Constants.FAMILY7, "7"));
        birdFamilyList.add(new BirdFamily(Constants.FAMILY8, "8"));
        birdFamilyList.add(new BirdFamily(Constants.FAMILY9, "9"));
        birdFamilyList.add(new BirdFamily(Constants.FAMILY10, "10"));

        birdList = new HashMap<String, Bird>();
        birdList.put(Constants.FAMILY1,new Bird(Constants.FAMILY1, "Black Headed Ibis", "Threskiornis melanocephalus", "1"));
        birdList.put(Constants.FAMILY2,new Bird(Constants.FAMILY2, "Common Hawk-Cuckoo","Hierococcyx varius", "2"));
        birdList.put(Constants.FAMILY3,new Bird(Constants.FAMILY3, "Common Kestrel", "Falco tinnunculus", "3"));
        birdList.put(Constants.FAMILY4,new Bird(Constants.FAMILY4, "Great Tit", "Parus major", "4"));
        birdList.put(Constants.FAMILY5,new Bird(Constants.FAMILY5, "Grey Junglefowl", "Gallus sonneratii","5"));
        birdList.put(Constants.FAMILY6,new Bird(Constants.FAMILY6, "Indian Roller","Coracias benghalensis", "6"));
        birdList.put(Constants.FAMILY7,new Bird(Constants.FAMILY7, "Oriental Magpie -Robin","Copsychus saularis", "7"));
        birdList.put(Constants.FAMILY8,new Bird(Constants.FAMILY8, "Purple rumped Sunbird","Leptocoma zeylonica", "8"));
        birdList.put(Constants.FAMILY9,new Bird(Constants.FAMILY9, "Small Pratincole","Glareola lactea", "9"));
        birdList.put(Constants.FAMILY10,new Bird(Constants.FAMILY10, "Wood Sandpiper","Tringa glareola","10"));
    }

    public DummyBirdFactory() {

    }

    public List<BirdFamily> getBirdFamilies(){
        return birdFamilyList;
    }

    public ArrayList<Bird> getAllBirdsInFamily(String family){

        ArrayList<Bird> allBirdsOfFamily = new ArrayList<Bird>();

        /*boolean found = birdList.containsKey(family);
        if (found) {
            for (HashMap.Entry<String,Bird> entry : birdList.entrySet()) {
                String key = entry.getKey();
                if (key.compareTo(family) == 0) {
                    Bird bird = new Bird();
                    bird = entry.getValue();
                    allBirdsOfFamily.add(bird);
                }
            }
        }*/

        for (HashMap.Entry<String,Bird> entry : birdList.entrySet()) {
            String key = entry.getKey();
                Bird bird = new Bird();
                bird = entry.getValue();
                allBirdsOfFamily.add(bird);

        }

        return allBirdsOfFamily;
    }
}
