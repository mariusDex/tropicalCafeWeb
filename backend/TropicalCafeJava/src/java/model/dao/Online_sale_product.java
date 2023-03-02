package model.dao;


import java.util.ArrayList;

public class Online_sale_product{
    private String saleDate; // Atributo para guarda la fecha que nos devolverá la BBDD en formato String
    private int clientID; // Lo utilizaremos para ponerlo en la consulta,y l ID del cliente vendrá desde el controller
    private int onlineSaleID;
    private int productID;
    private int amount;
    private Product product; // Producto para almacenar la información del mismo de la base de datos y sacarlo en el JSON de la consulta custom de triple inner join
    
    
    
    //private Genero genero;
    public Online_sale_product() {
        product = new Product();
    }

    public int getOnlineSaleID() {
        return onlineSaleID;
    }

    public void setOnlineSaleID(int onlineSaleID) {
        this.onlineSaleID = onlineSaleID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    
    

  

    
    
    
    
    
    
 
    @Override
    public String toString() {
        return "Online_Sale{" + "onlineSaleID=" + onlineSaleID + ", "
                + "productID=" + productID + ", "
                + "amount=" + amount + '}';
    }
    
    // JSON Adaptado no para devolver la infomracion exacta de una online_sale_product, si no para devolver
    // el JSON adaptado al result de la consulta que hacemos personalizada (mirar findAll de Online_sale_productDAO)
    public static String toJSON(ArrayList<Online_sale_product> onlineSaleProductList) {
        int contador = 0;
        String cadena = "{\n\"PurchaseHistory\": [";
        for (Online_sale_product onlineSaleProduct : onlineSaleProductList) {
            int totalItems = onlineSaleProductList.size();

            cadena += "\n{\"onlineSaleDate\":\"" + onlineSaleProduct.getSaleDate() + "\""
                    + " ,\"onlineSaleID\":\"" + onlineSaleProduct.getOnlineSaleID() + "\""
                    + " ,\"productName\":\"" + onlineSaleProduct.getProduct().getProductName()+ "\""
                    + " ,\"productImage\":\"" + onlineSaleProduct.getProduct().getProductImage()+ "\""
                    + " ,\"productAmount\":\"" + onlineSaleProduct.getAmount()+ "\""
                    + " ,\"productCost\":\"" + onlineSaleProduct.getProduct().getProductCost() + "\""
                    + " ,\"amount\":\"" + onlineSaleProduct.getAmount()+ "\"}";
            contador++;

            if (contador != totalItems) {
                cadena += ",";
            }
            // Si es i-1
        }
        cadena += "\n]\n}";
        return cadena;
    }
}
