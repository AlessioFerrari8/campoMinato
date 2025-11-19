import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Cella extends Canvas {
    
    private StatoCella stato;
    private int contenuto;
    private final int MINA = -1;
    private int r, c; // indici riga e colonna

    private Color[] colori;

    public Cella(int dim, int r, int c) {

        setSize(dim, dim); // dimensione componente
        this.r = r;
        this.c = c;
        this.contenuto = 0;
        this.stato = StatoCella.Coperto;
        // colori
        colori = new Color[] {
            Color.white,
            Color.blue,
            new Color(70, 170, 70),   // green
            Color.red,
            new Color(0, 0, 128),     // dark blue
            new Color(150, 50, 50),   // brown
            new Color(50, 255, 200),  // aqua
            Color.darkGray,
            Color.black
        };
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setFont(new Font("Consolas", Font.BOLD, 16));

        switch (stato) {
            case Coperto:
                g.setColor(Color.orange);
                g.fillRect(0, 0, getWidth(), getHeight());
                break;
            case Scoperta:
                g.setColor(Color.green);
                g.fillRect(0, 0, getWidth(), getHeight());
                // se c'è una mina la disegniamo
                if (contenuto == MINA) {
                    g.setColor(Color.red);
                    g.fillOval(0, 0, getWidth(), getHeight());
                } else if (contenuto > 0) {
                    g.setColor(colori[contenuto]);
                    g.drawString(contenuto + "", getWidth() / 3, 2 * getHeight()/ 3);
                }

                break;
            case Bandiera:
                g.setColor(Color.orange);
                g.fillRect(0, 0, getWidth(), getHeight());

                g.setColor(Color.black);
                g.fillRect(5, 5, getWidth() / 7, getHeight() - 5);
                g.setColor(Color.red);

                int xPoints[] = {getWidth() / 3, getWidth() - 5, getWidth()/3};
                int yPoints[] = {5, 2*getHeight()/5, 2*getHeight()/3};
                g.fillPolygon(xPoints, yPoints, 3);
                break;
        }

        g.setColor(Color.black);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    public boolean isScoperta() {
        return stato == StatoCella.Scoperta;
    }

    public boolean isBandiera() {
        return stato == StatoCella.Bandiera;
    }

    public int getContenuto() {
        return contenuto;
    }

    public void setContenuto(int contenuto) {
        if (contenuto >= -1 && contenuto <= 8)
            this.contenuto = contenuto;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }
    
    public void toggleBandiera() {
        if (stato == StatoCella.Coperto) {
            stato = StatoCella.Bandiera;
        } else {
            stato = StatoCella.Coperto;
        }
        repaint();
    }

    public void setVisible(boolean mostra) {
        if (mostra && stato == StatoCella.Coperto) {
            stato = StatoCella.Scoperta;
        }
        repaint();
    }
}
