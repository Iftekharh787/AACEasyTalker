package com.example.ashik.aactalker;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Ashik on 08-May-17.
 */
@Table(name="Letter")
public class Letter extends Model{
    @Column(name="letters")
    public String letters;

    @Column(name="img")
    public int img;

    public Letter(String letters, Integer img) {
        this.letters = letters;
        this.img = img;
    }

    public Letter() {
    }
}
