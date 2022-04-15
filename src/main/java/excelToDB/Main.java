package excelToDB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ExcelProject", "root", "parola06");
        String excelPath = args[0];
        try {
            PreparedStatement insertAngajati = con.prepareStatement("insert into Angajati(Nume,Prenume,Oras,Salariu) values (?,?,?,?)");
            FileReader excelFile = new FileReader(excelPath);
            BufferedReader lineReader = new BufferedReader(excelFile);
            String lineText ;
            lineReader.readLine();
            while((lineText = lineReader.readLine()) != null){
                String[] data = lineText.split(",");
                String nume = data[0];
                String prenume = data[1];
                String oras = data[2];
                String salariu = data[3];

                insertAngajati.setString(1, nume);
                insertAngajati.setString(2, prenume);
                insertAngajati.setString(3, oras);
                insertAngajati.setString(4, salariu);
                insertAngajati.addBatch();
                insertAngajati.executeBatch();


            }
            lineReader.close();
            insertAngajati.executeBatch();
            con.close();
            System.out.println("Ai trasnferat cu succes informatiile catre database");

        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
