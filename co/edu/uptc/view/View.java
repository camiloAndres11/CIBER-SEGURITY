package co.edu.uptc.view;

import java.io.IOException;
import java.util.ArrayList;


import co.edu.uptc.controller.Controller;
import co.edu.uptc.controller.JsonFile;
import co.edu.uptc.model.Model;
import javafx.application.Application;
import javafx.css.CssMetaData;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class View extends Application {

    private Controller loginController = new Controller();
    private int verificationCode;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Gestión UPTC");

        // Crea el StackPane para el formulario de inicio de sesión con imagen de fondo
        StackPane root = createLoginForm(primaryStage);

        // Crea una nueva escena y establece el StackPane como el contenido
        Scene loginScene = new Scene(root, 800, 600);
        applyStyles(loginScene);

        // Establece la escena en el escenario y muestra el escenario
        primaryStage.setScene(loginScene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void applyStyles(Scene scene) {
        String css = getClass().getResource("/co/edu/uptc/css/styles.css").toExternalForm();
        if (css == null) {
            System.err.println("No se pudo encontrar el archivo CSS");
        } else {
            scene.getStylesheets().add(css);
        }
    }

    private StackPane createLoginForm(Stage primaryStage) {
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(10));
        loginGrid.setHgap(10);
        loginGrid.setVgap(20);
        loginGrid.setStyle("-fx-background-color:white;");
    
        Label usernameLabel = new Label("Nombre de usuario:");
        TextField usernameInput = new TextField();
        Label passwordLabel = new Label("Contraseña:");
        PasswordField passwordInput = new PasswordField();
        Button loginButton = new Button("Iniciar Sesión");
        Label errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red;");
    

        Label registerText = new Label("¿No tienes una cuenta?");
        Hyperlink registerLink = new Hyperlink("Registrarse");

        Label recoverText = new Label("¿Olvidaste tu contraseña?");
        Hyperlink recoverLink = new Hyperlink("Recuperar");
    
        // Configurar las preferencias de los componentes
        usernameLabel.setPrefWidth(150);
        passwordLabel.setPrefWidth(150);
        loginButton.setPrefWidth(150);
    
        usernameInput.setStyle("-fx-background-color: lightgray;");
        passwordInput.setStyle("-fx-background-color: lightgray;");
    
        // Aplicar la clase CSS a los botones
        loginButton.getStyleClass().add("button-login");
    
        // Configurar imágenes
        ImageView imageView = new ImageView(new Image("co/edu/uptc/util/logo-uptc.png"));
        imageView.setFitHeight(80);
        imageView.setFitWidth(150);
        GridPane.setHalignment(imageView, HPos.LEFT);
    
        ImageView imageSistemas = new ImageView(new Image("co/edu/uptc/util/logo-sistemas.png"));
        imageSistemas.setFitHeight(100);
        imageSistemas.setFitWidth(100);
        GridPane.setHalignment(imageSistemas, HPos.RIGHT);
    
        // Añadir componentes al GridPane con sus respectivas posiciones
        loginGrid.add(imageView, 0, 0);
        loginGrid.add(imageSistemas, 1, 0);
        loginGrid.add(usernameLabel, 0, 1);
        loginGrid.add(usernameInput, 1, 1);
        loginGrid.add(passwordLabel, 0, 2);
        loginGrid.add(passwordInput, 1, 2);
        loginGrid.add(errorMessage, 1, 3); // Ajustar posición y span del errorMessage
        loginGrid.add(loginButton, 0, 4, 2, 1);
        loginGrid.add(recoverText, 0, 5); // Añadir texto de registro
        loginGrid.add(recoverLink, 1, 5); // Añadir enlace de registro
        loginGrid.add(registerText, 0, 6); 
        loginGrid.add(registerLink, 1, 6); 
    
        GridPane.setHalignment(usernameLabel, HPos.CENTER);
        GridPane.setHalignment(passwordLabel, HPos.CENTER);
        GridPane.setHalignment(errorMessage, HPos.CENTER);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setHalignment(registerText, HPos.CENTER);
        GridPane.setHalignment(registerLink, HPos.CENTER);
        GridPane.setHalignment(recoverText, HPos.CENTER);
        GridPane.setHalignment(recoverLink, HPos.CENTER);
    
        usernameLabel.setAlignment(Pos.CENTER_LEFT);
        passwordLabel.setAlignment(Pos.CENTER_LEFT);
    
        // Manejar evento de login
        loginButton.setOnAction(e -> {
            String username = usernameInput.getText().trim();
            String password = passwordInput.getText().trim();
    
            usernameInput.setStyle("-fx-background-color: lightgray;");
            passwordInput.setStyle("-fx-background-color: lightgray;");
            errorMessage.setText(""); // Resetear mensaje de error
    
            if (username.isEmpty()) {
                usernameInput.setStyle("-fx-background-color: lightcoral;");
                errorMessage.setText("⚠ El campo de usuario no puede estar vacío.");
            } else if (password.isEmpty()) {
                passwordInput.setStyle("-fx-background-color: lightcoral;");
                errorMessage.setText("⚠ El campo de contraseña no puede estar vacío.");
            } else {
                if (loginController.validarCredenciales(username, password)) {
                    showMainScreen(primaryStage);
                } else {
                    usernameInput.setStyle("-fx-background-color: lightcoral;");
                    passwordInput.setStyle("-fx-background-color: lightcoral;");
                    errorMessage.setText("⚠ El usuario o la contraseña son incorrectas");
                }
            }
        });
    
        // Manejar evento del hipervínculo de registro
        registerLink.setOnAction(e -> showRegisterForm(primaryStage));
        recoverLink.setOnAction(e -> showRecoverPasswordForm(primaryStage));
    
        // Crear layout
        HBox hBox = new HBox(loginGrid);
        hBox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(hBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
    
        StackPane stackPane = new StackPane();
        ImageView backgroundImage = new ImageView(new Image("co/edu/uptc/util/imagen.png"));
        backgroundImage.setPreserveRatio(false);
        backgroundImage.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundImage.fitHeightProperty().bind(primaryStage.heightProperty());
    
        stackPane.setCenterShape(true);
        stackPane.getChildren().addAll(backgroundImage, vbox);
        primaryStage.setMaximized(true);
    
        return stackPane;
    }
    
    
    private void showMainScreen(Stage primaryStage) {
        GridPane mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(10));
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);

        Label welcomeLabel = new Label("Bienvenido a la Escuela de Sistemas UPTC");
        Button logoutButton = new Button("Cerrar Sesión");

        GridPane.setConstraints(welcomeLabel, 0, 0);
        GridPane.setConstraints(logoutButton, 0, 1);


        logoutButton.setOnAction(e -> {
            Scene loginScene = new Scene(createLoginForm(primaryStage), 800, 600);
            applyStyles(loginScene);
            primaryStage.setScene(loginScene);
        });

        mainGrid.getChildren().addAll(welcomeLabel, logoutButton);


        Scene mainScene = new Scene(mainGrid, 800, 600);
        applyStyles(mainScene);
        primaryStage.setScene(mainScene);
    }


    private void showRegisterForm(Stage primaryStage) {
        GridPane registerGrid = new GridPane();
        registerGrid.setPadding(new Insets(10));
        registerGrid.setHgap(10);
        registerGrid.setVgap(10);

        Label usernameLabel = new Label("Nombre de usuario:");
        TextField usernameInput = new TextField();
        Label passwordLabel = new Label("Contraseña:");
        PasswordField passwordInput = new PasswordField();
        Label explanationPassword = new Label("Debe tener al menos 8 caracteres, incluir una letra mayúscula, dos números y un carácter especial.");
        Label comfirmPaswordLabel = new Label("Confirmar contraseña");
        PasswordField comfirmPassword = new PasswordField();
        Label phoneLabel = new Label("Número de teléfono:");
        TextField phoneInput = new TextField();
        Label firstNameLabel = new Label("Nombre:");
        TextField firstNameInput = new TextField();
        Label lastNameLabel = new Label("Apellidos:");
        TextField lastNameInput = new TextField();
        Button returnButton = new Button("Volver");
        Button registerButton = new Button("Registrar");

        explanationPassword.setWrapText(true);
        usernameLabel.setPrefWidth(150);
        passwordLabel.setPrefWidth(150);
        explanationPassword.setPrefWidth(300);
        phoneLabel.setPrefWidth(150);
        firstNameLabel.setPrefWidth(150);
        lastNameLabel.setPrefWidth(150);
        returnButton.setPrefWidth(150);
        registerButton.setPrefWidth(150);

        explanationPassword.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");
        registerGrid.setStyle("-fx-background-color:white;");
        usernameInput.setStyle("-fx-background-color: lightgray;");
        passwordInput.setStyle("-fx-background-color: lightgray;");
        phoneInput.setStyle("-fx-background-color: lightgray;");
        firstNameInput.setStyle("-fx-background-color: lightgray;");
        lastNameInput.setStyle("-fx-background-color: lightgray;");
        comfirmPassword.setStyle("-fx-background-color: lightgray;");

        ImageView imageView = new ImageView(new Image("co\\edu\\uptc\\util\\logo-uptc.png"));
        imageView.setFitHeight(80);
        imageView.setFitWidth(150);
        GridPane.setHalignment(imageView, HPos.LEFT);
        ImageView imageSistemas = new ImageView(new Image("co\\edu\\uptc\\util\\logo-sistemas.png"));
        imageSistemas.setFitHeight(80);
        imageSistemas.setFitWidth(80);
        GridPane.setHalignment(imageSistemas, HPos.RIGHT);


        GridPane.setConstraints(imageView, 0, 0);
        GridPane.setConstraints(imageSistemas, 1, 0);
        GridPane.setConstraints(usernameLabel, 0, 1);
        GridPane.setConstraints(usernameInput, 1, 1);
        GridPane.setConstraints(passwordLabel, 0, 2);
        GridPane.setConstraints(passwordInput, 1, 2);
        GridPane.setConstraints(comfirmPaswordLabel, 0, 3);
        GridPane.setConstraints(comfirmPassword, 1, 3);
        GridPane.setConstraints(explanationPassword, 0, 4, 4, 1);
        GridPane.setConstraints(phoneLabel, 0, 5);
        GridPane.setConstraints(phoneInput, 1, 5);
        GridPane.setConstraints(firstNameLabel, 0, 6);
        GridPane.setConstraints(firstNameInput, 1, 6);
        GridPane.setConstraints(lastNameLabel, 0, 7);
        GridPane.setConstraints(lastNameInput, 1, 7);
        GridPane.setConstraints(returnButton, 0, 8);
        GridPane.setConstraints(registerButton, 1, 8);

        GridPane.setHalignment(usernameLabel, HPos.CENTER);
        GridPane.setHalignment(passwordLabel, HPos.CENTER);
        GridPane.setHalignment(phoneLabel, HPos.CENTER);
        GridPane.setHalignment(firstNameLabel, HPos.CENTER);
        GridPane.setHalignment(lastNameLabel, HPos.CENTER);
        GridPane.setHalignment(returnButton, HPos.CENTER);
        GridPane.setValignment(returnButton, VPos.CENTER);
        GridPane.setHalignment(registerButton, HPos.CENTER);
        GridPane.setValignment(registerButton, VPos.CENTER);

        usernameLabel.setAlignment(Pos.CENTER_LEFT);
        passwordLabel.setAlignment(Pos.CENTER_LEFT);
        phoneLabel.setAlignment(Pos.CENTER_LEFT);
        firstNameLabel.setAlignment(Pos.CENTER_LEFT);
        lastNameLabel.setAlignment(Pos.CENTER_LEFT);
        //GridPane.setHalignment(explanationPassword, HPos.CENTER);
        explanationPassword.setAlignment(Pos.CENTER_LEFT);

        returnButton.setOnAction(e -> {
            Scene loginScene = new Scene(createLoginForm(primaryStage), 400, 300);
            applyStyles(loginScene);
            primaryStage.setScene(loginScene);
        });

        registerButton.setOnAction(e -> {
            String username = usernameInput.getText().trim();
            String password = passwordInput.getText().trim();
            String passwordComfirm = comfirmPassword.getText().trim();
            String phone = phoneInput.getText().trim();
            String firstName = firstNameInput.getText().trim();
            String lastName = lastNameInput.getText().trim();
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@uptc.edu.co";
            int number = (int) (1000 + Math.random() * 9000);
            String idInterno = username + number;
            String emailString = null;
            int contar = 0;

            try {
                emailString = loginController.crearCorreo(email);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            boolean isNumber = true;

            try {
                Integer.parseInt(phone);
            } catch (NumberFormatException exception) {
                isNumber = false;
            }

            String FILE_PATH = "co\\edu\\uptc\\persistence\\Usuarios.json";
            boolean isExist = false;
            if (usernameInput.getText().isEmpty() || phoneInput.getText().isEmpty() || firstNameInput.getText().isEmpty() || lastNameInput.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Campos incompletos, por favor, complete todos los campos requeridos");
            } else {
                try {
                    ArrayList<Model> cuentasEstudiantes = new ArrayList<>(JsonFile.readFromJson(FILE_PATH));
                    for (Model model : cuentasEstudiantes) {
                        if (model.getUserName().equals(username)) {
                            isExist = true;
                        }
                    }

                    if (password.equals(passwordComfirm)) {

                        if (loginController.validarNombres(firstName, lastName)) {

                            if (!isExist) {

                                for (int i = 0; i < phone.length(); i++) {
                                    contar = contar + 1;

                                }

                                if (contar == 10) {
                                    if (isNumber) {
                                        try {
                                            loginController.registrarUsuario(emailString, idInterno, username, password, phone, firstName, lastName);
                                            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Usuario registrado exitosamente.");
                                            primaryStage.setScene(new Scene(createLoginForm(primaryStage), 400, 300));
                                        } catch (Exception ex) {
                                            showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
                                        }
                                    } else if (!isNumber) {
                                        showAlert(Alert.AlertType.ERROR, "Error", " Numero de Telefono Invalido");

                                    }
                                } else if (contar < 10 || contar > 10) {
                                    showAlert(Alert.AlertType.ERROR, "Error", "El numero de telefono debe tener 10 caracteres");

                                }


                            } else if (isExist) {
                                showAlert(Alert.AlertType.ERROR, "Error", "Nombre de usuario ya existente");
                            }
                        } else {
                            showAlert(Alert.AlertType.ERROR, "Error", "Nombre o Apellido invalido");

                        }

                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Las contraseñas no coinciden");
                    }


                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }


        });

        registerGrid.getChildren().addAll(imageView, imageSistemas, usernameLabel, usernameInput, passwordLabel, explanationPassword, comfirmPaswordLabel, comfirmPassword, passwordInput, phoneLabel, phoneInput, firstNameLabel, firstNameInput, lastNameLabel, lastNameInput, returnButton, registerButton);


        HBox hBox = new HBox(registerGrid);
        hBox.setAlignment(Pos.CENTER);


        VBox vbox = new VBox(hBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        StackPane stackPane = new StackPane();
        ImageView backgroundImage = new ImageView(new Image("co\\edu\\uptc\\util\\imagen.png"));
        backgroundImage.setPreserveRatio(false);


        //bagroundImage.setFitHeight(600);
        //bagroundImage.setFitWidth(800);

        backgroundImage.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundImage.fitHeightProperty().bind(primaryStage.heightProperty());

        stackPane.setCenterShape(true);
        stackPane.getChildren().addAll(backgroundImage, vbox);


        primaryStage.setScene(new Scene(stackPane));
        primaryStage.setMaximized(true);
    }

    private void showRecoverPasswordForm(Stage primaryStage) {
        GridPane recoverGrid = new GridPane();
        recoverGrid.setPadding(new Insets(10));
        recoverGrid.setHgap(10);
        recoverGrid.setVgap(10);
    
        Label instructionLabel = new Label("Escriba su correo en el siguiente espacio para enviar un código para reestablecer su contraseña:");
        Label emailLabel = new Label("Correo electrónico:");
        TextField emailInput = new TextField();
        Button recoverButton = new Button("Recuperar");
        Button returnButton = new Button("Volver");
        Label errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red;");
    
        instructionLabel.setWrapText(true);
        instructionLabel.setPrefWidth(300);
        emailLabel.setPrefWidth(150);
        emailInput.setStyle("-fx-background-color: lightgray;");
        returnButton.setPrefWidth(150);
        recoverButton.setPrefWidth(150);
    
        // Añadir clases CSS a los botones
        recoverButton.getStyleClass().add("button-recover-1");
        returnButton.getStyleClass().add("button-return");
    
        ImageView imageView = new ImageView(new Image("co\\edu\\uptc\\util\\logo-uptc.png"));
        imageView.setFitHeight(80);
        imageView.setFitWidth(150);
        GridPane.setHalignment(imageView, HPos.LEFT);
        ImageView imageSistemas = new ImageView(new Image("co\\edu\\uptc\\util\\logo-sistemas.png"));
        imageSistemas.setFitHeight(80);
        imageSistemas.setFitWidth(80);
        GridPane.setHalignment(imageSistemas, HPos.RIGHT);
    
        GridPane.setConstraints(imageView, 0, 0);
        GridPane.setConstraints(imageSistemas, 1, 0);
        GridPane.setConstraints(instructionLabel, 0, 1, 2, 1);
        GridPane.setConstraints(emailLabel, 0, 2);
        GridPane.setConstraints(emailInput, 1, 2);
        GridPane.setConstraints(errorMessage, 1, 3);
        GridPane.setConstraints(returnButton, 0, 4);
        GridPane.setConstraints(recoverButton, 1, 4);
    
        recoverGrid.setStyle("-fx-background-color:white;");
        GridPane.setHalignment(instructionLabel, HPos.CENTER);
        GridPane.setHalignment(emailLabel, HPos.CENTER);
        GridPane.setHalignment(returnButton, HPos.CENTER);
        GridPane.setValignment(returnButton, VPos.CENTER);
        GridPane.setHalignment(recoverButton, HPos.CENTER);
        GridPane.setValignment(recoverButton, VPos.CENTER);
    
        instructionLabel.setAlignment(Pos.CENTER_LEFT);
        emailLabel.setAlignment(Pos.CENTER_LEFT);
    
        returnButton.setOnAction(e -> {
            Scene loginScene = new Scene(createLoginForm(primaryStage));
            applyStyles(loginScene);
            primaryStage.setScene(loginScene);
            primaryStage.setMaximized(true);
        });
    
        recoverButton.setOnAction(e -> {
            emailInput.setStyle("-fx-background-color: lightgray;"); // Reset input field style
            errorMessage.setText(""); // Reset error message
            String email = emailInput.getText().trim();
            if (email.isEmpty()) {
                emailInput.setStyle("-fx-background-color: lightcoral;");
                errorMessage.setText("⚠ El campo de correo electrónico no puede estar vacío.");
            } else if (loginController.verificarCorreoExistente(email)) {
                int verificationCode = generarCodigoVerificacion();
                mostrarCodigoVerificacion(verificationCode);
                showVerificationCodeForm(primaryStage, email, verificationCode);
            } else {
                emailInput.setStyle("-fx-background-color: lightcoral;");
                errorMessage.setText("⚠ Correo electrónico no encontrado.");
            }
        });
    
        recoverGrid.getChildren().addAll(imageView, imageSistemas, instructionLabel, emailLabel, emailInput, errorMessage, returnButton, recoverButton);
    
        HBox hBox = new HBox(recoverGrid);
        hBox.setAlignment(Pos.CENTER);
    
        VBox vbox = new VBox(hBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
    
        StackPane stackPane = new StackPane();
        ImageView backgroundImage = new ImageView(new Image("co\\edu\\uptc\\util\\imagen.png"));
        backgroundImage.setPreserveRatio(false);
        backgroundImage.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundImage.fitHeightProperty().bind(primaryStage.heightProperty());
    
        stackPane.setCenterShape(true);
        stackPane.getChildren().addAll(backgroundImage, vbox);
    
        Scene scene = primaryStage.getScene();
        if (scene == null) {
            scene = new Scene(stackPane, 800, 600);
            primaryStage.setScene(scene);
        } else {
            scene.setRoot(stackPane);
        }
        applyStyles(scene);
        primaryStage.setMaximized(true);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void showVerificationCodeForm(Stage primaryStage, String email, int initialVerificationCode) {
        // Inicializa el código de verificación
        this.verificationCode = initialVerificationCode;

        GridPane codeGrid = new GridPane();
        codeGrid.setPadding(new Insets(10));
        codeGrid.setHgap(10);
        codeGrid.setVgap(10);

        Label codeLabel = new Label("Código de verificación:");
        TextField codeInput = new TextField();
        Button verifyButton = new Button("Verificar");
        Button returnButton = new Button("Volver");
        Hyperlink generateLink = new Hyperlink("Generar nuevo código");
        Label newCodeText = new Label("¿Necesitas un nuevo código?");
        Label errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red;");

        codeLabel.setPrefWidth(150);
        codeInput.setStyle("-fx-background-color: lightgray;");
        returnButton.setPrefWidth(150);
        verifyButton.setPrefWidth(150);
        newCodeText.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");
        generateLink.setStyle("-fx-font-size: 11px; -fx-text-fill: blue;");

        returnButton.getStyleClass().add("button-return");
        verifyButton.getStyleClass().add("button-verify");

        ImageView imageView = new ImageView(new Image("co\\edu\\uptc\\util\\logo-uptc.png"));
        imageView.setFitHeight(80);
        imageView.setFitWidth(150);
        GridPane.setHalignment(imageView, HPos.LEFT);
        ImageView imageSistemas = new ImageView(new Image("co\\edu\\uptc\\util\\logo-sistemas.png"));
        imageSistemas.setFitHeight(80);
        imageSistemas.setFitWidth(80);
        GridPane.setHalignment(imageSistemas, HPos.RIGHT);

        GridPane.setConstraints(imageView, 0, 0);
        GridPane.setConstraints(imageSistemas, 1, 0);
        GridPane.setConstraints(codeLabel, 0, 1);
        GridPane.setConstraints(codeInput, 1, 1);
        GridPane.setConstraints(errorMessage, 1, 2);
        GridPane.setConstraints(newCodeText, 0, 3);
        GridPane.setConstraints(generateLink, 1, 3);
        GridPane.setConstraints(returnButton, 0, 4);
        GridPane.setConstraints(verifyButton, 1, 4);

        codeGrid.setStyle("-fx-background-color:white;");
        GridPane.setHalignment(codeLabel, HPos.CENTER);
        GridPane.setHalignment(newCodeText, HPos.CENTER);
        GridPane.setHalignment(generateLink, HPos.CENTER);
        GridPane.setHalignment(returnButton, HPos.CENTER);
        GridPane.setValignment(returnButton, VPos.CENTER);
        GridPane.setHalignment(verifyButton, HPos.CENTER);
        GridPane.setValignment(verifyButton, VPos.CENTER);
        GridPane.setHalignment(errorMessage, HPos.CENTER);

        codeLabel.setAlignment(Pos.CENTER_LEFT);

        returnButton.setOnAction(e -> {
            Scene loginScene = new Scene(createLoginForm(primaryStage));
            applyStyles(loginScene);
            primaryStage.setScene(loginScene);
            primaryStage.setMaximized(true);
        });

        verifyButton.setOnAction(ev -> {
            codeInput.setStyle("-fx-background-color: lightgray;"); // Reset input field style
            errorMessage.setText(""); // Reset error message
            String enteredCode = codeInput.getText().trim();
    
            // Verifica si el campo está vacío
            if (enteredCode.isEmpty()) {
                codeInput.setStyle("-fx-background-color: lightcoral;");
                errorMessage.setText("⚠ El campo del código de verificación no puede estar vacío.");
                return; // Salir de la función después de mostrar el error
            }
    
            try {
                int parsedCode = Integer.parseInt(enteredCode);
                if (parsedCode == verificationCode) {
                    showNewPasswordForm(primaryStage, email);
                } else {
                    codeInput.setStyle("-fx-background-color: lightcoral;");
                    errorMessage.setText("⚠ Código de verificación incorrecto.");
                }
            } catch (NumberFormatException ex) {
                codeInput.setStyle("-fx-background-color: lightcoral;");
                errorMessage.setText("⚠ Código de verificación incorrecto.");
            }
        });

        generateLink.setOnAction(e -> {
            this.verificationCode = generarCodigoVerificacion();  // Actualiza el código de verificación
            mostrarCodigoVerificacion(verificationCode);
        });

        codeGrid.getChildren().addAll(imageView, imageSistemas, codeLabel, codeInput, errorMessage, newCodeText, generateLink, returnButton, verifyButton);

        HBox hBox = new HBox(codeGrid);
        hBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(hBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        StackPane stackPane = new StackPane();
        ImageView backgroundImage = new ImageView(new Image("co\\edu\\uptc\\util\\imagen.png"));
        backgroundImage.setPreserveRatio(false);
        backgroundImage.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundImage.fitHeightProperty().bind(primaryStage.heightProperty());

        stackPane.setCenterShape(true);
        stackPane.getChildren().addAll(backgroundImage, vbox);

        Scene scene = primaryStage.getScene();
        if (scene == null) {
            scene = new Scene(stackPane, 800, 600);
            primaryStage.setScene(scene);
        } else {
            scene.setRoot(stackPane);
        }
        applyStyles(scene);
        primaryStage.setMaximized(true);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void showNewPasswordForm(Stage primaryStage, String email) {
        GridPane newPasswordGrid = new GridPane();
        newPasswordGrid.setPadding(new Insets(10));
        newPasswordGrid.setHgap(10);
        newPasswordGrid.setVgap(10);
    
        Label newPasswordLabel = new Label("Nueva contraseña:");
        PasswordField newPasswordInput = new PasswordField();
        Label confirmPasswordLabel = new Label("Confirmar contraseña:");
        PasswordField confirmPasswordInput = new PasswordField();
        Button saveButton = new Button("Guardar");
        Button returnButton = new Button("Volver");
        Label errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red;");
        Label explanationPassword = new Label("Debe tener al menos 8 caracteres, incluir una letra mayúscula, dos números y un carácter especial.");
        
        explanationPassword.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");
        explanationPassword.setWrapText(true);
        explanationPassword.setPrefWidth(300);
        
        newPasswordLabel.setPrefWidth(150);
        newPasswordInput.setStyle("-fx-background-color: lightgray;");
        confirmPasswordLabel.setPrefWidth(150);
        confirmPasswordInput.setStyle("-fx-background-color: lightgray;");
        returnButton.setPrefWidth(150);
        saveButton.setPrefWidth(150);
    
        returnButton.getStyleClass().add("button-return");
        saveButton.getStyleClass().add("button-save");
    
        ImageView imageView = new ImageView(new Image("co\\edu\\uptc\\util\\logo-uptc.png"));
        imageView.setFitHeight(80);
        imageView.setFitWidth(150);
        GridPane.setHalignment(imageView, HPos.LEFT);
        ImageView imageSistemas = new ImageView(new Image("co\\edu\\uptc\\util\\logo-sistemas.png"));
        imageSistemas.setFitHeight(80);
        imageSistemas.setFitWidth(80);
        GridPane.setHalignment(imageSistemas, HPos.RIGHT);
    
        GridPane.setConstraints(imageView, 0, 0);
        GridPane.setConstraints(imageSistemas, 1, 0);
        GridPane.setConstraints(newPasswordLabel, 0, 1);
        GridPane.setConstraints(newPasswordInput, 1, 1);
        GridPane.setConstraints(explanationPassword, 0, 2, 2, 1); // Modificado para ocupar 2 columnas
        GridPane.setConstraints(confirmPasswordLabel, 0, 3);
        GridPane.setConstraints(confirmPasswordInput, 1, 3);
        GridPane.setConstraints(errorMessage, 1, 4);
        GridPane.setConstraints(returnButton, 0, 5);
        GridPane.setConstraints(saveButton, 1, 5);
    
        newPasswordGrid.setStyle("-fx-background-color:white;");
        GridPane.setHalignment(newPasswordLabel, HPos.CENTER);
        GridPane.setHalignment(confirmPasswordLabel, HPos.CENTER);
        GridPane.setHalignment(returnButton, HPos.CENTER);
        GridPane.setValignment(returnButton, VPos.CENTER);
        GridPane.setHalignment(saveButton, HPos.CENTER);
        GridPane.setValignment(saveButton, VPos.CENTER);
        GridPane.setHalignment(explanationPassword, HPos.CENTER); // Alinea el texto de explicación al centro
    
        newPasswordLabel.setAlignment(Pos.CENTER_LEFT);
        confirmPasswordLabel.setAlignment(Pos.CENTER_LEFT);
        explanationPassword.setAlignment(Pos.CENTER_LEFT);
    
        returnButton.setOnAction(e -> {
            Scene loginScene = new Scene(createLoginForm(primaryStage));
            applyStyles(loginScene);
            primaryStage.setScene(loginScene);
            primaryStage.setMaximized(true);
        });
    
        saveButton.setOnAction(e -> {
            newPasswordInput.setStyle("-fx-background-color: lightgray;"); // Reset input field style
            confirmPasswordInput.setStyle("-fx-background-color: lightgray;"); // Reset input field style
            errorMessage.setText(""); // Reset error message
            String newPassword = newPasswordInput.getText().trim();
            String confirmPassword = confirmPasswordInput.getText().trim();
    
            if (newPassword.isEmpty()) {
                newPasswordInput.setStyle("-fx-background-color: lightcoral;");
                errorMessage.setText("⚠ El campo de nueva contraseña no puede estar vacío.");
            } else if (confirmPassword.isEmpty()) {
                confirmPasswordInput.setStyle("-fx-background-color: lightcoral;");
                errorMessage.setText("⚠ El campo de confirmar contraseña no puede estar vacío.");
            } else if (!newPassword.equals(confirmPassword)) {
                newPasswordInput.setStyle("-fx-background-color: lightcoral;");
                confirmPasswordInput.setStyle("-fx-background-color: lightcoral;");
                errorMessage.setText("⚠ Las contraseñas no coinciden.");
            } else if (newPassword.contains(" ")) {
                newPasswordInput.setStyle("-fx-background-color: lightcoral;");
                errorMessage.setText("⚠ La contraseña no puede contener espacios.");
            } else if (!isValidPassword(newPassword)) {
                newPasswordInput.setStyle("-fx-background-color: lightcoral;");
                errorMessage.setText("⚠ La contraseña no cumple con los requisitos.");
            } else {
                try {
                    String currentPassword = loginController.getPasswordByEmail(email); // Obtener la contraseña actual
                    if (newPassword.equals(currentPassword)) {
                        newPasswordInput.setStyle("-fx-background-color: lightcoral;");
                        errorMessage.setText("⚠ La nueva contraseña no puede ser igual a la anterior.");
                    } else {
                        loginController.actualizarContraseña(email, newPassword);
                        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Contraseña cambiada exitosamente.");
                        Scene loginScene = new Scene(createLoginForm(primaryStage));
                        applyStyles(loginScene);
                        primaryStage.setScene(loginScene);
                        primaryStage.setMaximized(true);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    
        newPasswordGrid.getChildren().addAll(imageView, imageSistemas, newPasswordLabel, newPasswordInput, confirmPasswordLabel, confirmPasswordInput, explanationPassword, errorMessage, returnButton, saveButton);
    
        HBox hBox = new HBox(newPasswordGrid);
        hBox.setAlignment(Pos.CENTER);
    
        VBox vbox = new VBox(hBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
    
        StackPane stackPane = new StackPane();
        ImageView backgroundImage = new ImageView(new Image("co\\edu\\uptc\\util\\imagen.png"));
        backgroundImage.setPreserveRatio(false);
        backgroundImage.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundImage.fitHeightProperty().bind(primaryStage.heightProperty());
    
        stackPane.setCenterShape(true);
        stackPane.getChildren().addAll(backgroundImage, vbox);
    
        Scene scene = primaryStage.getScene();
        if (scene == null) {
            scene = new Scene(stackPane, 800, 600);
            primaryStage.setScene(scene);
        } else {
            scene.setRoot(stackPane);
        }
        applyStyles(scene);
        primaryStage.setMaximized(true);
    }
    

    private boolean isValidPassword(String password) {
        // Verificar que la contraseña tenga al menos 8 caracteres
        if (password.length() < 8) {
            return false;
        }
        // Verificar que la contraseña tenga al menos una letra mayúscula
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        // Verificar que la contraseña tenga al menos una letra minúscula
        if (!password.matches(".*[a-z].*")) {
            return false;
        }
        // Verificar que la contraseña tenga al menos dos números
        if (!password.matches(".*[0-9].*[0-9].*")) {
            return false;
        }
        // Verificar que la contraseña tenga al menos un carácter especial
        if (!password.matches(".*[!@#$%^&*()].*")) {
            return false;
        }
        return true;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void mostrarCodigoVerificacion(int verificationCode) {
        Stage codeStage = new Stage();
        codeStage.setTitle("Código de Verificación");

        GridPane codeGrid = new GridPane();
        codeGrid.setPadding(new Insets(20));
        codeGrid.setHgap(10);
        codeGrid.setVgap(20);
        codeGrid.setAlignment(Pos.CENTER); // Center the grid

        Label codeLabel = new Label("Tu código de verificación es:");
        codeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label codeValue = new Label(String.valueOf(verificationCode));
        codeValue.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Button closeButton = new Button("Cerrar");
        closeButton.setStyle("-fx-font-size: 14px;");

        Label timerLabel = new Label("Tiempo restante: 60 segundos");
        timerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        GridPane.setHalignment(codeLabel, HPos.CENTER);
        GridPane.setHalignment(codeValue, HPos.CENTER);
        GridPane.setHalignment(timerLabel, HPos.CENTER);
        GridPane.setHalignment(closeButton, HPos.CENTER);

        GridPane.setConstraints(codeLabel, 0, 0);
        GridPane.setConstraints(codeValue, 0, 1);
        GridPane.setConstraints(timerLabel, 0, 2);
        GridPane.setConstraints(closeButton, 0, 3);

        closeButton.setOnAction(e -> codeStage.close());

        codeGrid.getChildren().addAll(codeLabel, codeValue, timerLabel, closeButton);

        Scene codeScene = new Scene(codeGrid, 400, 300);
        codeStage.setScene(codeScene);
        codeStage.show();

        // Timeline for countdown
        final int[] secondsRemaining = {60};
        Timeline countdown = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsRemaining[0]--;
            timerLabel.setText("Tiempo restante: " + secondsRemaining[0] + " segundos");

            if (secondsRemaining[0] <= 0) {
                codeStage.close();
            }
        }));
        countdown.setCycleCount(60); // Run for 60 seconds
        countdown.play();
    }


    private int generarCodigoVerificacion() {
        // Genera un código de verificación aleatorio de 6 dígitos
        return (int) (Math.random() * 900000) + 100000;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void MenuPrincipal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'MenuPrincipal'");

    }
}
