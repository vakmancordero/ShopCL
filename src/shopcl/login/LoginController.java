package shopcl.login;

import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;

import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

import shopcl.model.User;
import shopcl.utils.Shop;
import shopcl.utils.ValidatorUtil;
import shopcl.utils.comparator.LoginUserComparator;

/**
 *
 * @author Arturh
 */
public class LoginController implements Initializable {
    
    @FXML
    private TextField userTF;
    
    @FXML
    private PasswordField passwordPF;
    
    private ValidatorUtil validator;
    
    private Shop shop;
    
    public static ArrayList<User> users;
    
    static {
        
        LoginController.users = new ArrayList<>(
                Arrays.asList(
                        new User("vaksfk", "jaqart_"),
                        new User("root", "jejeasdasdasd"),
                        new User("arturh", "corderoasdasasd")
                )
        );
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.shop = new Shop();
        
        this.validator = new ValidatorUtil(userTF, passwordPF);
        
        Collections.sort(LoginController.users);
        
    }
    
    @FXML
    private void login(ActionEvent event) throws IOException {
        
        String user = this.userTF.getText();
        String password = this.passwordPF.getText();
        
        if (this.validator.validateFields()) {
            
            User loginUser = new User(user, password);
            
            int binarySearch = Collections.binarySearch(
                    LoginController.users, loginUser, new LoginUserComparator()
            );
            
            if (binarySearch >= 0) {
                
                openFXML("/shopcl/admon/AdmonFXML.fxml", "Administrador", true);
                
                this.close(event);
                
            } else {
                
                if (this.shop.login(user, password)) {
                    
                    openFXML("/shopcl/ShopFXML.fxml", "Tienda", true);
                    
                    this.close(event);

                } else {
                    notFound();
                }
                
            }
            
        } else {
            this.validator.emptyFields().showAndWait();
        }
        
    }
    
    private void notFound() {
        
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("No se ha podido iniciar sesión");
        alert.setContentText("Usuario y/o contraseña incorrectos...");
        alert.showAndWait();
        
    }
    
    private void close(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
    private void openFXML(String fxml, String title, boolean flag) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml)); 
       
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane) loader.load()));
        
        if (flag) {
            
            stage.setOnCloseRequest((WindowEvent event) -> {
                
                try {
                    
                    openFXML("/shopcl/login/LoginFXML.fxml", "Login", false);
                    
                } catch (IOException ex) {
                    
                }
                
            });
            
        }
        
        stage.setTitle(title);
        stage.show();
    }
    
}