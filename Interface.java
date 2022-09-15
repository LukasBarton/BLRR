import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface {

    JFrame frame = new JFrame("Something");
    JPanel tablePanel = new JPanel();
    JPanel controlPanel = new JPanel();
    JScrollPane scrollable;
    JTable displayTable;
    JTextField tf1 = new JTextField();
    JTextField tf2 = new JTextField();
    JTextField filter = new JTextField();
    JButton addRow = new JButton("Submit Rows");
    JButton filterRows = new JButton("Filter rows");
    JButton removeRow = new JButton("Remove selected Row");

    String[] columns = {"ID", "Name", "Age"};
    DefaultTableModel model = new DefaultTableModel(columns, 0);

    void initComponents() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(800, 800);
        frame.setResizable(false);

        filterRows.addActionListener(e -> filterRows(filter.getText()));

        addRow.addActionListener(e -> {
            if (!tf1.getText().equals("") && !tf2.getText().equals("")) {

                addRow(generateID(), tf1.getText(), tf2.getText());
                tf1.setText("");
                tf2.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "None of added fields must be empty!");
            }
        });

        removeRow.addActionListener(e -> {
            if (displayTable.getSelectedRow() != -1) {
                model.removeRow(displayTable.getSelectedRow());
            } else {
                JOptionPane.showMessageDialog(null, "No row selected. Left click on a row to select it.");
            }
        });
    }

    void createPanels() {

        scrollable = new JScrollPane(tablePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollable.setBounds(0, 0, 600, 764);
        frame.add(scrollable);

        tablePanel.setBackground(Color.gray);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBounds(0, 0, 600,800);
        tablePanel.setPreferredSize(new Dimension(600, 800));

        controlPanel.setBackground(Color.lightGray);
        controlPanel.setLayout(null);
        controlPanel.setBounds(600, 0, 200, 800);
        frame.add(controlPanel);


    }

    void renderTable() {

        displayTable = new JTable(model);
        displayTable.setBounds(0, 0, 600, 800);
        displayTable.setFillsViewportHeight(true);
        displayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablePanel.add(displayTable, BorderLayout.CENTER);
        tablePanel.add(displayTable.getTableHeader(), BorderLayout.NORTH);
    }

    void renderSidePanel() {
        filter.setBounds(10, 10, 160, 20);
        filterRows.setBounds(10, 40, 160, 20);
        tf1.setBounds(10, 170, 160, 20);
        tf2.setBounds(10, 200, 160, 20);
        addRow.setBounds(10, 230, 160, 20);
        removeRow.setBounds(10, 260, 160, 20);

        controlPanel.add(filter);
        controlPanel.add(filterRows);
        controlPanel.add(tf1);
        controlPanel.add(tf2);
        controlPanel.add(addRow);
        controlPanel.add(removeRow);
    }

    void filterRows(String filterPhrase) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(((DefaultTableModel) displayTable.getModel()));
        sorter.setRowFilter(RowFilter.regexFilter(filterPhrase));
        displayTable.setRowSorter(sorter);
        refresh();
    }

    int generateID() {
        int id = 1;
        while (!checkID(id)) {
            System.out.println("bad id: " + id);
            id++;

        }
        return id;
    }

    boolean checkID(Integer id) {
        boolean foundMatch = false;
        for (int i = 0; i < displayTable.getRowCount(); i++) {
            if (id == displayTable.getValueAt(i, 0)) {
                foundMatch = true;
                System.out.println("found matching id: " + id + " value at row " + i + " : " + displayTable.getValueAt(i, 0));
            }
        }

        if (foundMatch) {
            return false;
        } else {
            return true;
        }
    }

    void addRow(Integer id, String data1, String data2) {
        model.addRow(new Object[]{id, data1, data2});
    }

    void refresh() {
        frame.setVisible(true);
        frame.repaint();
        frame.revalidate();
    }
}