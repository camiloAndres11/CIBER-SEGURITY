package co.edu.uptc.view;

import co.edu.uptc.controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class View extends Application {

    private Controller loginController = new Controller();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Gestión UPTC");

        GridPane loginGrid = createLoginForm(primaryStage);
        Scene loginScene = new Scene(loginGrid, 400, 300);

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private GridPane createLoginForm(Stage primaryStage) {
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(10));
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);

        Label usernameLabel = new Label("Nombre de usuario:");
        TextField usernameInput = new TextField();
        Label passwordLabel = new Label("Contraseña:");
        PasswordField passwordInput = new PasswordField();
        Button loginButton = new Button("Iniciar Sesión");
        Button registerButton = new Button("Registrarse");
        Button recoverButton = new Button("Recuperar Contraseña");

        GridPane.setConstraints(usernameLabel, 0, 0);
        GridPane.setConstraints(usernameInput, 1, 0);
        GridPane.setConstraints(passwordLabel, 0, 1);
        GridPane.setConstraints(passwordInput, 1, 1);
        GridPane.setConstraints(loginButton, 1, 2);
        GridPane.setConstraints(registerButton, 1, 3);
        GridPane.setConstraints(recoverButton, 1, 4);

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

        loginGrid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton, registerButton, recoverButton);

        return loginGrid;
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

        logoutButton.setOnAction(e -> primaryStage.setScene(new Scene(createLoginForm(primaryStage), 400, 300)));

        mainGrid.getChildren().addAll(welcomeLabel, logoutButton);

        Scene mainScene = new Scene(mainGrid, 400, 300);
        primaryStage.setScene(mainScene);
    }

    private void showRegisterForm(Stage primaryStage) {
        GridPane registerGrid = new GridPane();
        registerGrid.setPadding(new Insets(10));
        registerGrid.setHgap(10);
        registerGrid.setVgap(10);

        Label usernameLabel = new Label("ID de usuario:");
        TextField usernameInput = new TextField();
        Label passwordLabel = new Label("Contraseña:");
        PasswordField passwordInput = new PasswordField();
        Label phoneLabel = new Label("Número de teléfono:");
        TextField phoneInput = new TextField();
        Label firstNameLabel = new Label("Nombre:");
        TextField firstNameInput = new TextField();
        Label lastNameLabel = new Label("Apellidos:");
        TextField lastNameInput = new TextField();
        Button registerButton = new Button("Registrar");

        GridPane.setConstraints(usernameLabel, 0, 0);
        GridPane.setConstraints(usernameInput, 1, 0);
        GridPane.setConstraints(passwordLabel, 0, 1);
        GridPane.setConstraints(passwordInput, 1, 1);
        GridPane.setConstraints(phoneLabel, 0, 2);
        GridPane.setConstraints(phoneInput, 1, 2);
        GridPane.setConstraints(firstNameLabel, 0, 3);
        GridPane.setConstraints(firstNameInput, 1, 3);
        GridPane.setConstraints(lastNameLabel, 0, 4);
        GridPane.setConstraints(lastNameInput, 1, 4);
        GridPane.setConstraints(registerButton, 1, 5);

        registerButton.setOnAction(e -> {
            String username = usernameInput.getText().trim();
            String password = passwordInput.getText().trim();
            String phone = phoneInput.getText().trim();
            String firstName = firstNameInput.getText().trim();
            String lastName = lastNameInput.getText().trim();

            try {
                loginController.registrarUsuario(username, password, phone, firstName, lastName, lastName, lastName);
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Usuario registrado exitosamente.");
                primaryStage.setScene(new Scene(createLoginForm(primaryStage), 400, 300));
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        registerGrid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, phoneLabel, phoneInput, firstNameLabel, firstNameInput, lastNameLabel, lastNameInput, registerButton);

        primaryStage.setScene(new Scene(registerGrid, 400, 300));
    }

    private void showRecoverPasswordForm(Stage primaryStage) {
    GridPane recoverGrid = new GridPane();
    recoverGrid.setPadding(new Insets(10));
    recoverGrid.setHgap(10);
    recoverGrid.setVgap(10);

    Label emailLabel = new Label("Correo electrónico:");
    TextField emailInput = new TextField();
    Button recoverButton = new Button("Recuperar");

    GridPane.setConstraints(emailLabel, 0, 0);
    GridPane.setConstraints(emailInput, 1, 0);
    GridPane.setConstraints(recoverButton, 1, 1);

    recoverButton.setOnAction(e -> {
        String email = emailInput.getText().trim();
        if (loginController.verificarCorreoExistente(email)) {
            int verificationCode = generarCodigoVerificacion();
            mostrarCodigoVerificacion(verificationCode);

            // Crear una nueva ventana para ingresar el código de verificación
            Stage codeStage = new Stage();
            codeStage.setTitle("Ingresar código de verificación");

            GridPane codeGrid = new GridPane();
            codeGrid.setPadding(new Insets(10));
            codeGrid.setHgap(10);
            codeGrid.setVgap(10);

            Label codeLabel = new Label("Código de verificación:");
            TextField codeInput = new TextField();
            Button verifyButton = new Button("Verificar");

            GridPane.setConstraints(codeLabel, 0, 0);
            GridPane.setConstraints(codeInput, 1, 0);
            GridPane.setConstraints(verifyButton, 1, 1);

            codeGrid.getChildren().addAll(codeLabel, codeInput, verifyButton);
            Scene codeScene = new Scene(codeGrid, 300, 200);
            codeStage.setScene(codeScene);
            codeStage.show();

            verifyButton.setOnAction(ev -> {
                String enteredCode = codeInput.getText().trim();
                if (Integer.parseInt(enteredCode) == verificationCode) {
                    codeStage.close();

                    Dialog<ButtonType> passwordDialog = new Dialog<>();
                    passwordDialog.setTitle("Nueva contraseña");

                    GridPane passwordGrid = new GridPane();
                    passwordGrid.setPadding(new Insets(10));
                    passwordGrid.setHgap(10);
                    passwordGrid.setVgap(10);

                    Label newPasswordLabel = new Label("Nueva contraseña:");
                    PasswordField newPasswordInput = new PasswordField();
                    Label confirmPasswordLabel = new Label("Confirmar contraseña:");
                    PasswordField confirmPasswordInput = new PasswordField();

                    GridPane.setConstraints(newPasswordLabel, 0, 0);
                    GridPane.setConstraints(newPasswordInput, 1, 0);
                    GridPane.setConstraints(confirmPasswordLabel, 0, 1);
                    GridPane.setConstraints(confirmPasswordInput, 1, 1);

                    passwordGrid.getChildren().addAll(newPasswordLabel, newPasswordInput, confirmPasswordLabel, confirmPasswordInput);

                    passwordDialog.getDialogPane().setContent(passwordGrid);
                    passwordDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                    // Personalizar el comportamiento del botón OK
                    Button okButton = (Button) passwordDialog.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.addEventFilter(ActionEvent.ACTION, event -> {
                        String newPassword = newPasswordInput.getText().trim();
                        String confirmPassword = confirmPasswordInput.getText().trim();

                        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Los campos de contraseña no pueden estar vacíos.");
                            event.consume();
                        } else if (!newPassword.equals(confirmPassword)) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Las contraseñas no coinciden.");
                            event.consume();
                        } else if (!loginController.verificarContraseña(newPassword)) {
                            showAlert(Alert.AlertType.ERROR, "Error", "La nueva contraseña no cumple los requisitos.");
                            event.consume();
                        } else {
                            try {
                                loginController.actualizarContraseña(email, newPassword);
                                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Contraseña cambiada exitosamente.");
                                primaryStage.setScene(new Scene(createLoginForm(primaryStage), 400, 300));
                            } catch (Exception ex) {
                                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
                                event.consume();
                            }
                        }
                    });

                    passwordDialog.showAndWait();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Código de verificación incorrecto.");
                }
            });
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Correo electrónico no encontrado.");
        }
    });

    recoverGrid.getChildren().addAll(emailLabel, emailInput, recoverButton);

    primaryStage.setScene(new Scene(recoverGrid, 400, 300));
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
}

