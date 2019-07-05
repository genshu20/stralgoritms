package j2lesson6.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public TextField textFild;
    @FXML
    public TextArea textArea;
    @FXML
    public Button button;

    public void sendMsg(ActionEvent actionEvent) {
        try {
            out.writeUTF(textFild.getText());
            textFild.setText("");
            textFild.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    Socket socket;
    DataOutputStream out;
    DataInputStream in;

    final String IP_ADRESS="localhost";
    final int PORT=8189;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket=new Socket(IP_ADRESS,PORT);
            in=new DataInputStream(socket.getInputStream());
            out=new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    while (true){
                            String str=in.readUTF();
                            if(str.equals("/end")){
                                System.out.println("client is disconnect");
                                break;
                            }
                            textArea.appendText(str+"\n");
                        }
                    } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                        try{socket.close();}catch (IOException e){e.printStackTrace();}
                    }

                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
