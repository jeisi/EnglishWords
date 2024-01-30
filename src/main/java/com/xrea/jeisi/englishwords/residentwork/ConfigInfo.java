/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xrea.jeisi.englishwords.residentwork;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 *
 * @author jeisi
 */
public class ConfigInfo {

    private Path configFile;
    private HashMap<String, Object> map = new HashMap<>();
    
    public ConfigInfo() {
        configFile = Paths.get(System.getProperty("user.home"), ".EnglishWords", "config.yaml");
    }
    
    public static Path getPath(String filename) {
        return Paths.get(System.getProperty("user.home"), ".EnglishWords", filename);
    }
}
