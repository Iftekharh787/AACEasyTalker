package com.example.ashik.aactalker;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Ashik on 11-Apr-17.
 *
 */
@Table(name="Word")
public class Word extends Model {
    @Column(name="parentName")
    public String parent;

    @Column(name="wordName")
    public String word;

    public Word(String parent, String word) {
        this.parent = parent;
        this.word = word;
    }

    public Word() {
    }
}
