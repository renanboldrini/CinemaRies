package dao;

import java.util.List;


//A ideia da interface Dao é que padronizar todos os métodos do CRUD da aplicação.
public interface Dao<T> {
    public void salvar(T model);
    public void deletar(T model);
    public void atualizar(T model);
    public List<T> listar();
    public T procurarPorId(int id);
}
