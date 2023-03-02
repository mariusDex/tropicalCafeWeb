package model.dao;

import java.util.ArrayList;


public class Candidate {
    
    // ATRIBUTOS DE LA CLASE CANDIDATE ( mismos que en BBDD)
    private int candidateID;
    private String candidateNameSurname;
    private String candidateEmail;
    private String candidateCv;
    
    // BUILDER
    public Candidate() {
    }

    // GETTERS Y SETTERS
    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public String getCandidateNameSurname() {
        return candidateNameSurname;
    }

    public void setCandidateNameSurname(String candidateNameSurname) {
        this.candidateNameSurname = candidateNameSurname;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }

    public String getCandidateCv() {
        return candidateCv;
    }

    public void setCandidateCv(String candidateCv) {
        this.candidateCv = candidateCv;
    }

    

    
    // ------------------- TO STRING & TO JSON ------------------------//
    @Override
    public String toString() {
        return "Candidate{" + "candidateID=" + candidateID + 
                ", nameSurname=" + candidateNameSurname + 
                ", email=" + candidateEmail + 
                ", cv=" + candidateCv + '}';
    }
    
    public static String toJSON(ArrayList<Candidate> candidateList) {
        int contador = 0;
        String cadena = "{\n\"Candidates\": [";
        for (Candidate candidate : candidateList) {
            int totalItems = candidateList.size();

            cadena += "\n{\"candidateID\":\"" + candidate.getCandidateID() + "\""
                    + " ,\"nameSurname\":\"" + candidate.getCandidateNameSurname()+ "\""
                    + " ,\"email\":\"" + candidate.getCandidateEmail()+ "\""
                    + " ,\"cv\":\"" + candidate.getCandidateCv()+ "\"}";
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

