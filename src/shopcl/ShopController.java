package shopcl;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.layout.HBox;
import javafx.util.Callback;
import shopcl.beans.Order;
import shopcl.model.Product;
import shopcl.utils.AutoComplete;
import shopcl.utils.Shop;
import shopcl.utils.ValidatorUtil;

/**
 *
 * @author Arturh
 */
public class ShopController implements Initializable {
    
    @FXML
    private Button actionButton, cancelButton;
    
    @FXML
    private Label salesNumberLabel, hourLabel, addressLabel;
    
    @FXML
    private TextField productNameTF, quantityTF;
    
    @FXML
    private HBox toolBar;
    
    @FXML
    private TableView<Order> ordersTV;
    
    @FXML
    private TableColumn editTC, removeTC;
    
    private ObservableList<Order> ordersList;
    
    private AutoComplete autoComplete;
    
    private Shop shop;
    
    private ValidatorUtil validatorUtil;
    
    private int counter;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.shop = new Shop();
        this.counter = 0;
        
        this.ordersList = FXCollections.observableArrayList();
        this.ordersTV.setItems(this.ordersList);
        
        this.initAutoComplete();
        this.initButtons();
        this.initValidator();
        
    }
    
    private void initAutoComplete() {
        
        List<Product> products = this.shop.getList(Product.class);
        
        this.autoComplete = new AutoComplete(products);
        
        ObservableList<Node> children = this.toolBar.getChildren();
        children.set(0, this.autoComplete);
        
    }
    
    private void initButtons() {
        
        this.quantityTF.textProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                if (!newValue.matches("\\d*")) {
                    
                    quantityTF.setText(oldValue);
                    
                }
            }
            
        });;
        
        Callback<TableColumn<Order, String>, TableCell<Order, String>> buttonFactory = 
                (TableColumn<Order, String> value) -> {
                    
            TableCell<Order, String> cell = new TableCell<Order, String>() {

                @Override
                protected void updateItem(String item, boolean empty) {
                    
                    String action = tableColumnProperty().get().getText();
                    Button button = new Button(action);
                    
                    if (action.equalsIgnoreCase("editar")) {
                        
                        
                    } else {
                        
                        if (action.equalsIgnoreCase("remover")) {
                            
                            
                            
                        }
                        
                    }
                    super.updateItem(item, empty);

                    if (empty) {

                        super.setGraphic(null);
                        super.setText(null);

                    } else {

                        int index = super.getIndex();
                        Order order = getTableView().getItems().get(index);

                        button.setOnAction((ActionEvent event) -> {
                            
                            if (action.equalsIgnoreCase("editar")) {
                                
                                autoComplete.setCurrentProduct(order.getProduct());
                                autoComplete.setText(order.getProduct().getName());
                                quantityTF.setText(Integer.toString(order.getQuantity()));
                                actionButton.setText("Editar");
                                cancelButton.setVisible(true);
                                
                            } else {
                                
                                if (action.equalsIgnoreCase("remover")) {
                                    
                                    quantityTF.clear();
                                    autoComplete.clear();
                                    autoComplete.setCurrentProduct(null);
                                    actionButton.setText("Agregar");
                                    cancelButton.setVisible(false);
                                    
                                    remove(order);
                                    
                                }
                                
                            }

                        });

                        super.setGraphic(button);
                        super.setAlignment(Pos.CENTER);
                        super.setText(null);

                    }
                }
            };
            
            return cell;        
        };
        
        this.editTC.setCellFactory(buttonFactory);
        this.removeTC.setCellFactory(buttonFactory);
        
    }
    
    private void initValidator() {
        
        this.validatorUtil = new ValidatorUtil(
                this.autoComplete,
                this.quantityTF
        );
    }
    
    @FXML
    private void actionOrder(ActionEvent event) {
        
        String action = ((Button) event.getSource()).getText();
        
        if (action.equalsIgnoreCase("agregar")) {
            
            this.add();
            
        } else {
            
            if (action.equalsIgnoreCase("editar")) {
                
                this.edit();
                
            }
            
        }
        
    }
    
    private void add() {
        
        System.out.println("Añadiendo orden");
        
        if (this.validatorUtil.validateFields()) {
            
            String text = this.autoComplete.getText();
            int quantity = Integer.parseInt(this.quantityTF.getText());

            Product product = this.autoComplete.getCurrentProduct();
            
            if (product == null) {
                product = this.autoComplete.searchProduct(text);
            }
            
            if (product != null) {
                
                Order order = new Order(
                        ++ this.counter,    product.getId(),
                        product,            product.getPrice(),
                        quantity,           quantity * product.getPrice()
                );
                
                this.ordersList.add(order);
                
            } else {
                
                new Alert(
                        AlertType.ERROR,
                        "No se ha encontrado un producto valido"
                ).showAndWait();
                
            }
            
        } else {
            
            this.validatorUtil.emptyFields().showAndWait();
            
        }
        
    }
    
    private void edit() {
        
        System.out.println("Finalizando edición");
        
        /**
         * Product product = this.autoComplete.getCurrentProduct();
            
        int quantity = Integer.parseInt(this.quantityTF.getText());

        Order order = new Order(

                ++ this.counter,    product.getId(), 

                product,  product.getPrice(), 

                quantity, quantity * product.getPrice()

        );

        this.ordersList.add(order);
        */
        
    }
    
    private void remove(Order order) {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Remover orden");
        alert.setHeaderText("Está seguro?");
        alert.setContentText("Remover orden: " + order);
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            this.ordersList.remove(order);
        }
        
    }
    
    @FXML
    private void cancelEdit() {
        
        System.out.println("Cancelando edición");
        
        this.quantityTF.clear();
        this.autoComplete.clear();
        this.autoComplete.setCurrentProduct(null);
        this.actionButton.setText("Agregar");
        this.cancelButton.setVisible(false);
        
    }
    
}