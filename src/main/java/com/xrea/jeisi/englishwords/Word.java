/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xrea.jeisi.englishwords;

/**
 *
 * @author jeisi
 */
public class Word {
    private final String english;
    private final String japanese;
    private final String mp3;
    
    public Word(String english, String japanese) {
        this(english, japanese, "");
    }
    
    public Word(String english, String japanese, String mp3) {
        this.english = english;
        this.japanese = japanese;
        this.mp3 = mp3;
    }
    
    public Word(String[] csv) {
        this(csv[0], csv[1], csv[2]);
    }
    
    public String getEnglish() {
        return english;
    }
    
    public String getJapanese() {
        return japanese;
    }
    
    public String getMp3() {
        return mp3;
    }
}
