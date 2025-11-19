import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Campo extends JPanel {
    private Cella[][] campo;
    private final int mine;

    private int nBandiere = 0;
    private final int MINA = -1;

    public Campo(int righe, int colonne, int maxMine, Container content) {
        setPreferredSize(new Dimension(450, 300));
        campo = new Cella[righe][colonne];
        this.mine = maxMine;

        // gestire input delle celle
        for (int r = 0; r < campo.length; r++) {
            for (int c = 0; c < campo[0].length; c++) {
                // istanziare cella + posizione a schermo
                campo[r][c] = new Cella(30, r, c);
                content.add(campo[r][c]);
                campo[r][c].setLocation(campo[r][c].getWidth() * c, campo[r][c].getHeight() * r);

                campo[r][c].addMouseListener(new MouseAdapter() {

                    // usiamo una classe anonima perché dobbiamo implementare diversi metodi astratti
                    // non si può usare la lambda function
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        
                        Cella cliccata = (Cella)e.getSource();

                        // button 1: pulsante sx
                        // button 2: tasto centrale / rotellina
                        // button 3: pulsante dx
                        switch (e.getButton()) {
                            case MouseEvent.BUTTON1:
                                // scopro la cella

                                break;
                            case MouseEvent.BUTTON3:
                                if (cliccata.isScoperta())
                                break;

                                if (!cliccata.isScoperta() && nBandiere == mine) {
                                    JOptionPane.showMessageDialog(null, "Hai usato tutte le bandiere");
                                    break;
                                }

                                cliccata.toggleBandiera();
                                // incremento
                                nBandiere += cliccata.isBandiera() ? +1 : -1;
                                break;
                        
                        }
                        
                        super.mouseReleased(e);

                    }
                });
            }
        }

        // generare posizione mine
        generaMine();

        // imposta gli indizi
        contaIndizi();

        scopriCella(maxMine, maxMine);
    }

    private void generaMine() {
        Random rand = new Random();

        for (int i = 0; i < mine; i++) {
            int r = rand.nextInt(0, campo.length);
            int c = rand.nextInt(0, campo.length);
            if (campo[r][c].getContenuto() == MINA) {
                break;
            }
            campo[r][c].setContenuto(MINA);
        }
    }

    private void contaIndizi() {
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                if (campo[i][j].isScoperta()) {
                    int count = 0;
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            if (x == 0 && y == 0) continue;
                            int ni = i + x;
                            int nj = j + y;
                            if (ni >= 0 && ni < campo.length && nj >= 0 && nj < campo[0].length) {
                                if (campo[ni][nj].getContenuto() == MINA) {
                                    count++;
                                }
                            }
                        }
                    }
                    campo[i][j].setContenuto(count);
                }
            }
        }
    }

    private void scopriCella(int x, int y) {
    
        if (x < 0 || x >= campo.length || y < 0 || y >= campo[0].length) {
            return;
        }
    
        Cella cella = campo[x][y];
    
        if (cella.isScoperta()) {
            return;
        }
    
        cella.setVisible(true);
    
        if (cella.getContenuto() == MINA) {
            System.out.println("Game Over! You hit a mine.");
            return;
        }
    
        if (cella.getContenuto() == 0) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx != 0 || dy != 0) {
                        scopriCella(x + dx, y + dy);
                    }
                }
            }
        }
    }
}
