/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xrea.jeisi.englishwords.residentwork;

import com.xrea.jeisi.englishwords.Word;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jeisi
 */
public class Words {

    private List<Word> words = new ArrayList<>();
    private Random random = new Random();
    
    public int size() {
        return words.size();
    }
    
    public Word get(int index) {
        return words.get(index);
    }
    
    public Word getByEnglish(String english) {
        for(Word word : words) {
            if(word.getEnglish().equals(english)) {
                return word;
            }
        }
        throw new NoSuchFieldError(english + "は見つかりませんでした。");
    }
    
    public Word getByRandom() {
        int r = random.nextInt(words.size());
        return words.get(r);
    }
    
    public List<Word> getList() {
        return words;
    }
    
    public void load() {
        words.add(new Word("gym", "体育館", "gym.mp3"));
        words.add(new Word("playground", "運動場", "playground.mp3"));
        words.add(new Word("classroom", "教室", "classroom.mp3"));
        words.add(new Word("science room", "理科室", "science_room.mp3"));
        words.add(new Word("cooking room", "調理室", "cooking_room.mp3"));
        words.add(new Word("library", "図書室", "library.mp3"));
        
        words.add(new Word("breakfast", "朝食", "breakfast.mp3"));
        words.add(new Word("lunch", "昼食", "lunch.mp3"));
        words.add(new Word("dinner", "夕食", "dinner.mp3"));
        //words.add(new Word("", "", ""));
    }
}
