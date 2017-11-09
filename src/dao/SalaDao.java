/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.util.List;
import model.Sala;

/**
 *
 * @author Renan
 */
public interface SalaDao extends Dao<Sala>{
    public Sala procurarPorNumero(String numero);

   public List<Sala> listarPorNumero(String numero);
}




