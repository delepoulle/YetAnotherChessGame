package echecs;



/**
 *
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
