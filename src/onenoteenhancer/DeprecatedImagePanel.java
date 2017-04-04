package onenoteenhancer;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import sun.awt.SunHints;

public class DeprecatedImagePanel extends JPanel implements MouseMotionListener, MouseListener{

    private BufferedImage image;

    private Rectangle rect = null;
    
    private boolean doSelect = true;
    private boolean drawEdge = false;
    private boolean canDraw = false;
    
    private BufferedImage draw;
    private BufferedImage mask;
    
    public DeprecatedImagePanel(BufferedImage img, boolean doSelect, boolean drawEdge, boolean canDraw) {
        setImage(img);
       
        this.doSelect = doSelect;
        this.drawEdge = drawEdge;
        this.canDraw = canDraw;
       
        addMouseListener(this);
        addMouseMotionListener(this);

    }
    
    public void setImage (BufferedImage img)
    {
        this.image = img;
        resetDrawImage();
        resetMask();
        prev = null;
        repaint();
    }

    private void resetMask ()
    {
        if (image != null)
        {
            this.mask = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = this.mask.createGraphics();

            g.setColor(new Color (255, 255, 255, 128));
            
            g.fillRect(0, 0, mask.getWidth(), mask.getHeight());
        }
        
    }
    
    private void resetDrawImage ()
    {
        if (image != null)
        {
            this.draw = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = draw.createGraphics();

            g.setColor(new Color(1, 1, 1, 0));
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters  
        g.drawImage(draw, 0, 0, this);
        
        if (doSelect)
        {
            g.drawImage(mask, 0, 0, null);
        }
        
        if (rect != null && doSelect)
        {
            Graphics2D g2 = (Graphics2D)g;
            
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            
            g2.drawImage(image.getSubimage(rect.x, rect.y, rect.width, rect.height), rect.x, rect.y, null);
            
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.black);
            
            g2.draw(rect);
        }
        
        if (drawEdge)
        {
            g.setColor(Color.red);
            g.drawRect(0, 0, image.getWidth()-1, image.getHeight()-1);
        }
    }

    private Point prev = null;
    @Override
    public void mouseDragged(MouseEvent e) {
        rect = new Rectangle(pressedPoint.x, pressedPoint.y, e.getPoint().x - pressedPoint.x, e.getPoint().y - pressedPoint.y);
        
        if (canDraw)
        {
            
            
            if (SwingUtilities.isLeftMouseButton(e))
            {
                Graphics2D g = draw.createGraphics();
                g.setStroke(new BasicStroke(2));
                g.setColor(Color.RED);

                g.drawLine(prev.x, prev.y, e.getPoint().x, e.getPoint().y);

                
            } else {
                Graphics2D g = draw.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.setStroke(new BasicStroke(2));
                g.setComposite(AlphaComposite.SrcOut);
                g.setColor(new Color (1, 1, 1, 0));
               

                g.fillOval(e.getPoint().x - 10, e.getPoint().y - 10, 20, 20);

                
            }
            
            prev = e.getPoint();
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
    
    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (canDraw)
        {
            CopyImageToClipBoard cp = new CopyImageToClipBoard();
            
            BufferedImage imgFinal = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            
            imgFinal.createGraphics().drawImage(image, 0, 0, null);
            imgFinal.createGraphics().drawImage(draw, 0, 0, null);
            
            cp.copy(imgFinal);
                    
        }
    }

    Point pressedPoint = null;
    Point releasedPoint = null;
    
    int typePressed = MouseEvent.BUTTON1;
    
    @Override
    public void mousePressed(MouseEvent e) {
        pressedPoint = e.getPoint();
        prev = e.getPoint();
        typePressed = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        releasedPoint = e.getPoint();
        
        if (doSelect)
        {
            BufferedImage imgTk = image.getSubimage(pressedPoint.x, pressedPoint.y, releasedPoint.x - pressedPoint.x, releasedPoint.y - pressedPoint.y);

            imageTaken.fire(imgTk);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    
    private SingleArgumentEventManager<BufferedImage> imageTaken = new SingleArgumentEventManager<>();
    
    public void addImageTakenListener (Consumer<BufferedImage> list)
    {
        imageTaken.addListener(list);
    }
    
    public void removeImageTakenListener (Consumer<BufferedImage> list)
    {
        imageTaken.removeListener(list);
    }
    
}
