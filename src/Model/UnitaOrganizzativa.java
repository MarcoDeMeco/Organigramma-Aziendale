package Model;

import java.io.Serializable;
import java.util.*;

public class UnitaOrganizzativa extends AbstractModel implements Serializable {
    private LinkedList<String> listaRuoli;
    private LinkedList<Impiegato> listaImpiegati;
    private String nomeUnita;

    public UnitaOrganizzativa(String nomeUnita) {
        this.nomeUnita = nomeUnita;
        listaRuoli = new LinkedList<>();
        listaImpiegati = new LinkedList<>();
    }

    public LinkedList<Impiegato> getListaImpiegati() {
        return listaImpiegati;
    }

    public LinkedList<String> getListaRuoli() {
        return listaRuoli;
    }

    public void aggiungiImpiegato(Impiegato impiegato) {
        listaImpiegati.add(impiegato);
        aggiorna();
    }

    public void rimuoviImpiegato(Impiegato impiegato) {
        listaImpiegati.remove(impiegato);
        aggiorna();
    }

    public void aggiungiRuolo(String ruolo) {
        listaRuoli.add(ruolo);
        aggiorna();
    }

    public void rimuoviRuolo(String ruolo) {
        listaRuoli.remove(ruolo);
        aggiorna();
    }

    @Override
    public String toString() {
        return nomeUnita;
    }
}
