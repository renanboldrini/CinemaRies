
package view;
 
import java.time.LocalTime;
import model.Filme;
import model.Sala;
import model.Sessao;
import java.util.InputMismatchException;
import java.util.List;
import negocio.FilmeNegocio;
import negocio.NegocioException;
import negocio.SalaNegocio;
import negocio.SessaoNegocio;
import util.Console;
import util.DateUtil;
import view.menu.SessaoMenu;;

/**
 *
 * @author 631620025
 */
public class SessaoUI {
    private FilmeNegocio filmeNegocio;
    private SalaNegocio salaNegocio;
    private SessaoNegocio sessaoNegocio;

 public SessaoUI() {
        salaNegocio = new SalaNegocio();
        filmeNegocio = new FilmeNegocio();
        sessaoNegocio = new SessaoNegocio();
    }

     public void menu() {
        int opcao = -1;
        do {
            try{
                System.out.println(SessaoMenu.getOpcoes());
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case SessaoMenu.OP_CADASTRAR:
                        cadastrarSessao();
                        break;
                    case SessaoMenu.OP_DELETAR:
                        deletarSessao();
                        break;
                    case SessaoMenu.OP_LISTAR:
                        mostrarSessao();
                        break;
                    case SessaoMenu.OP_CONSULTAR:
                        consultarSessaoPorId();
                        break;
                    case SessaoMenu.OP_SAIR:
                        System.out.println("Finalizando a aplicacao..");
                        break;
                    default:
                        System.out.println("Opção inválida..");
                }
            } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
            }

        } while (opcao != SessaoMenu.OP_SAIR);
    }
 
 
     private void cadastrarSessao() {
        try {
            LocalTime h = DateUtil.stringToTime(Console.scanString("Digite o horario da sessao(hh:mm)"));

            String numero = Console.scanString("Numero da sala: ");
            Sala s = salaNegocio.procurarPorNumero(numero);

            String codigo = Console.scanString("Codigo do filme: ");
            Filme f = filmeNegocio.procurarPorCodigo(codigo);
       
            sessaoNegocio.salvar(new Sessao(h, s, f));            
            this.mostrarSessao(new Sessao(h, s, f));
            System.out.println("Sessão cadastrada com sucesso! \n");
            
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Cadstrar as informações de acordo com o item solicitado \n lembrando que não é possível cadastrar duas sessões na mesma sala e horário"); 
    }
   }
        
    public void mostrarSessao() {
        List<Sessao> listaSessao = sessaoNegocio.listar();
        this.listarSessao(listaSessao);
    }
    
    public void listarSessao(List<Sessao> listaSessao) { //lista
        if (listaSessao.isEmpty()) {
            System.out.println("Sessao nao encontradas!");
        } else {
            System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "ID") + "\t"
                    + String.format("%-10s", "|HORARIO")+ "\t"
                    + String.format("%-10s", "|INGRESSOS")+ "\t"
                    + String.format("%-10s", "|SALA")+ "\t"
                    + String.format("%-20s", "|FILME"));
            for (Sessao sessao : listaSessao) {
                System.out.println(String.format("%-10s", sessao.getId()) + "\t"
                        + String.format("%-10s", sessao.getHorario()) + "\t"
                        + String.format("%-10s", sessao.getQntIngresso()) + "\t"
                        + String.format("%-10s", sessao.getSala().getNumero()) + "\t"
                        + String.format("%-20s", "|" + sessao.getFilme().getTitulo()));
            }
        }
    }
    
       private void mostrarSessao(Sessao s) { //apresenta sessão
        if (s == null) {
            System.out.println("Sessao nao encontrada!");
        } else {
            System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "|HORARIO")+ "\t"
                    + String.format("%-10s", "|INGRESSOS")+ "\t"
                    + String.format("%-10s", "|SALA")+ "\t"
                    + String.format("%-20s", "|FILME"));
            
                System.out.println(String.format("%-10s", s.getHorario()) + "\t"
                        + String.format("%-10s", s.getQntIngresso()) + "\t"
                        + String.format("%-10s", s.getSala().getNumero()) + "\t"
                        + String.format("%-20s", "|" + s.getFilme().getTitulo()));
            
        }
    }
       
    private void consultarSessaoPorId() {
        String numero = Console.scanString("ID da sessao: ");
        try {
            this.mostrarSessao(sessaoNegocio.procurarPorId(numero));
        } catch (Exception ex) {
            System.out.println("errouuu 2");
        }

    }
    
    private void deletarSessao() {
        String id = Console.scanString("Id da sessao a ser deletada: ");
        try {
            Sessao sessao = sessaoNegocio.procurarPorId(id);
            this.mostrarSessao(sessao);
                sessaoNegocio.deletar(sessao);
                System.out.println("deletada com sucesso!");

        } catch (Exception ex) {
            System.out.println("Verificar informações concedidas");
        }
    }    

}



//public void mostrarSessaoAtiva() {