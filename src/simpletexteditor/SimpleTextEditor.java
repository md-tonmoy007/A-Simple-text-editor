
package simpletexteditor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SimpleTextEditor extends JFrame implements ActionListener{

    /**
     * @param args the command line arguments
     */
    private JTextArea area;
    private JScrollPane scpane;
    String text = "";
    SimpleTextEditor(){
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("sun.arch.data.model"));
        System.out.println(System.getProperty("os.arch"));
        System.out.println(System.getProperty("os.version"));
        System.out.println(System.getProperty("user.home"));
        // Get the host name of the machine
        try {
            System.out.println(java.net.InetAddress.getLocalHost().getHostName());
        } catch (java.net.UnknownHostException e) {
            System.out.println("can't");
            e.printStackTrace();
        }
                // Get the number of processors available to the Java Virtual Machine
        System.out.println(Runtime.getRuntime().availableProcessors());

        // Get the total amount of memory in the Java Virtual Machine
        System.out.println(Runtime.getRuntime().totalMemory());

        // Get the maximum amount of memory that the Java Virtual Machine will attempt to use
        System.out.println(Runtime.getRuntime().maxMemory());


         // Set the frame title
        setTitle("Simple Text Editor");
        // Set the frame size
        setSize(700, 500);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        // Specify how the frame should close when the close button is clicked
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar(); // menubar

JMenu file = new JMenu("File"); // file menu

JMenuItem newdoc = new JMenuItem("New");
newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
 newdoc.addActionListener(this);

JMenuItem open = new JMenuItem("Open");
open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
 open.addActionListener(this);

JMenuItem save = new JMenuItem("Save");
save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
save.addActionListener(this);

JMenuItem print = new JMenuItem("Print");
print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
// print.addActionListener(this);

JMenuItem exit = new JMenuItem("Exit");
exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
 exit.addActionListener(this);

JMenu edit = new JMenu("Edit");

JMenuItem copy = new JMenuItem("Copy");
copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
 copy.addActionListener(this);

JMenuItem paste = new JMenuItem("Paste");
paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
 paste.addActionListener(this);

JMenuItem cut = new JMenuItem("Cut");
cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
 cut.addActionListener(this);

JMenuItem selectall = new JMenuItem("Select All");
selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
 selectall.addActionListener(this);

JMenu about = new JMenu("Help");

JMenuItem notepad = new JMenuItem("About Notepad");
 notepad.addActionListener(this);

area = new JTextArea();
area.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
area.setLineWrap(true);
area.setWrapStyleWord(true);

scpane = new JScrollPane(area);
scpane.setBorder(BorderFactory.createEmptyBorder());

setJMenuBar(menuBar);
menuBar.add(file);
menuBar.add(edit);
menuBar.add(about);

file.add(newdoc);
file.add(open);
file.add(save);
file.add(print);
file.add(exit);

edit.add(copy);
edit.add(paste);
edit.add(cut);
edit.add(selectall);

about.add(notepad);

add(scpane, BorderLayout.CENTER);
setVisible(true);

        
        
        
        ImageIcon notepadIcon = new ImageIcon(ClassLoader.getSystemResource("simpletexteditor/icons/icon.png"));
        Image icon = notepadIcon.getImage();
        setIconImage(icon);  
       
        
    }
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("New")) {
            area.setText("");
        
        } else if (ae.getActionCommand().equals("Open")) {
            JFileChooser chooser = new JFileChooser("D:/Java");
            chooser.setAcceptAllFileFilterUsed(false); 
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt"); 
            chooser.addChoosableFileFilter(restrict);
    	
            int result = chooser.showOpenDialog(this);
            if(result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
				
                try{
                    System.out.println("HEki");
                    FileReader reader = new FileReader(file);
                    BufferedReader br = new BufferedReader(reader);
                    area.read( br, null );
                    br.close();
                    area.requestFocus();
                }catch(Exception e){
                    System.out.print(e);
                }
            }
        } else if(ae.getActionCommand().equals("Save")){
            final JFileChooser SaveAs = new JFileChooser();
            SaveAs.setApproveButtonText("Save");
            int actionDialog = SaveAs.showOpenDialog(this);
            if (actionDialog != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileName = new File(SaveAs.getSelectedFile() + ".txt");
            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(fileName));
                area.write(outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(ae.getActionCommand().equals("Print")){
            try{
                area.print();
            }catch(Exception e){}
        }else if (ae.getActionCommand().equals("Exit")) {
            System.exit(0);
        }else if (ae.getActionCommand().equals("Copy")) {
            text = area.getSelectedText();
        }else if (ae.getActionCommand().equals("Paste")) {
            area.insert(text, area.getCaretPosition());
        }else if (ae.getActionCommand().equals("Cut")) {
            text = area.getSelectedText();
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
        }else if (ae.getActionCommand().equals("Select All")) {
            area.selectAll();
        }else if (ae.getActionCommand().equals("About Notepad")) {
            new About().setVisible(true);
            
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
        new SimpleTextEditor();
        
    }
    
}
