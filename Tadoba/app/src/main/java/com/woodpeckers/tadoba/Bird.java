package com.woodpeckers.tadoba;

/**
 * Created by vaishaliagarwal on 11/06/16.
 */
public class Bird {
    private String commonName;
    private String latinName;
    private String family;
    private String image;

    public Bird (){
    }
    public Bird(String family, String commonName, String latinName, String image) {
        this.family = family;
        this.commonName = commonName;
        this.latinName = latinName;
        this.image = image;
    }

    public String getCommonName() {

        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
