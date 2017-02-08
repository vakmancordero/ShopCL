package shopcl.admon;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import shopcl.model.User;
import shopcl.utils.Shop;
import shopcl.utils.ValidatorUtil;


/**
 *
 * @author Arturh
 */
public class AdmonController implements Initializable {
    
    @FXML
    private TableView<User> usersTV;
    
    @FXML
    private TextField usernameTF;
    
    @FXML
    private PasswordField passwordPF, rePasswordPF;
    
    private ValidatorUtil validator;
    
    private Shop shop;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.shop = new Shop();
        
        this.validator = new ValidatorUtil(
                usernameTF,
                passwordPF,
                rePasswordPF
        );
        
    }
    
    @FXML
    private void create() {
        
        if (this.validator.validateFields()) {
            
            String username = this.usernameTF.getText();
            String password = this.passwordPF.getText();
            String rePassword = this.rePasswordPF.getText();
            
            if (password.equals(rePassword)) {
                
                User user = new User(username, password);
                
                boolean saved = this.shop.save(user);
                
                if (saved) {
                    
                    new Alert(
                            AlertType.INFORMATION,
                            "Usuario almacenado correctamente"
                    ).show();
                    
                } else {
                    
                    new Alert(
                            AlertType.ERROR,
                            "No se ha podido almacenar el usuario"
                    ).show();
                    
                }
                
            } else {
                
                
                
            }
            
        } else {
            
            this.validator.emptyFields().showAndWait();
            
        }
        
    }
    
}