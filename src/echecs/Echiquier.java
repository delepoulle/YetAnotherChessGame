package echecs;



/**
 * Représente l'échiquer. C'est un ensemble de cases avec des pièces dessus.
 *  
 * @author samuel
 */

public class Echiquier{
	
    private Case[][] c;
    private int dimX, dimY;

    char trait;

    // false par défaut
    boolean roqueK;
    boolean roquek;
    boolean roqueQ;
    boolean roqueq;
    
    // pour la promotion
    boolean promotion = false;
    private boolean petitRoqueEnCours = false;
    private boolean grandRoqueEnCours = false;
    private boolean priseEnPassantEnCours = false;
    
    private char choixPromotion = 'Q';
    
    // position de la prise en passant éventuelle
    private Position priseEnPassant;

    /**
     * Constructeur par défaut echiquier 8x8
     */
    public Echiquier(){
            this(8,8);		
    }

    private void vider(){
        for (int i = 0; i<dimX; i++){
            for (int j = 0; j<dimY; j++){
                c[i][j] = new Case();
            }
        }
    }
    
    
    /**
     * Construit un échiquier de taille spécifiée
     * @param dimX nombre de colonnes souhaitées
     * @param dimY nombre de lignes souhaitées
     */
    public Echiquier(int dimX, int dimY){
            this.dimX = dimX;
            this.dimY = dimY;

            this.trait = 'w';

            c = new Case[dimX][dimY];
            vider();

    }
    
    
    /**
     * Construit un échiquier par recopie.
     * @param ech un autre échiquier
     */
    public Echiquier(Echiquier ech){
        this.dimX = ech.dimX;
        this.dimY = ech.dimY;
        this.trait = ech.trait;
        
            c = new Case[dimX][dimY];
            for (int i = 0; i<dimX; i++){
                    for (int j = 0; j<dimY; j++){
                        c[i][j] = new Case(ech.getCase(i, j));
                    }
            }               
    }
    
    /**
     * Retourne la pièce qui est dans la case x,y. Null si vide.
     * @param x colonne
     * @param y ligne
     * @return la pièce qui est dans la case x,y. 
     */
    public Piece getPieceCase(int x, int y){        
        return c[x][y].getPiece();        
    }

   /**
    * Retourne la pièce qui est dans la case p. Null si vide.
    * @param p case
    * @return la pièce qui est dans la case p.
    */
    public Piece getPieceCase(Position p){
        return c[p.getX()][p.getY()].getPiece();        
    }
    
    
    /**
     * Indique si la case est vide ou non.
     * @param p case
     * @return un booléen vrai si la case est vide (ne contient pas de pièce)
     */
    public boolean estVideCase(Position p){
        return getPieceCase(p) == null;
    }

    /**
     * nombre de colonnes de l'échiquier
     * @return nombre de colonnes.
     */
    public int getDimX() {
        return dimX;
    }


    /**
     * nombre de ligne de l'échiquier.
     * @return nombre de ligne.
     */
    public int getDimY() {
        return dimY;
    }

    /**
     * Retourne la case en fonction de ses coordonnées
     * @param i colonne
     * @param j ligne
     * @return la case
     */
    public Case getCase(int i, int j) {
        return c[i][j];
    }
    
    

    
    /**
     * Positionne une pièce à une place
     * @param p La piece
     * @param x colonne
     * @param y ligne
     */
    void place(Piece p, int x, int y){
        c[x][y].setPiece(p);
    }

    /**
     * Permet de retourner la pièce en fonction de la position
     * @param x colonne
     * @param y ligne
     * @return la pièce (null si la case est vide)
     */
    public Piece getPiece(int x, int y){
        return c[x][y].getPiece();
    }
    
    /**
     * Indique la pièce qui dans la case de position p
     * @param p position de la case
     * @return la pièce contenue (null si vide).
     */
    public Piece getPiece(Position p){
        return getPiece(p.getX(),p.getY());
    }

    
    /**
     * Lecture d'un code FEN
     * @see <a href="https://fr.wikipedia.org/wiki/Notation_Forsyth-Edwards">
     *      https://fr.wikipedia.org/wiki/Notation_Forsyth-Edwards</a>
     * @param FENcode code à lire
     */
    
