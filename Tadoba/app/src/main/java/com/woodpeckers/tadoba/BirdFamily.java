package com.woodpeckers.tadoba;

/**
 * Created by vaishaliagarwal on 10/06/16.
 */
public class BirdFamily {
    private String name;
    private String image;

    public BirdFamily(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {

        return name;
    }

    public String getImage() {
        return image;
    }
}
