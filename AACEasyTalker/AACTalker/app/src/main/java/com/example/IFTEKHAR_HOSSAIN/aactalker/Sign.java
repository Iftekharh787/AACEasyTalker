package com.example.ashik.aactalker;

import android.graphics.PorterDuff;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Ashik on 08-May-17.
 */
@Table(name="Sign")
public class Sign extends Model{
    @Column(name="letter")
    public String letter;

    @Column(name="image")
    public int image;

    public Sign(String letter, Integer image) {
        this.letter = letter;
        this.image = image;
    }

    public Sign() {
    }
}
