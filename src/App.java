import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame f = new JFrame("Minesweeper");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // crea campo
        Campo gioco = new Campo(10, 15, 25, f.getContentPane());
        f.getContentPane().add(gioco);
        f.setSize(466, 342);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
