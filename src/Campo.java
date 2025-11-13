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
    }

    private void generaMine() {
        Random rand = new Random();

    }

    private void contaIndizi() {
        
    }
}
