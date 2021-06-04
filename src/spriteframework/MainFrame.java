package spriteframework;

import javax.swing.JFrame;

public abstract class MainFrame extends JFrame  {

    protected abstract AbstractBoard createBoard();
    
    public MainFrame(String t) {
        add(createBoard());
		setTitle(t);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
    }
}
