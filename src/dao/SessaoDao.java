
package dao;

import java.util.List;
import model.Sessao;

/**
 *
 * @author Renan
 */
public interface SessaoDao extends Dao<Sessao>{
    public List<Sessao> listarAtivas();    
    public void retiraIngresso(Sessao sessao);
   //public List<Sessao> listarPorCodigo(String numero);//verificar
}
