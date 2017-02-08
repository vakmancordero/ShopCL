package shopcl.utils;

import java.util.HashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import shopcl.model.Product;

public class AutoComplete extends TextField {
    
    private final SortedSet<String> entries;
    private ContextMenu entriesPopup;
    
    private Map<String, Product> map;
    
    private Product currentProduct;
    
    public AutoComplete(List<Product> products) {
        
        super();
        
        super.setPrefHeight(25.0);
        super.setPrefWidth(260.0);
        
        this.map = new HashMap<>();
        
        this.entries = new TreeSet<>();
        
        for (Product product : products) {
            
            String productName = product.toString().toLowerCase();
            
            this.map.put(productName, product);
            
            this.entries.add(productName);
            
        }
        
        this.entriesPopup = new ContextMenu();
    
        this.textProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                
                if (getText().isEmpty()) {
                    
                    entriesPopup.hide();
                    
                } else {
                    
                    LinkedList<String> searchResult = new LinkedList<>();
                    
                    searchResult.addAll(entries.subSet(getText(), getText() + Character.MAX_VALUE));
                    
                    if (entries.size() > 0) {
                        
                        populatePopup(searchResult);
                        
                        if (!entriesPopup.isShowing()) {
                            
                            entriesPopup.show(AutoComplete.this, Side.BOTTOM, 0, 0);
                        }
                        
                    } else {
                        entriesPopup.hide();
                    }
                }
            }
        });

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                
                entriesPopup.hide();
                
            }
            
        });
        
    }
    
    public SortedSet<String> getEntries() {    
        return entries;
    }
    
    private void populatePopup(List<String> searchResult) {

        List<CustomMenuItem> menuItems = new LinkedList<>();

        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);

        for (int i = 0; i < count; i++) {
            
            String result = searchResult.get(i);
            
            Label entryLabel = new Label(result);
            
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            
            item.setOnAction((ActionEvent actionEvent) -> {
                
                currentProduct = map.get(result);
                
                super.setText(currentProduct.toString());
                
                entriesPopup.hide();
                
            });
            
            menuItems.add(item);
            
        }
        
        this.entriesPopup.getItems().clear();
        
        this.entriesPopup.getItems().addAll(menuItems);
        
    }

    public Product getCurrentProduct() {
        return this.currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }
    
    public Product searchProduct(String text) {
        return this.map.get(text);
    }
    
}