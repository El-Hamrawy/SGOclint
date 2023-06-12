package com.example.sgoclint;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ClientController {
    private Socket socket;
    private DataInputStream dataInputStream;
    private PrintStream printStream ;
    public String UserName ;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    @FXML
    private TextArea textArea;

    @FXML
    private Button LogoutButton;

    @FXML
    private Button SaveButton;

    @FXML
    private RadioButton AvailableButton;

    @FXML
    private RadioButton BusyButton ;

    public void EnterMessage(ActionEvent actionEvent) {
    }
    void changeStatus(ActionEvent event) throws SQLException {
        ChatServer.status(UserName, "Online");
        BusyButton.setSelected(false);
    }
    void Busy(ActionEvent event) throws SQLException {
        ChatServer.status(UserName, "Busy");
        AvailableButton.setSelected(false);
    }

    public void SendMessage(ActionEvent actionEvent) {
        String message = messageField.getText();
        printStream.println( UserName + " : " + message);
        messageField.clear();
    }

    public void SaveChat(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files" , "*.txt")
        );
        Stage primaryStage = new Stage();
        File SlectedFile = fileChooser.showSaveDialog(primaryStage);
        if (SlectedFile != null){
            try {
                FileOutputStream output = new FileOutputStream(SlectedFile);
                DataOutputStream data = new DataOutputStream(output);
                data.writeUTF(textArea.getText());
                data.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void Logout(ActionEvent actionEvent) throws IOException,SQLException {

        ChatServer.status(UserName, "Offline");
        Stage stage = (Stage) LogoutButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeStatus(ActionEvent actionEvent)throws SQLException {
        ChatServer.status(UserName, "Online");
        BusyButton.setSelected(false);
    }

    public ClietnController( ){
        try {
            socket = new Socket("127.0.0.1",90);
            dataInputStream=new DataInputStream(socket.getInputStream());
            printStream=new PrintStream(socket.getOutputStream());
            RecieveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void RecieveMessage(){
        new Thread(new Runnable() {
            public void run(){
                String messageFromChat ;
                while (socket.isConnected()){
                    try {
                        messageFromChat = dataInputStream.readLine();
                        textArea.setText(textArea.getText()+"\n"+messageFromChat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
