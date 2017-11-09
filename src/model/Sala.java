package model;

public class Sala {
        private int id;
	private String numero;// string poid pode conter letras: 2b...34c
	private int lugares;
	
    public Sala(String numero, int lugares) {
        this.numero = numero;
        this.lugares = lugares;
    }
    
        public Sala(int id, String numero, int lugares) {
        this.id = id;    
        this.numero = numero;
        this.lugares = lugares;
    }

    public String getNumero() {
	return numero;
    }

    public int getId() {
        return id;
    }

    public int getLugares() {
	return lugares;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLugares(int lugares) {
        this.lugares = lugares;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    @Override
    public String toString() {
        return numero+" - "+lugares;
    }
}
