
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;
import db.DbException;
import db.DbIntegrityException;

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
        // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // Connection coon = null;
        // PreparedStatement st = null;//prepara consulta deixando os parâmetros

        // try{
        //     coon = DB.getConnection();
        //     st = coon.prepareStatement(
        //         "INSERT INTO seller " +
        //         "(Name, Email, BirthDate, BaseSalary, DepartmentId)"+
        //         "VALUES " +
        //         "(?, ?, ?, ?, ?)",
        //         Statement.RETURN_GENERATED_KEYS//recuperar ID do novo objeto do banco
        //     );

        //     st.setString(1, "Carl Purple");
        //     st.setString(2, "carl@gmail.com");
        //     st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime()));//instânciar uma data no banco, é pelo sql
        //     st.setDouble(4, 3000);
        //     st.setInt(5, 4);

        //     int rowsAffected = st.executeUpdate();//executa alteração no banco

        //     if (rowsAffected > 0){
        //        ResultSet rs = st.getGeneratedKeys();//resultado da consulta sql
        //        while (rs.next()){
        //             int id = rs.getInt(1);
        //             System.out.println("Done! Id = " + id);
        //        }
        //     }
        //     else{
        //         System.out.println("No rown affected!");
        //     }
        // }
        // catch (SQLException e){
        //     e.printStackTrace();
        // }
        // catch (ParseException e){
        //     e.printStackTrace();
        // }
        // finally{//preciso fechar recurso manualmente para evitar vazamento de memória
		// 	DB.closeStatement(st);
		// 	DB.closeConnection();
        // }

        //atualizar dados
        // Connection conn = null;
		// PreparedStatement st = null;
		// try {
		// 	conn = DB.getConnection();
	
		// 	st = conn.prepareStatement(
		// 			"UPDATE seller "
		// 			+ "SET BaseSalary = BaseSalary + ? "
		// 			+ "WHERE "
		// 			+ "(DepartmentId = ?)");

		// 	st.setDouble(1, 200.0);
		// 	st.setInt(2, 2);
			
		// 	int rowsAffected = st.executeUpdate();
			
		// 	System.out.println("Done! Rows affected: " + rowsAffected);
		// }
		// catch (SQLException e) {
		// 	e.printStackTrace();
		// } 
		// finally {
		// 	DB.closeStatement(st);
		// 	DB.closeConnection();
		// }

        //deletar dados
        // Connection conn = null;
		// PreparedStatement st = null;
		// try {
		// 	conn = DB.getConnection();
	
		// 	st = conn.prepareStatement(
		// 			"DELETE FROM department "
		// 			+ "WHERE "
		// 			+ "Id = ?");

		// 	st.setInt(1, 4);
			
		// 	int rowsAffected = st.executeUpdate();
			
		// 	System.out.println("Done! Rows affected: " + rowsAffected);
		// }
		// catch (SQLException e) {
		// 	throw new DbIntegrityException(e.getMessage());
		// } 
		// finally {
		// 	DB.closeStatement(st);
		// 	DB.closeConnection();
		// }

        //transações
        Connection conn = null;
		Statement st = null;     
        try {
			conn = DB.getConnection();

            conn.setAutoCommit(false);//não confirmar as alterações automaticamentes, ficando pendentes de autorização explícita

            st = conn.createStatement();
			
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");

            // int x = 1;
            // if (x < 2){
            //     throw new SQLException("Fake error");
            // }

            int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");

            conn.commit();//confirmar transação
			
			System.out.println("Rows 1:" + rows1 + "\n Rows 2:" + rows2 );
		}
		catch (SQLException e) {
            try {
                conn.rollback();//volta transação caso tenha ocorrido algum erro, deve tratar a exceção 
                throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
            } catch (SQLException e1) {//quando tenta voltar e acontece um erro
                throw new DbException("Error trying to rollback! Caused by: " + e.getMessage());
            }
		} 
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}   

	}
}
    

