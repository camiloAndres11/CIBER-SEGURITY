package co.edu.uptc.view;

import java.io.IOException;
import java.util.ArrayList;


import co.edu.uptc.controller.Controller;
import co.edu.uptc.controller.JsonFile;
import co.edu.uptc.model.Model;
import javafx.application.Application;
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



public class View extends Application {

    private Controller loginController = new Controller();

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

        // Establece la escena en el escenario y muestra el escenario
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private StackPane createLoginForm(Stage primaryStage) {
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(10));
        loginGrid.setHgap(10);
        loginGrid.setVgap(20);
        loginGrid.setStyle("-fx-background-color:white;"); // Fondo  para el GridPane

        Label usernameLabel = new Label("Nombre de usuario:");
        TextField usernameInput = new TextField();
        Label passwordLabel = new Label("Contraseña:");
        PasswordField passwordInput = new PasswordField();
        Button loginButton = new Button("Iniciar Sesión");
        Button registerButton = new Button("Registrarse");
        Button recoverButton = new Button("Recuperar Contraseña");

        usernameLabel.setPrefWidth(150);
        passwordLabel.setPrefWidth(150);
        loginButton.setPrefWidth(150);
        registerButton.setPrefWidth(150);
        recoverButton.setPrefWidth(150);

        usernameInput.setStyle("-fx-background-color: lightgray;");
        passwordInput.setStyle("-fx-background-color: lightgray;");

        ImageView imageView = new ImageView(new Image("co\\edu\\uptc\\util\\logo-uptc.png"));
        imageView.setFitHeight(80);
        imageView.setFitWidth(150);
        GridPane.setHalignment(imageView, HPos.LEFT);
        ImageView imageSistemas = new ImageView(new Image("co\\edu\\uptc\\util\\logo-sistemas.png"));
        imageSistemas.setFitHeight(100);
        imageSistemas.setFitWidth(100);
        GridPane.setHalignment(imageSistemas, HPos.RIGHT);

        GridPane.setConstraints(imageView, 0, 0);
        GridPane.setConstraints(imageSistemas, 1, 0);
        GridPane.setConstraints(usernameLabel, 0, 1);
        GridPane.setConstraints(usernameInput, 1, 1);
        GridPane.setConstraints(passwordLabel, 0, 2);
        GridPane.setConstraints(passwordInput, 1, 2);
        GridPane.setConstraints(loginButton, 0, 3);
        GridPane.setConstraints(registerButton, 1, 3);
        GridPane.setConstraints(recoverButton, 1, 4);

        GridPane.setHalignment(usernameLabel, HPos.CENTER);
        GridPane.setHalignment(passwordLabel, HPos.CENTER);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setHalignment(registerButton, HPos.CENTER);
        GridPane.setHalignment(recoverButton, HPos.CENTER);

        usernameLabel.setAlignment(Pos.CENTER_LEFT);
        passwordLabel.setAlignment(Pos.CENTER_LEFT);

        loginButton.setOnAction(e -> {
            String username = usernameInput.getText().trim();
            String password = passwordInput.getText().trim();
            if (loginController.validarCredenciales(username, password)) {
                showMainScreen(primaryStage);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Credenciales incorrectas.");
            }
        });

        registerButton.setOnAction(e -> showRegisterForm(primaryStage));
        recoverButton.setOnAction(e -> showRecoverPasswordForm(primaryStage));

        loginGrid.getChildren().addAll(imageView, imageSistemas, usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton, registerButton, recoverButton);

