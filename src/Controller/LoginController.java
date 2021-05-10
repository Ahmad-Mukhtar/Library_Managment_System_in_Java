package Controller;

import Classes.Admin;
import Classes.User;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class LoginController
{
    //Fields ids
    @FXML
    private TextField Userfield;
    @FXML
    private PasswordField Passwordfield;
    @FXML
    private Label Errorlabel;

    @FXML private Circle circle1;

    @FXML private Circle circle2;

    @FXML private Circle circle3;

    @FXML private Label Loadinglabel;

    @FXML private Hyperlink Loginadmin;

    @FXML private Hyperlink Registerlink;

    @FXML private Label NotRegisteredLabel;

    @FXML private BorderPane LoginFrame;


    //When User Clicks Login Click Validate the fields
    public void onLoginClick() throws SQLException {

        Errorlabel.setVisible(false);

        if(Userfield.getPromptText().equals("Username"))
        {
            User user=new User();

            if(user.validateLogin(Userfield.getText(),Passwordfield.getText()))
            {
                playTransition(true);
            }
            else
            {
                playTransition(false);

            }
        }
        else if (Userfield.getPromptText().equals("AdminUsername"))
        {
            Admin admin=new Admin();
            if(admin.validateLogin(Userfield.getText(),Passwordfield.getText()))
            {
                playTransition(true);
            }
            else
            {
                playTransition(false);

            }

        }
    }
    //Play Loading Transition
    public void playTransition(Boolean isValid)
    {
        Loginadmin.setDisable(true);

        Registerlink.setDisable(true);

        double DefaultCircle1value=circle1.getLayoutX();

        double DefaultCircle2value=circle2.getLayoutX();

        double DefaultCircle3value=circle3.getLayoutX();

        circle1.setVisible(true);

        circle2.setVisible(true);

        circle3.setVisible(true);

        Loadinglabel.setVisible(true);

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(1),circle1);

        TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(1),circle2);

        TranslateTransition translateTransition2=new TranslateTransition(Duration.seconds(1),circle3);


        translateTransition.setByX(40);

        translateTransition.setCycleCount(6);

        translateTransition.setAutoReverse(true);

        translateTransition.play();

        translateTransition1.setByX(40);

        translateTransition1.setCycleCount(6);

        translateTransition1.setAutoReverse(true);

        translateTransition1.play();

        translateTransition2.setByX(40);

        translateTransition2.setCycleCount(6);

        translateTransition2.setAutoReverse(true);

        translateTransition2.play();

        translateTransition.setOnFinished(e->{
            circle1.setLayoutX(DefaultCircle1value);

            circle2.setLayoutX(DefaultCircle2value);

            circle3.setLayoutX(DefaultCircle3value);

            circle1.setVisible(false);

            circle2.setVisible(false);

            circle3.setVisible(false);

            if (isValid)
            {

                Stage stage=(Stage) LoginFrame.getScene().getWindow();

                stage.close();

                Parent root=null;
                try
                {
                    if(Userfield.getPromptText().equals("AdminUsername"))
                    {
                        root = FXMLLoader.load(getClass().getResource("/Views/AdminPanel.fxml"));
                    }
                    else {
                        root = FXMLLoader.load(getClass().getResource("/Views/UserPanel.fxml"));
                    }

                    Stage stage1=new Stage();

                    stage1.initStyle(StageStyle.UNDECORATED);

                    stage1.setScene(new Scene(root, 1366, 810));

                    stage1.show();
                }
                catch (Exception exception)
                {
                    Loadinglabel.setVisible(false);

                    Registerlink.setDisable(false);

                    Loginadmin.setDisable(false);

                    Errorlabel.setText(exception.toString());

                    Errorlabel.setVisible(true);

                    System.out.println(exception.toString());
                }

            }
            else
            {
                Loadinglabel.setVisible(false);

                Registerlink.setDisable(false);

                Loginadmin.setDisable(false);

                Errorlabel.setVisible(true);
            }

        });

    }
    //when User clicks Register
    public void onRegisterClick()
    {
        Stage stage=(Stage) LoginFrame.getScene().getWindow();

        stage.close();

        Parent root=null;
        try
        {
            root= FXMLLoader.load(getClass().getResource("/Views/Register.fxml"));

            Stage stage1=new Stage();

            stage1.initStyle(StageStyle.UNDECORATED);

            stage1.setScene(new Scene(root, 700, 500));

            stage1.show();
        }
        catch (Exception exception)
        {
            System.out.println(exception.toString());
        }
    }

    //Check if its user or admin who has clicked and call below functions accordingly
    public void onLoginAsAdminClick()
    {
        Errorlabel.setVisible(false);

        Loadinglabel.setVisible(false);

        if (Loginadmin.getText().equals("Login As User"))
        {
           setLoginUser();
        }
        else if (Loginadmin.getText().equals("Login As Admin"))
        {
            setLoginadmin();
        }

    }

    //When user clicks as admin login Change the prompt text and remove Register Option

    public void setLoginadmin() {

        Userfield.clear();

        Userfield.setPromptText("AdminUsername");

        Passwordfield.clear();

        NotRegisteredLabel.setVisible(false);

        Registerlink.setVisible(false);

        Loginadmin.setText("Login As User");

        Loginadmin.setLayoutX(Loginadmin.getLayoutX()+10);

        Loginadmin.setLayoutY(Loginadmin.getLayoutY()-20);
    }

    //Opposite of setloginadmin click
    public void setLoginUser()
    {
        Userfield.clear();

        Userfield.setPromptText("Username");

        Passwordfield.clear();

        NotRegisteredLabel.setVisible(true);

        Registerlink.setVisible(true);

        Loginadmin.setText("Login As Admin");

        Loginadmin.setLayoutX(Loginadmin.getLayoutX()-10);

        Loginadmin.setLayoutY(Loginadmin.getLayoutY()+20);
    }
}
