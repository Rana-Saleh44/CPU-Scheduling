import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.*;

class schedulingGUI {
    Vector<Process> Processes = new Vector<>();
    static JFrame frame = new JFrame("CPU Schedulers Simulator");
    static GridBagConstraints gbc = new GridBagConstraints();
    static JPanel panel = new JPanel();

    public schedulingGUI(Vector<Process> P) {
        Processes = P;

        panel.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 5, 5, 5);

        for (int i = 0; i < Processes.size(); i++) {
            JLabel processLabel = new JLabel(Processes.get(i).getID());
            processLabel.setOpaque(true);
            processLabel.setBackground(Color.decode(Processes.get(i).getColor()));
            gbc.gridx = 0;
            gbc.gridy = i;
            panel.add(processLabel, gbc);
        }

        JScrollPane panelPane = new JScrollPane(panel);
        panelPane.setBackground(new Color(255, 255, 255));
        frame.setBackground(new Color(255, 255, 255));
        frame.add(panelPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
