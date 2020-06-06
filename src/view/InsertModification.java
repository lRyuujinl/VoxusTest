package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertModification extends JFrame
{
    private JFormattedTextField modification_text_field;
    private JButton cancel_modify_button;
    private JButton save_modify_button;

    public InsertModification(String title)
    {
        super(title);
        this.setPreferredSize(new Dimension(300, 140));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(modification_text_field);
        this.pack();

        //Cancels the Operation
        cancel_modify_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        save_modify_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
    }


}

