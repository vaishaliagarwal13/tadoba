package com.woodpeckers.tadoba;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
/**
 * Created by vaishaliagarwal on 11/06/16.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "commonName",
        "latinName",
        "imageList",
})



public class Bird {



    @JsonProperty("Id")
    private String Id;

    @JsonProperty("commonName")
    private String commonName;

    @JsonProperty("latinName")
    private String latinName;

    private String familyID;


    private String familyName;

    private long   viewedDateTime;

    @JsonProperty("imageList")
    private ArrayList<ImageList> imageList;

    private boolean viewed;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonCreator
    public Bird (){
        imageList = new ArrayList<>();
    }

    public Bird(String family, String commonName, String latinName,  ArrayList<ImageList> imageList) {
        this.commonName = commonName;
        this.latinName = latinName;
        this.familyID = family;
        this.imageList = imageList;
    }

    public Bird(String commonName, String latinName,  ArrayList<ImageList> imageList) {
        this.commonName = commonName;
        this.latinName = latinName;
        //this.family = family;
        this.imageList = imageList;
    }

    @JsonProperty("commonName")
    public String getCommonName() {

        return commonName;
    }

    @JsonProperty("commonName")
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    @JsonProperty("latinName")
    public String getLatinName() {
        return latinName;
    }

    @JsonProperty("latinName")
    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    @JsonProperty("Id")
    public String getId() {
        return Id;
    }

    @JsonProperty("Id")
    public void setId(String id) {
        Id = id;
    }

    public String getFamilyID() {
        return familyID;
    }

    public void setFamilyID(String familyID) {
        this.familyID = familyID;
    }

    public long getViewedDateTime() {
        return viewedDateTime;
    }

    public void setViewedDateTime(long viewedDateTime) {
        this.viewedDateTime = viewedDateTime;
    }

    public String getFamilyName() { return familyName; }

    public void setFamilyName(String familyName) { this.familyName = familyName; }

    @JsonProperty("imageList")
    public void setImageList(ArrayList<ImageList> imageList) {
        this.imageList = imageList;
    }

    @JsonProperty("imageList")
    public ArrayList<ImageList> getImageList() {
        return imageList;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

  /*  public String getImageListAsString() {
        String imageListAsString = new String();
        for (int i = 0; i < imageList.size(); i++) {
            if (i == 0){
                imageListAsString = imageList.get(i).getImage();
            }
            else {
                imageListAsString = imageListAsString + ":" + imageList.get(i).getImage();
            }
        }
        return imageListAsString;
    }*/

  /*  public void mapImageListToArray(String semicolonSeparatedString) {
        String[] stringArray = semicolonSeparatedString.split(":");
        for(int i = 0; i < stringArray.length; i++) {
            ImageList imgObj = new ImageList();
            imgObj.setImage(stringArray[i]);
            imageList.set(i,imgObj);
        }
    }*/

    public static String convertImageListToString(ArrayList<ImageList> images) {
        String imageListAsString = new String();
        for (int i = 0; i < images.size(); i++) {
            if (i == 0){
                imageListAsString = images.get(i).getImage();
            }
            else {
                imageListAsString = imageListAsString + ":" + images.get(i).getImage();
            }
        }
        return imageListAsString;
    }

    public static ArrayList<ImageList> convertStringToImageList(String semicolonSeparatedString) {
        String[] stringArray = semicolonSeparatedString.split(":");
        ArrayList<ImageList> images = new ArrayList<>();
        for(int i = 0; i < stringArray.length; i++) {
            ImageList imgObj = new ImageList();
            imgObj.setImage(stringArray[i]);
            images.add(imgObj);
        }
        return images;
    }

    public static Bird fromCursor(Cursor cursor) {
        Bird bird = new Bird();
        bird.setId(cursor.getString(cursor.getColumnIndex(BirdContract.BirdEntry.COLUMN_ID)));
        bird.setFamilyID(cursor.getString(cursor.getColumnIndex(BirdContract.BirdEntry.COLUMN_FAMILY_KEY)));
        bird.setLatinName(cursor.getString(cursor.getColumnIndex(BirdContract.BirdEntry.COLUMN_LATIN_NAME)));
        bird.setCommonName(cursor.getString(cursor.getColumnIndex(BirdContract.BirdEntry.COLUMN_COMMON_NAME)));
        bird.setImageList(convertStringToImageList(cursor.getString(cursor.getColumnIndex(BirdContract.BirdEntry.COLUMN_BIRD_IMAGES))));
        bird.setViewed(cursor.getInt(cursor.getColumnIndex(BirdContract.BirdEntry.COLUMN_IS_VIEWED)) == 1);
        bird.setViewedDateTime(cursor.getLong(cursor.getColumnIndex(BirdContract.BirdEntry.COLUMN_VIEWED_DATE_TIME)));
        bird.setFamilyName(cursor.getString(cursor.getColumnIndex(BirdContract.BirdFamilyEntry.COLUMN_LATIN_NAME)));

        return bird;
    }
}
