package com.woodpeckers.tadoba;

import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by vaishaliagarwal on 10/06/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
//@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "latinName",
        "commonName",
        "familyImage",
        "birdList"
})

public class BirdFamily {

    @JsonProperty("commonName")
    private String commonName;

    @JsonProperty("latinName")
    private String latinName;

    @JsonProperty("familyImage")
    private String familyImage;

    @JsonProperty("birdList")
    private ArrayList<Bird> birdList;

    @JsonProperty("familyID")
    private String familyID;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonCreator
    public BirdFamily (){
    }

    public BirdFamily(String latinName,  String commonName, String familyImage, ArrayList<Bird> birdList) {
        this.commonName = commonName;
        this.latinName = latinName;
        this.familyImage = familyImage;
        this.birdList = birdList;
    }

    @JsonProperty("familyImage")
    public void setFamilyImage(String image) {
        this.familyImage = image;
    }

    @JsonProperty("familyImage")
    public String getFamilyImage() {
        return familyImage;
    }

    @JsonProperty("latinName")
    public String getLatinName() {
        return latinName;
    }

    @JsonProperty("latinName")
    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    @JsonProperty("commonName")
    public String getCommonName() {
        return commonName;
    }

    @JsonProperty("commonName")
    public void setCommonName(String commonName) {

        this.commonName = commonName;
    }

    @JsonProperty("birdList")
    public ArrayList<Bird> getBirdList() {
        return birdList;
    }

    @JsonProperty("birdList")
    public void setBirdList(ArrayList<Bird> birdList) {
        this.birdList = birdList;
    }

    @JsonProperty("familyID")
    public String getFamilyID() {
        return familyID;
    }

    @JsonProperty("familyID")
    public void setFamilyID(String familyID) {
        this.familyID = familyID;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public static BirdFamily fromCursor(Cursor cursor) {
        BirdFamily birdFamily = new BirdFamily();
        birdFamily.setFamilyID(cursor.getString(cursor.getColumnIndex(BirdContract.BirdFamilyEntry.COLUMN_ID)));
        birdFamily.setLatinName(cursor.getString(cursor.getColumnIndex(BirdContract.BirdFamilyEntry.COLUMN_LATIN_NAME)));
        birdFamily.setCommonName(cursor.getString(cursor.getColumnIndex(BirdContract.BirdFamilyEntry.COLUMN_COMMON_NAME)));
        birdFamily.setFamilyImage(cursor.getString(cursor.getColumnIndex(BirdContract.BirdFamilyEntry.COLUMN_FAMILY_PIC)));
        return birdFamily;
    }
}
