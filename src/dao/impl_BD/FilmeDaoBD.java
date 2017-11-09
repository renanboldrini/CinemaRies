
package dao.impl_BD;
import dao.FilmeDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Filme;

/**
 *
 * @author Renan
 */
public class FilmeDaoBD extends DaoBd<Filme> implements FilmeDao{

    @Override
    public void salvar(Filme filme) {
        int id = 0;
        try {
            String sql = "INSERT INTO Filme (codigo, titulo, genero, sinopse) "
                    + "VALUES (?,?,?,?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            
            comando.setString(1, filme.getCodigo());
            comando.setString(2, filme.getTitulo());
            comando.setString(3, filme.getGenero());
            comando.setString(4, filme.getSinopse());
                      
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                filme.setId(id);
            }
            else{
                System.err.println("Erro de Sistema - Nao gerou o id conforme esperado!");
                throw new BDException("Nao gerou o id conforme esperado!");
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar filmes no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void deletar(Filme filme) {
        try {
            String sql = "DELETE FROM filme WHERE id = ?";

            conectar(sql);
            comando.setInt(1, filme.getId());
            comando.executeUpdate();

        } catch (Exception ex) {
            System.out.println("O filme não pode ser ");
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Filme filme) {
                try {
            String sql = "UPDATE filme SET codigo=?, titulo=?, genero=?, sinopse=?"
                    + "WHERE id=?";

            conectar(sql);
            comando.setString(1, filme.getCodigo());
            comando.setString(2, filme.getTitulo());
            comando.setString(3, filme.getGenero());
            comando.setString(4, filme.getSinopse());
            comando.setInt(5, filme.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar filme no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Filme> listar() {
        List<Filme> listaFilmes = new ArrayList<>();

        String sql = "SELECT * FROM filme";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String codigo = resultado.getString("codigo");
                String titulo = resultado.getString("titulo");
                String genero = resultado.getString("genero");
                String sinopse = resultado.getString("sinopse");


                Filme fil = new Filme(id, codigo, titulo, genero, sinopse);
                listaFilmes.add(fil);

            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os filmes do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaFilmes);
    }

    @Override
    public Filme procurarPorId(int id) {
                String sql = "SELECT * FROM filme WHERE id = ?";

        try {
            conectar(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String codigo = resultado.getString("codigo");
                String titulo = resultado.getString("titulo");
                String genero = resultado.getString("genero");
                String sinopse = resultado.getString("sinopse");

                Filme fil = new Filme(id, codigo, titulo, genero, sinopse);

                return fil;

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o filme pelo id do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }

    @Override
    public Filme procurarPorCodigo(String codigo) {
                String sql = "SELECT * FROM filme WHERE codigo = ?";

        try {
            conectar(sql);
            comando.setString(1, codigo);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id");
                String titulo = resultado.getString("titulo");
                String genero = resultado.getString("genero");
                String sinopse = resultado.getString("sinopse");

                Filme fil = new Filme(id, codigo, titulo, genero, sinopse);

                return fil;

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
    public List<Filme> listarPorTitulo(String titulo) {
        List<Filme> listaFilmes = new ArrayList<>();
        String sql = "SELECT * FROM filme WHERE titulo LIKE ?";

        try {
            conectar(sql);
            comando.setString(1, "%" + titulo + "%");
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String codigo = resultado.getString("codigo");
                String tituloX = resultado.getString("titulo");
                String genero = resultado.getString("genero");
                String sinopse = resultado.getString("sinopse");

                Filme fil = new Filme(id, codigo, tituloX, genero, sinopse);                

                listaFilmes.add(fil);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os filmes pelo titulo no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaFilmes); 
    }
    
}
