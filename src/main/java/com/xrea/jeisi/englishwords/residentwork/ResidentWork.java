/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xrea.jeisi.englishwords.residentwork;

import com.xrea.jeisi.englishwords.TestScore;
import com.xrea.jeisi.englishwords.Word;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author jeisi
 */
public class ResidentWork {
    private TreeMap<String,TestScore> scores = new TreeMap<>();
    private Words words = new Words();
    
    public TestScore getTestScore(String key) {
        TestScore testScore = scores.get(key);
        if(testScore == null) {
            testScore = new TestScore();
            scores.put(key, testScore);
        }
        return testScore;
    }
    
    public Words getWords() {
        return words;
    }
}
