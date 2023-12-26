
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
import java.io.InputStreamReader;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
    private JLabel currentFileLabel;
    private File currentFile;
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
        setTitle("SPy");
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

JMenu about = new JMenu("tools");

JMenuItem notepad = new JMenuItem("About Notepad");
 notepad.addActionListener(this);
 
JMenuItem runscript = new JMenuItem("Python run");
 runscript.addActionListener(this);

area = new JTextArea();
area.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
area.setLineWrap(true);
area.setWrapStyleWord(true);

scpane = new JScrollPane(area);

currentFileLabel = new JLabel("Current File: None");
add(currentFileLabel, BorderLayout.NORTH);
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
about.add(runscript);

add(scpane, BorderLayout.CENTER);
setVisible(true);

        
        
        
        ImageIcon notepadIcon = new ImageIcon(ClassLoader.getSystemResource("simpletexteditor/icons/icon.png"));
        Image icon = notepadIcon.getImage();
        setIconImage(icon);  
       
        
    }
    private void updateCurrentFileLabel() {
        currentFileLabel.setText("Current File: " + currentFile.getAbsolutePath());
    }
     private void executePythonScript() {
        try {
            // Save the content of the text area to a temporary Python file

            // Build and start the process
            ProcessBuilder processBuilder = new ProcessBuilder("python", currentFile.getAbsolutePath());
            Process process = processBuilder.start();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            String res = "";
            while ((line = reader.readLine()) != null) {
                res+=line;
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            JOptionPane.showMessageDialog(null,res , "Information", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Python script executed with exit code: " + exitCode);

        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("New")) {
            area.setText("");
        
        } else if (ae.getActionCommand().equals("Open")) {
            JFileChooser chooser = new JFileChooser("D:/Java");
            chooser.setAcceptAllFileFilterUsed(false); 
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .py files", "py"); 
            chooser.addChoosableFileFilter(restrict);
    	
            int result = chooser.showOpenDialog(this);
            if(result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                currentFile = file;
                updateCurrentFileLabel();
				
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
            JFileChooser chooser = new JFileChooser("D:/Java");
            File fileName = new File(SaveAs.getSelectedFile()+ "");
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .py files", "py"); 
            chooser.addChoosableFileFilter(restrict);
            currentFile = fileName;
            updateCurrentFileLabel();
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
            
        }else if (ae.getActionCommand().equals("Python run")) {
            executePythonScript();
            
        }
        
    }
    public static void main(String[] args) {
        // TODO code application logic here
        new SimpleTextEditor();
        
    }
    
}