    public void setFEN(String FENcode){
        
        //vider l'échiquier
        vider();

        try{
            String[] code = FENcode.split(" ");

            String[] ligne = code[0].split("/");

            // Traitement de la première partie du code

            for (int i = 0; i<ligne.length; i++){

                int decal = 0;
                for (int j = 0; j<ligne[i].length(); j++){

                    char l = ligne[i].charAt(j);

                    if (Character.isDigit(l)){
                        decal += (Character.getNumericValue(l)-1);
                    }else{     
                        int pos = j+(decal); 
                        
                        c[pos][dimY-i-1].setPiece(new Piece(l));
                        
                        /*
                        switch (l){
                            case 'k' : c[pos][dimY-i-1].setPiece(new Piece("roi",'k')); break;
                            case 'K' : c[pos][dimY-i-1].setPiece(new Piece("roi",'K')); break;
                            case 'q' : c[pos][dimY-i-1].setPiece(new Piece("dame",'q')); break;
                            case 'Q' : c[pos][dimY-i-1].setPiece(new Piece("dame",'Q')); break;   
                            case 'r' : c[pos][dimY-i-1].setPiece(new Piece("tour",'r')); break;
                            case 'R' : c[pos][dimY-i-1].setPiece(new Piece("tour",'R')); break;
                            case 'b' : c[pos][dimY-i-1].setPiece(new Piece("fou",'b')); break;
                            case 'B' : c[pos][dimY-i-1].setPiece(new Piece("fou",'B')); break;  
                            case 'n' : c[pos][dimY-i-1].setPiece(new Piece("cavalier",'n')); break;
                            case 'N' : c[pos][dimY-i-1].setPiece(new Piece("cavalier",'N')); break;                                          
                            case 'p' : c[pos][dimY-i-1].setPiece(new Piece("pion",'p')); break;
                            case 'P' : c[pos][dimY-i-1].setPiece(new Piece("pion",'P')); break;  

                        }*/
                    }                                                                                               
                }
            }	

            // seconde partie

            try{
                if (code[1].equals("w")){
                    trait = 'w';                
                } else{
                    if (code[1].equals("b")){
                        trait = 'b';
                    }
                    else{
                        throw (new MalformedFENException("le second champs doit etre b ou w et non "+code[1]));
                    }
                }
            }catch(ArrayIndexOutOfBoundsException e){
                //sans indication, le trait est au blancs
                trait = 'w';
            }

            // troisième partie
            try{
                if (code[2].contains("k")) {roquek = true;}
                if (code[2].contains("K")) {roqueK = true;}
                if (code[2].contains("q")) {roqueq = true;}
                if (code[2].contains("Q")) {roqueQ = true;}
            }catch(ArrayIndexOutOfBoundsException e){
                // sans indication, les roques sont possibles
                roquek = roqueK = roqueq = roqueQ = true;
            }
        }catch(ArrayIndexOutOfBoundsException e){
            throw (new MalformedFENException("le code "+FENcode+" n'est pas un code FEN valide"));
        }
        
       
        construitPositionsAccessibles ();
        
    }

    /**
     * Ecriture du code FEN qui représente la position de l'échiquier
     * @see <a href="https://fr.wikipedia.org/wiki/Notation_Forsyth-Edwards">
     *      https://fr.wikipedia.org/wiki/Notation_Forsyth-Edwards</a>
     * 
     * @return chaine fen qui représente l'échiquier
     */
    
    public String getFEN(){
        String res = "";

        for (int j = (dimY-1); j>=0; j--){                                        
            int compteVide = 0;
            for (int i = 0; i<dimX; i++){
                Piece p = c[i][j].getPiece();

                if (c[i][j].getPiece() != null){

                        if (compteVide != 0) {
                            res += compteVide;
                        }
                        res += p.getCode();
                        compteVide = 0;
                }
                else{
                    compteVide++;
                }
            }
            if (compteVide != 0){
                res += compteVide;
            }
            if (j!=0){
                res +="/";
            }
        }

        res+= (trait=='w' ? " w" : " b");
        res += " ";

        if (roquek || roqueK || roqueq || roqueQ ){
            if (roqueK) res+= "K";
            if (roqueQ) res+= "Q";
            if (roquek) res+= "k";
            if (roqueq) res+= "q";
        }
        else{
            res+="-";
        }
        
        res += " ";
        
        res += priseEnPassant == null ? "-" : priseEnPassant;        

        return res;
    }

