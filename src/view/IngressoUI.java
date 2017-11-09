package view;
import java.util.InputMismatchException;
import java.util.List;
import model.Ingresso;
import model.Sessao;
import negocio.IngressoNegocio;
import negocio.NegocioException;
import negocio.SessaoNegocio;
import util.Console;
import static util.DateUtil.timeToString;
import view.menu.IngressoMenu;
/**
 * @author Renan
 */
public class IngressoUI {
    
    private SessaoUI sessaoUI;
    private IngressoNegocio ingressoNegocio;
    private SessaoNegocio sessaoNegocio;   

    public IngressoUI() {
        ingressoNegocio = new IngressoNegocio();
        sessaoNegocio = new SessaoNegocio();
        sessaoUI = new SessaoUI();
    }    
    
    public void menu() {
        int opcao = -1;
        do {
            try{
                System.out.println(IngressoMenu.getOpcoes());
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case IngressoMenu.OP_CADASTRAR:
                        comprarIngresso();
                        break;
                    case IngressoMenu.OP_DELETAR:
                        deletarIngresso();
                        break;
                    case IngressoMenu.OP_LISTAR:
                        mostrarIngresso();
                        break;
                    case IngressoMenu.OP_CONSULTAR:
                        consultarIngressoPorSessao();
                        break;
                    case IngressoMenu.OP_SAIR:
                        System.out.println("Finalizando a aplicacao..");
                        break;
                    default:
                        System.out.println("Opção inválida..");
                }
            } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
            }

        } while (opcao != IngressoMenu.OP_SAIR);
    }

    private void comprarIngresso() {          
        try {
            sessaoUI.listarSessao(sessaoNegocio.listarAtivas());            
            String sessao = Console.scanString("Id da sessao: ");
            Sessao s = sessaoNegocio.procurarPorId(sessao);            
            ingressoNegocio.salvar(new Ingresso(s));            
            if(s.getQntIngresso() > 0){
            this.mostrarIngresso(new Ingresso(s));
            System.out.println("Compra efetudda com sucesso! \n");
            }
            
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        } catch (Exception ex) {
           System.out.println("Cadastrar as informações de acordo com o item solicitado"); 
    }
    }
    
     private void mostrarIngresso(Ingresso i) { //apresenta sessão
        if (i == null) {
            System.out.println("Sessao nao encontrada!");
        } else {
            System.out.println("-----------------------------------\n");
            System.out.println(String.format("Ingresso para o filme: "+i.getSessao().getFilme().getTitulo())+ "\n"
                    + String.format("Horário da sessão: "+i.getSessao().getHorario()+" | sala: "+i.getSessao().getSala().getNumero()+"."));
    }
     }

    public void mostrarIngresso() {
        List<Ingresso> listaIngresso = ingressoNegocio.listar();
        this.mostrarIngresso(listaIngresso);
    }
    
    private void mostrarIngresso(List<Ingresso> listaIngresso) { //lista
        if (listaIngresso.isEmpty()) {
            System.out.println("Ingresso nao encontrado!");
        } else {
            System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "ID") + "\t"
                    + String.format("%-10s", "|HORARIO")+ "\t"
                    + String.format("%-10s", "|SALA")+ "\t"
                    + String.format("%-20s", "|FILME"));
            for (Ingresso ing : listaIngresso) {
                System.out.println(String.format("%-10s", ing.getId()) + "\t"
                        + String.format("%-10s", ing.getSessao().getHorario()) + "\t"
                        + String.format("%-10s", ing.getSessao().getSala().getNumero()) + "\t"
                        + String.format("%-20s", "|" + ing.getSessao().getFilme().getTitulo()));
            }
        }
    }
    
    private void consultarIngressoPorSessao() {
        String sessao = Console.scanString("ID da sessao: ");
        try {
            this.mostrarIngresso(ingressoNegocio.listarPorSessao(sessao));
        } catch (Exception ex) {
            System.out.println("Ingressos não encontrados");
        }

    }
    
        private void deletarIngresso() {
        String id = Console.scanString("Id do ingresso a ser deletado: ");
        try {
            Ingresso ing = ingressoNegocio.procurarPorId(id);
            this.mostrarIngresso(ing);
               ingressoNegocio.deletar(ing);
                System.out.println("deletado com sucesso!");

        } catch (Exception ex) {
            System.out.println();// mensagem opcional... traar as exceções
        }
    } 
    
}
