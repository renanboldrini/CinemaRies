/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.util.List;
import model.Ingresso;
/**
 *
 * @author Renan
 */
public interface IngressoDao extends Dao<Ingresso>{

   public List<Ingresso> listarPorSessao(int Id);//verificar
}