    /**
     * Sortie texte sous forme simplifiée 
     * @return chaine de caractère qui représente l'échiquier
     */
    @Override
    public String toString(){
        String res = "";
        for (int j = dimY-1; j>=0; j--){
            for (int i = 0; i<dimX; i++){                        
                    res += (c[i][j]);                       
            }
            res += "\n";
        }
        return res;
    }
   
    


    /**
     * Méthode qui indique si un déplacement est valide ou non. La méthode indique
     * simplement si le déplacement respecte les règles. L'échiquier est
     * laissé dans l'état. Le déplacement n'est pas effectué.
     * 
     * @param d Deplacement
     * @return true si valide sur l'echiquier false sinon
     */

    public boolean estValideDeplacement(Deplacement d){   

        int x1 = d.getX1();
        int y1 = d.getY1();
        int x2 = d.getX2();
        int y2 = d.getY2();

        // si la case de départ = la case d'arrivée, ce n'est pas un déplacement
        if (x1==x2 && y1==y2)
            return false;

        // si la case de départ est vide, ce n'est pas un déplacement 
        if ( c[x1][y1].getPiece() == null )
            return false;
        
        if ( c[x1][y1].estVide() )
            return false;
        
        // si ce n'est pas le tour du joueur        
        if ( ( c[x1][y1].getPiece().estBlanc() && trait == 'b' ) 
            || ( c[x1][y1].getPiece().estNoir() && trait == 'w' ) ){
            return false;
        }
        
        
        
        //System.out.println("# Positions accessibles pour "+c[x1][y1].getPiece().getNom());
        //System.out.println(c[x1][y1].getPiece().accessible);
        
        // partie verification pour la piece
        if ( c[x1][y1].getPiece().getAccessible().contains(new Position(x2,y2)) ){
            
            // vérification de la validité du roque
            if (Character.toUpperCase(c[x1][y1].getPiece().getCode()) == 'K'){
                
                int depl = d.deplacementHorizontal();
                
                
                if (Math.abs(depl) == 2){
                    
                    Position caseDePassage = new Position(d.getX1()+(depl/2),d.getY1());                    
                    Deplacement passage = new Deplacement(d.getDepart(), caseDePassage);
                    
                    //System.out.println("Case de passage = "+caseDePassage);
                    
                    if ( !verifiePasEchecsApres(passage) ) return false;
                    
                    if ( !estVideCase(caseDePassage)) return false;
                    
                    if (depl==2 && c[x1][y1].getPiece().estBlanc() && !roqueK){
                        return false;
                    }
                    
                    if (depl==2 && c[x1][y1].getPiece().estNoir() && !roquek){
                        return false;
                    }
                    
                    if (depl==-2 && c[x1][y1].getPiece().estBlanc() && !roqueQ){
                        return false;
                    }
                    
                    if (depl==-2 && c[x1][y1].getPiece().estNoir() && !roqueq){
                        return false;
                    }
                   
                }
                

            }
                
            return verifiePasEchecsApres(d);
            
        }

        return false;
    }

    
    /**
     * Execute le déplacement (après avoir vérifié sa validité).
     * 
     * Après contrôle de validité du déplacement, il est effectué. L'état
     * de l'échiquier est donc changé.
     * 
     * 
     * @param d déplacement à executer
     */
    public void executeDeplacement(Deplacement d){ 
                
        if (estValideDeplacement(d)){
            
            int x1 = d.getX1();
            int y1 = d.getY1();
            int x2 = d.getX2();
            int y2 = d.getY2();
            
            Piece piece = c[x1][y1].getPiece();
            char codePiece = piece.getCode();
            d.setPiece(codePiece);
            
            promotion = false;
            // cas du pion noir sur première rangée
            if (codePiece =='p' && (y2==0)){
                promotion = true;
                piece=new Piece("dame",Character.toLowerCase(choixPromotion));
                d.setPromotion('Q');
            }
            // cas du pion blanc sur dernière rangée
            if (codePiece=='P' && (y2==7)){
                promotion = true;
                piece=new Piece("dame",'Q');
                d.setPromotion('Q');
            }
            
            // autoriser la prise en passant si et seulement si avance de 2 pour un pion
            priseEnPassant = null;
                        
            if ((codePiece=='P') && y1==1 && y2==3){
                priseEnPassant = new Position(x1,y1+1);
            }
            if ((codePiece=='p') && y1==6 && y2==4){
                priseEnPassant = new Position(x1,y1-1);
            }
            
            // cas de la prise en passant 
            if (Character.toUpperCase(codePiece) =='P' && y1!=y2 && c[x2][y2].estVide() ){
                
                priseEnPassantEnCours = true;
                
                c[x2][y1].vider();
            }
            
            // détection du roque
            

            if ( Character.toUpperCase(codePiece) == 'K'  ){
                
               if (d.deplacementHorizontal() == 2){ 
                       
                    petitRoqueEnCours = true;

                    // petit roque blanc
                    if (codePiece=='K'){                    
                        c[7][0].vider();
                        c[5][0].setPiece(new Piece("tour",'R'));                        
                    }

                    // petit roque noir
                    if (codePiece=='k'){                    
                        c[7][7].vider();
                        c[5][7].setPiece(new Piece("tour",'r'));                        
                    }
                }

                                   
                else{
                    petitRoqueEnCours = false;
                }
                
                if (d.deplacementHorizontal() == -2){
                    
 
                    grandRoqueEnCours = true;

                    // grand roque blanc
                    if (codePiece=='K'){ 
                        
                        c[0][0].vider();
                        c[3][0].setPiece(new Piece("tour",'R'));
                        
                    }

                    // grand roque noir
                    if (codePiece=='k'){                    
                        c[0][7].vider();
                        c[3][7].setPiece(new Piece("tour",'r'));
                        
                    }                    
 
                }
                else{
                    grandRoqueEnCours = false;
                }                                                 
            }
            
            // Pour interdir les futurs roques...
            if ((codePiece=='R') && (x1==0)){
                roqueQ = false;
            }
            
            if ((codePiece=='r') && (x1==0)){
                roqueq = false;
            }
            
            if ((codePiece=='R') && (x1==7)){
                roqueK = false;
            }
            
            if ((codePiece=='r') && (x1==7)){
                roquek = false;
            }
            
            if (codePiece=='K'){
                roqueQ = false;
                roqueK = false;
            }
            
            if (codePiece=='k'){
                roqueq = false;
                roquek = false;
            }
            
           //si la case de destination n'est pas vide, il s'agit d'une prise            
            if (!c[x2][y2].estVide()){                
                d.setPrise(true);
            }
            
            c[x2][y2].setPiece(piece);
            c[x1][y1].vider();              
           
        }               
        
        // on change le trait
        trait = (trait=='w' ? 'b' : 'w');
        
        construitPositionsAccessibles();
        
        System.out.println(this.getFEN());
    }
    
