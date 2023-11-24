package controllers;

import Application.MySQL;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private Button signUpButton;
    @FXML
    private Button signInButton;
    @FXML
    private Button exitButton;
    @FXML
    private Pane windowPane;
    @FXML
    private Pane loginPane;
    @FXML
    private Pane registerPane;
    @FXML
    private Text textLeft1;
    @FXML
    private Text textLeft2;
    @FXML
    private Text textRight1;
    @FXML
    private Text textRight2;
    @FXML
    private TextField textAccount1;
    @FXML
    private TextField textAccount2;
    @FXML
    private PasswordField textPassword1;
    @FXML
    private PasswordField textPassword2;
    @FXML
    private PasswordField textConfirmPassword;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Text textResponse1;
    @FXML
    private Text textResponse2;

    MySQL mySQL = MySQL.getInstance();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1), windowPane);
        translateTransition1.setByX(180);
        translateTransition1.play();

        transitionLoginPane(1);

        controllerSignUp();
        controllerSignIn();
        controllerExit();

        controllerLogin();
        controllerRegister();
    }

    public void controllerSignUp() {
        signUpButton.setOnAction(e -> {
            signUpButton.setVisible(false);

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1), windowPane);
            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(1), registerPane);

            transitionLoginPane(-1);
            translateTransition1.setByX(-180);
            translateTransition1.play();

            translateTransition1.setOnFinished(event -> {
                loginPane.setVisible(false);
                registerPane.setVisible(true);

                windowPane.getStyleClass().clear();
                windowPane.getStyleClass().add("left-window");

                textLeft1.setVisible(true);
                textLeft2.setVisible(true);
                textRight1.setVisible(false);
                textRight2.setVisible(false);

                TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), windowPane);
                translateTransition3.setByX(-180);
                translateTransition3.play();

                translateTransition2.setByX(180);
                translateTransition2.play();
            });
            translateTransition2.setOnFinished(event -> {
                signInButton.setVisible(true);
            });

        });

    }

    public void controllerSignIn() {
        signInButton.setOnAction(e -> {
            signInButton.setVisible(false);

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1), windowPane);
            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(1), loginPane);

            transitionRegisterPane(-1);
            translateTransition1.setByX(180);
            translateTransition1.play();

            translateTransition1.setOnFinished(event -> {
                loginPane.setVisible(true);
                registerPane.setVisible(false);

                windowPane.getStyleClass().clear();
                windowPane.getStyleClass().add("right-window");

                textLeft1.setVisible(false);
                textLeft2.setVisible(false);
                textRight1.setVisible(true);
                textRight2.setVisible(true);

                TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), windowPane);
                translateTransition3.setByX(180);
                translateTransition3.play();

                translateTransition2.setByX(-180);
                translateTransition2.play();
            });
            translateTransition2.setOnFinished(event -> {
                signUpButton.setVisible(true);
            });
        });
    }

    public void controllerExit() {
        exitButton.setOnAction(e -> {
            Platform.exit();
        });
    }

    public void controllerLogin() {
        loginButton.setOnAction(e -> {
            try {
                loadMainAppWindow(new Stage());

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            textResponse1.setVisible(true);
            textResponse1.setText("Loading...");

            new Timeline(new KeyFrame(Duration.seconds(0.5), event1 -> {
                    if (textAccount1.getText().isEmpty()) {
                        textResponse1.setText("Account is empty !");

                    } else if (textPassword1.getText().isEmpty()) {
                        textResponse1.setText("Password is empty !");

                    } else if (!mySQL.checkUser(textAccount1.getText(), textPassword1.getText())) {
                        textResponse1.setText("Account or password is incorrect !");

                    } else {
                        textResponse1.setText("Logged in success");
                        textResponse1.setFill(Color.GREEN);

                        new Timeline(new KeyFrame(Duration.seconds(0.5), event2 -> login())).play();
                    }
                })).play();

        });
    }

    public void controllerRegister() {
        registerButton.setOnAction(e -> {
            textResponse2.setFill(Color.RED)
            ;
            if (textAccount2.getText().isEmpty()) {
                textResponse2.setText("Account is empty !");

            } else if (textPassword2.getText().isEmpty()) {
                textResponse2.setText("Password is empty !");

            } else if (textConfirmPassword.getText().isEmpty()) {
                textResponse2.setText("Please confirm your password !");

            } else if (!textPassword2.getText().equals(textConfirmPassword.getText())) {
                textResponse2.setText("Confirm Password is incorrect !");

            } else if (mySQL.checkExistUser(textAccount2.getText())) {
                textResponse2.setText("Account name already exists");

            } else {
                mySQL.addUser(textAccount2.getText(), textPassword2.getText());
                textResponse2.setText("Register Successfully.");
                textResponse2.setFill(Color.GREEN);

                new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                    signInButton.setVisible(false);

                    TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1), windowPane);
                    TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(1), loginPane);

                    transitionRegisterPane(-1);
                    translateTransition1.setByX(180);
                    translateTransition1.play();

                    translateTransition1.setOnFinished(event2 -> {
                        loginPane.setVisible(true);
                        registerPane.setVisible(false);

                        windowPane.getStyleClass().clear();
                        windowPane.getStyleClass().add("right-window");

                        textLeft1.setVisible(false);
                        textLeft2.setVisible(false);
                        textRight1.setVisible(true);
                        textRight2.setVisible(true);

                        TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), windowPane);
                        translateTransition3.setByX(180);
                        translateTransition3.play();

                        translateTransition2.setByX(-180);
                        translateTransition2.play();
                    });

                    translateTransition2.setOnFinished(event3 -> {
                        signUpButton.setVisible(true);
                        textAccount1.setText(textAccount2.getText());
                        textPassword1.setText(textPassword2.getText());
                    });
                })).play();
            }
        });
    }

    private void transitionLoginPane(int direct) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), loginPane);

        translateTransition.setByX(direct * -180);
        translateTransition.play();
    }

    private void transitionRegisterPane(int direct) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), registerPane);

        translateTransition.setByX(direct * 180);
        translateTransition.play();
    }

    private void loadMainAppWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/homeTab.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        scene.setFill(Color.TRANSPARENT);

        stage.setTitle("Dictionary");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        System.out.println("Done");
    }

    private void login() {
        Stage newStage = new Stage();

        try {
            loadMainAppWindow(newStage);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        Platform.runLater(() -> {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            newStage.show();
        });
    }
}