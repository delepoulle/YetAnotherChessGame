package echecs;


import java.util.ArrayList;

/**
 *
 * Représente une pièce du jeu d'échec.
 * 
 * @author samuel
 */

public class Piece{
	
	private String nom;
	private char code;
        private String couleur;
        
        private ArrayList<Position> accessible = new ArrayList<>();
	
        /**
         * Définit une pièce en fonction de son non, son code et 
         * sa couleur
         * @param nom
         * @param code
         * @param couleur 
         */
	Piece(String nom, char code){
		this.nom = nom;
		this.code = code;
                couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
	}
        
        /** Définit une pièce en fonction de son code
         * 
         * @param code 
         */
        Piece(char code){
            
            this.code = code;
            
            if (Character.isUpperCase(code)){
                couleur = "blanc";                
            }else{
                couleur = "noir";
            }
            char couleurMaj = Character.toUpperCase(code);
            
            if (couleurMaj == 'K'){
                nom="roi";                
            }
            
            if (couleurMaj == 'Q'){
                nom="reine";                
            }            
            
            if (couleurMaj == 'R'){
                nom="tour";                
            }            
            
            if (couleurMaj == 'N'){
                nom="cavalier";                
            }             
                        
            if (couleurMaj == 'B'){
                nom="fou";                
            } 
            
            if (couleurMaj == 'P'){
                nom="pion";                
            }
            
            
            
            
        }
        
        /**
         * Construit une pièce par recopie
         * @param p une autre pièce
         */
        Piece(Piece p){
            this.nom = new String(p.nom);
            this.code = p.code;
            this.couleur = new String(p.couleur);
        }
	
        /**
         * Retourne le nom
         * @return le nom de la pièce
         */
	public String getNom(){
		return nom+"_"+couleur;
	}
        
        
        /**
         * Retourne la couleur de la pièce
         * @return 'w' si la pièce est blanche 'b' sinon
         */
        public char getColor(){
            return couleur.equals("blanc") ? 'w' : 'b';
        }
	
        /**
         * Retourne le code
         * @return code de la pièce
         */
	public char getCode(){
		return code;
	}
        
        
        /**
         * Retroune vrai si la pièce est blanche faux sinon
         * @return vrai si la pièce est blanche faux sinon
         */
        public boolean estBlanc(){
            return (couleur.equals("blanc"));
        }
        
        /**
         * Retroune vrai si la pièce est noire faux sinon
         * @return vrai si la pièce est noire faux sinon
         */
        public boolean estNoir(){
            return (couleur.equals("noir"));
        }
        
        /**
         * Chaine de caractères qui représenta la pièce
         * @return code de la pièce
         */
        public String toString(){
            return Character.toString(code); 
        }
        
        
        /**
         * ajoute une case accessible à la pièce
         * @param p une position accessible
         */
        public void addCaseAccessible(Position p){
            accessible.add(p);            
        }
        
        
        /**
         * Retourne la liste des positions possibles d'une pièce
         * @return liste des positions accessibles
         */
    public ArrayList<Position> getAccessible() {
        return accessible;
    }
    
    /**
     * Vide la liste des positions accessibles.
     */
    public void videAccessible(){
        accessible = new ArrayList<>();
    }
        
        
}
