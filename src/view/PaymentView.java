package view;

import controller.PaymentInput;
import model.DBOperations;
import model.Payment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

//Principais componentes da UI do programa + Comportamento(Apesar de estar em View)
public class PaymentView extends JFrame
{
    private JPanel mainPanel;
    private JTable paymentTableP;
    private JButton showAllPayments;
    private JButton insertButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField modify_text_field;
    private JButton uploadFileButton;

    public PaymentView(String title)
    {
        super(title);
        this.setPreferredSize(new Dimension(1440, 880));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        //Botão que insere novo pagamento, Chama InsertFrame
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertFrame inf = new InsertFrame("Insira as informações do novo pagamento");
                inf.setVisible(true);
            }
        });

        //Botão que mostra a tabela
        showAllPayments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBOperations db = new DBOperations();
                DefaultTableModel dtm = db.showData("payment_log");
                JTable table=new JTable(dtm);
                paymentTableP.setModel(dtm);
                paymentTableP.add(new JScrollPane(table));
                JScrollPane js=new JScrollPane(table);
                js.setVisible(true);
                add(js);
            }
        });

        //Delete Selected row
        deleteButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int column = 0;
                int row = paymentTableP.getSelectedRow();
                String value = paymentTableP.getModel().getValueAt(row, column).toString();
                try {
                    DBOperations db1 = new DBOperations();
                    db1.deletePayment("payment_log", Integer.valueOf(value));
                    JOptionPane.showMessageDialog(null, "Deleted!");

                    //Not efficient Refresh
                    DefaultTableModel dtm = db1.showData("payment_log");
                    JTable table=new JTable(dtm);
                    paymentTableP.setModel(dtm);
                    paymentTableP.add(new JScrollPane(table));


                }
                catch(Exception E)
                {
                    JOptionPane.showMessageDialog(null, "Failed to Save!");
                    E.printStackTrace();

                }

            }
        });

        //Botão que altera informações de um pagamento #HardCoded Unfortunelly
        editButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String modifyData = modify_text_field.getText();
                int selectColumn = paymentTableP.getSelectedColumn();
                int row = paymentTableP.getSelectedRow();

                PaymentInput pi = new PaymentInput();
                HashMap<String, String> dados = new HashMap<>();
                int posting_id = Integer.valueOf(getColValue(row, 0));

                try
                {
                    switch(selectColumn)    //Infelizmente hardcodado também
                    {
                        case 1:
                            dados.put("titulo",modifyData);
                            break;

                        case 2:
                            dados.put("valor",modifyData);
                            break;

                        case 3:
                            dados.put("data",modifyData);
                            break;

                        case 4:
                            dados.put("comentario", modifyData);
                            break;

                        case 5:
                            dados.put("taxa_ext", modifyData);
                            break;

                        default:
                            System.out.println("Coluna não encontrada para a inserção");
                    }

                    DBOperations db2 = new DBOperations();
                    db2.connect();
                    db2.alterPayment("payment_log",posting_id,dados,pi.ordem);
                    JOptionPane.showMessageDialog(null, "Value Modified!");

                    //Not efficient Refresh
                    DefaultTableModel dtm = db2.showData("payment_log");
                    JTable table=new JTable(dtm);
                    paymentTableP.setModel(dtm);
                    paymentTableP.add(new JScrollPane(table));

                } catch (SQLException throwables) {
                    JOptionPane.showMessageDialog(null, "Failed to Modify the value!");

                    throwables.printStackTrace();
                }

            }
        });
        uploadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    String getColValue(int row, int column)
    {
        String result = paymentTableP.getModel().getValueAt(row, column).toString();
        return result;
    }


}
