/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package onenoteenhancer;

/**
 *
 * @author Jigar
 */
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.*;
import java.io.*;

public class CopyImageToClipBoard implements ClipboardOwner {
    

    public void copy (BufferedImage img)
    {
        try {
            Robot robot = new Robot();
            
            TransferableImage trans = new TransferableImage(img);
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents( trans, this );
        }
        catch ( AWTException x ) {
            x.printStackTrace();
            System.exit( 1 );
        }
    }
    
    public static void main( String[] arg ) {
        CopyImageToClipBoard ci = new CopyImageToClipBoard();
    }

    public void lostOwnership( Clipboard clip, Transferable trans ) {
        System.out.println( "Lost Clipboard Ownership" );
    }

    private class TransferableImage implements Transferable {

        Image i;

        public TransferableImage( Image i ) {
            this.i = i;
        }

        public Object getTransferData( DataFlavor flavor )
        throws UnsupportedFlavorException, IOException {
            if ( flavor.equals( DataFlavor.imageFlavor ) && i != null ) {
                return i;
            }
            else {
                throw new UnsupportedFlavorException( flavor );
            }
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[ 1 ];
            flavors[ 0 ] = DataFlavor.imageFlavor;
            return flavors;
        }

        public boolean isDataFlavorSupported( DataFlavor flavor ) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for ( int i = 0; i < flavors.length; i++ ) {
                if ( flavor.equals( flavors[ i ] ) ) {
                    return true;
                }
            }

            return false;
        }
    }
}
