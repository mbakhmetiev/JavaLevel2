package Lesson4.chat.sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {
    @FXML
    TextArea textArea;
    @FXML
    TextField textField;
    @FXML
    public void textAction(KeyEvent e){
    if(e.getCode().equals(KeyCode.ENTER))
        sendMsg();
    }
    @FXML
    public void sendMsg () {
            textArea.appendText(textField.getText() + "\n");
            textField.clear();
            textField.requestFocus();
    }
}
