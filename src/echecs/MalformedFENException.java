package echecs;



/**
 * Classe qui représente une exception pour code FEN incorrecte.
 * @author samuel
 */
public class MalformedFENException extends IllegalArgumentException{
    
    
    MalformedFENException(){
        super();
    }
    
    MalformedFENException(String msg){
        super(msg);
    }
    
    
}
