package com.chrisluttazi.holidaysrecipes.auxiliary;

public class jsonMarker {
    private String title;
    private float latitude;
    private float longitude;
    private String holiday;
    private String id;

    public jsonMarker(String title, float latitude, float longitude, String holiday, String id) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.holiday = holiday;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
