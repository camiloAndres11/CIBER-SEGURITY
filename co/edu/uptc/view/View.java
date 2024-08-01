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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
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

        Image icon = new Image("co\\edu\\uptc\\util\\logo_uptc.jpeg");
        primaryStage.getIcons().add(icon);

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
        primaryStage.setMaximized(true);
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
    Label lastNameLabel = new Label("Apellido:");
    TextField lastNameInput = new TextField();
    Button returnButton = new Button("Volver");
    Button registerButton = new Button("Registrar");
    Label messageError = new Label();

    messageError.setStyle("-fx-text-fill: red;");

    explanationPassword.setWrapText(true);
    explanationPassword.setMaxWidth(Double.MAX_VALUE);
    usernameLabel.setPrefWidth(150);
    passwordLabel.setPrefWidth(150);
    explanationPassword.setPrefWidth(300);
    phoneLabel.setPrefWidth(150);
    firstNameLabel.setPrefWidth(150);
    lastNameLabel.setPrefWidth(150);
    returnButton.setPrefWidth(100);
    registerButton.setPrefWidth(100);

    messageError.setWrapText(true);
    messageError.setPrefWidth(300);

    explanationPassword.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");
    registerGrid.setStyle("-fx-background-color:white;");
    usernameInput.setStyle("-fx-background-color: lightgray;");
    passwordInput.setStyle("-fx-background-color: lightgray;");
    phoneInput.setStyle("-fx-background-color: lightgray;");
    firstNameInput.setStyle("-fx-background-color: lightgray;");
    lastNameInput.setStyle("-fx-background-color: lightgray;");
    comfirmPassword.setStyle("-fx-background-color: lightgray;");
    registerButton.setStyle("-fx-background-color: #4299d6; -fx-text-fill: white;");
    returnButton.setStyle("-fx-background-color: gray; -fx-text-fill: white;");  // Cambia el color del botón de volver a gris y el texto a negro

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
    GridPane.setConstraints(explanationPassword, 0, 4, 2, 1);
    GridPane.setConstraints(phoneLabel, 0, 5);
    GridPane.setConstraints(phoneInput, 1, 5);
    GridPane.setConstraints(firstNameLabel, 0, 6);
    GridPane.setConstraints(firstNameInput, 1, 6);
    GridPane.setConstraints(lastNameLabel, 0, 7);
    GridPane.setConstraints(lastNameInput, 1, 7);
    GridPane.setConstraints(returnButton, 0, 9);
    GridPane.setConstraints(registerButton, 1, 9);
    GridPane.setConstraints(messageError, 0, 8, 2, 1);

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
    explanationPassword.setAlignment(Pos.CENTER_LEFT);
    messageError.setAlignment(Pos.CENTER_LEFT);
        


        returnButton.setOnAction(e -> {
            Scene loginScene = new Scene(createLoginForm(primaryStage));
            applyStyles(loginScene);
            primaryStage.setScene(loginScene);
            primaryStage.setMaximized(true);
        });

        registerButton.setOnAction(e -> {

            usernameInput.setStyle("-fx-background-color: lightgray;");

        passwordInput.setStyle("-fx-background-color: lightgray;");
        phoneInput.setStyle("-fx-background-color: lightgray;");
        firstNameInput.setStyle("-fx-background-color: lightgray;");
        lastNameInput.setStyle("-fx-background-color: lightgray;");
        comfirmPassword.setStyle("-fx-background-color: lightgray;");
            messageError.setText("");

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
                Double.parseDouble(phone);
            } catch (NumberFormatException exception) {
                isNumber = false;
            }

            String FILE_PATH = "co\\edu\\uptc\\persistence\\Usuarios.json";
            boolean isExist = false;
            boolean passwordExist=false;
            if (usernameInput.getText().trim().isEmpty()) {

                usernameInput.setStyle("-fx-background-color: lightcoral;");
                messageError.setText("⚠ El campo de nombre de usuario no puede estar vacío. Por favor, ingréselo.");

            }else if ( phoneInput.getText().isEmpty())   {
                phoneInput.setStyle("-fx-background-color: lightcoral;");
                messageError.setText("⚠ El campo de telefono no puede estar vacío. Por favor, ingréselo.");

            }else if ( firstNameInput.getText().isEmpty())   {

                firstNameInput.setStyle("-fx-background-color: lightcoral;");
                messageError.setText("⚠ El campo de nombre no puede estar vacío. Por favor, ingréselo.");

            }else if ( lastNameInput.getText().isEmpty())   {
                lastNameInput.setStyle("-fx-background-color: lightcoral;");
                messageError.setText("⚠ El campo de apellido no puede estar vacío. Por favor, ingréselo.");
            
            }else if ( passwordInput.getText().trim().isEmpty())   {
                passwordInput.setStyle("-fx-background-color: lightcoral;");
                messageError.setText("⚠ El campo de contraseña no puede estar vacío. Por favor, ingréselo.");

            }else if( comfirmPassword.getText().trim().isEmpty()){
                comfirmPassword.setStyle("-fx-background-color: lightcoral;");
                messageError.setText("⚠ El campo de contraseña no puede estar vacío. Por favor, ingréselo.");

            }else {
                try {
                    ArrayList<Model> cuentasEstudiantes = new ArrayList<>(JsonFile.readFromJson(FILE_PATH));
                    for (Model model : cuentasEstudiantes) {
                        if (model.getUserName().equals(username)) {
                            isExist = true;
                        }
                        
                        if (model.getContraseña().equals(password)){
                            passwordExist=true;
                            
                        }
                            
                        
                    }

                    if (password.equals(passwordComfirm)) {

                        if (firstName.matches("[a-zA-Z]+") ) {

                            if (lastName.matches("[a-zA-Z]+")) {
                                if (!isExist) {

                                    if(!passwordExist)  {

                                        for (int i = 0; i < phone.length(); i++) {
                                            contar = contar + 1;

                                        }

                                        if (contar == 10) {


                                            if (firstName.length()>=3) {

                                                if (lastName.length()>=3) {

                                                    if (isNumber) {
                                                        try {
                                                            loginController.registrarUsuario(emailString, idInterno, username, password, phone, firstName, lastName);
                                                            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Usuario registrado exitosamente.");
                                                            showRegisterUser(primaryStage, username, emailString, password, phone, firstName, lastName);
                                                            primaryStage.setScene(new Scene(createLoginForm(primaryStage)));
                                                            primaryStage.setMaximized(true);
                                                            primaryStage.show();
                                                            System.out.println(phone);

                                                        } catch (Exception ex) {
                                                            showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
                                                        }
                                                    } else if (!isNumber) {
                                                        phoneInput.setStyle("-fx-background-color: lightcoral;");
                                                        messageError.setText("Numero de telefono invalido, ingrese solo numeros");
                                                    }



                                                }else if (lastName.length()<3) {
                                                    lastNameInput.setStyle("-fx-background-color: lightcoral;");
                                                    messageError.setText("El apellido debe tener mas de 2 letras");

                                                }

                                            }else if (firstName.length()<3) {
                                                firstNameInput.setStyle("-fx-background-color: lightcoral;");
                                                messageError.setText("El nombre debe tener mas de 3 letras");

                                            }


                                        } else if (contar < 10 || contar > 10) {

                                            phoneInput.setStyle("-fx-background-color: lightcoral;");
                                            messageError.setText("El numero de telefono debe tener solo 10 caracteres");
                                        }


                                    }else if(passwordExist) {
                                        comfirmPassword.setStyle("-fx-background-color: lightcoral;");
                                        passwordInput.setStyle("-fx-background-color: lightcoral;");
                                        messageError.setText("Contraseña ya existentes");

                                    }



                                } else if (isExist) {
                                    usernameInput.setStyle("-fx-background-color: lightcoral;");
                                    messageError.setText("Nombre de usuario ya existente");
                                }



                            }else   {
                                lastNameInput.setStyle("-fx-background-color: lightcoral;");
                                messageError.setText("Apellido invalido, solo debe contener letras");

                            }


                        } else {

                            firstNameInput.setStyle("-fx-background-color: lightcoral;");
                            messageError.setText("Nombre invalido, solo debe contener letras");

                        }

                    } else {

                        comfirmPassword.setStyle("-fx-background-color: lightcoral;");
                        passwordInput.setStyle("-fx-background-color: lightcoral;");
                        messageError.setText("Las contraseñas no coinciden");
                    }



                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }


        });

        registerGrid.getChildren().addAll(imageView, imageSistemas, usernameLabel, usernameInput, passwordLabel, explanationPassword, comfirmPaswordLabel, comfirmPassword, passwordInput, phoneLabel, phoneInput, firstNameLabel, firstNameInput, lastNameLabel, lastNameInput, returnButton, registerButton, messageError);

    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(50);
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(50);
    registerGrid.getColumnConstraints().addAll(col1, col2);

    HBox hBox = new HBox(registerGrid);
    hBox.setAlignment(Pos.CENTER);
    hBox.setPadding(new Insets(0)); // Ajustar el padding si es necesario

    VBox vbox = new VBox(hBox);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(20));

    StackPane stackPane = new StackPane();
    ImageView backgroundImage = new ImageView(new Image("co\\edu\\uptc\\util\\imagen.png"));
    backgroundImage.setPreserveRatio(false);
    backgroundImage.fitWidthProperty().bind(primaryStage.widthProperty());
    backgroundImage.fitHeightProperty().bind(primaryStage.heightProperty());

    stackPane.getChildren().addAll(backgroundImage, vbox);

    primaryStage.setScene(new Scene(stackPane));
    primaryStage.setMaximized(true);
    primaryStage.show();
    }

    private void showRegisterUser(Stage primaryStage, String userName, String email, String password, String phone, String firstName, String LastName) {

        Stage userInfoStage = new Stage();
    userInfoStage.initModality(Modality.APPLICATION_MODAL);
    userInfoStage.setTitle("Información del Usuario");

    Image icon = new Image("co\\edu\\uptc\\util\\logo_uptc.jpeg");
   primaryStage.getIcons().add(icon);

   userInfoStage.setResizable(false);

    GridPane userRegistrerInfo = new GridPane();
    userRegistrerInfo.setPadding(new Insets(10));
    userRegistrerInfo.setHgap(10);
    userRegistrerInfo.setVgap(10);

    Label userDates=new Label("DATOS REGISTRADOS");
    Label usernameLabel = new Label("Nombre de usuario:");
    Label usernameValue = new Label(userName);
    Label emailLabel = new Label("Correo electronico:");
    Label emailValue = new Label(email);
    Label passwordLabel = new Label("Contraseña:");
    Label passwordValue = new Label(password);
    Label phoneLabel = new Label("Número de teléfono:");
    Label phoneValue = new Label(phone);
    Label firstNameLabel = new Label("Nombre:");
    Label firstNameValue = new Label(firstName);
    Label lastNameLabel = new Label("Apellido:");
    Label lastNameValue = new Label(LastName);

    usernameLabel.setStyle("-fx-font-weight: bold;-fx-background-color: lightgray; border-color: black; -fx-border-width: 10px; -fx-padding: 5px;");
    emailLabel.setStyle("-fx-font-weight: bold;-fx-background-color: lightgray; border-color: black; -fx-border-width: 10px; -fx-padding: 5px;");
    passwordLabel.setStyle("-fx-font-weight: bold;-fx-background-color: lightgray; border-color: black; -fx-border-width: 10px; -fx-padding: 5px;");
    phoneLabel.setStyle("-fx-font-weight: bold;-fx-background-color: lightgray; border-color: black; -fx-border-width: 10px; -fx-padding: 5px;");
    firstNameLabel.setStyle("-fx-font-weight: bold;-fx-background-color: lightgray; border-color: black; -fx-border-width: 10px; -fx-padding: 5px;");
    lastNameLabel.setStyle("-fx-font-weight: bold;-fx-background-color: lightgray; border-color: black; -fx-border-width: 10px; -fx-padding: 5px;");

    usernameLabel.setPrefWidth(150);
    emailLabel.setPrefWidth(150);
    passwordLabel.setPrefWidth(150);
    phoneLabel.setPrefWidth(150);
    firstNameLabel.setPrefWidth(150);
    lastNameLabel.setPrefWidth(150);




    usernameValue.setStyle("-fx-font-weight: bold;-fx-background-color: lightgray; border-color: black; -fx-border-width: 10px; -fx-padding: 5px;");
    usernameValue.setPrefWidth(150);
    emailValue.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray; border-color: black; -fx-border-width: 10px; -fx-padding: 5px;");
    emailValue.setPrefWidth(150);
    passwordValue.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray; border-color: black; -fx-border-width: 10px; -fx-padding: 5px;");
    passwordValue.setPrefWidth(150);
    phoneValue.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray; border-color: black; -fx-border-width: 10px; -fx-padding: 5px;");
    phoneValue.setPrefWidth(150);
    firstNameValue.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray; border-color: black; -fx-border-width: 10px; -fx-padding: 5px;");
    firstNameValue.setPrefWidth(150);
    lastNameValue.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray; border-color: black; -fx-border-width: 10px; -fx-padding: 5px;");
    lastNameValue.setPrefWidth(150);

    userDates.setStyle("-fx-font-weight: bold; -fx-font-weight: bold; -fx-font-size: 16px; -fx-underline: true; -fx-background-color: white;");
    userDates.setPrefWidth(200);
    userRegistrerInfo.add(userDates, 0, 0);
    GridPane.setColumnSpan(userDates, 2);
    GridPane.setHalignment(userDates, HPos.CENTER);
    

    userRegistrerInfo.add(usernameLabel, 0, 1);
    userRegistrerInfo.add(usernameValue, 1, 1);
    userRegistrerInfo.add(emailLabel, 0, 2);
    userRegistrerInfo.add(emailValue, 1, 2);
    userRegistrerInfo.add(passwordLabel, 0, 3);
    userRegistrerInfo.add(passwordValue, 1, 3);
    userRegistrerInfo.add(phoneLabel, 0, 4);
    userRegistrerInfo.add(phoneValue, 1, 4);
    userRegistrerInfo.add(firstNameLabel, 0, 5);
    userRegistrerInfo.add(firstNameValue, 1, 5);
    userRegistrerInfo.add(lastNameLabel, 0, 6);
    userRegistrerInfo.add(lastNameValue, 1, 6);



    Button button = new Button("Aceptar");
    
    button.setStyle("-fx-background-color: #4299d6;");
    button.setPrefWidth(100);
    userRegistrerInfo.add(button, 0, 7 , 1, 7); 
    GridPane.setColumnSpan(button, 2);
    GridPane.setHalignment(button, HPos.CENTER); 
    button.setOnAction(e -> userInfoStage.close());

    

    
    
    ImageView backgroundImage = new ImageView(new Image("co\\edu\\uptc\\util\\logo_uptc.jpeg"));
   // backgroundImage.setPreserveRatio(false);
backgroundImage.setFitHeight(350);
backgroundImage.setFitWidth(300);
   // backgroundImage.fitWidthProperty().bind(primaryStage.widthProperty());
    //backgroundImage.fitHeightProperty().bind(primaryStage.heightProperty());

    StackPane stackPane=new StackPane();
    stackPane.getChildren().addAll(backgroundImage,userRegistrerInfo);
    

    Scene userInfoScene = new Scene( stackPane, 350, 450);
    userInfoStage.setScene(userInfoScene);
    userInfoStage.sizeToScene();
    userInfoStage.show();

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
            } else if (!loginController.verificarContraseña(newPassword)) {
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


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void mostrarCodigoVerificacion(int verificationCode) {
        Stage codeStage = new Stage();
        codeStage.setTitle("Código de Verificación");
    
        // Añadir el icono personalizado a la ventana
        codeStage.getIcons().add(new Image("co\\edu\\uptc\\util\\logo_uptc.jpeg"));
    
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
