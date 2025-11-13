import java.awt.Canvas;
import java.awt.Color;
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
    
    public void setBandiera() {
        if (stato == StatoCella.Coperto) {
            stato = StatoCella.Bandiera;
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
