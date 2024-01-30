/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xrea.jeisi.englishwords;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author jeisi
 */
public class FinishPane {

    private final List<ActionListener> listeners = new ArrayList<>();

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public Parent build() {
        Label label = new Label("終了!!");

        Button retryButton = new Button("リトライ");
        retryButton.setOnAction(eh -> retry());

        VBox vbox = new VBox(label, retryButton);
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

    private void retry() {
        for (ActionListener listener : listeners) {
            listener.retry();
        }
    }
}
