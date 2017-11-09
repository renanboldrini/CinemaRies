
package dao.impl_BD;

import dao.SessaoDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.Filme;
import model.Sala;
import model.Sessao;

/**
 *
 * @author Renan
 */
public class SessaoDaoBD extends DaoBd<Sessao> implements SessaoDao{

    @Override
    public void salvar(Sessao sessao) {
        int id = 0;
        try {
            String sql = "INSERT INTO sessao (horario, qnt_ingresso, id_sala, id_filme) "
                    + "VALUES (?,?,?,?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            //Trabalhando com data: convertendo LocalDateTime -> Timestamp
            comando.setTime(1, Time.valueOf(sessao.getHorario()));
            comando.setInt(2, sessao.getQntIngresso());  //comando.setInt(2, sessao.getSala().getLugares());
            comando.setInt(3, sessao.getSala().getId());
            comando.setInt(4, sessao.getFilme().getId());           
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                sessao.setId(id);
            }
            else{
                System.err.println("Erro de Sistema - Nao gerou o id conforme esperado!");
                throw new BDException("Nao gerou o id conforme esperado!");
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar sessao no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void deletar(Sessao sessao) {
                try {
            String sql = "DELETE FROM sessao WHERE id = ?";

            conectar(sql);
            comando.setInt(1, sessao.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("A sessao não pode ser ");
            //throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Sessao model) { //não é aplicado para a sessao, por questoés de integridade aos ingressos.
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sessao> listar() {
                List<Sessao> listaSessao = new ArrayList<>();

        String sql = "SELECT * FROM sessao";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                
                Time dataSql = resultado.getTime("horario");
                LocalTime hora = dataSql.toLocalTime();
                
                int qntIngresso = resultado.getInt("qnt_ingresso");
                
                int idSala = resultado.getInt("id_sala");
                //System.out.println("idS: "+idSala);//verificar essa questão
                
                Sala s = new SalaDaoBD().procurarPorId(idSala);
                
                int idFilme = resultado.getInt("id_filme");
                //System.out.println("idF: "+idFilme);//verificar essa questão
                
                Filme f = new FilmeDaoBD().procurarPorId(idFilme);
                

                Sessao sess = new Sessao(id, hora, qntIngresso, s, f);
                
                listaSessao.add(sess);

            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os sessaoes no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaSessao);
    }

    @Override
    public Sessao procurarPorId(int id) {
                String sql = "SELECT * FROM sessao WHERE id = ?";

        try {
            conectar(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                
                Time dataSql = resultado.getTime("horario");
                LocalTime hora = dataSql.toLocalTime();
                
                int qntIngresso = resultado.getInt("qnt_ingresso");
                
                int idSala = resultado.getInt("id_sala");
                //System.out.println("idS: "+idSala);//verificar essa questão
                
                Sala s = new SalaDaoBD().procurarPorId(idSala);
                
                int idFilme = resultado.getInt("id_filme");
                //System.out.println("idF: "+idFilme);//verificar essa questão
                
                Filme f = new FilmeDaoBD().procurarPorId(idFilme);
                

                Sessao sess = new Sessao(id, hora, qntIngresso, s, f);

                return sess;

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar a sessao pelo id no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }
    
    @Override    
    public void retiraIngresso(Sessao sessao) { 
                try {
            String sql = "UPDATE sessao SET horario=?, qnt_ingresso=?, id_sala=?, id_filme=?"
                    + "WHERE id=?";

            conectar(sql);
            
            int i = (sessao.getQntIngresso() - 1);
            
            comando.setTime(1, Time.valueOf(sessao.getHorario()));
            comando.setInt(2, i);
            comando.setInt(3, sessao.getSala().getId());
            comando.setInt(4, sessao.getFilme().getId());
            System.out.println(sessao.getId());
            comando.setInt(5, sessao.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar filme no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }
    
        @Override
    public List<Sessao> listarAtivas() {
                List<Sessao> listaSessao = new ArrayList<>();

        String sql = "SELECT * FROM sessao";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                
                Time dataSql = resultado.getTime("horario");
                LocalTime hora = dataSql.toLocalTime();
                
                int qntIngresso = resultado.getInt("qnt_ingresso");
                
                int idSala = resultado.getInt("id_sala");
                //System.out.println("idS: "+idSala);//verificar essa questão
                
                Sala s = new SalaDaoBD().procurarPorId(idSala);
                
                int idFilme = resultado.getInt("id_filme");
                //System.out.println("idF: "+idFilme);//verificar essa questão
                
                Filme f = new FilmeDaoBD().procurarPorId(idFilme);
                
                LocalTime horaNow = LocalTime.now(); 
                
                if(horaNow.isBefore(hora)){
                Sessao sess = new Sessao(id, hora, qntIngresso, s, f);
                listaSessao.add(sess);
                }

            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os sessaoes no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaSessao);
    }

}
