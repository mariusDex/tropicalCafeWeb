/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Map;

/**
 *
 * @author Mariusz Broza
 */
@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");//cross domain request/CORS
        //******Escritor
        PrintWriter out = response.getWriter();
        //Controller?ACTION=PELICULA.LISTAR_PELICULAS_BY_FILTER&GENERO=Accion
        String action = request.getParameter("ACTION");
        //ACTION="PELICULA.FIND_ALL"
        String arrayAction[] = action.split("\\.");
        String objeto = arrayAction[0]; // PELICULA
        String accion = arrayAction[1]; // FIND_ALL

        switch (objeto) {
            
            case "SALE":   // Registro de la compra a la base de datos
                if (accion.equals("ADD_SALE")) {
                    // Procedimiento para almacenar una venta, tanto en la tabla de SALE como SALE_PRODUCT y resta existencias de los productos

                    Online_saleDAO onlineSaleDAO = new Online_saleDAO();
                    Online_sale new_onlineSale = new Online_sale();
                    Online_sale_product new_online_sale_product = new Online_sale_product();
                    Online_sale_productDAO onlineSaleProductDAO = new Online_sale_productDAO();
                    ProductDAO productDAO = new ProductDAO();
                    Product product  = new Product();
                    
                    
                    String clientID = request.getParameter("CLIENT_ID");
                    String invonce = request.getParameter("INVONCE");
                    String address = request.getParameter("ADDRESS");

                    new_onlineSale.setAddress(address);
                    new_onlineSale.setClientID(Integer.parseInt(clientID));
                    new_onlineSale.setInvonce(invonce);
                    // Añadimos en la tabla de online sale el registro de la compra
                    onlineSaleDAO.add(new_onlineSale);

                    // proceso para aÃ±adir online sale - product (tabla N-M para sacar mas tarde los productos de cada compra)
                    String productos = request.getParameter("PRODUCTS");
                    
                    // pasamos el string que nos lleva a JSON para poder recorrerlo y sacar su key y su value para repartirlos en los distintos beans
                    System.out.println(productos);
                    JsonObject object = new JsonParser().parse(productos).getAsJsonObject();

                    // recorremos el JSON que hemos cogido de java (hashMapProductos)  y lo recorremos
                    for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                        new_online_sale_product.setAmount(entry.getValue().getAsInt());
                        new_online_sale_product.setProductID(Integer.parseInt(entry.getKey()));
                        
                        // update de las existencias de cada producto
                        product.setProductID(Integer.parseInt(entry.getKey()));
                        product.setProductStock(entry.getValue().getAsInt());
                        
                        // updateamos las existencias del producto cada vez que se compra algo
                        productDAO.update(product);
                        
                        
                        
                        // Añadimos el producto y su cantidad a la tabla N-M de Online_sale - Product
                        onlineSaleProductDAO.add(new_online_sale_product);
                    }

                }

                break;

            // Búsqueda de productos y modificación a la hora de compra
            case "PRODUCT":
                if (accion.equals("FIND_ALL")) {

                    ProductDAO productDAO = new ProductDAO();
                    Product new_product = new Product();
                    ArrayList<Product> productList;

                    String productID = request.getParameter("PRODUCT_ID");
                    String productName = request.getParameter("PRODUCT_NAME");
                    String productCategory = request.getParameter("PRODUCT_CATEGORY");
                    String productTopProduct = request.getParameter("TOP_PRODUCT");
                    if (productID != null) {
                        new_product.setProductID(Integer.parseInt(productID));
                    }
                    if (productName != null) {
                        new_product.setProductName(productName);
                    }
                    if (productCategory != null ) {
                        new_product.setProductCategory(productCategory);
                    }
                    if (productTopProduct != null) {
                        new_product.setTopProduct(Integer.parseInt(productTopProduct));
                    }

                    productList = productDAO.findAll(new_product);

                    // Transformar de ArrayList a JSON
                    System.out.println(Product.toJSON(productList));
                    out.print(Product.toJSON(productList));

                }
                break;

            // Toda interacción que puede realizar el usuario con la base de datos
            case "USER":

                if (accion.equals("REGISTER")) {
                    UserDAO userDAO2 = new UserDAO();
                    User newUser = new User();

                    newUser.setUserNickname(request.getParameter("NICKNAME"));
                    newUser.setUserPassword(request.getParameter("PASSWORD"));
                    newUser.setUserName(request.getParameter("NAME"));
                    newUser.setUserSurname(request.getParameter("SURNAME"));
                    newUser.setUserEmail(request.getParameter("EMAIL"));
                    newUser.setUserAddress(request.getParameter("ADDRESS"));
                    newUser.setUserPhone(request.getParameter("PHONE"));
                    userDAO2.add(newUser);

                } else if (accion.equals("FIND_ALL")) {
                    UserDAO userDAO = new UserDAO();
                    User user = new User();
                    ArrayList<User> userList = new ArrayList();
                    String userID = request.getParameter("USER_ID");
                    String userNickname = request.getParameter("NICKNAME");
                    String userEmail = request.getParameter("EMAIL");
                    // Al venir como string , si el suerID no es nulo, lo convertimos a INT para almacener correctamente la ID
                    if (userID != null) {
                        int intUserID = Integer.parseInt(userID);
                        user.setUserID(intUserID);
                    } else {
                        if (userNickname != null) {
                            user.setUserNickname(userNickname);
                        } else {
                            if (userEmail != null) {
                                user.setUserEmail(userEmail);
                            }
                        }
                    }

                    userList = userDAO.findAll(user);

                    if (userList == null) {
                        System.out.println("Ha venido vacÃ­o por lo tanto userList null a js");

                        out.print(userList);

                    } else {
                        System.out.println(User.toJSON(userList));
                        out.print(User.toJSON(userList));
                    }

                } else if (accion.equals("UPDATE")) {
                    UserDAO userDAO = new UserDAO();
                    User user = new User();
                    String userID = request.getParameter("USER_ID");
                    String userNickname = request.getParameter("NICKNAME");
                    String userEmail = request.getParameter("EMAIL");
                    String userPassword = request.getParameter("PASSWORD");
                    String userAddress = request.getParameter("ADDRESS");
                    String userPhone = request.getParameter("PHONE");

                    user.setUserID(Integer.parseInt(userID));
                    user.setUserNickname(userNickname);
                    user.setUserEmail(userEmail);
                    user.setUserPassword(userPassword);
                    user.setUserAddress(userAddress);
                    user.setUserPhone(userPhone);

                    userDAO.update(user);

                } else if (accion.equals("DELETE_USER")) {
                    UserDAO userDAO = new UserDAO();
                    Online_saleDAO onlineSaleDAO = new Online_saleDAO();
                    Online_sale_productDAO onlineSaleProductDAO = new Online_sale_productDAO();

                    int userID = Integer.parseInt(request.getParameter("USER_ID"));
                    // tenemos que ir borrando por orden porque las relaciones de las bases de datos me restringen deleteare directamente un usuario
                    // porque afectaría a las primary Keys de tablas como online sale y online_sale_product
                    onlineSaleProductDAO.delete(userID);
                    onlineSaleDAO.delete(userID);
                    userDAO.delete(userID);
                } else if (accion.equals("PURCHASE_HISTORY")) {
                    Online_sale_product onlineSaleProduct = new Online_sale_product();
                    ArrayList<Online_sale_product> onlineSaleProductList = new ArrayList<Online_sale_product>();
                    Online_sale_productDAO onlineSaleProductDAO = new Online_sale_productDAO();

                    String userID = request.getParameter("USER_ID");
                    onlineSaleProduct.setClientID(Integer.parseInt(userID));
                    onlineSaleProductList = onlineSaleProductDAO.findAll(onlineSaleProduct);

                    System.out.println(Online_sale_product.toJSON(onlineSaleProductList));
                    out.print(Online_sale_product.toJSON(onlineSaleProductList));
                }
                

                break;
                
                
                // Todo lo relacionado al insert de un nuevo candidato en la base de datos
            case "CANDIDATE":

                if (accion.equals("ADD_CANDIDATE")) {
                    CandidateDAO candidateDAO = new CandidateDAO();
                    Candidate candidate = new Candidate();
                    Offer_candidateDAO offerCandidateDAO = new Offer_candidateDAO();
                    Offer_candidate offerCandidate = new Offer_candidate();
                    
                    // recogemos los parametros
                    String candidateID = request.getParameter("CANDIDATE_ID");
                    String candidateName = request.getParameter("CANDIDATE_NAME");
                    String candidateSurname = request.getParameter("CANDIDATE_SURNAME");
                    String candidateEmail = request.getParameter("CANDIDATE_EMAIL");
                    String candidateCV = request.getParameter("CANDIDATE_CV");
                    String offerID =  request.getParameter("OFFER_ID");
                    
                    candidate.setCandidateID(Integer.parseInt(candidateID));
                    candidate.setCandidateNameSurname(candidateName + " " + candidateSurname);
                    candidate.setCandidateEmail(candidateEmail);
                    
                    offerCandidate.setOfferID(Integer.parseInt(offerID));
                    offerCandidate.setCandidateID(Integer.parseInt(candidateID));
                    offerCandidate.setCV(candidateCV);
                    
                    offerCandidateDAO.add(offerCandidate);
                    candidateDAO.add(candidate);
                    
                }

                break;
                
        }

    }
        
        
       
        
        
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}