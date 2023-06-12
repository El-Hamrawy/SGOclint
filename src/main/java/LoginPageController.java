import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController {
    private PasswordField Password;

    @FXML
    private Button Signup;

    @FXML
    private TextField UserName;

    @FXML
    private Button login;

    @FXML
    private Button Exit;

    @FXML
    private Label erroLabel;

    @FXML
    void EnterPassword(ActionEvent event) {

    }

    @FXML
    void ExitButton(ActionEvent event){
        Platform.exit();
    }


    public void handleLogin(ActionEvent actionEvent) {
    }

    public void handleSignup(ActionEvent actionEvent) {
    }
}
