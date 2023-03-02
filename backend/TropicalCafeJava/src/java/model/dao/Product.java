package model.dao;


import java.util.ArrayList;

public class Product {
   
    private int productID;
    private String productName; 
    private float productCost;
    private String productImage;
    private String productDescription;
    private String productCategory;
    private int topProduct;
    private int productStock;
    //private Genero genero;
    public Product() {
    }

    public String getProductName() {
        return productName;
    }

    
    public int getProductID() {
        return productID;
    }

   

    public void setProductID(int productID) {
        this.productID = productID;
    }
    
    
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductCost() {
        return productCost;
    }

    public void setProductCost(Float productCost) {
        this.productCost = productCost;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public int getTopProduct() {
        return topProduct;
    }

    public void setTopProduct(int topProduct) {
        this.topProduct = topProduct;
    }
    

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    
    
    
    
    
    
    
 
    @Override
    public String toString() {
        return "Product{" + "prodcutID=" + productID + ", "
                + "prodcutName=" + productName + ", "
                + "productCost=" + productCost + ", "
                + "productDescription=" + productDescription + ", "
                + "productImage" + productImage + '}';
    }
    
    // Paso manual de ArrayList a JSON
    public static String toJSON(ArrayList<Product> productList) {
        int contador = 0;
        String cadena = "{\n\"Products\": [";
        for (Product product : productList) {
            int totalItems = productList.size();

            cadena += "\n{\"productID\":\"" + product.getProductID() + "\""
                    + " ,\"productName\":\"" + product.getProductName()+ "\""
                    + " ,\"productImage\":\"" + product.getProductImage()+ "\""
                    + " ,\"productDescription\":\"" + product.getProductDescription()+ "\""
                    + " ,\"productCategory\":\"" + product.getProductCategory()+ "\""
                    + " ,\"topProduct\":\"" + product.getTopProduct()+ "\""
                    + " ,\"productStock\":\"" + product.getProductStock()+ "\""
                    + " ,\"productCost\":\"" + product.getProductCost()+ "\"}";
            contador++;

            if (contador != totalItems) {
                cadena += ",";
            }
            // Si es i-1
        }
        cadena += "\n]\n}";
        return cadena;
    }
    
    // Pasar a JSON de forma ilegal
    
    /*
     public static String 
        toArrayJSon(ArrayList<Pelicula> peliculas) {
            GsonBuilder builder = new GsonBuilder(); 
            builder.setPrettyPrinting();

            Gson gson = builder.create();
            String resp = gson.toJson(peliculas);
            
            return resp;
    }

*/
    
    
}