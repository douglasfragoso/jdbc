package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection; //conection do jdbc 
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {


    private static Connection coon = null; //objeto de conexão com banco de dados do jdbc 

    public static Connection getConnection(){ //cria e abre conexão com o banco de dados
        if (coon == null){ 
            try{
            Properties props = loadProperties(); //pega as propriedades do banco pelo método loadProperties
            String url = props.getProperty("dburl");//vai pegar a url do banco de dados 
            coon = DriverManager.getConnection(url, props); //conecta com o banco de dados e salva na variável coon 
            }
            catch(SQLException e){ //o método do driver manager pode causar uma sql exception e deve ser tratada
                throw new DbException(e.getMessage());
            }

        }
        return coon;
    }

    public static void closeConnection(){ //fecha o banco de dados
        if(coon != null){
            try{
                coon.close();
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties(){ //ler os dados do db.properties e carrega propriedades
        try (FileInputStream fs = new FileInputStream("db.properties")) { //lê o arquivo da pasta raiz do projeto
            Properties props = new Properties(); //instância o objeto do tipo properties
            props.load(fs); //recebe como parametro o fs
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

}
