
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class App {
    public static void main(String[] args){
        
        //recuperar dados
        // Connection coon = null;
        // Statement st = null;//prepara uma consulta sql
        // ResultSet rs = null;//resultado da consulta sql

        // try{
        //     coon = DB.getConnection();//conecta banco de dados

        //     st = coon.createStatement();//intância o objeto do tipo statement

        //     rs = st.executeQuery("select * from department");//método que faz a consulta sql

        //     while (rs.next()){//enquanto tiver o próximo
        //         System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
        //     }
        // }
        // catch(SQLException e){
        //         e.printStackTrace();
        //     }
        // finally{//preciso fechar recurso manualmente para evitar vazamento de memória
        //     DB.closeResultSet(rs);
		// 	DB.closeStatement(st);
		// 	DB.closeConnection();
        // }

        //inserir dados
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Connection coon = null;
        PreparedStatement st = null;//prepara consulta deixando os parâmetros

        try{
            coon = DB.getConnection();
            st = coon.prepareStatement(
                "INSERT INTO seller " +
                "(Name, Email, BirthDate, BaseSalary, DepartmentId)"+
                "VALUES " +
                "(?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS//recuperar ID do novo objeto do banco
            );

            st.setString(1, "Carl Purple");
            st.setString(2, "carl@gmail.com");
            st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime()));//instânciar uma data no banco, é pelo sql
            st.setDouble(4, 3000);
            st.setInt(5, 4);

            int rowsAffected = st.executeUpdate();//executa alteração no banco

            if (rowsAffected > 0){
               ResultSet rs = st.getGeneratedKeys();//resultado da consulta sql
               while (rs.next()){
                    int id = rs.getInt(1);
                    System.out.println("Done! Id = " + id);
               }
            }
            else{
                System.out.println("No rown affected!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        finally{//preciso fechar recurso manualmente para evitar vazamento de memória
			DB.closeStatement(st);
			DB.closeConnection();
        }

    }

}