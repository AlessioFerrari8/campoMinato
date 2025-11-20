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

                    // usiamo una classe anonima perché dobbiamo implementare diversi metodi
                    // astratti
                    // non si può usare la lambda function
                    @Override
                    public void mouseReleased(MouseEvent e) {

                        Cella cliccata = (Cella) e.getSource();

                        // button 1: pulsante sx
                        // button 2: tasto centrale / rotellina
                        // button 3: pulsante dx
                        switch (e.getButton()) {
                            case MouseEvent.BUTTON1:
                                // scopro la cella
                                scopriCella(cliccata.getR(), cliccata.getC());

                                if (checkVittoria()) {
                                    JOptionPane.showMessageDialog(null, "You won!");
                                    return;
                                }

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

                        // super.mouseReleased(e);

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

        int mineInserite = 0;

        while (mineInserite < mine) {
            int r = rand.nextInt(campo.length);
            int c = rand.nextInt(campo[0].length);
            if (campo[r][c].getContenuto() != MINA) {
                campo[r][c].setContenuto(MINA);
                mineInserite++;
            }

        }
    }

    private void contaIndizi() {

        int mineContate = 0;

        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {

                mineContate = 0;

                if (campo[i][j].getContenuto() == MINA) {
                    continue;
                }

                // scorro le adiacenze 3*3
                for (int j2 = i - 1; j2 <= i + 1; j2++) {
                    for (int k = j - 1; k <= j + 1; k++) {
                        // controllo validità celle
                        try {
                            if (campo[j2][k].getContenuto() == MINA) {
                                mineContate++;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            // salto cella
                        }
                    }
                }
                campo[i][j].setContenuto(mineContate);

            }
        }

    }

    private void scopriCella(int r, int c) {

        if (campo[r][c].isBandiera()) {
            return;
        }
        // caso base
        campo[r][c].setVisible(true);

        // controllo se ho perso
        if (campo[r][c].getContenuto() == MINA) {
            JOptionPane.showMessageDialog(null, "Game Over!");
            System.exit(0);
        }

        // chiusura
        if (campo[r][c].getContenuto() > 0) { // se incappo in un numero
            return;
        }

        // chiamata ricorsiva
        for (int riga = r - 1; riga <= r + 1; riga++) {
            for (int col = c - 1; col <= c + 1; col++) {

                if (riga == r && col == c)
                    continue;
                // controllo se le celle adiacenti sono coperte
                try {
                    if (!campo[riga][col].isScoperta()) {
                        scopriCella(riga, col);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }
    }

    private boolean checkVittoria() {
        boolean win = true;
        
        // label, identifica il ciclo più esterno
        ciclo: 
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo.length; j++) {
                if (!campo[i][j].isScoperta() && campo[i][j].getContenuto() != MINA) { // se è coperto e non è una mina
                    win = false;
                    break ciclo;
                }
            }
        }
        return win;
    }
}
