import javax.swing.*;

public class NewNodeForm extends JFrame {
    private JTextField textField1;
    private JButton button1;
    private JPanel jPanel;

    Trees trees;
    MainForm mf;

    public NewNodeForm(Trees trees, MainForm frame) {
        this.trees = trees;
        this.mf = frame;
    }

    public void start() {
        button1.addActionListener(e -> {
            if (textField1.getText().equals("")) {
                dispose();
            } else {
                mf.newNode(textField1.getText());
                dispose();
            }
            dispose();
        });

        setTitle("Create");
        setContentPane(jPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(300, 400);
        setVisible(true);
    }
}
