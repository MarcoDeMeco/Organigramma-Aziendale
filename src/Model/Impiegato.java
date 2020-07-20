package Model;

import View.Observer;

import java.io.Serializable;
import java.util.HashMap;

public class Impiegato implements Serializable {
    private String nome;

    // <nodo, ruolo>
    private HashMap<String, String> ruoloByNodo;

    public Impiegato(String nome) {
        ruoloByNodo = new HashMap<>();
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void aggiungiRuolo(String nodo, String ruolo){
        ruoloByNodo.put(nodo, ruolo);
    }

    public String getRuolo(String nodo){
        return ruoloByNodo.get(nodo);
    }

    public void rimuoviImpiego(String nodo){
        ruoloByNodo.remove(nodo);
    }

    public boolean isDisoccupato(){
        return ruoloByNodo.isEmpty();
    }

    public HashMap<String, String> getRuoloByNodo() {
        return ruoloByNodo;
    }

    @Override
    public String toString() {
        return nome;
    }
}