    /** Effectue un déplacement sans aucun contrôle de validité.
     * Ceci permet de faire des vérifications (pour le roque, par exemple) 
     *  
     * 
     * @param d Déplacement qui doit être effectué
     */
    private void forceDeplacement(Deplacement d){
        int x1 = d.getX1();
        int y1 = d.getY1();
        int x2 = d.getX2();
        int y2 = d.getY2();
        
        Piece piece = c[x1][y1].getPiece();
        c[x2][y2].setPiece(piece);
        c[x1][y1].vider();
    }
    
    
    /**
     * Retourne un échiquier sur lequel le déplacement a été réalisé.
     * Ceci permet de ne pas modifier l'état de l'échiquier. 
     * Les contrôles de validité du déplacement sont effectués.
     * 
     * @param d le déplacement
     * @return une instance d'échiquier sur lequel le déplacement a été réalisé.
     */
    public Echiquier simuleDeplacement(Deplacement d){
        Echiquier ech = new Echiquier(this);
        ech.executeDeplacement(d);
        
        return ech;
    }
        

    /**
     * Execute le déplacement (après avoir vérifié sa validité)
     * @param x1 x de départ
     * @param y1 y de départ
     * @param x2 x d'arrivée
     * @param y2 y d'arrivée
     */
    public void executeDeplacement(int x1, int y1, int x2, int y2){
        executeDeplacement(new Deplacement(x1, y1, x2, y2));
    }
    
    
    /**
     * Execute le déplacement (après avoir vérifié sa validité).
     * La chaine de caractère représente le dépoe
     * 
     * @param s déplacement donné en notation algébrique.
     */
    public void executeDeplacement(String s){
        executeDeplacement(new Deplacement(s));
    }
    
    
    /**
     * Ajoute à la case toutes les cases qui lui sont accessibles en ligne
     * droite dans une direction donnée. Pour continuer, il faut que la case soit
     * vide. On s'arrête dès qu'on a rencontré une pièce ou un bord. Si la case est 
     * occupée par une pièce de couleur opposée, la case est accessible (mais on ne
     * poursuit pas la recherche).
     * 
     * @param i colonne de départ
     * @param j ligne de départ
     * @param di déplacement horizontal (nombre signé)
     * @param dj déplacement vertical (nombre signé)
     */
    public void accessibleEnLigneDroite(int i, int j,int di, int dj){
        
        int posi = i+di;
        int posj = j+dj;
        
        boolean continuer = true;
        
        while(continuer){
           
        
            if (posi<0 || posi>=dimX || posj<0 || posj>=dimY){
                continuer = false;
            }else{        
                // si la case de destination est vide
                if (c[posi][posj].estVide()) {
                    c[i][j].getPiece().addCaseAccessible(new Position(posi,posj));    
                    posi += di;
                    posj += dj;
                }else{
                    // si elle est occupée par une case de couleur différente
                    if ((c[i][j].getPiece().estBlanc() && c[posi][posj].getPiece().estNoir()) 
                        || (c[i][j].getPiece().estNoir() && c[posi][posj].getPiece().estBlanc())){
                            c[i][j].getPiece().addCaseAccessible(new Position(posi,posj));                             
                    }
                    continuer = false;
                }
            }        
        }        
    }
   
    
     /**
     * Indique si la case existe dans l'échiquier (elle n'est pas en dehors
     * des limites).
     * 
     * @param i colonne de la case de départ
     * @param j ligne de la case de départ
     * @return vrai si la case existe faux sinon
     */
    public boolean existeCase(int i, int j){
       return  (i>=0 && i<dimX && j>=0 && j<dimY);
    }
    
    
    /**
     * Indique si la case existe dans l'échiquier (elle n'est pas en dehors
     * des limites) et si elle est inoccupée.
     * 
     * @param i colonne de la case de départ
     * @param j ligne de la case de départ
     * @return vrai si la case existe et qu'elle ne contient pas de pièce faux sinon
     */
    public boolean existeEtVide(int i, int j){
        return existeCase(i,j) 
            && (c[i][j].estVide());
    }
   
    
     /**
     * Indique si la case existe dans l'échiquier (elle n'est pas en dehors
     * des limites) et si elle est libre (c'est à dire vide ou occupée par une
     * pièce adverse).
     * 
     * @param i colonne de la case de départ
     * @param j ligne de la case de départ
     * @param blanc booléen qui idique la couleur (vrai pour blanc, faux pour noir)
     * @return vrai si la case existe et qu'elle ne contient pas de pièce de la couleur faux sinon
     */
    public boolean existeEtLibre(int i, int j, boolean blanc){
        // il faut que la case existe, soit libre ou occupée par une pièce 
        // de couleur différente
        return existeCase(i,j) 
            && (c[i][j].estVide() || (c[i][j].getPiece().estBlanc() != blanc));
    }
    
     
    /**
     * Ajoute à la liste des positions accessibles, les cases de déplacement 
     * valides pour une tour.
     * 
     * @param i colonne de la case de départ
     * @param j ligne de la case de départ
     */
    public void accessibleTour(int i, int j){
        accessibleEnLigneDroite(i,j,0,1);
        accessibleEnLigneDroite(i,j,0,-1);
        accessibleEnLigneDroite(i,j,1,0);
        accessibleEnLigneDroite(i,j,-1,0);
    }

