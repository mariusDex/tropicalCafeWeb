package model.dao;


import java.util.ArrayList;

public class User {
    
    private int userID;
    private String userNickname; 
    private String userPassword;
    private String userName;
    private String userSurname;
    private String userEmail;
    private String userAddress;
    private String userPhone;
    
    // Construcotr para que en caso de que creemos instancias de Usar para trbaajar con ellas en el findAll
    // le pongamos un parametro en "True" y así no se sumará al número de users creados
    public User() {
        
    }
    
    

    

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    
    
    
    
    
    
 
    @Override
    public String toString() {
        return "User{" + "userID= " + userID + ", "
                + "userNickname= " + userNickname + ", "
                + "userPassword= " + userPassword + ", "
                + "userName= " + userName + ", "
                + "userSurname= " + userSurname + ", "
                + "userEmail= " + userEmail + ", "
                + "userAddress= " + userAddress + ", "
                + "userPhone = " + userPhone + '}';
    }
    
    // Paso manual de ArrayList a JSON
    
    // Para coger array en javascript : Users
    public static String toJSON(ArrayList<User> userList) {
        int contador = 0;
        String cadena = "{\n\"Users\": [";
        for (User user : userList) {
            int totalItems = userList.size();

            cadena += "\n{\"userID\":\"" + user.getUserID() + "\""
                    + " , \"userNickname\":\"" + user.getUserNickname() + "\""
                    + " , \"userPassword\":\"" + user.getUserPassword() + "\""
                    + " , \"userName\":\"" + user.getUserName()+ "\""
                    + " , \"userSurname\":\"" + user.getUserSurname()+ "\""
                    + " , \"userEmail\":\"" + user.getUserEmail()+ "\""
                    + " , \"userAddress\":\"" + user.getUserAddress()+ "\""
                    + " , \"userPhone\":\"" + user.getUserPhone()+ "\"}";
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
