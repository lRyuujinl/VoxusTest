package model;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;


//Clientes não devem ser capazes de modificar os valores de um registro. Portanto é necessário encapsular e proteger os dados:
public class Payment {

    private String titulo;
    private double valor;
    private double taxa_ext = 1.05;
    private Date data;
    String comentario;
    private int posting_id;

    public Payment(String titulo, double valor, String data, String comentario, double taxa_ext)
    {
        this.posting_id = ThreadLocalRandom.current().nextInt(6, 100000 + 1);
        this.titulo = titulo;
        this.valor = valor*taxa_ext;
        this.data = java.sql.Date.valueOf(data);
        this.comentario = comentario;
        this.taxa_ext = taxa_ext;
    }

    public Payment(){}

    //Mapeamento da ordem das colunas, é necessario uma vez que o Hashing distribui de forma aleatoria o indice das colunas. O mapeamento é importante para associar o JDBC corretamente com as colunas.
    final public HashMap<String, Integer> ordem = new HashMap(){{
        put("posting_id",1);
        put("titulo",2);
        put("valor",3);
        put("Data",4);
        put("comentario",5);
        put("taxa_ext",6);
    }};

    public String getTitle()
    {
        return this.titulo;
    }

    public double getValue()
    {
        return this.valor;
    }

    public double getTax()
    {
        return this.taxa_ext;
    }

    public String getDate()
    {
        return this.data.toString();
    }

    public String getComment()
    {
        return this.comentario;
    }

    public int getPosting_id()
    {
        return this.posting_id;
    }


}