     /**
     * Ajoute à la liste des positions accessibles, les cases de déplacement 
     * valides pour un fou.
     * 
     * @param i colonne de la case de départ
     * @param j ligne de la case de départ
     */   
    public void accessibleFou(int i, int j){
        accessibleEnLigneDroite(i,j,1,1);
        accessibleEnLigneDroite(i,j,-1,-1);
        accessibleEnLigneDroite(i,j,1,-1);
        accessibleEnLigneDroite(i,j,-1,1);
    }
    
    
     /**
     * Ajoute à la liste des positions accessibles, les cases de déplacement 
     * valides pour un cavalier.
     * 
     * @param i colonne de la case de départ
     * @param j ligne de la case de départ
     */   
    public void accessibleCavalier(int i, int j){
        boolean blanc = c[i][j].getPiece().estBlanc();
        
        // Les 8 positions du cavalier
        
        if (existeEtLibre(i+1,j+2,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i+1,j+2));
        }

        if (existeEtLibre(i+2,j+1,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i+2,j+1));
        }
        
         if (existeEtLibre(i-1,j-2,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i-1,j-2));
        }

        if (existeEtLibre(i-2,j-1,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i-2,j-1));
        }      
        
        if (existeEtLibre(i+1,j-2,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i+1,j-2));
        }

        if (existeEtLibre(i+2,j-1,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i+2,j-1));
        }
        
         if (existeEtLibre(i-1,j+2,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i-1,j+2));
        }

        if (existeEtLibre(i-2,j+1,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i-2,j+1));
        }      
        
    }
    
     /**
     * Ajoute à la liste des positions accessibles, les cases de déplacement 
     * valides pour un roi.
     * 
     * @param i colonne de la case de départ
     * @param j ligne de la case de départ
     */   
    public void accessibleRoi(int i, int j){
        boolean blanc = c[i][j].getPiece().estBlanc();
        
        // Les 8 positions du roi
        
        if (existeEtLibre(i,j+1,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i,j+1));
        }

        if (existeEtLibre(i,j-1,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i,j-1));
        }
        
         if (existeEtLibre(i+1,j,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i+1,j));
        }

        if (existeEtLibre(i-1,j,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i-1,j));
        }      
        
        if (existeEtLibre(i+1,j+1,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i+1,j+1));
        }

        if (existeEtLibre(i+1,j-1,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i+1,j-1));
        }
        
        if (existeEtLibre(i-1,j-1,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i-1,j-1));
        }

        if (existeEtLibre(i-1,j+1,blanc)) {
            c[i][j].getPiece().addCaseAccessible(new Position(i-1,j+1));
        }              
        
        //  roques
        if ( (blanc && (i== 4) && (j==0))
            || (!blanc && (i== 4 ) && (j==7)) ){
            //System.out.println("roi se déplace depuis sa position de départ");
            c[i][j].getPiece().addCaseAccessible(new Position(i+2,j));
            c[i][j].getPiece().addCaseAccessible(new Position(i-2,j));
            
        }
    }
    
     /**
     * Ajoute à la liste des positions accessibles, les cases de déplacement 
     * valides pour un pion blanc.
     * 
     * @param i colonne de la case de départ
     * @param j ligne de la case de départ
     */   
    public void accessiblePionBlanc (int i, int j){
        
        
        // déplacement normal
        if (existeEtVide(i,j+1)){
            c[i][j].getPiece().addCaseAccessible(new Position(i,j+1));
        
            // premier déplacement
            if ((j== 1) && existeEtVide(i,j+2)) {
                c[i][j].getPiece().addCaseAccessible(new Position(i,j+2));
            }
        }
        
        // prise à gauche
        try{
            if (c[i-1][j+1].getPiece().estNoir()){
               c[i][j].getPiece().addCaseAccessible(new Position(i-1,j+1)); 
            }
        }catch(ArrayIndexOutOfBoundsException | NullPointerException e){
            // rien à faire puisque la pièce ou la case n'existe pas...
        }
        
        // prise à droite
        try{
            if (c[i+1][j+1].getPiece().estNoir()){
               c[i][j].getPiece().addCaseAccessible(new Position(i+1,j+1)); 
            }
        }catch(ArrayIndexOutOfBoundsException | NullPointerException e){
            // rien à faire puisque la pièce ou la case n'existe pas...
        }
        
        // prise en passant à gauche
        
        try{
            if ((j==4) && c[i-1][4].getPiece().getCode() == 'p'){
                Position pep = new Position(i-1,5);
                if (pep.equals(priseEnPassant)){
                    c[i][j].getPiece().addCaseAccessible(pep);
                }
            }
        }catch(ArrayIndexOutOfBoundsException | NullPointerException e){
            // rien à faire puisque la pièce ou la case n'existe pas...
        }
        
        // prise en passant à droite
        
        try{
            if ((j==4) && c[i+1][4].getPiece().getCode() == 'p'){
                Position pep = new Position(i+1,5);
                 if (pep.equals(priseEnPassant)){
                     c[i][j].getPiece().addCaseAccessible(pep);    
                 }
            }
        }catch(ArrayIndexOutOfBoundsException | NullPointerException e){
            // rien à faire puisque la pièce ou la case n'existe pas...
        }
        
    }
    
    
     /**
     * Ajoute à la liste des positions accessibles, les cases de déplacement 
     * valides pour un pion noir.
     * 
     * @param i colonne de la case de départ
     * @param j ligne de la case de départ
     */   
    public void accessiblePionNoir (int i, int j){
               
        if (existeEtVide(i,j-1)){
            c[i][j].getPiece().addCaseAccessible(new Position(i,j-1));
        
            if (j== (dimY-2) && existeEtVide(i,j-2)) {
                c[i][j].getPiece().addCaseAccessible(new Position(i,j-2));
            }
        }
        
         // prise à gauche
        try{
            if (c[i-1][j-1].getPiece().estBlanc()){
               c[i][j].getPiece().addCaseAccessible(new Position(i-1,j-1)); 
            }
        }catch(ArrayIndexOutOfBoundsException | NullPointerException e){
            // rien à faire puisque la pièce ou la case n'existe pas...
        }
        
        // prise à droite
        try{
            if (c[i+1][j-1].getPiece().estBlanc()){
               c[i][j].getPiece().addCaseAccessible(new Position(i+1,j-1)); 
            }
        }catch(ArrayIndexOutOfBoundsException | NullPointerException e){
            // rien à faire puisque la pièce ou la case n'existe pas...
        }
        
        // prise en passant à gauche
        
        try{
            if ((j==3) && c[i-1][3].getPiece().getCode() == 'P'){
                Position pep = new Position(i-1,2);
                if (pep.equals(priseEnPassant)){
                    c[i][j].getPiece().addCaseAccessible(pep);
                }        
            }
        }catch(ArrayIndexOutOfBoundsException | NullPointerException e){
            // rien à faire puisque la pièce ou la case n'existe pas...
        }
        
        // prise en passant à droite
        
        try{
            if ((j==3) && c[i+1][3].getPiece().getCode() == 'P'){
                 Position pep = new Position(i+1,2);
                 if (pep.equals(priseEnPassant)){
                     c[i][j].getPiece().addCaseAccessible(pep);    
                 }          
            }
        }catch(ArrayIndexOutOfBoundsException | NullPointerException e){
            // rien à faire puisque la pièce ou la case n'existe pas...
        }
            
       
        
    }
    
    /**
     * Constuit pour toutes les pièces la liste de leur 
     * positions accessibles . Elle est construite "naivement" 
     * (c'est à dire sans vérification de l'échec)
     * 
     * 
     */
    public void construitPositionsAccessibles (){
        
        // Premier parcours "naïf"
        for (int j = 0; j<dimY; j++){
            for (int i = 0; i<dimX; i++){                        
                 Piece p = c[i][j].getPiece();
                 if (p!=null){
                    
                    p.videAccessible();
                     
                    if (p.getCode() == 'P') {
                        accessiblePionBlanc(i,j);
                    }
                    if (p.getCode() == 'p') {
                        accessiblePionNoir(i,j);
                    }
                    if (Character.toLowerCase(p.getCode()) == 'r' ){
                        accessibleTour(i,j);
                    }
                    
                    if (Character.toLowerCase(p.getCode()) == 'b' ){
                        accessibleFou(i,j);
                    }
                    
                    if (Character.toLowerCase(p.getCode()) == 'q' ){
                        accessibleTour(i,j);
                        accessibleFou(i,j);                        
                    }
                    
                    if (Character.toLowerCase(p.getCode()) == 'n' ){
                        accessibleCavalier(i,j);
                    }
                    
                    if (Character.toLowerCase(p.getCode()) == 'k' ){
                        accessibleRoi(i,j);
                    }
                    
                }
            }            
        }
    }
        
    
    
    /** Permet d'afficher toutes les pièces avec leur
     * position accessibles.
     */
    public void affichePositionAccessibles (){
        for (int j = 0; j<dimY; j++){
            for (int i = 0; i<dimX; i++){                        
                 Piece p = c[i][j].getPiece();
                
                 if (p!=null){
                    System.out.print("Sur la case "+new Position(i,j));                    
                    System.out.print(" il y a "+p+" qui peut aller en ");
                   
                   /* java 1.8 : lambda
                    p.getAccessible().stream().forEach((pos) -> {
                        System.out.print(pos+" ");
                     });
                   */
                   /* java 1.7 */
                    for (Position pos : p.getAccessible()){
						System.out.print(pos+" ");
					}
					System.out.println();
                 }
                 
            }            
        }        
    }
    
    
   
    
    /**
     * Vérifie si le joueur est en échec ou non. On suppose que la liste des positions
     * possibles pour les pièces sont déjà calculées
     * @param couleur couleur du joueur
     * @return vrai si le joueur est en échec, faux sinon.
     */
    public boolean estEnEchec(char couleur){
        Position pos = rechercheRoi(couleur);
        //System.out.println("# le roi est en position "+pos);
        
        
        
        // Pour toutes les pièces
        for (int j = 0; j<dimY; j++){
            for (int i = 0; i<dimX; i++){         
                 Piece p = c[i][j].getPiece();
                 if (p!=null){
                     if (p.getColor() != couleur){
                         //System.out.print(p+" en case "+new Position(i,j));
                         //System.out.println(" peut aller en "+p.getAccessible());
                         if (p.getAccessible().contains(pos)) return true;
                    }
                 }
            }     
        }
        
        return false;
    }
    
    /**
     * Recherche la position du roi d'une couleur (on suppose qu'il y a
     * un et un seul roi).
     * 
     * @param couleur couleur du roi
     * @return Position du roi de la couleur
     */
    public Position rechercheRoi(char couleur){
        
        // recherche de la position du roi
        for (int j = 0; j<dimY; j++){
            for (int i = 0; i<dimX; i++){                        
                 Piece p = c[i][j].getPiece();
                 if (p!=null){
                     if ((p.getCode() == 'k' && couleur == 'b' ) ||
                         (p.getCode() == 'K' && couleur == 'w' ) ){
                        return new Position(i,j);
                    }
                 }
            }     
        }
        return null;
    }

    /**
     * Indique si le dernier mouvement est une promotion
     * @return vrai si le dernier mouvement est une promotion
     */
    public boolean isPromotion() {
        return promotion;
    }

    /** Indique si le dernier mouvement est un petit roque
     * 
     * @return vrai si le dernier mouvement était un petit roque
     */
    public boolean petitRoqueEnCours() {        
        return petitRoqueEnCours;
    }
    
    
    /** Indique si le dernier mouvement est un grand roque
     * 
     * @return vrai si le dernier mouvement était un grand roque
     */
    public boolean grandRoqueEnCours() {        
        return grandRoqueEnCours;
    }
    
    
    /**
     * Indique si le dernier mouvement est une prise en passant
     * @return vrai si le dermier mouvement était une prise en passant
     */
    public boolean priseEnPassantEnCours(){
        return priseEnPassantEnCours;
    }
    
    /**
     * Permet de vérifier qu'un déplacement ne met pas en échec.
     * 
     * @param d Déplacement considéré
     * @return vrai si le déplacement ne met pas en échec, faux sinon.
     */
    public boolean verifiePasEchecsApres(Deplacement d){
            
        Echiquier n = new Echiquier(this);            
        n.forceDeplacement(d);                                   
        n.construitPositionsAccessibles();

        if (n.estEnEchec(trait)) return false;

        return true;
    }
}
