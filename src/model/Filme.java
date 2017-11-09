/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author 631620025
 */
public class Filme {
    private int id;    
    private String codigo;//pode conter letras e numeros
    private String titulo;
    private String genero;
    private String sinopse;

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
    
        public Filme(String codigo, String titulo, String genero, String sinopse) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.genero = genero;
        this.sinopse = sinopse;
    }
        
        public Filme(int id, String codigo, String titulo, String genero, String sinopse) {
        this.id = id;    
        this.codigo = codigo;
        this.titulo = titulo;
        this.genero = genero;
        this.sinopse = sinopse;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    @Override
    public String toString() {
        return codigo+" - "+titulo+" - "+genero+" - "+sinopse;
    }    
    
}