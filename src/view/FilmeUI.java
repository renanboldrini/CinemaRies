package view;
import java.util.InputMismatchException;
import java.util.List;
import model.Filme;
import negocio.NegocioException;
import negocio.FilmeNegocio;
import util.Console;
import view.menu.FilmeMenu;


public class FilmeUI {
    
    private FilmeNegocio filmeNegocio;

    public FilmeUI() {
        filmeNegocio = new FilmeNegocio();       
    }    
    
     public void menu() {
        int opcao = -1;
        do {
            try{
                System.out.println(FilmeMenu.getOpcoes());                
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case FilmeMenu.OP_CADASTRAR:
                        cadastrarFilme();
                        break;
                    case FilmeMenu.OP_DELETAR:
                        deletarFilme();
                        break;
                    case FilmeMenu.OP_ATUALIZAR:
                        atualizarFilme();
                        break;
                    case FilmeMenu.OP_LISTAR:
                        mostrarFilme();
                        break;
                    case FilmeMenu.OP_CONSULTAR:
                        consultarFilmePorTitulo();
                        break;
                    case FilmeMenu.OP_SAIR:
                        System.out.println("Finalizando a aplicacao..");
                        break;
                    default:
                        System.out.println("Opção inválida..");
                }
            } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
            }

        } while (opcao != FilmeMenu.OP_SAIR);
    }

     private void cadastrarFilme() {
        String codigo = Console.scanString("Codigo: ");
        String titulo = Console.scanString("Titulo: ");
        String genero = Console.scanString("Genero: ");
        String sinopse = Console.scanString("Sinopse: ");        
        try {
            filmeNegocio.salvar(new Filme(codigo, titulo, genero, sinopse));
            System.out.println("Filme " + titulo + " cadastrado com sucesso!");
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        } 
    }
     
     public void mostrarFilme() {
        List<Filme> listaFilmes = filmeNegocio.listar();
        this.mostrarFilmes(listaFilmes);
     }

     private void deletarFilme() {
        String codigo = Console.scanString("Codigo do filme a ser deletada: ");
        try {
            Filme fil = filmeNegocio.procurarPorCodigo(codigo);
            this.mostrarFilme(fil);
            if (UIUtil.getConfirmacao("Realmente deseja excluir esse Filme?")) {
                filmeNegocio.deletar(fil);
                System.out.println("deletado com sucesso!");
            } else {
                System.out.println("Operacao cancelada!");
            }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }
    }
     
    private void atualizarFilme() {
        String codigo = Console.scanString("Codigo do filme a ser alterado: ");
        try {
            Filme fil = filmeNegocio.procurarPorCodigo(codigo);
            this.mostrarFilme(fil);

            System.out.println("Digite os dados do filme que quer alterar [Vazio caso nao queira]"); 

            String titulo = Console.scanString("Titulo: ");
            String genero = Console.scanString("Genero: ");
            String sinopse = Console.scanString("Sinopse: ");

            if (!titulo.isEmpty()) {
                fil.setTitulo(titulo);
            }
            if (!genero.isEmpty()) {
                fil.setGenero(genero);
            }
            if (!sinopse.isEmpty()) {
                fil.setSinopse(sinopse);
            }            
            
            filmeNegocio.atualizar(fil);
            System.out.println("Filme " + titulo + " atualizado com sucesso!");
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        } 
    }
    
        private void consultarFilmePorTitulo() {
        String titulo = Console.scanString("Titulo: ");
        try {
            List<Filme> listaFil = filmeNegocio.listarPorTitulo(titulo);
            this.mostrarFilmes(listaFil);
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }
        }
        
    private void mostrarFilme(Filme filme) {
        System.out.println("-----------------------------");
        System.out.println("Filme");
        System.out.println("Condigo: " + filme.getCodigo());
        System.out.println("Titulo: " + filme.getTitulo());
        System.out.println("Genero: " + filme.getGenero());
        System.out.println("Sinopse: " + filme.getSinopse());        
        System.out.println("-----------------------------");
    }
    
    private void mostrarFilmes(List<Filme> listaFilmes) {
        if (listaFilmes.isEmpty()) {
            System.out.println("Filmes nao encontrados!");
        } else {
            System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "Codigo") + "\t"
                    + String.format("%-20s", "|Titulo") + "\t"
                    + String.format("%-20s", "|Genero") + "\t"
                    + String.format("%-20s", "|Sinopse"));
            for (Filme filme : listaFilmes) {
                System.out.println(String.format("%-10s", filme.getCodigo()) + "\t"
                        + String.format("%-20s", "|" + filme.getTitulo()) + "\t"
                        + String.format("%-20s", "|" + filme.getGenero()) + "\t"
                        + String.format("%-20s", "|" + filme.getSinopse()));
            }
        }
    }     
}
