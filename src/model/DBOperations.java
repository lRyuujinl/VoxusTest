package model;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import static java.sql.Types.*;

//Classe que faz a conexao com o banco de dados e controla a manipulação de dados
public class DBOperations
{
    //Parametros para conectar com o banco de dados
    private final String url = "jdbc:postgresql://localhost:5432/Payment";
    private final String user = "postgres";
    private final String password = "945974297";

    public Connection connect() throws SQLException //Função que conecta com o banco de dados
    {
        return DriverManager.getConnection(url, user, password);
    }

    HashMap<String, String> col_value = new HashMap<>();

    //Função que insere data
    public long insertData(String tableName, HashMap<String, String> data, HashMap<String, Integer> ordem)
    {
        String SQL = "INSERT INTO " + tableName + " ("; //INSERT INTO payment (posting_id, titulo, valor, Data, Comment, taxa_ext) VALUES (valores respectivos das chaves criados no hashmap)
        long id = 0;
        Set entrySet = data.entrySet();
        Iterator it = entrySet.iterator();
        while(it.hasNext())     //Iterar pelo hashmap para pegar as colunas(keys)
        {
            Map.Entry me = (Map.Entry)it.next();
            String key = (String) me.getKey();
            if(!it.hasNext())
            {
                SQL = SQL + key;
            }
            else
            {
                SQL = SQL + key + ",";
            }
        }
        SQL = SQL + ") VALUES (" + "?,?,?,?,?,?" + ")";     //Fieldscards = aos valores das colunas(values)

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;

            for(Map.Entry<String, String> entry: data.entrySet())
            {
                switch(pstmt.getMetaData().getColumnType(ordem.get(entry.getKey())))    //Extrair o tipo da coluna em pré-SQL e converter para o tipo de coluna respectivo do PostgreSQL
                {
                    case INTEGER:
                        pstmt.setInt(i, Integer.valueOf(entry.getValue()));
                        break;

                    case FLOAT:
                    case DOUBLE:
                        pstmt.setDouble(i,Double.valueOf(entry.getValue()));
                        break;

                    case DATE:
                        pstmt.setDate(i, Date.valueOf(entry.getValue()));
                        break;

                    default:
                        pstmt.setString(i, entry.getValue());
                }
                i++;
            }


            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    //Função que executa a query de exibir os dados
    public DefaultTableModel showData (String table)
    {
        String SQL = "SELECT * FROM " + table;
        DefaultTableModel model = new DefaultTableModel(new String[]{"posting_id", "titulo", "valor", "data", "comentario", "taxa_ext"}, 0);
        try (Connection conn = connect();
            PreparedStatement pst = conn.prepareStatement(SQL))
        {
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int nColumns = rsmd.getColumnCount();

            while(rs.next())
            {
                String a = rs.getString("posting_id");
                String b = rs.getString("titulo");
                String c = rs.getString("valor");
                String d = rs.getString("data");
                String e = rs.getString("comentario");
                String f = rs.getString("taxa_ext");
                model.addRow(new Object[]{a,b,c,d,e,f});
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return model;
    }

    //Função que executa query de deletar dados baseado em uma primary key
    public void deletePayment(String tableName, int posting_id)
    {
        String SQL = "DELETE FROM " + tableName + " WHERE " + "posting_id " + " = " + Integer.toString(posting_id);

        try (Connection conn = connect();
        PreparedStatement pst = conn.prepareStatement(SQL);)
        {
            pst.executeUpdate();
            System.out.println("Payment deleted successfully \n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Função que executa query que altera os dados de uma tupla da sua tabela
    public long alterPayment(String tableName, int posting_id, HashMap<String, String> data, HashMap<String, Integer> ordem)
    {
        String SQL;
        if(data.size() > 1)
        {
            SQL = "UPDATE "+ tableName + " SET ("; //Ex de como deve ficar a query final: UPDATE payment SET (value) = 123654 FROM Payment WHERE posting_id = 546123
        }
        else
        {
            SQL = "UPDATE " + tableName + " SET ";
        }
            long id = 0;

        //Iterar pelo HashMap para poder colocar multiplos valores.
        Set entrySet = data.entrySet();
        Iterator it = entrySet.iterator();
        int cont = 0;
        while(it.hasNext())
        {
            Map.Entry me = (Map.Entry)it.next();
            String key = (String) me.getKey();
            if(!it.hasNext())
            {
                if(data.size() == 1)
                {
                    SQL += key;
                    cont++;
                }
                else
                {
                    SQL += key + ") ";
                    cont++;
                }
            }
            else
            {
                SQL = SQL + key + ",";
                cont++;
            }
        }

        SQL += " = (";
        for(int j = 0; j<=cont-1;j++)
        {
            if(j== cont-1)
            {
                SQL += "?)";
            }
            else
            {
                SQL +="?,";
            }

        }
        SQL += " WHERE posting_id = " + posting_id; //Final da composição da Query

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;

            for(Map.Entry<String, String> entry: data.entrySet())
            {
                switch(pstmt.getMetaData().getColumnType(ordem.get(entry.getKey())))    //Extrair o tipo da coluna em pré-SQL e converter para o tipo de coluna respectivo do PostgreSQL
                {
                    case INTEGER:
                        pstmt.setInt(i, Integer.valueOf(entry.getValue()));
                        break;

                    case FLOAT:
                    case DOUBLE:
                        pstmt.setDouble(i,Double.valueOf(entry.getValue()));
                        break;

                    case DATE:
                        pstmt.setDate(i, Date.valueOf(entry.getValue()));
                        break;

                    default:
                        pstmt.setString(i, entry.getValue());
                        break;
                }
                i++;
            }

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }


}
