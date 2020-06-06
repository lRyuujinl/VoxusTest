package controller;

import model.Payment;

import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PaymentInput extends Payment
{
    //Recebe input de um novo pagamento
    public HashMap<String,String> cadastrarPagamento(String titulo, double valor, String data, String comentario, double taxa_ext)
    {
        HashMap<String, String> dados = new HashMap<>();
        try
        {
            if (titulo != null && data != null) {
                Payment p1 = new Payment(titulo, valor, data, comentario, taxa_ext);
                dados.put("posting_id", Integer.toString(p1.getPosting_id()));
                dados.put("titulo", p1.getTitle());
                dados.put("valor", Double.toString(p1.getValue()*p1.getTax()));
                dados.put("Data", p1.getDate());
                dados.put("comentario", p1.getComment());
                dados.put("taxa_ext", Double.toString(p1.getTax()));
                return dados;
            }
        }
        catch(Exception E)
        {
            throw new IllegalArgumentException("Uma das entradas está errada!");
        }

        return dados;
    }


    public void readExcelFile(String filePath) throws IOException, InvalidFormatException
    {
        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);
        DataFormatter dataF = new DataFormatter();
        Iterator<Sheet> sheetIterator = workbook.sheetIterator(); //Iterador para Sheets
        int aux = 1; //Usar Aux para pular os headers
        ArrayList<String> dados = new ArrayList<>();


        while(sheetIterator.hasNext())  //Itera pelos sheets
        {
            Sheet sh = sheetIterator.next();
            Iterator<Row> rowIterator = sh.iterator();

            if(aux == 1)
            {
                rowIterator.next();     //Pula os headers.
                aux++;
            }

            while(rowIterator.hasNext())    //Itera Rows
            {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                dados.clear();  //Limpa o array para receber a próxima row

                while(cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    String cellValue = dataF.formatCellValue(cell);
                    System.out.print(cellValue + "\t");
                    dados.add(cellValue);
                }
            }
        }

    }



}


