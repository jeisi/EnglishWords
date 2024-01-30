/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xrea.jeisi.englishwords.residentwork;

import com.xrea.jeisi.englishwords.residentwork.Result;

/**
 *
 * @author jeisi
 */
public class Score {

    private final String english;
    private int numCorrect = 0;
    private int numWrong = 0;
    private Result lastResult = Result.NOT_YET;

    public Score(String english) {
        this.english = english;
    }

    public Score(String[] csv) {
        this.english = csv[0];
        this.numCorrect = Integer.parseInt(csv[1]);
        this.numWrong = Integer.parseInt(csv[2]);
        this.lastResult = Result.valueOf(csv[3]);
    }

    public String[] toCsv() {
        String[] line = new String[4];
        line[0] = english;
        line[1] = Integer.toString(numCorrect);
        line[2] = Integer.toString(numWrong);
        line[3] = lastResult.toString();
        return line;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        buffer.append(english);
        buffer.append(",");
        buffer.append(numCorrect);
        buffer.append(",");
        buffer.append(numWrong);
        buffer.append(",");
        buffer.append(lastResult);
        buffer.append("}");
        return buffer.toString();
    }

    public String getEnglish() {
        return english;
    }
    
    public Result getLastResult() {
        return lastResult;
    }

    public void incCorrect() {
        ++numCorrect;
        lastResult = Result.CORRECT;
    }

    public void incWrong() {
        ++numWrong;
        lastResult = Result.WRONG;
    }

}
