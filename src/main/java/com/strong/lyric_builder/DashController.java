package com.strong.lyric_builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class DashController implements Initializable {

    Stage primaryStage = new Stage();
    public JFXTextArea lyrics = new JFXTextArea();
    public TextField minute = new TextField();
    public TextField second = new TextField();
    public TextField mill = new TextField();
    public TextField symbol = new TextField();
    public Text Error = new Text();
    public Text FileName = new Text();
    public JFXButton OpenFile = new JFXButton();

    File LyricFile;

    @FXML
    public void Close(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void Add(ActionEvent event) {
        if (lengthCheck()) {
            String Lyrics = "[" + minute.getText() + ":" + second.getText() + "." + mill.getText() + "]"
                    + lyrics.getText()
                    + " " + symbol.getText();

            if (LyricFile != null) {
                FileWrite(Lyrics, LyricFile);
            } else {
                Error.setText("Choose A File");
                OpenFile.requestFocus();
                return;
            }

            /* Setting LyricsField To Null */
            lyrics.setText(null);
            Error.setText("Lyrics Saved.");
            Error.setFill(Paint.valueOf("Green"));

        } else {
            Error.setText("Type Some Lyrics....");
            lyrics.requestFocus();
            return;
        }
    }

    private void FileWrite(String Lyrics, File LyricFile) {
        try {
            FileWriter fileWriter = new FileWriter(LyricFile, true);
            fileWriter.append("\n" + Lyrics);
            fileWriter.close();
        } catch (IOException e) {
            Error.setText(e.getLocalizedMessage());
            OpenFile.requestFocus();
            return;
        }

    }

    @FXML
    public void OpenFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose The File For Lyrics");
        FileChooser.ExtensionFilter extensionFilter = new ExtensionFilter("TXT files (*.lrc)", "*lrc");
        fileChooser.getExtensionFilters().add(extensionFilter);
        LyricFile = fileChooser.showOpenDialog(primaryStage);

        if (LyricFile != null) {
            FileName.setText(LyricFile.getName());
        }

        System.out.println(LyricFile.getAbsolutePath());
    }

    @FXML
    public void Read(ActionEvent event) throws FileNotFoundException {
        Scanner input = new Scanner(LyricFile);
        while (input.hasNextLine()) {
            System.out.println(input.nextLine());
        }
        input.close();
    }

    private Boolean lengthCheck() {
        if (lyrics.getText() == null) {
            return false;
        } else if (lyrics.getText().isEmpty())
            return false;
        else
            return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
