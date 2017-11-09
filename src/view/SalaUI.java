package view;
import java.util.InputMismatchException;
import java.util.List;
import model.Sala;
import negocio.NegocioException;
import negocio.SalaNegocio;
import repositorio.RepositorioSalas;
import util.Console;
import view.menu.SalaMenu;

public class SalaUI {
    
    private SalaNegocio salaNegocio;

    public SalaUI() {
        salaNegocio = new SalaNegocio();
    }
    
     public void menu() {
        int opcao = -1;
        do {
            try{
                System.out.println(SalaMenu.getOpcoes());
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case SalaMenu.OP_CADASTRAR:
                        cadastrarSala();
                        break;
                    case SalaMenu.OP_DELETAR:
                        deletarSala();
                        break;
                    case SalaMenu.OP_ATUALIZAR:
                        atualizarSala();
                        break;
                    case SalaMenu.OP_LISTAR:
                        mostrarSalas();
                        break;
                    case SalaMenu.OP_CONSULTAR:
                        consultarSalaPorNumero();
                        break;
                    case SalaMenu.OP_SAIR:
                        System.out.println("Finalizando a aplicacao..");
                        break;
                    default:
                        System.out.println("Opção inválida..");
                }
            } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
            }

        } while (opcao != SalaMenu.OP_SAIR);
    }

    private void cadastrarSala() {
        String numero = Console.scanString("Numero da Sala: ");
        String lug = Console.scanString("Lugares: ");
        if (lug == null || lug.isEmpty()) {
            System.out.println("Preencha todos os campos");
        }else{

        try {
            int lugares = Integer.parseInt(lug);
            salaNegocio.salvar(new Sala(numero, lugares));
            System.out.println("Sala " + numero + " cadastrado com sucesso!");
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }catch(Exception e){
        System.out.println("em lugares, digitar apenas números");
        } 
        }
    }
     
    public void mostrarSalas() {
        List<Sala> listaSalas = salaNegocio.listar();
        this.mostrarSalas(listaSalas);
    }
 
    private void deletarSala() {
        String numero = Console.scanString("Numero da sala a ser deletada: ");
        try {
            Sala sal = salaNegocio.procurarPorNumero(numero);
            this.mostrarSala(sal);
            if (UIUtil.getConfirmacao("Realmente deseja excluir essa sala?")) {
                salaNegocio.deletar(sal);
                System.out.println("deletada com sucesso!");
            } else {
                System.out.println("Operacao cancelada!");
            }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }
    }    
    
    private void atualizarSala() {
        String numero = Console.scanString("Numero da sala a ser alterada: ");
        try {
            Sala sal = salaNegocio.procurarPorNumero(numero);
            this.mostrarSala(sal);

            System.out.println("Digite os dados da sala que quer alterar"); 
            int lugares = Integer.parseInt(Console.scanString("Lugares: "));
            if(lugares < 1){
               System.out.println("A capacidade deve ser superior a 0 lugares"); 
            }else{
                
                salaNegocio.atualizar(sal);
                System.out.println("Sala " + numero + " atualizada com sucesso!");
            }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }catch(Exception e){
        System.out.println("em lugares, digitar apenas números");
        }  
    }

    private void consultarSalaPorNumero() {
        String numero = Console.scanString("Numero: ");
        try {
            List<Sala> listaSal = salaNegocio.listarPorNumero(numero);
            this.mostrarSalas(listaSal);
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }

    } 
    
    private void mostrarSala(Sala sala) {
        System.out.println("-----------------------------");
        System.out.println("Sala");
        System.out.println("Numero: " + sala.getNumero());
        System.out.println("Lugares: " + sala.getLugares());
        System.out.println("-----------------------------");
    }    
    
      private void mostrarSalas(List<Sala> listaSalas) {
        if (listaSalas.isEmpty()) {
            System.out.println("PSalas nao encontradas!");
        } else {
            System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "NUMERO") + "\t"
                    + String.format("%-10s", "|LUGARES"));
            for (Sala sala : listaSalas) {
                System.out.println(String.format("%-10s", sala.getNumero()) + "\t"
                        + String.format("%-20s", "|" + sala.getLugares()));
            }
        }
    }
      
      
}
