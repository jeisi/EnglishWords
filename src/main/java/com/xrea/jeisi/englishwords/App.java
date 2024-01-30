package com.xrea.jeisi.englishwords;

import com.xrea.jeisi.englishwords.residentwork.ResidentWork;
import com.xrea.jeisi.englishwords.residentwork.Words;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application implements ActionListener {

    private ResidentWork residentWork;
    private EnglishToJapanesePane englishToJapanesePane;
    private final FinishPane finishPane;
    private Stage stage;

    private final int PAGE_ENGLISH_TO_JAPANESE = 0;
    private final int PAGE_FINISH = 1;
    private int pageIndex = 0;

    //private List<Word> words = new ArrayList<>();

    public App() {
        residentWork = new ResidentWork();
        
        this.finishPane = new FinishPane();
        this.finishPane.addActionListener(this);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        loadWords();

        var pane = createPage(PAGE_ENGLISH_TO_JAPANESE);
        var scene = new Scene(pane, 640, 480);
        stage.setScene(scene);
        stage.show();

        try {
            englishToJapanesePane.makeQuestions();
        } catch(Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "エラー", ButtonType.CLOSE);
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
            stage.close();
            return;
        }
        englishToJapanesePane.nextQuestion();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void finish() {
        ++pageIndex;
        changePage(pageIndex);
    }

    @Override
    public void retry() {
        pageIndex = 0;
        changePage(pageIndex);

        englishToJapanesePane.makeQuestions();
        englishToJapanesePane.nextQuestion();
    }

    private void changePage(int page) {
        stage.getScene().setRoot(createPage(page));
    }

    private Parent createPage(int page) {
        switch (page) {
            case PAGE_ENGLISH_TO_JAPANESE:
                this.englishToJapanesePane = new EnglishToJapanesePane(residentWork);
                this.englishToJapanesePane.addActionListener(this);
                return englishToJapanesePane.build();
            case PAGE_FINISH:
                return finishPane.build();
            default:
                throw new RuntimeException("定義外のページです。");
        }
    }

    private void loadWords() {
        Words words = residentWork.getWords();
        words.load();
    }

}
