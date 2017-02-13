package shopcl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Arturh
 */
public class ShopCL extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/shopcl/login/LoginFXML.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/shopcl/ShopFXML.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Iniciar sesión");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
