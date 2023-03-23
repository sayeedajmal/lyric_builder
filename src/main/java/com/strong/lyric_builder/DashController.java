package com.strong.lyric_builder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;

public class DashController{

    Stage stage = new Stage();
    public JFXTextArea lyrics = new JFXTextArea();
    public TextField minute = new TextField();
    public TextField second = new TextField();
    public TextField mill = new TextField();
    public TextField symbol = new TextField();
    public static Text Error = new Text();
    public Text FileName = new Text();
    public JFXButton OpenFile = new JFXButton();

    static File LyricFile;

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
        fileChooser.setTitle("Choose File For Lyrics");
        FileChooser.ExtensionFilter extensionFilter = new ExtensionFilter("TXT files (*.lrc)", "*lrc");
        fileChooser.getExtensionFilters().add(extensionFilter);
        LyricFile = fileChooser.showOpenDialog(stage);

        if (LyricFile != null) {
            FileName.setText(LyricFile.getName());
        }

        System.out.println(LyricFile.getAbsolutePath());
    }

    @FXML
    public void Read(ActionEvent event) throws Exception {
        Stage stage=new Stage();
        stage.setTitle("Read Lyrics");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Read.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/style.css");
        Image icon = new Image("/images/icon.png");
        stage.getIcons().add(icon);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private Boolean lengthCheck() {
        if (lyrics.getText() == null) {
            return false;
        } else if (lyrics.getText().isEmpty())
            return false;
        else
            return true;
    }
}
