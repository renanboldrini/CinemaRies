/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.util.List;
import model.Filme;

/**
 *
 * @author Renan
 */
public interface FilmeDao extends Dao<Filme>{
    public Filme procurarPorCodigo(String codigo);

    public List<Filme> listarPorTitulo(String titulo);   
}
