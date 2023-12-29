package db;

public class DbException extends RuntimeException { //não precisa tratar sempre, apenas quando achar necessário
   
    private static final long serialVersionUID = 1L;

    public DbException(String msg){
        super(msg);
    }

}
