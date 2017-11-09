
package model;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
- Cadastro de Sessão: relaciona-se em cada sessão a sala (objeto),
* um horário (só hora, mas utilizar LocalTime) e o filme (objeto).
 * @author RENAN
 */
public class Sessao {
    private int id; // id, vai servir tbm como código unitario
    private LocalTime horario;
    private Sala sala;
    private Filme filme;
    private int qntIngresso;    
    
        public Sessao(LocalTime h, Sala sala, Filme filme) {
        this.horario = h;
        this.sala = sala;
        this.filme = filme;
        this.qntIngresso = sala.getLugares();
    }
        
        public Sessao(int id, LocalTime h, int qntIngresso, Sala sala, Filme filme) {
        this.id = id;
        this.qntIngresso = qntIngresso;
        this.horario = h;
        this.sala = sala;
        this.filme = filme;

    }
        
    public int getQntIngresso() {
        return qntIngresso;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public void setQntIngresso(int qntIngresso) {
        this.qntIngresso = qntIngresso;
    }

    public int getId() {
        return id;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public Sala getSala() {
        return sala;
    }

    public Filme getFilme() {
        return filme;
    }
    
    public void retiraIngresso(){
        qntIngresso--;    
    }
    


   
}
