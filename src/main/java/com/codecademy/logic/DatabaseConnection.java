package com.codecademy.logic;

import java.sql.*;

public class DatabaseConnection {


    /**
     * Dit is een voorbeeld Java toepassing waarin je verbinding maakt met een
     * SQLServer database.
     */

    public static void main(String[] args) {

        // Dit zijn de instellingen voor de verbinding. Vervang de databaseName indien deze voor jou anders is.
        String connectionUrl = "jdbc:sqlserver://aei-sql2.avans.nl:1443;databaseName=White;user=white;password=Cheese;encrypt=true;trustServerCertificate=true;";// ;encrypt=true;trustServerCertificate=true"

        // Connection beheert informatie over de connectie met de database.
        Connection con = null;

        // Statement zorgt dat we een SQL query kunnen uitvoeren.
        Statement stmt = null;

        // ResultSet is de tabel die we van de database terugkrijgen.
        // We kunnen door de rows heen stappen en iedere kolom lezen.
        ResultSet rs = null;
        try {
            // 'Importeer' de driver die je gedownload hebt.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Maak de verbinding met de database.
            con = DriverManager.getConnection(connectionUrl);

            // Stel een SQL query samen.
            String SQL = "SELECT TOP 10 * FROM Student";
            stmt = con.createStatement();
            // Voer de query uit op de database.
            rs = stmt.executeQuery(SQL);

            System.out.print(String.format("| %7s | %-32s | %-24s |\n", " ", " ", " ").replace(" ", "-"));

            // Als de resultset waarden bevat dan lopen we hier door deze waarden en printen ze.
            while (rs.next()) {
                // Vraag per row de kolommen in die row op.
//                int ISBN = rs.getInt("ISBN");
                String title = rs.getString("ID");
                String author = rs.getString("Email");
                String authosr = rs.getString("Name");
                String authoar = rs.getString("BirthDate");
                String authodr = rs.getString("Sex");
                String authogr = rs.getString("PostalCode");
                String authohr = rs.getString("HouseNumber");
                String authokr = rs.getString("Suffix");
                String authoru = rs.getString("Street");
                String authotr = rs.getString("City");
                String authorb = rs.getString("Country");

                // Print de kolomwaarden.
                // System.out.println(ISBN + " " + title + " " + author);
                // Met 'format' kun je de string die je print het juiste formaat geven, als je dat wilt.
                // %d = decimal, %s = string, %-32s = string, links uitgelijnd, 32 characters breed.
//                System.out.format("| %7d | %-32s | %-24s | \n", ISBN, title, author);
                System.out.println(title);
            }
            System.out.println(String.format("| %7s | %-32s | %-24s |\n", " ", " ", " ").replace(" ", "-"));
//
        } // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (con != null) try {
                con.close();
            } catch (Exception e) {
            }
        }
    }
}
