/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mvhsbandinventory;

import javax.swing.JFrame;

/**
 *
 * @author nicholson
 */
public class Main
{
    public static JFrame window = new JFrame();
    public static Display panel = new Display();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        window.add(panel);
        window.setSize(800,800);
        window.setTitle("MVHS - Band Inventory");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        panel.repaint();
    }
}
