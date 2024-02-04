/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xrea.jeisi.englishwords;

import com.xrea.jeisi.englishwords.residentwork.Score;
import com.xrea.jeisi.englishwords.residentwork.ResidentWork;
import com.xrea.jeisi.englishwords.residentwork.Result;
import com.xrea.jeisi.englishwords.residentwork.Words;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;

/**
 *
 * @author jeisi
 */
public class EnglishToJapanesePane {

    private final int NUM_QUESTIONS = 6;
    private final int NUM_NOT_YET_QUESTION = 5;
    private final int NUM_LAST_WRONG_QUESTION = 5;
    private final int NUM_CHOICE = 4;       // 答えの選択肢数
    private final String IDENTIFIER = "english_to_japanese";

    private final Words words;
    private List<Word> questions;
    private TextField questionTextField;
    private VBox resultPane;
    private Parent mainPane;
    private Label resultLabel;
    private final Button answerButtons[] = new Button[NUM_CHOICE];
    private int m_questionIndex = -1;
    private final TestScore testScore;
    private final List<ActionListener> listeners = new ArrayList<>();
    private Button voiceButton;
    //private Word currentQuestion;

    public EnglishToJapanesePane(ResidentWork residentWork) {
        this.words = residentWork.getWords();
        this.testScore = residentWork.getTestScore(IDENTIFIER);
    }

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    private void checkAnswer(Object obj) {
        Button button = (Button) obj;

        resultPane.setVisible(true);
        mainPane.setDisable(true);

        if (button.getId().equals(questionTextField.getId())) {
            // 正解
            button.setStyle("-fx-base: #00ff00");
            resultPane.setStyle("-fx-background-color: #00ff00");
            resultLabel.setText("正解！");
            if (m_questionIndex < NUM_QUESTIONS) {
                testScore.correct(questionTextField.getId());
            }
        } else {
            // 間違い
            button.setStyle("-fx-base: #ff0000");
            resultPane.setStyle("-fx-background-color: #ff0000");
            resultLabel.setText("間違い");
            testScore.wrong(questionTextField.getId());

            for (int i = 0; i < NUM_CHOICE; ++i) {
                if (answerButtons[i].getId().equals(questionTextField.getId())) {
                    answerButtons[i].setStyle("-fx-base: #00ff00");
                }
            }

            questions.add(questions.get(m_questionIndex));
        }
    }

    private void finish() {
        for (ActionListener listener : listeners) {
            listener.finish();
        }
        System.out.println("\nResults:");
        testScore.print();
        testScore.save(IDENTIFIER);
    }

    public void nextQuestion() {
        ++m_questionIndex;
        if (m_questionIndex >= questions.size()) {
            finish();
            return;
        }

        resultPane.setVisible(false);
        resultPane.setStyle("");
        mainPane.setDisable(false);
        for (int i = 0; i < NUM_CHOICE; ++i) {
            answerButtons[i].setStyle("");
        }

        question(m_questionIndex);
        answerButtons[0].requestFocus();
    }

    private void question(int questionIndex) {
        var word = questions.get(questionIndex);
        //currentQuestion = word;

        HashSet<Word> answers = new HashSet<>();
        answers.add(word);
        Random rand = new Random();
        while (answers.size() < NUM_CHOICE) {
            int r = rand.nextInt(words.size());
            var answerWord = words.get(r);
            answers.add(answerWord);
        }

        questionTextField.setText(word.getEnglish());
        questionTextField.setId(word.getEnglish());
        int i = 0;
        for (Word choice : answers) {
            answerButtons[i].setText(choice.getJapanese());
            answerButtons[i].setId(choice.getEnglish());
            ++i;
        }

        if (word.getMp3().isEmpty()) {
            voiceButton.setDisable(true);
        } else {
            voiceButton.setDisable(false);
            voiceButton.setId(word.getMp3());
            pronounce();
        }
    }

    private void pronounce() {
        String mp3file = "mp3/" + voiceButton.getId();
        URL resource = getClass().getResource(mp3file);
        if (resource != null) {
            AudioClip audio = new AudioClip(resource.toString());
            audio.play();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "ERROR", ButtonType.CLOSE);
            alert.setContentText(mp3file + "が見つかりませんでした。\n（" + questionTextField.getText() + " 用の .mp3）");
            alert.showAndWait();
        }
    }

    public void makeQuestions() {
        if (testScore.isEmpty()) {
            testScore.load(IDENTIFIER);
            testScore.addWords(words);
        }
        testScore.print();

        HashSet<Word> q = new HashSet<>();

        List<Score> notYetList = testScore.extract(e -> e.getLastResult() == Result.NOT_YET);
        int i = 0;
        while (q.size() < NUM_QUESTIONS && i < notYetList.size() && i < NUM_NOT_YET_QUESTION) {
            String english = notYetList.get(i).getEnglish();
            q.add(words.getByEnglish(english));
            ++i;
        }

        List<Score> wrongList = testScore.extract(e -> e.getLastResult() == Result.WRONG);
        i = 0;
        while (q.size() < NUM_QUESTIONS && i < wrongList.size() && i < NUM_LAST_WRONG_QUESTION) {
            String english = wrongList.get(i).getEnglish();
            q.add(words.getByEnglish(english));
            ++i;
        }

        while (q.size() < NUM_QUESTIONS) {
            q.add(words.getByRandom());
        }

        System.out.println("\nQuestions:");
        q.stream().forEach(e -> System.out.println(e.getEnglish()));

        if (q.size() != NUM_QUESTIONS) {
            throw new RuntimeException("問題が" + q.size() + "問しか作れませんでした。");
        }
        questions = new ArrayList<>();
        questions.addAll(q);
    }

    public Parent build() {
        var questionLabel = new Label("問題:");

        questionTextField = new TextField("教室");
        questionTextField.setEditable(false);

        voiceButton = new Button("♪");
        voiceButton.setOnAction(eh -> pronounce());

        HBox hbox = new HBox(questionTextField, voiceButton);
        hbox.setSpacing(5);

        var answerLabel = new Label("答え:");

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        for (int i = 0; i < NUM_CHOICE; ++i) {
            Button answerButton = new Button("classroom");
            answerButton.setMaxWidth(Double.MAX_VALUE);
            answerButton.setOnAction((e) -> checkAnswer(e.getSource()));
            answerButtons[i] = answerButton;
            vbox.getChildren().add(answerButton);
        }

        // 1行目
        GridPane.setConstraints(questionLabel, 0, 0);
        GridPane.setConstraints(hbox, 1, 0);

        // 2行目
        GridPane.setConstraints(answerLabel, 0, 1);
        GridPane.setConstraints(vbox, 1, 1);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5));
        gridPane.setHgap(10);
        gridPane.setVgap(12);
        gridPane.getChildren().addAll(questionLabel, hbox, answerLabel, vbox);
        mainPane = gridPane;

        resultLabel = new Label("正解");
        resultLabel.setPadding(new Insets(10));
        resultLabel.setStyle("-fx-font-size: 24px");
        Button nextButton = new Button("次へ");
        nextButton.setOnAction(eh -> nextQuestion());
        resultPane = new VBox(resultLabel, nextButton);
        resultPane.setPadding(new Insets(5));
        resultPane.setAlignment(Pos.CENTER);
        resultPane.setVisible(false);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        borderPane.setBottom(resultPane);
        return borderPane;
    }
}
