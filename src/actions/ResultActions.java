/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import gui.ResultWindow;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author knn
 */
public class ResultActions implements ActionListener {

	private ResultWindow rw;

	public ResultActions(ResultWindow rw) {
		this.rw = rw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == rw.getSs()) {
			try {
				Container c = rw.getTestNamesPanel();
				BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
//				im = this.getScaledImage(im, c.getWidth()*4, c.getHeight()*4);
				c.paint(im.getGraphics());
				ImageIO.write(im, "PNG", new File("./shot.png"));
			} catch (IOException ex) {
				Logger.getLogger(ResultActions.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();

		double scaleX = (double) width / imageWidth;
		double scaleY = (double) height / imageHeight;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

		return bilinearScaleOp.filter(
				image,
				new BufferedImage(width, height, image.getType()));
	}
}
