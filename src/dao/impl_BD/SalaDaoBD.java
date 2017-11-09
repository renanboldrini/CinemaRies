/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl_BD;
import dao.SalaDao;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Sala;

/**
 *
 * @author Renan
 */
public class SalaDaoBD extends DaoBd<Sala> implements SalaDao {

    @Override
    public void salvar(Sala sala) {
        int id = 0;
        try {
            String sql = "INSERT INTO Sala (numero, lugares) "
                    + "VALUES (?,?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            comando.setString(1, sala.getNumero());
            comando.setInt(2, sala.getLugares());
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                sala.setId(id);
            }
            else{
                System.err.println("Erro de Sistema - Nao gerou o id conforme esperado!");
                throw new BDException("Nao gerou o id conforme esperado!");
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar Salas no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void deletar(Sala sala) {
        try {
            String sql = "DELETE FROM sala WHERE id = ?";

            conectar(sql);
            comando.setInt(1, sala.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("A sala não pode ser ");
            //throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Sala sala) {
                try {
            String sql = "UPDATE sala SET numero=?, lugares=? "
                    + "WHERE id=?";

            conectar(sql);
            comando.setString(1, sala.getNumero());
            comando.setInt(2, sala.getLugares());
            comando.setInt(3, sala.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar sala no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Sala> listar() {
        List<Sala> listaSalas = new ArrayList<>();

        String sql = "SELECT * FROM sala";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String numero = resultado.getString("numero");
                int lugares = resultado.getInt("lugares");

                Sala sal = new Sala(id, numero, lugares);
                listaSalas.add(sal);

            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar as salas do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaSalas);
    }

    @Override
    public Sala procurarPorId(int id) {
        String sql = "SELECT * FROM sala WHERE id = ?";

        try {
            conectar(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String numero = resultado.getString("numero");
                int lugares = resultado.getInt("lugares");

                Sala sal = new Sala(id, numero, lugares);

                return sal;

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar a sala pelo id do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }

    @Override
    public Sala procurarPorNumero(String numero) {
        String sql = "SELECT * FROM sala WHERE numero = ?";

        try {
            conectar(sql);
            comando.setString(1, numero);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id");
                int lugares = resultado.getInt("lugares");
                
                Sala Sal = new Sala(id, numero, lugares);

                return Sal;

            }

        } catch (SQLException ex) {
            //System.err.println("Erro de Sistema - Problema ao buscar o paciente pelo rg do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }

    @Override
    public List<Sala> listarPorNumero(String numero) {
        List<Sala> listaSalas = new ArrayList<>();
        String sql = "SELECT * FROM sala WHERE numero LIKE ?";

        try {
            conectar(sql);
            comando.setString(1, "%" + numero + "%");
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                
                String numeroX = resultado.getString("numero");
                //Trabalhando com data: convertendo dataSql -> LocalDate
                int lugares = resultado.getInt("lugares");

                Sala sal = new Sala(id, numeroX, lugares);

                listaSalas.add(sal);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar as salas pelo numero no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaSalas);        
    }
    
}
