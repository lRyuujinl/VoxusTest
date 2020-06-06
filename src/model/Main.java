package model;

import controller.PaymentInput;
import view.PaymentView;

import javax.swing.*;
import java.sql.*;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        try
        {
            JFrame frame = new PaymentView("Voxus APP");
            frame.setVisible(true);
            PaymentInput pi = new PaymentInput();
           // pi.readExcelFile("W://Documents/USP/AED II/VoxusTech/Arquivo base - Desafio Voxus.xlsx");

        }
        catch (Exception throwables)
        {
            throwables.printStackTrace();
        }

    }
}
