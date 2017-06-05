package com.chrisluttazi.holidaysrecipes.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
public class Campaign {

    @Id
    @GeneratedValue
    private Long campaignId;

    // private Long id;

    @Min(-90)
    @Max(90)
    private int minLatitude;

    @Min(-90)
    @Max(90)
    private int maxLatitude;

    @Min(-180)
    @Max(180)
    private int minLongitude;

    @Min(-180)
    @Max(180)
    private int maxLongitude;

    @Length(min = 5, max = 40)
    private String title;

    @Length(min = 5, max = 200)
    private String text;

    public Campaign() {
    }

    public Campaign(Long campaignId, Holiday targetHoliday, int minLatitude, int maxLatitude, int minLongitude,
                    int maxLongitude, String title, String text) {
        this.campaignId = campaignId;
        this.minLatitude = minLatitude;
        this.maxLatitude = maxLatitude;
        this.minLongitude = minLongitude;
        this.maxLongitude = maxLongitude;
        this.title = title;
        this.text = text;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public int getMinLatitude() {
        return minLatitude;
    }

    public void setMinLatitude(int minLatitude) {
        this.minLatitude = minLatitude;
    }

    public int getMaxLatitude() {
        return maxLatitude;
    }

    public void setMaxLatitude(int maxLatitude) {
        this.maxLatitude = maxLatitude;
    }

    public int getMinLongitude() {
        return minLongitude;
    }

    public void setMinLongitude(int minLongitude) {
        this.minLongitude = minLongitude;
    }

    public int getMaxLongitude() {
        return maxLongitude;
    }

    public void setMaxLongitude(int maxLongitude) {
        this.maxLongitude = maxLongitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean checkPoint(float latitude, float longitude) {
        boolean checkLatitude = (latitude > minLatitude && latitude < maxLatitude);
        boolean checkLongitude = (longitude > minLongitude && longitude < maxLongitude);

        return checkLatitude && checkLongitude;
    }

    @Override
    public String toString() {
        return "<h3>" + title + "</h3>" + "<p>" + text + "</p>";
    }

    // public Long assignId() {
    // this.campaignId = idSequence.incrementAndGet();
    // this.id = campaignId;
    // return campaignId;
    // }

    // public Long getId() {
    // return id;
    // }
    //
    // public void setId(Long id) {
    // this.id = id;
    // }
    //
    // private static final AtomicLong idSequence = new AtomicLong();

}
