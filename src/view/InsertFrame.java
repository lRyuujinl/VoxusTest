package view;

import controller.PaymentInput;
import model.DBOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class InsertFrame extends JFrame
{
    private JTextField title_text_field;
    private JTextField tax_text_field;
    private JTextField comment_text_field;
    private JTextField date_text_field;
    private JTextField value_txt_field;
    private JButton save_button;
    private JButton cancel_button;
    private JPanel insertPanel;

    public InsertFrame(String title)
    {
        super(title);
        this.setPreferredSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(insertPanel);
        this.pack();

        //Salvar os dados preenchidos pela pessoa
        save_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = title_text_field.getText();
                double value = Double.parseDouble(value_txt_field.getText().toString());
                String date = date_text_field.getText();
                String comentario = comment_text_field.getText();
                double taxa = Double.parseDouble(tax_text_field.getText().toString());

                try
                {
                    PaymentInput pi = new PaymentInput();
                    HashMap<String,String> newPayment = pi.cadastrarPagamento(title,value,date,comentario,taxa);
                    DBOperations con1 = new DBOperations();
                    con1.connect();
                    con1.insertData("payment_log",newPayment,pi.ordem);
                    dispose();
                    JOptionPane.showMessageDialog(null, "Saved Successfully!");
                }
                catch(Exception E)
                {
                    JOptionPane.showMessageDialog(null, "Failed to Save!");
                    E.printStackTrace();
                }
            }

        });

        //Cancela a operação atual
        cancel_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });



    }
}
