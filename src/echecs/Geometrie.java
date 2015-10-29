package echecs;



/**
 *
 * @author samuel
 */
public class Geometrie {
    
    
    public static boolean estSurColonne(Deplacement d){
        return (d.getX1() == d.getX2());
        
    }
    
    public static boolean estSurLigne(Deplacement d){
        return (d.getY1() == d.getY2());
        
    }
    
    public static boolean estSurDiagonale(Deplacement d){
        int dX = Math.abs(d.getX1() - d.getX2());
        int dY = Math.abs(d.getY1() - d.getY2());
        return (dX == dY);
    }
    
    public static boolean estChevaleresque(Deplacement d){
        int dX = Math.abs(d.getX1() - d.getX2());
        int dY = Math.abs(d.getY1() - d.getY2());   
        return ((dX==2 && dY == 1) || (dX==2 && dY == 1));
    }
}
