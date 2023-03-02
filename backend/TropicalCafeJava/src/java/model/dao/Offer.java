package model.dao;
/**
 * @author : Cristian Ezquerra
 */
import java.util.ArrayList;

public class Offer {
    private int offerId;
    private String nameOffer;
    private String descripitionOffer;
    private String dateOffer;
    private String salaryOffer;
    private String responsabilitiesOffer;
    private String hardOffer;
    private String softOffer;
    private String moreOffer;

    public Offer() {
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getNameOffer() {
        return nameOffer;
    }

    public void setNameOffer(String nameOffer) {
        this.nameOffer = nameOffer;
    }

    public String getDescripitionOffer() {
        return descripitionOffer;
    }

    public void setDescripitionOffer(String descripitionOffer) {
        this.descripitionOffer = descripitionOffer;
    }

    public String getDateOffer() {
        return dateOffer;
    }

    public void setDateOffer(String dateOffer) {
        this.dateOffer = dateOffer;
    }

    public String getSalaryOffer() {
        return salaryOffer;
    }

    public void setSalaryOffer(String salaryOffer) {
        this.salaryOffer = salaryOffer;
    }

    public String getResponsabilitiesOffer() {
        return responsabilitiesOffer;
    }

    public void setResponsabilitiesOffer(String responsabilitiesOffer) {
        this.responsabilitiesOffer = responsabilitiesOffer;
    }

    public String getHardOffer() {
        return hardOffer;
    }

    public void setHardOffer(String hardOffer) {
        this.hardOffer = hardOffer;
    }

    public String getSoftOffer() {
        return softOffer;
    }

    public void setSoftOffer(String softOffer) {
        this.softOffer = softOffer;
    }

    public String getMoreOffer() {
        return moreOffer;
    }

    public void setMoreOffer(String moreOffer) {
        this.moreOffer = moreOffer;
    }

    @Override
    public String toString() {
        return "Offer{" + "offerId=" + offerId + 
                ", nameOffer=" + nameOffer + 
                ", descripitionOffer=" + descripitionOffer + 
                ", dateOffer=" + dateOffer + 
                ", salaryOffer=" + salaryOffer + 
                ", responsabilitiesOffer=" + responsabilitiesOffer + 
                ", hardOffer=" + hardOffer + 
                ", softOffer=" + softOffer + 
                ", moreOffer=" + moreOffer + '}';
    }
    
    public static String toJSON(ArrayList<Offer> offerList) {
        int contador = 0;
        String cadena = "{\n\"Candidate\": [";
        for (Offer offer : offerList) {
            int totalItems = offerList.size();

            cadena += "\n{\"offerId\":\"" + offer.getOfferId()+ "\""
                    + " ,\"nameOffer\":\"" + offer.getNameOffer()+ "\""
                    + " ,\"descriptionOffer\":\"" + offer.getDescripitionOffer()+ "\""
                    + " ,\"dateOffer\":\"" + offer.getDateOffer()+ "\""
                    + " ,\"salaryOffer\":\"" + offer.getSalaryOffer()+ "\""
                    + " ,\"responsabilitiesOffer\":\"" + offer.getResponsabilitiesOffer()+ "\""
                    + " ,\"hardOffer\":\"" + offer.getHardOffer()+ "\""
                    + " ,\"softOffer\":\"" + offer.getSoftOffer()+ "\""
                    + " ,\"moreOffer\":\"" + offer.getMoreOffer()+ "\"}";
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
