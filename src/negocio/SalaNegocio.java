package negocio;

import dao.SalaDao;
import dao.impl_BD.SalaDaoBD;
import java.util.List;
import model.Sala;

/**
 *
 * @author Renan
 */
public class SalaNegocio {
    
    private SalaDao salaDao;

    public SalaNegocio() {
        salaDao = new SalaDaoBD();
    }
    
    public void salvar(Sala s) throws NegocioException {
        this.validarCamposObrigatorios(s);
        this.validarNumeroExistente(s);
        salaDao.salvar(s);
        
    }

    public List<Sala> listar() {
        return (salaDao.listar());
    }
    
    public void deletar(Sala sala) throws NegocioException {
        if (sala == null || sala.getNumero() == null) {
            throw new NegocioException("Sala nao existe!");
        }
        salaDao.deletar(sala);
    }

    public void atualizar(Sala sala) throws NegocioException {
        if (sala == null || sala.getNumero() == null) {
            throw new NegocioException("Sala nao existe!");
        }
        this.validarCamposObrigatorios(sala);
        salaDao.atualizar(sala);
    }

    public Sala procurarPorNumero(String numero) throws NegocioException {
        if (numero == null || numero.isEmpty()) {
            throw new NegocioException("Campo Numero nao informado");
        }
        Sala sala = salaDao.procurarPorNumero(numero);
        if (sala == null) {
            throw new NegocioException("Sala nao encontrado");
        }
        return (sala);
    }

    public boolean salaExiste(String numero) {
        Sala sala = salaDao.procurarPorNumero(numero);
        return (sala != null);
    }

    private void validarCamposObrigatorios(Sala s) throws NegocioException {
        if (s.getNumero() == null || s.getNumero().isEmpty()) {
            throw new NegocioException("Campo Numero nao informado");
        }

        if (s.getLugares() < 1) {   // testar se vai funcionar
            throw new NegocioException("Campo lugares nao informado(lugares deve ser maior que 0)");
        }
    }

    private void validarNumeroExistente(Sala s) throws NegocioException {
        if (salaExiste(s.getNumero())) {
            throw new NegocioException("Sala ja existente");
        }
    }

    public List<Sala> listarPorNumero(String numero) throws NegocioException {
        if (numero == null || numero.isEmpty()) {
            throw new NegocioException("Campo numero nao informado");
        }
        return(salaDao.listarPorNumero(numero));
    }    
}

