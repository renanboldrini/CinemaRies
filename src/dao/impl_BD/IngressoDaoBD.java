/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl_BD;

import dao.IngressoDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Ingresso;
import model.Sessao;

/**
 *
 * @author Renan
 */
public class IngressoDaoBD extends DaoBd<Ingresso> implements IngressoDao{

    @Override
    public void salvar(Ingresso ing) {
        int id = 0;
        try {
            String sql = "INSERT INTO ingresso (id_sessao) "
                    + "VALUES (?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            comando.setInt(1, ing.getSessao().getId());           
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                ing.setId(id);
            }
            else{
                System.err.println("Erro de Sistema - Nao gerou o id conforme esperado!");
                throw new BDException("Nao gerou o id conforme esperado!");
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar ingresso no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void deletar(Ingresso ing) {
        try {
            String sql = "DELETE FROM ingresso WHERE id = ?";

            conectar(sql);
            comando.setInt(1, ing.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("O ingresso não pode ser ");
            //throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Ingresso model) {
        //não se faz necessário
    }

    @Override
    public List<Ingresso> listar() {
                List<Ingresso> listaIngresso = new ArrayList<>();

        String sql = "SELECT * FROM ingresso";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");

                int idSessao = resultado.getInt("id_sessao");
                //System.out.println("idS: "+idSala);//verificar essa questão
                Sessao s = new SessaoDaoBD().procurarPorId(idSessao);

                Ingresso ing = new Ingresso(id, s);
                
                listaIngresso.add(ing);

            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os ingressos no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaIngresso);
    }

    @Override
    public Ingresso procurarPorId(int id) {
                String sql = "SELECT * FROM ingresso WHERE id = ?";
        try {
            conectar(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
 
                int idSessao = resultado.getInt("id_sessao");
                
                Sessao s = new SessaoDaoBD().procurarPorId(idSessao);

                Ingresso ing = new Ingresso(id, s);

                return ing;

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o ingresso pelo id no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }

    @Override
    public List<Ingresso> listarPorSessao(int id) {
        List<Ingresso> listaIngresso = new ArrayList<>();
        String sql = "SELECT * FROM ingresso WHERE id_sessao=?";

        try {
            conectar(sql);
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                
                int idx = resultado.getInt("id");
                int idSessao = resultado.getInt("id_sessao");
                Sessao s = new SessaoDaoBD().procurarPorId(idSessao);
                
                Ingresso ing = new Ingresso(idx, s);                

                listaIngresso.add(ing);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os ingressos pelo titulo no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaIngresso); 
    }
    
}
