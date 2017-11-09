
package model;
/**
 * @author Renan
 */
public class Ingresso {
    int id;
    Sessao sessao;
    
    public Ingresso(Sessao sessao) {
        this.sessao = sessao;
    }   
    
    public Ingresso(int id, Sessao sessao) {
        this.id = id;
        this.sessao = sessao;
    } 

    public int getId() {
        return id;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }
        
}
