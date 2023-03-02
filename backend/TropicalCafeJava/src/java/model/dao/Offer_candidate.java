package model.dao;


import java.util.ArrayList;

public class Offer_candidate{
    private int candidateID;
    private int offerID;
    private String CV;
    
    
    
    //private Genero genero;
    public Offer_candidate() {
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public int getOfferID() {
        return offerID;
    }

    public void setOfferID(int offerID) {
        this.offerID = offerID;
    }

    public String getCV() {
        return CV;
    }

    public void setCV(String CV) {
        this.CV = CV;
    }
    
    
    
    

  

    
    
    
    
    
    
 
    @Override
    public String toString() {
        return "Online_Sale{" + "candidateID=" + candidateID + ", "
                + "offerID=" + offerID + ", "
                + "cv=" + CV + '}';
    }
    
    // JSON Adaptado no para devolver la infomracion exacta de una online_sale_product, si no para devolver
    // el JSON adaptado al result de la consulta que hacemos personalizada (mirar findAll de Online_sale_productDAO)
    public static String toJSON(ArrayList<Offer_candidate> offerCandidateList) {
        int contador = 0;
        String cadena = "{\n\"offerCandidateList\": [";
        for (Offer_candidate offerCandidate : offerCandidateList) {
            int totalItems = offerCandidateList.size();

            cadena += "\n{\"candidateID\":\"" + offerCandidate.getCandidateID() + "\""
                    + " ,\"offerID\":\"" + offerCandidate.getOfferID() + "\""
                    + " ,\"cv\":\"" + offerCandidate.getCV()+ "\"}";
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
