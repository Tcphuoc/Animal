package com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Others;

import android.graphics.Bitmap;

public class Animal {
    private final String name;
    private final String content;
    private final Bitmap icon;
    private final Bitmap photo;
    private boolean isFav;
    private final String animalType;
    private String phone;
    private final String link;

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public boolean isFav() {
        return isFav;
    }

    public Animal(String name, String content, Bitmap icon, Bitmap photo, boolean isFav, String animalType, String link) {
        this.name = name;
        this.content = content;
        this.icon = icon;
        this.photo = photo;
        this.isFav = isFav;
        this.animalType = animalType;
        this.phone = null;
        this.link = link;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getAnimalType() {
        return animalType;
    }

    public String getPhone() {
        return phone;
    }

    public String getLink() {
        return link;
    }
}
