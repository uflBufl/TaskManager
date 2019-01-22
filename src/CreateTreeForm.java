import javax.swing.*;

public class CreateTreeForm extends JFrame {

    private javax.swing.JPanel jPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;

    Trees trees;
    MainForm mf;

    public CreateTreeForm(Trees trees, MainForm frame){
        this.trees = trees;
        this.mf = frame;
    }

    public void start(){
        button1.addActionListener(e -> {
            if(textField1.getText().equals("") || textField2.getText().equals("")){
                dispose();
            }
            else{
                mf.createTree(textField1.getText(),textField2.getText());
                dispose();
            }
            dispose();
        });

        setTitle("Create");
        setContentPane(jPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }

}


