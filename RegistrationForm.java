import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegistrationForm extends JFrame {

    // Components of the form
    private Container container;
    private JLabel title;
    private JLabel idLabel;
    private JTextField idField;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel genderLabel;
    private JRadioButton male;
    private JRadioButton female;
    private ButtonGroup genderGroup;
    private JLabel addressLabel;
    private JTextField addressField;
    private JLabel contactLabel;
    private JTextField contactField;
    private JButton register;
    private JButton exit;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;

    public RegistrationForm() {
        setTitle("Registration Form");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        container = getContentPane();
        container.setLayout(null);

        title = new JLabel("Registration Form");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(300, 30);
        title.setLocation(50, 30);
        container.add(title);

        idLabel = new JLabel("ID");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        idLabel.setSize(100, 20);
        idLabel.setLocation(50, 80);
        container.add(idLabel);

        idField = new JTextField();
        idField.setFont(new Font("Arial", Font.PLAIN, 15));
        idField.setSize(150, 20);
        idField.setLocation(200, 80);
        container.add(idField);

        nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        nameLabel.setSize(100, 20);
        nameLabel.setLocation(50, 120);
        container.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 15));
        nameField.setSize(150, 20);
        nameField.setLocation(200, 120);
        container.add(nameField);

        genderLabel = new JLabel("Gender");
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        genderLabel.setSize(100, 20);
        genderLabel.setLocation(50, 160);
        container.add(genderLabel);

        male = new JRadioButton("Male");
        male.setFont(new Font("Arial", Font.PLAIN, 15));
        male.setSelected(true);
        male.setSize(75, 20);
        male.setLocation(200, 160);
        container.add(male);

        female = new JRadioButton("Female");
        female.setFont(new Font("Arial", Font.PLAIN, 15));
        female.setSelected(false);
        female.setSize(80, 20);
        female.setLocation(275, 160);
        container.add(female);

        genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        addressLabel = new JLabel("Address");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        addressLabel.setSize(100, 20);
        addressLabel.setLocation(50, 200);
        container.add(addressLabel);

        addressField = new JTextField();
        addressField.setFont(new Font("Arial", Font.PLAIN, 15));
        addressField.setSize(150, 20);
        addressField.setLocation(200, 200);
        container.add(addressField);

        contactLabel = new JLabel("Contact");
        contactLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        contactLabel.setSize(100, 20);
        contactLabel.setLocation(50, 240);
        container.add(contactLabel);

        contactField = new JTextField();
        contactField.setFont(new Font("Arial", Font.PLAIN, 15));
        contactField.setSize(150, 20);
        contactField.setLocation(200, 240);
        container.add(contactField);

        register = new JButton("Register");
        register.setFont(new Font("Arial", Font.PLAIN, 15));
        register.setSize(100, 20);
        register.setLocation(150, 300);
        container.add(register);

        exit = new JButton("Exit");
        exit.setFont(new Font("Arial", Font.PLAIN, 15));
        exit.setSize(100, 20);
        exit.setLocation(270, 300);
        container.add(exit);

        String[] columnNames = {"ID", "Name", "Gender", "Address", "Contact"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(400, 80, 450, 400);
        container.add(scrollPane);

        // Load data from database and display in the table
        loadData();

        register.addActionListener((var e) -> {
            String id = idField.getText();
            String name1 = nameField.getText();
            String gender = male.isSelected() ? "Male" : "Female";
            String address = addressField.getText();
            String contact = contactField.getText();
            if (id.isEmpty() || name1.isEmpty() || address.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields");
                return;
            }
            // Save to database
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration_db", "root", "");
                String query = "INSERT INTO users (ID, Name, Gender, Address, Contact) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, name1);
                preparedStatement.setString(3, gender);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, contact);
                preparedStatement.executeUpdate();
                connection.close();
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            model.addRow(new Object[]{id, name1, gender, address, contact});
            idField.setText("");
            nameField.setText("");
            addressField.setText("");
            contactField.setText("");
            genderGroup.clearSelection();
            male.setSelected(true);
        });

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private void loadData() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration_db", "root", "");
            String query = "SELECT * FROM users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String name = resultSet.getString("Name");
                String gender = resultSet.getString("Gender");
                String address = resultSet.getString("Address");
                String contact = resultSet.getString("Contact");
                model.addRow(new Object[]{id, name, gender, address, contact});
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new RegistrationForm();
    }
}
