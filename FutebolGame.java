import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Dimension;

public class FutebolGame extends JPanel implements ActionListener, KeyListener {
    // Configurações da tela
    private final int LARGURA = 800;
    private final int ALTURA = 500;
    
    // Posições e tamanhos
    private int bolaX = LARGURA / 2, bolaY = ALTURA / 2;
    private int bolaDX = 3, bolaDY = 3; // Velocidade da bola
    private int playerY = 200, cpuY = 200;
    private final int BARRA_LARGURA = 15, BARRA_ALTURA = 80;

    public FutebolGame() {
        this.setPreferredSize(new Dimension(LARGURA, ALTURA));
        this.setBackground(new Color(34,139,34));
        this.addKeyListener(this);
        this.setFocusable(true);
        
        // Timer para atualizar o jogo a cada 10ms
        Timer timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);

        // Desenha a bola
        g.fillOval(bolaX, bolaY, 20, 20);

        // Desenha as barras
        g.fillRect(20, playerY, BARRA_LARGURA, BARRA_ALTURA); // Jogador
        g.fillRect(LARGURA - 40, cpuY, BARRA_LARGURA, BARRA_ALTURA); // CPU

        // Linha central
        g.drawLine(LARGURA / 2, 0, LARGURA / 2, ALTURA);

        // minha assinatura 
        g.setFont(new Font("Arial",Font.ITALIC, 14) );

        // onde ela vai ficar
        g.drawString("By Ian Sakis", 100, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Movimentação da bola
        bolaX += bolaDX;
        bolaY += bolaDY;

        // Colisão com teto e chão
        if (bolaY <= 0 || bolaY >= ALTURA - 20) bolaDY *= -1;

        // Lógica da CPU (segue a bola)
        if (bolaY > cpuY + BARRA_ALTURA / 2) cpuY += 3;
        else cpuY -= 3;

        // Colisão com as barras
        Rectangle bolaRect = new Rectangle(bolaX, bolaY, 20, 20);
        Rectangle playerRect = new Rectangle(20, playerY, BARRA_LARGURA, BARRA_ALTURA);
        Rectangle cpuRect = new Rectangle(LARGURA - 40, cpuY, BARRA_LARGURA, BARRA_ALTURA);

        if (bolaRect.intersects(playerRect) || bolaRect.intersects(cpuRect)) {
            bolaDX *= -1;
            bolaDX += (bolaDX > 0) ? 1 : -1; // Aumenta a velocidade para ficar difícil
        }

        // Reset se alguém fizer gol
        if (bolaX < 0 || bolaX > LARGURA) {
            bolaX = LARGURA / 2;
            bolaY = ALTURA / 2;
            bolaDX = 3;
        }

        repaint();
    }

    // Controles do Jogador
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && playerY > 0) playerY -= 20;
        if (e.getKeyCode() == KeyEvent.VK_DOWN && playerY < ALTURA - BARRA_ALTURA) playerY += 20;
    }
    
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Futebol Pong");
        FutebolGame game = new FutebolGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
