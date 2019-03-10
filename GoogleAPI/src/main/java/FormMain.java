import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class FormMain extends JFrame {
    public FormMain()
    {
        try
        {
            //Create Form Main
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setTitle("Google Drive App");
            this.setSize(800,400);
            this.setLayout(null);
            this.setVisible(true);
            JButton btnRefest = new JButton("ReFresh");
            btnRefest.setLocation(100,10);
            btnRefest.setSize(100,25);
            this.add(btnRefest);
            //butot
            JButton btn = new JButton("Add +");

            btn.setLocation(10,10);
            btn.setSize(80,25);
            this.add(btn);

            //Add JTable to show data
            ArrayList<ItemFile> lstFile = DriveService.GetListFile();
            String data[][] = new String[20][3];
            for(int i=0;i<lstFile.size();i++)
            {
                data[i][0]=Integer.toString(i+1);
                data[i][1]=lstFile.get(i).getName();
                data[i][2]=lstFile.get(i).getLink();
            }

            String column[]={"ID","NAME","SALARY"};
            final JTable jt=new JTable(data,column);

            jt.setCellSelectionEnabled(true);
            ListSelectionModel select= jt.getSelectionModel();
            select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane sp=new JScrollPane(jt);
            sp.setLocation(10,50);
            Font d = new Font("Dialog", Font.PLAIN, 25);
            sp.setFont(d);
            sp.setSize(this.getWidth()-30,400);
            sp.setBackground(Color.BLACK);

            this.add(sp);

            btnRefest.addActionListener(new ActionListener()  {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DefaultTableModel tMOdel = (DefaultTableModel) jt.getModel();
                    tMOdel.setRowCount(0);
                    try
                    {
                        ArrayList<ItemFile> lstFile = DriveService.GetListFile();
                    }
                    catch (Exception es)
                    {
                        es.printStackTrace();
                    }

                    String data[][] = new String[20][3];
                    for(int i=0;i<lstFile.size();i++)
                    {
                        data[i][0]=Integer.toString(i+1);
                        data[i][1]=lstFile.get(i).getName();
                        data[i][2]=lstFile.get(i).getLink();
                    }

                    String column[]={"ID","NAME","SALARY"};
                    final JTable jt=new JTable(data,column);


                }
            });
           btn.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   JFileChooser fileChooser = new JFileChooser();
                   fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                   int result = fileChooser.showOpenDialog(null);
                   if (result == JFileChooser.APPROVE_OPTION) {
                       File selectedFile = fileChooser.getSelectedFile();
                       System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                       try
                       {
                           DriveService.UploadFile(selectedFile.getAbsolutePath());
                       }
                       catch (Exception ex)
                       {
                           ex.printStackTrace();
                       }

                   }
               }
           });

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
