
package negocio;
import dao.IngressoDao;
import dao.SessaoDao;
import dao.impl_BD.IngressoDaoBD;
import dao.impl_BD.SessaoDaoBD;
import java.util.List;
import model.Ingresso;
/**
 *
 * @author Renan
 */
public class IngressoNegocio {
    
    private IngressoDao ingressoDao;
    private SessaoDao sessaoDao;


    public IngressoNegocio() {
        ingressoDao = new IngressoDaoBD();
        sessaoDao = new SessaoDaoBD();
    }
    
    public void salvar(Ingresso i) throws NegocioException {
        this.validarCamposObrigatorios(i);
       if(i.getSessao().getQntIngresso()<1){
       System.out.println("Ingressos esgotados para essa sessão");
       }else{
           
        sessaoDao.retiraIngresso(i.getSessao());
        ingressoDao.salvar(i);
       }       
    }    
    
    public List<Ingresso> listar() {
        return (ingressoDao.listar());
    }    

    public void deletar(Ingresso ing) throws NegocioException {
        if (ing == null) {
            throw new NegocioException("Ingresso nao existe!");
        }
        ingressoDao.deletar(ing);
    }
    
        public Ingresso procurarPorId(String id) throws NegocioException {
        if (id == null || id.isEmpty()) {
            throw new NegocioException("Campo ID nao informado");
        }
        try {
            int i = Integer.parseInt(id);
            Ingresso ing = ingressoDao.procurarPorId(i);
        if (ing == null) {
            throw new NegocioException("Ingresso nao encontrado");
        }
        return (ing);
                    }catch(NumberFormatException e){
                        System.err.println("para o ID, escrever apenas numeros");
                    }
        return (null);
    }
    
    private void validarCamposObrigatorios(Ingresso i) throws NegocioException {
        
        if (i.getSessao() == null) {
            throw new NegocioException("Campo Sessao nao informado");
        }      
    }
    
    public List<Ingresso> listarPorSessao(String id) throws NegocioException {
        if (id == null || id.isEmpty()) {
            throw new NegocioException("Campo ID Sessao nao informado");
        }
        try{
        int i = Integer.parseInt(id);
        return(ingressoDao.listarPorSessao(i));
        }catch(Exception e){
        System.out.println("VErificar informações concedidas.");
        }
        return (null);
    }      
}
