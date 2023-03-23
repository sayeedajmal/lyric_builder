package com.strong.lyric_builder;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ReadController implements Initializable {
    public JFXTextArea readText = new JFXTextArea();
    public Text info = new Text();

    @FXML
    public void Close(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void Save(ActionEvent event) throws IOException {
        String editedWords = readText.getText().toString();
        FileWriter writer = new FileWriter(com.strong.lyric_builder.DashController.LyricFile, false);
        writer.write(editedWords);
        writer.close();
        info.setText("Saved");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (com.strong.lyric_builder.DashController.LyricFile != null) {
            try {
                Scanner input = new Scanner(com.strong.lyric_builder.DashController.LyricFile);
                while (input.hasNextLine()) {
                    String words = input.nextLine().toString();
                    readText.appendText(words);

                }
                input.close();
            } catch (FileNotFoundException e) {
                com.strong.lyric_builder.DashController.Error.setText(e.getLocalizedMessage());
            }
        } else
            com.strong.lyric_builder.DashController.Error.setText("First Choose File");

    }

    private Boolean regex(String words) {
        Pattern pattern = Pattern.compile("[\\d\\d:\\d\\d:\\d\\d]");
        Matcher matcher = pattern.matcher(words);
        if (matcher.matches())
            return true;
        else
            return false;
    }
}
