package shopcl.beans;

import shopcl.model.Product;

/**
 *
 * @author Arturh
 */
public class Order {
    
    private int no;
    private int id;
    
    private Product product;
    private double price;
    private int quantity;
    
    private double subtotal;
    
    public Order() {
        
    }
    
    public Order(int no, int id, Product product, double price, int quantity, double subtotal) {
        this.no = no;
        this.id = id;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }
    
    public int getNo() {
        return no;
    }
    
    public void setNo(int no) {
        this.no = no;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return no + " : " + product + " : " + quantity + " : " + subtotal;
    }
    
}