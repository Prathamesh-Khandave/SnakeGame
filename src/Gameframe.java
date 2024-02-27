import javax.swing.JFrame;

public class Gameframe extends JFrame{
 Gameframe(){
	 this.add(new Gamepanel());
	 this.setTitle("Pratham SnakeGame");
	 this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	 this.setResizable(false);
	 this.pack();
	 this.setVisible(true);
	 this.setLocationRelativeTo(null);;
 }
}
