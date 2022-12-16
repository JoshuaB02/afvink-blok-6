import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Afvink5_1 extends JFrame implements ActionListener {
    JPanel p = new JPanel();
    JTextArea input1 =  new JTextArea();
    JTextArea input2 =  new JTextArea();
    JTextArea input3 =  new JTextArea();
    JTextArea output =  new JTextArea();
    JButton button = new JButton();
    String[] dropdown = {"All", "1 & 2", "1 & 3", "2 & 3"};
    JComboBox<String> dropdown_menu = new JComboBox<>(dropdown);
    Set<String> set;
    Container w;

    public static void main(String[] args) {
        Afvink5_1 frame = new Afvink5_1();
        frame.setSize(1000, 500);
        frame.setBackground(Color.lightGray);
        frame.CreateGUI();
        frame.setVisible(true);
    }

    public void CreateGUI(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        w = getContentPane();
        p.setSize(w.getSize());
        p.setLayout(new GridLayout(2, 3));
        w.add(p);
        input1.setText("A1AT\nP88\nA2AH\np63");
        input2.setText("A1AB\nP99\nA2AH\np63");
        input3.setText("p63\np19");
        p.add(input1);
        p.add(input2);
        p.add(input3);
        p.add(output);
        p.add(button);
        p.add(dropdown_menu);
        button.addActionListener(this);
        button.setText("Calculate match");
    }
    @Override
    public void actionPerformed(ActionEvent e){
        String in1 = input1.getText();
        String in2 = input2.getText();
        String in3 = input3.getText();
        String dropdown_result = dropdown_menu.getSelectedItem().toString();
        Set<String> split1 = Arrays.stream(in1.split("\n")).collect(Collectors.toSet());
        Set<String> split2 = Arrays.stream(in2.split("\n")).collect(Collectors.toSet());
        Set<String> split3 = Arrays.stream(in3.split("\n")).collect(Collectors.toSet());
        Set<String> matching = null;
        switch (dropdown_result){
            case "All":
                split1.retainAll(split2);
                split1.retainAll(split3);
                matching = split1;
                break;
            case "1 & 2":
                split1.retainAll(split2);
                matching = split1;
                break;
            case "1 & 3":
                split1.retainAll(split3);
                matching = split1;
                break;
            case "2 & 3":
                split2.retainAll(split3);
                matching = split2;
                break;
        }
        output.setText(String.join("\n", matching.toArray(new String[matching.size()])));

    }

}

