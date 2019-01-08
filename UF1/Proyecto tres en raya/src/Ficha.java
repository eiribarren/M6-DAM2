import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Ficha extends JLabel {
	
	enum Forma {
		CRUZ, CIRCULO
	}
	private Forma forma;
	
	public Ficha( Forma forma ) {
		this.forma = forma;
		this.setVisible(true);
	}
	
	public Forma getForma() {
		return this.forma;
	}
	
	public void ponerImagen(int ancho, int altura, String urlImagen) {
		   Image imagen = new javax.swing.ImageIcon(getClass().getResource(urlImagen)).getImage();
	        
	       BufferedImage bi = new BufferedImage(imagen.getWidth(null), imagen.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	       Graphics2D g = bi.createGraphics();
	       g.drawImage(imagen,0,altura/2,ancho,altura,null);
	       this.setIcon(new ImageIcon(bi));
	}
	
}
