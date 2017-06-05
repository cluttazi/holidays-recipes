package com.chrisluttazi.holidaysrecipes.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue
    public Long recipeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "holiday")
    private Holiday holiday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cheffBook")
    private CheffBook cheffBook;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe")
    private List<Instruction> instruction = new ArrayList<Instruction>();

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipeId")
    private Recipe previous = null;

    @NotEmpty
    @Length(min = 5, max = 40)
    private String title;

    @Min(-90)
    @Max(90)
    private float latitude;

    @Min(-180)
    @Max(180)
    private float longitude;

    private boolean published = true;

    @Min(0)
    private float version;

    @Min(0)
    @Max(5)
    private int ranking;

    @Transient
    private MultipartFile photo;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] photoBytes;

    private String photoContentType;

    private String photoFilename;

    public Recipe() {
    }

//	public Recipe(List<Instruction> instruction, Recipe recipe, String title, float latitude, float longitude,
//			Holiday holiday, boolean published, float version, int ranking) {
//		this.instruction = instruction;
//		this.title = title;
//		this.latitude = latitude;
//		this.longitude = longitude;
//		this.holiday = holiday;
//		this.published = published;
//		this.version = version;
//		this.ranking = ranking;
//	}

    public Recipe(Recipe recipe) {
        this.cheffBook = recipe.getCheffBook();
        this.instruction = recipe.getInstruction();
        this.title = recipe.getTitle();
        this.latitude = recipe.getLatitude();
        this.longitude = recipe.getLongitude();
        this.holiday = recipe.getHoliday();
        this.published = recipe.isPublished();
        this.version = recipe.getVersion() + 1.0F;
        this.ranking = recipe.getRanking();
        this.previous = recipe;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Holiday getHoliday() {
        return holiday;
    }

    public void setHoliday(Holiday holiday) {
        this.holiday = holiday;
    }

    public CheffBook getCheffBook() {
        return cheffBook;
    }

    public void setCheffBook(CheffBook cheffBook) {
        this.cheffBook = cheffBook;
    }

    public List<Instruction> getInstruction() {
        return instruction;
    }

    public void setInstruction(List<Instruction> instruction) {
        this.instruction = instruction;
    }

    public Recipe getPrevious() {
        return previous;
    }

    public void setPrevious(Recipe previous) {
        this.previous = previous;
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public float getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;

        setPhotoContentType(photo.getContentType());
        setPhotoFilename(photo.getOriginalFilename());
        try {
            setPhotoBytes(photo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getPhotoFilename() {
        return photoFilename;
    }

    public void setPhotoFilename(String photoFilename) {
        this.photoFilename = photoFilename;
    }

    public byte[] getPhotoBytes() {
        return photoBytes;
    }

    public void setPhotoBytes(byte[] photoBytes) {
        this.photoBytes = photoBytes;
    }

    @Override
    public String toString() {
        return title;
    }

}
