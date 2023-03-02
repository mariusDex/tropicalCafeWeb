package model.dao;


import java.util.ArrayList;

public class Online_sale {
    private int onlineSaleID;
    private String address; 
    private String date;
    private String invonce;
    private int clientID;
    
    
    //private Genero genero;
    public Online_sale() {
    }

    

    

    public int getOnlineSaleID() {
        return onlineSaleID;
    }

    public void setOnlineSaleID(int onlineSaleID) {
        this.onlineSaleID = onlineSaleID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInvonce() {
        return invonce;
    }

    public void setInvonce(String invonce) {
        this.invonce = invonce;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    
    
    
    
    
    
 
    @Override
    public String toString() {
        return "Online_Sale{" + "onlineSaleID=" + onlineSaleID + ", "
                + "onlineSaleDate=" + date + ", "
                + "onlineSaleAddress=" + address + ", "
                + "invonce=" + invonce + ", "
                + "clienteID=" + clientID + '}';
    }
    
    // Paso manual de ArrayList a JSON
    public static String toJSON(ArrayList<Online_sale> onlineSaleList) {
        int contador = 0;
        String cadena = "{\n\"OnlineSales\": [";
        for (Online_sale onlineSale : onlineSaleList) {
            int totalItems = onlineSaleList.size();

            cadena += "\n{\"onlineSaleID\":\"" + onlineSale.getOnlineSaleID() + "\""
                    + " ,\"date\":\"" + onlineSale.getDate()+ "\""
                    + " ,\"address\":\"" + onlineSale.getAddress()+ "\""
                    + " ,\"invonce\":\"" + onlineSale.getInvonce() + "\""
                    + " ,\"clientID\":\"" + onlineSale.getClientID()+ "\"}";
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