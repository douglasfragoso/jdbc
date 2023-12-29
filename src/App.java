import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

public class App {
    public static void main(String[] args){
        
        Connection coon = null;
        Statement st = null;//prepara uma consulta sql
        ResultSet rs = null;//resultado da consulta sql

        try{
            coon = DB.getConnection();//conecta banco de dados

            st = coon.createStatement();//intância o objeto do tipo statement

            rs = st.executeQuery("select * from department");//método que faz a consulta sql

            while (rs.next()){//enquanto tiver o próximo
                System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
            }
        }
        catch(SQLException e){
                e.printStackTrace();
            }
        finally{//preciso fechar recurso manualmente para evitar vazamento de memória
            DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
        }
        }
    }

