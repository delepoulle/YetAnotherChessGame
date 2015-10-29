package echecs;



/**
 * Représentation d'un déplacement
 * @author samuel
 */
public class Deplacement {
    
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;

    public Deplacement(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    public Deplacement(Position p1, Position p2){
        x1 = p1.getX();
        y1 = p1.getY();
        x2 = p2.getX();
        y2 = p2.getY();        
    }
    
    /** 
     * Constructeur par recopie
     * @param that
     */
    public Deplacement(Deplacement that){
        x1 = that.x1;
        y1 = that.y1;
        x2 = that.x2;
        y2 = that.y2; 
    }
    
    public Position getDepart(){
        return new Position(x1,y1);
    }
    
    
    public Position getArrive(){
        return new Position(x2,y2);
    }
    
    public int deplacementHorizontal(){
        return x2-x1;
    }

    
    /**
     * Mouvement codé suivant la notation algébrique
     * @param s 
     */
    public Deplacement(String s){
       x1 = s.charAt(0)-'a';
       y1 = s.charAt(1)-'1';
       x2 = s.charAt(3)-'a';
       y2 = s.charAt(4)-'1';
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }
    
    public int minX(){
        return (x1 < x2) ? x1 : x2; 
    }

    public int maxX(){
        return (x1 > x2) ? x1 : x2; 
    }
 
    public int minY(){
        return (y1 < y2) ? y1 : y2; 
    }

    public int maxY(){
        return (y1 > y2) ? y1 : y2; 
    }
    
    public String toString(){
        return (new Position(x1,y1)).toString()
                +" "
                +(new Position(x2,y2)).toString();
    }
    
}
