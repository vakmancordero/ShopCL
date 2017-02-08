package shopcl.admon;

import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import shopcl.login.LoginController;

import shopcl.model.User;
import shopcl.utils.Shop;
import shopcl.utils.ValidatorUtil;


/**
 *
 * @author Arturh
 */
public class AdmonController implements Initializable {
    
    @FXML
    private Accordion accordion;
    
    @FXML
    private TitledPane createPane, editPane;
    
    @FXML
    private TableView<User> usersTV;
    
    private ObservableList<User> usersList;
    
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
        
        this.initTV();
        this.initAll();
        
    }
    
    private void initTV() {
        
        this.usersList = FXCollections.observableArrayList();
        this.usersTV.setItems(this.usersList);
        
        this.usersList.setAll(
                this.shop.getList(User.class)
        );
        
        if (!this.usersList.isEmpty()) {
            this.usersTV.getSelectionModel().selectFirst();
        }
        
    }
    
    private void initAll() {
        
        this.accordion.setExpandedPane(createPane);
        
    }
    
    @FXML
    private void save() {
        
        if (this.validator.validateFields()) {
            
            String username = this.usernameTF.getText();
            String password = this.passwordPF.getText();
            String rePassword = this.rePasswordPF.getText();
            
            if (password.equals(rePassword)) {
                
                User user = new User(username, password);
                
                int binarySearch = Collections.binarySearch(LoginController.users, user);

                if (binarySearch < 0) {
                    
                    boolean saved = this.shop.saveOrUpdate(user);
                    
                    if (saved) {

                        this.usersList.add(user);
                        this.validator.clearFields();

                        new Alert(
                                AlertType.INFORMATION,
                                "Usuario almacenado correctamente"
                        ).show();

                    } else {

                        new Alert(
                                AlertType.ERROR,
                                "No se ha podido almacenar el usuario, "
                              + "por favor intente con diferente nombre de usuario"
                        ).show();

                    }

                } else {

                    new Alert(
                            AlertType.ERROR,
                            "Error, intente con otro usuario, "
                          + "el nombre de usuario no puede ser utilizado."
                    ).show();

                }
                
            } else {
                
                new Alert(
                        AlertType.ERROR,
                        "Error, las contraseñas no coinciden, vuelva intentarlo"
                ).show();
                
            }
            
        } else {
            this.validator.emptyFields().showAndWait();
        }
        
    }
    
    @FXML
    private void update() {
        
    }
    
    @FXML
    private void context(ActionEvent event) {
        
        MenuItem menuItem = (MenuItem) event.getSource();
        
        String item = menuItem.getText();
            
        if (item.equals("Crear")) {

            this.accordion.setExpandedPane(this.createPane);

        } else {

            if (item.equals("Editar")) {
                
                this.accordion.setExpandedPane(this.editPane);

            } else {

                if (item.equals("Eliminar")) {
                    
                    this.deleteUser();
                    
                }

            }

        }
        
    }
    
    private void deleteUser() {
        
        User user = this.usersTV.getSelectionModel().getSelectedItem();
        
        if (user != null) {
            
            Alert confirmation = new Alert(AlertType.WARNING);
            
            confirmation.setTitle("Confirmacion de eliminación");
            confirmation.setHeaderText("Está seguro?");
            confirmation.setContentText(
                    "Desea eliminar al usuario " + user + "?"
            );
            
            Optional<ButtonType> option = confirmation.showAndWait();
            
            if (option.get() == ButtonType.OK) {
                
                this.shop.delete(user);
                
                this.usersList.remove(user);
                
                if (this.usersList.isEmpty()) {
                    //Something
                }
                
                new Alert(
                        AlertType.INFORMATION, 
                        "Usuario eliminado"
                ).show();
                
            }
            
        }
        
    }
    
}