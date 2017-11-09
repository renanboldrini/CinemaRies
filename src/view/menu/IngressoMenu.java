
package view.menu;
/**
 * @author Renan
 */
public class IngressoMenu {
    public static final int OP_CADASTRAR = 1;
    public static final int OP_DELETAR = 2;
    public static final int OP_LISTAR = 3;
    public static final int OP_CONSULTAR = 4;
    public static final int OP_SAIR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Comprar ingresso\n"
                + "2- Deletar ingresso\n"
                + "3- Listar ingressos\n"                
                + "4- Consultar ingresso por sessão\n"
                + "0- Voltar"
                + "\n--------------------------------------");
    }      
}
