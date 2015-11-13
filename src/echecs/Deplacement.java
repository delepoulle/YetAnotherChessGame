package echecs;



/**
 * Représentation d'un déplacement.
 * 
 * @author samuel
 */
public class Deplacement {
    
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;
    
    private boolean prise;
    
    /**
     * Indique que le mouvement consititue une prise.
     * @param prise vrai si c'est une prise, faux sinon.
     */
    public void setPrise(boolean prise) {
        this.prise = prise;
    }

    
    
/**
 * Construit un déplacement avec l'ensemble des ses paramètres.
 * @param x1 coordonnée x de la case de départ
 * @param y1 coordonnée y de la case de départ
 * @param x2 coordonnée x de la case d'arrivée
 * @param y2 coordonnée y de la case d'arrivée
 */
    public Deplacement(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    /**
     * Construit un déplacement connaissant la case de déart et d'arrivée
     * @param p1 position de la case de départ
     * @param p2 position de la case d'arrivée
     */
    public Deplacement(Position p1, Position p2){
        x1 = p1.getX();
        y1 = p1.getY();
        x2 = p2.getX();
        y2 = p2.getY();        
    }
    
    /** 
     * Constructeur par recopie
     * @param that un autre paramètre
     */
    public Deplacement(Deplacement that){
        x1 = that.x1;
        y1 = that.y1;
        x2 = that.x2;
        y2 = that.y2; 
    }
    
    /**
     * retourne la position de la case de départ.
     * @return la position du départ
     */
    public Position getDepart(){
        return new Position(x1,y1);
    }
    
    /**
     * retourne la position de la case d'arrivée.
     * @return la position d'arrivée
     */
    public Position getArrive(){
        return new Position(x2,y2);
    }
    
    
    /**
     * retourne le nombre entier signé qui correspond au déplacement horizontal (utile pour le roque)
     * Si le nombre est positif, c'est un déplacement vers la droite, si le nombre est
     * négatif, c'est un déplacement vers la gauche.
     * @return entier relatif qui indique le nombre de case du déplacement.
     */
    public int deplacementHorizontal(){
        return x2-x1;
    }

    
    /**
     * Mouvement codé suivant la notation algébrique (non abrégée)
     * @see <a href="https://fr.wikipedia.org/wiki/Notation_alg%C3%A9brique">
     * https://fr.wikipedia.org/wiki/Notation_alg%C3%A9brique</a>
     * @param s chaine qui représente le déplacement
     */
    public Deplacement(String s){
       x1 = s.charAt(0)-'a';
       y1 = s.charAt(1)-'1';
       x2 = s.charAt(3)-'a';
       y2 = s.charAt(4)-'1';
    }

    /** Coordonnée x de la position de départ.
     * 
     * @return Coordonnée x de la position de départ.
     */
    public int getX1() {
        return x1;
    }
    /** Coordonnée y de la position de départ.
     * 
     * @return Coordonnée y de la position de départ.
     */
    public int getY1() {
        return y1;
    }

    /** Coordonnée x de la position de arrivée.
     * 
     * @return Coordonnée x de la position d'arrivée.
     */
    public int getX2() {
        return x2;
    }

     /** Coordonnée y de la position de arrivée.
     * 
     * @return Coordonnée y de la position d'arrivée.
     */   
    public int getY2() {
        return y2;
    }
    
    
    /**
     * Retourne la plus petite valeur entre la coordonnée x de
     * la position de départ et la coordonnée x de la position
     * d'arrivée.
     * @return le min de x départ et x arrivée.
     */
    public int minX(){
        return (x1 < x2) ? x1 : x2; 
    }

    /**
     * Retourne la plus grande valeur entre la coordonnée x de
     * la position de départ et la coordonnée x de la position
     * d'arrivée.
     * @return le max de x départ et x arrivée.
     */
    public int maxX(){
        return (x1 > x2) ? x1 : x2; 
    }
 
    /**
     * Retourne la plus petite valeur entre la coordonnée y de
     * la position de départ et la coordonnée y de la position
     * d'arrivée.
     * @return le min de y départ et y arrivée.
     */
    public int minY(){
        return (y1 < y2) ? y1 : y2; 
    }

    /**
     * Retourne la plus grande valeur entre la coordonnée y de
     * la position de départ et la coordonnée y de la position
     * d'arrivée.
     * @return le max de y départ et y arrivée.
     */
    public int maxY(){
        return (y1 > y2) ? y1 : y2; 
    }
    
    /** 
     * Chaine qui représente le coup en notation 
     * algébrique complète. 
     * @return chaine qui représente le coup
     */
    @Override
    public String toString(){
        
        StringBuilder sb = new StringBuilder();
        
        sb.append((new Position(x1,y1)).toString());
        if (prise){
            sb.append("x");
        }
        else{            
            sb.append("-");
        }
        sb.append((new Position(x2,y2)).toString());
        
        return sb.toString();
    }
    
}