        // Create el VBox en el centro  loginGrid in el  StackPane
        HBox hBox = new HBox(loginGrid);
        hBox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(hBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        // Crea un  StackPane con la imagen de fondito
        StackPane stackPane = new StackPane();
        ImageView backgroundImage = new ImageView(new Image("co\\edu\\uptc\\util\\imagen.png"));
        backgroundImage.setPreserveRatio(false);
        backgroundImage.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundImage.fitHeightProperty().bind(primaryStage.heightProperty());

        stackPane.setCenterShape(true);
        stackPane.getChildren().addAll(backgroundImage, vbox);



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

        logoutButton.setOnAction(e -> primaryStage.setScene(new Scene(createLoginForm(primaryStage), 800, 600)));

        mainGrid.getChildren().addAll(welcomeLabel, logoutButton);

        Scene mainScene = new Scene(mainGrid, 800, 600);
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
        Label explanationPassword=new Label("Debe tener al menos 8 caracteres, incluir una letra mayúscula, dos números y un carácter especial.");
        Label phoneLabel = new Label("Número de teléfono:");
        TextField phoneInput = new TextField();
        Label firstNameLabel = new Label("Nombre:");
        TextField firstNameInput = new TextField();
        Label lastNameLabel = new Label("Apellidos:");
        TextField lastNameInput = new TextField();
        Button returnButton=new Button("Volver");
        Button registerButton = new Button("Registrar");
        
        explanationPassword.setWrapText(true);
        usernameLabel.setPrefWidth(150); 
        passwordLabel.setPrefWidth(150);
        explanationPassword.setPrefWidth(150);
        phoneLabel.setPrefWidth(150);
        firstNameLabel.setPrefWidth(150);
        lastNameLabel.setPrefWidth(150);
        returnButton.setPrefWidth(150);
        registerButton.setPrefWidth(150);

        explanationPassword.setStyle("-fx-font-size: 7px;");
        registerGrid.setStyle("-fx-background-color:white;");
        usernameInput.setStyle("-fx-background-color: lightgray;");
        passwordInput.setStyle("-fx-background-color: lightgray;");
        phoneInput.setStyle("-fx-background-color: lightgray;");
        firstNameInput.setStyle("-fx-background-color: lightgray;");
        lastNameInput.setStyle("-fx-background-color: lightgray;");

        ImageView imageView = new ImageView(new Image("co\\edu\\uptc\\util\\logo-uptc.png"));
        imageView.setFitHeight(80);
        imageView.setFitWidth(150);
        GridPane.setHalignment(imageView, HPos.LEFT);
        ImageView imageSistemas=new ImageView(new Image("co\\edu\\uptc\\util\\logo-sistemas.png"));
        imageSistemas.setFitHeight(80);
        imageSistemas.setFitWidth(80);
        GridPane.setHalignment(imageSistemas, HPos.RIGHT);


        GridPane.setConstraints(imageView, 0, 0);
        GridPane.setConstraints(imageSistemas, 1, 0);
        GridPane.setConstraints(usernameLabel, 0, 1);
        GridPane.setConstraints(usernameInput, 1, 1);
        GridPane.setConstraints(passwordLabel, 0, 2);
        GridPane.setConstraints(passwordInput, 1, 2);
        GridPane.setConstraints(phoneLabel, 0, 3);
        GridPane.setConstraints(phoneInput, 1, 3);
        GridPane.setConstraints(firstNameLabel, 0, 4);
        GridPane.setConstraints(firstNameInput, 1, 4);
        GridPane.setConstraints(lastNameLabel, 0, 5);
        GridPane.setConstraints(lastNameInput, 1, 5);
        GridPane.setConstraints(returnButton, 0, 6);
        GridPane.setConstraints(registerButton, 1, 6);

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


        returnButton.setOnAction(e ->   primaryStage.setScene(new Scene(createLoginForm(primaryStage), 400, 300)));

        registerButton.setOnAction(e -> {
            String username = usernameInput.getText().trim();
            String password = passwordInput.getText().trim();
            String phone = phoneInput.getText().trim();
            String firstName = firstNameInput.getText().trim();
            String lastName = lastNameInput.getText().trim();
            String email=firstName.toLowerCase() +"." + lastName.toLowerCase() + "@uptc.edu.co";
            int number= (int) (1000 + Math.random() * 9000);
            String idInterno=username + number;
            String emailString=null;
            try {
                 emailString=loginController.crearCorreo(email);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            boolean isNumber=true;

            try {
                Integer.parseInt(phone);
            } catch (NumberFormatException exception) {
                isNumber = false;
            }

            String FILE_PATH = "co\\edu\\uptc\\persistence\\Usuarios.json";
            boolean isExist=false;
            if(usernameInput.getText().isEmpty() || phoneInput.getText().isEmpty() || firstNameInput.getText().isEmpty() || lastNameInput.getText().isEmpty())    {
                showAlert(Alert.AlertType.ERROR, "Error","Campos incompletos, por favor, complete todos los campos requeridos");
            }else{
                try {
                    ArrayList<Model> cuentasEstudiantes = new ArrayList<>(JsonFile.readFromJson(FILE_PATH));
                    for(Model model:cuentasEstudiantes) {
                        if (model.getUserName().equals(username)) {
                            isExist=true;
                        }
                    }
                    if(loginController.validarNombres(firstName,lastName))  {
                        
                        if(!isExist) {
                            if(isNumber)    {
                                try {
                                    loginController.registrarUsuario(emailString,idInterno, username,password,phone,firstName,lastName);
                                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Usuario registrado exitosamente.");
                                    primaryStage.setScene(new Scene(createLoginForm(primaryStage), 400, 300));
                                } catch (Exception ex) {
                                    showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
                                }
                            }else if(!isNumber)  {
                                showAlert(Alert.AlertType.ERROR, "Error"," Numero de Telefono Invalido");
        
                            }
                           
                        }else if (isExist){
                            showAlert(Alert.AlertType.ERROR, "Error","Nombre de usuario ya existente");
                        }
                    }else {
                        showAlert(Alert.AlertType.ERROR, "Error","Nombre o Apellido invalido");
                       
                    }
                    
                     
    
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

          

            
            
           
        });

        registerGrid.getChildren().addAll(imageView,imageSistemas,usernameLabel, usernameInput, passwordLabel, passwordInput, phoneLabel, phoneInput, firstNameLabel, firstNameInput, lastNameLabel, lastNameInput,returnButton, registerButton);
        
        

         HBox hBox = new HBox(registerGrid);
    hBox.setAlignment(Pos.CENTER);
   

         VBox vbox = new VBox(hBox);
    vbox.setAlignment(Pos.CENTER);  
    vbox.setPadding(new Insets(20));

        StackPane stackPane=new StackPane();
        ImageView backgroundImage=new ImageView(new Image("co\\edu\\uptc\\util\\imagen.png"));
        backgroundImage.setPreserveRatio(false); 
        

        //bagroundImage.setFitHeight(600);
        //bagroundImage.setFitWidth(800);

        backgroundImage.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundImage.fitHeightProperty().bind(primaryStage.heightProperty());

        stackPane.setCenterShape(true);
        stackPane.getChildren().addAll(backgroundImage,vbox);
        
       

        primaryStage.setScene(new Scene(stackPane, 800, 600));
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
    
        instructionLabel.setWrapText(true);
        instructionLabel.setPrefWidth(300);
        emailLabel.setPrefWidth(150);
        emailInput.setStyle("-fx-background-color: lightgray;");
        returnButton.setPrefWidth(150);
        recoverButton.setPrefWidth(150);
    
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
        GridPane.setConstraints(returnButton, 0, 3);
        GridPane.setConstraints(recoverButton, 1, 3);
    
        recoverGrid.setStyle("-fx-background-color:white;");
        GridPane.setHalignment(instructionLabel, HPos.CENTER);
        GridPane.setHalignment(emailLabel, HPos.CENTER);
        GridPane.setHalignment(returnButton, HPos.CENTER);
        GridPane.setValignment(returnButton, VPos.CENTER);
        GridPane.setHalignment(recoverButton, HPos.CENTER);
        GridPane.setValignment(recoverButton, VPos.CENTER);
    
        instructionLabel.setAlignment(Pos.CENTER_LEFT);
        emailLabel.setAlignment(Pos.CENTER_LEFT);
    
        returnButton.setOnAction(e -> primaryStage.setScene(new Scene(createLoginForm(primaryStage), 400, 300)));
    
        recoverButton.setOnAction(e -> {
            String email = emailInput.getText().trim();
            if (email.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "El campo de correo electrónico no puede estar vacío.");
            } else if (loginController.verificarCorreoExistente(email)) {
                int verificationCode = generarCodigoVerificacion();
                mostrarCodigoVerificacion(verificationCode);
                showVerificationCodeForm(primaryStage, email, verificationCode);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Correo electrónico no encontrado.");
            }
        });
    
        recoverGrid.getChildren().addAll(imageView, imageSistemas, instructionLabel, emailLabel, emailInput, returnButton, recoverButton);
    
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
    
        primaryStage.setScene(new Scene(stackPane, 800, 600));
    }
    
    private void showVerificationCodeForm(Stage primaryStage, String email, int verificationCode) {
        GridPane codeGrid = new GridPane();
        codeGrid.setPadding(new Insets(10));
        codeGrid.setHgap(10);
        codeGrid.setVgap(10);
    
        Label codeLabel = new Label("Código de verificación:");
        TextField codeInput = new TextField();
        Button verifyButton = new Button("Verificar");
        Button returnButton = new Button("Volver");
    
        codeLabel.setPrefWidth(150);
        codeInput.setStyle("-fx-background-color: lightgray;");
        returnButton.setPrefWidth(150);
        verifyButton.setPrefWidth(150);
    
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
        GridPane.setConstraints(returnButton, 0, 2);
        GridPane.setConstraints(verifyButton, 1, 2);
    
        codeGrid.setStyle("-fx-background-color:white;");
        GridPane.setHalignment(codeLabel, HPos.CENTER);
        GridPane.setHalignment(returnButton, HPos.CENTER);
        GridPane.setValignment(returnButton, VPos.CENTER);
        GridPane.setHalignment(verifyButton, HPos.CENTER);
        GridPane.setValignment(verifyButton, VPos.CENTER);
    
        codeLabel.setAlignment(Pos.CENTER_LEFT);
    
        returnButton.setOnAction(e -> primaryStage.setScene(new Scene(createLoginForm(primaryStage), 400, 300)));
    
        verifyButton.setOnAction(ev -> {
            String enteredCode = codeInput.getText().trim();
            if (enteredCode.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "El campo del código de verificación no puede estar vacío.");
            } else if (Integer.parseInt(enteredCode) == verificationCode) {
                showNewPasswordForm(primaryStage, email);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Código de verificación incorrecto.");
            }
        });
    
        codeGrid.getChildren().addAll(imageView, imageSistemas, codeLabel, codeInput, returnButton, verifyButton);
    
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
    
        primaryStage.setScene(new Scene(stackPane, 800, 600));
    }
    
    private void showNewPasswordForm(Stage primaryStage, String email) {
        GridPane passwordGrid = new GridPane();
        passwordGrid.setPadding(new Insets(10));
        passwordGrid.setHgap(10);
        passwordGrid.setVgap(10);
    
        Label newPasswordLabel = new Label("Nueva contraseña:");
        PasswordField newPasswordInput = new PasswordField();
        Label confirmPasswordLabel = new Label("Confirmar contraseña:");
        PasswordField confirmPasswordInput = new PasswordField();
        Button returnButton = new Button("Volver");
        Button saveButton = new Button("Guardar");
    
        // Label para mostrar los requisitos de la contraseña con fuente más pequeña
        Label passwordRequirementsLabel = new Label("La contraseña debe contar con mínimo 8 caracteres, una mayúscula, 2 números y un carácter especial.");
        passwordRequirementsLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;"); // Tamaño de fuente más pequeño y color gris
    
        newPasswordLabel.setPrefWidth(150);
        confirmPasswordLabel.setPrefWidth(150);
        newPasswordInput.setStyle("-fx-background-color: lightgray;");
        confirmPasswordInput.setStyle("-fx-background-color: lightgray;");
        returnButton.setPrefWidth(150);
        saveButton.setPrefWidth(150);
    
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
        GridPane.setConstraints(passwordRequirementsLabel, 0, 2, 2, 1); // Requiere 2 columnas de ancho
        GridPane.setConstraints(confirmPasswordLabel, 0, 3);
        GridPane.setConstraints(confirmPasswordInput, 1, 3);
        
        // Contenedor HBox para los botones
        HBox buttonBox = new HBox(10, returnButton, saveButton);
        buttonBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(buttonBox, 0, 4, 2, 1); // Requiere 2 columnas de ancho y se coloca en la fila 4
    
        passwordGrid.setStyle("-fx-background-color:white;");
        GridPane.setHalignment(newPasswordLabel, HPos.CENTER);
        GridPane.setHalignment(confirmPasswordLabel, HPos.CENTER);
        GridPane.setHalignment(passwordRequirementsLabel, HPos.CENTER);
        GridPane.setHalignment(buttonBox, HPos.CENTER);
    
        newPasswordLabel.setAlignment(Pos.CENTER_LEFT);
        confirmPasswordLabel.setAlignment(Pos.CENTER_LEFT);
        passwordRequirementsLabel.setAlignment(Pos.CENTER);
    
        returnButton.setOnAction(e -> primaryStage.setScene(new Scene(createLoginForm(primaryStage), 400, 300)));
    
        saveButton.setOnAction(e -> {
            String newPassword = newPasswordInput.getText().trim();
            String confirmPassword = confirmPasswordInput.getText().trim();
    
            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Los campos de contraseña no pueden estar vacíos.");
            } else if (!newPassword.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Las contraseñas no coinciden.");
            } else if (!loginController.verificarContraseña(newPassword)) {
                showAlert(Alert.AlertType.ERROR, "Error", "La nueva contraseña no cumple los requisitos.");
            } else {
                try {
                    loginController.actualizarContraseña(email, newPassword);
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Contraseña cambiada exitosamente.");
                    primaryStage.setScene(new Scene(createLoginForm(primaryStage), 400, 300));
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
                }
            }
        });
    
        passwordGrid.getChildren().addAll(imageView, imageSistemas, newPasswordLabel, newPasswordInput, passwordRequirementsLabel, confirmPasswordLabel, confirmPasswordInput, buttonBox);
    
        HBox hBox = new HBox(passwordGrid);
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
    
        primaryStage.setScene(new Scene(stackPane, 800, 600));
    }
    
    

    private void mostrarCodigoVerificacion(int verificationCode) {
        Stage codeStage = new Stage();
        codeStage.setTitle("Código de Verificación");

        GridPane codeGrid = new GridPane();
        codeGrid.setPadding(new Insets(10));
        codeGrid.setHgap(10);
        codeGrid.setVgap(10);

        Label codeLabel = new Label("Tu código de verificación es:");
        Label codeValue = new Label(String.valueOf(verificationCode));
        Button closeButton = new Button("Cerrar");

        GridPane.setConstraints(codeLabel, 0, 0);
        GridPane.setConstraints(codeValue, 1, 0);
        GridPane.setConstraints(closeButton, 1, 1);

        closeButton.setOnAction(e -> codeStage.close());

        codeGrid.getChildren().addAll(codeLabel, codeValue, closeButton);

        Scene codeScene = new Scene(codeGrid, 300, 200);
        codeStage.setScene(codeScene);
        codeStage.show();
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
