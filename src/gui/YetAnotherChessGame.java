
package gui;



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.net.URLClassLoader;

import echecs.Deplacement;
import echecs.Echiquier;
import echecs.Piece;
import echecs.Position;


/**
 *  Classe principale pour l'interface graphique
 * 
 * @author samuel
 */
public class YetAnotherChessGame extends JFrame implements MouseListener, MouseMotionListener {
    
  /** Paneau de fond */ 
  JLayeredPane layeredPane;
  
  /** Echiquier */
  JPanel chessBoard;
  
  /** Une pièce */
  JLabel chessPiece;
  
  /** Déplacement en x */
  int xDeplace;
  /** Déplacement en y */
  int yDeplace;
  
  /** position de départ */
  Position depart;
  /** position d'arrivée */
  Position arrive;
  
  /** L'échiquier courrant */
  static Echiquier ech;
  
  
  
  /** Efface tout l'échiquier */
  private void videEchiquier(){
      
        for (int j = 0 ; j < ech.getDimY(); j++) {
            for (int i = 0; i < ech.getDimX(); i++) {                                  
                    JPanel panel = (JPanel) chessBoard.getComponent(j * ech.getDimX() + i);
                    panel.removeAll();
                    panel.repaint();
            }
        }
    }
  
  
    /** Dessine intégralement toutes les pièces de l'échiquier */
    private void dessineToutesLesPieces(){
               // dessin des pièces
        for (int j = 0 ; j < ech.getDimY(); j++) {
            for (int i = 0; i < ech.getDimX(); i++) {
                Piece p = ech.getPieceCase(i, 8-j-1);
                
                if (p != null) {
                                        
                    String nom = p.getNom();
                    String nomComplet = "icons/" + nom + ".png";
                    //java.net.URL imgURL = getClass().getResource(nomComplet);
                    ClassLoader cl = this.getClass().getClassLoader();
                    JLabel piece = new JLabel(new ImageIcon(cl.getResource(nomComplet)));
                    JPanel panel = (JPanel) chessBoard.getComponent(j * ech.getDimX() + i);
                    panel.add(piece);
                }
            }
        } 
    }
    
    
    /** Vide et dessine */
    private void redessine(){
        videEchiquier();
        dessineToutesLesPieces();
    }
  
  
    /**
     * Constructeur
     */
    public YetAnotherChessGame() {
        Dimension boardSize = new Dimension(600, 600);

        //  Use a Layered Pane for this this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add a chess board to the Layered Pane 
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout(new GridLayout(8, 8));
        chessBoard.setPreferredSize(boardSize);
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

         // dessin de l'équiquier
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            chessBoard.add(square);

            int row = (i / 8) % 2;
            if (row == 0) {
                square.setBackground(i % 2 == 0 ? Color.white : Color.getHSBColor(0.56f, 1.0f, 0.8f));
            } else {
                square.setBackground(i % 2 == 0 ? Color.getHSBColor(0.56f, 1.0f, 0.8f) : Color.white);
            }
        }

        dessineToutesLesPieces();

  }
 
    
    /** 
     * Méthode appelée lorsque la souris est cliquée
     * @param e Evenement souris
     */
  @Override
    public void mousePressed(MouseEvent e) {
        chessPiece = null;
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());
        
        // si la case est vide : rien à faire
        if (c instanceof JPanel) {            
            return;
        }
        
        // retrouver la case correspondante
        depart = new Position((int) ((e.getX()/600.0)*8.0) , (int)((((600.0-e.getY())/600.0)*8.0))) ;

        //System.out.print(depart);

        Point parentLocation = c.getParent().getLocation();
                        
        xDeplace = parentLocation.x - e.getX();
        yDeplace = parentLocation.y - e.getY();
        chessPiece = (JLabel) c;
        chessPiece.setLocation(e.getX() + xDeplace, e.getY() + yDeplace);
        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
    }
 
 /**
  * Méthode appelée lorsque la souris est déplacée (pour que la pièce suive le
  * mouvement de la souris).
  * 
  * @param me Evenement souris
  */
  @Override
    public void mouseDragged(MouseEvent me) {
        if (chessPiece == null) {
            return;
        }
        chessPiece.setLocation(me.getX() + xDeplace, me.getY() + yDeplace);
    }
 
  /** 
   * Méthode qui permet de reposer une pièce sur l'échiquier.
   * 
   * @param e evenement souris
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    if(chessPiece != null) {

        chessPiece.setVisible(false);
        
        arrive = new Position((int) ((e.getX()/600.0)*8.0) , (int)((((600.0-e.getY())/600.0)*8.0))) ;   
        Deplacement d = new Deplacement(depart,arrive);
        
        //System.out.println("==> Déplacement : "+d);
        
        
        if (ech.estValideDeplacement(d)){
            
            ech.executeDeplacement(d);
        
            Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
            
            
            
            if (ech.isPromotion()){
            
                String nom = ech.getPiece(d.getArrive()).getNom();
                Container parent = (Container)c;
                
                chessPiece = new JLabel(new ImageIcon("icons/" + nom + ".png"));                
                parent.add(chessPiece);
                
            }
                        

            // cas d'une prise
            if (c instanceof JLabel){
                Container parent = c.getParent();
                parent.remove(0);
                parent.add( chessPiece );
            }
            else {
                Container parent = (Container)c;
                parent.add( chessPiece );

            }
            
            if (ech.petitRoqueEnCours() || ech.grandRoqueEnCours() 
                    || ech.priseEnPassantEnCours()){                                
                redessine();
            }

            chessPiece.setVisible(true);
        }else{
            // replacer sur la case de départ
            JPanel panel = (JPanel) chessBoard.getComponent((8-depart.getY()-1) * ech.getDimX() + depart.getX());
            panel.add( chessPiece );
            chessPiece.setVisible(true);
        }           
    }
  }
 
  @Override
  public void mouseClicked(MouseEvent e) {
  
  }
  @Override
  public void mouseMoved(MouseEvent e) {
      
  }
  
  @Override
  public void mouseEntered(MouseEvent e){
  
  }  
  
  @Override
  public void mouseExited(MouseEvent e) {
  
  }
 
  public static void main(String[] args) {
      
    ech = new Echiquier();
    ech.setFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    //ech.setFEN("rn1q1rk1/pb1p1pnp/2pP2p1/2p1P1P1/2P5/3Q3B/PP1NK2P/5RR1 b KQkq");
    //ech.setFEN("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R");
    //ech.setFEN("r3k2r/8/8/8/8/8/8/R3K2R");
    //ech.setFEN("r3k2r/8/8/8/8/8/8/R3KR1R");
    
    JFrame frame = new YetAnotherChessGame();
    frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
    frame.pack();
    frame.setResizable(true);
    frame.setLocationRelativeTo( null );
    frame.setVisible(true);
 }
}
