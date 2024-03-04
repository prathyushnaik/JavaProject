import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BankAccount extends JFrame {
    private double balance;
    private String accountHolder;
    private String accountNumber;
    private int pin;
    private List<String> transactionHistory;

    private JTextField accountNumberField;
    private JPasswordField pinField;
    private JTextArea transactionTextArea;

    public BankAccount() {
        setTitle("VPN Bank");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK); // Set background color to black

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel vpnBankLabel = new JLabel("VPN Bank");
        vpnBankLabel.setFont(new Font("Arial", Font.BOLD, 24));
        vpnBankLabel.setForeground(Color.RED);
        vpnBankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        vpnBankLabel.setBounds(150, 20, 300, 30);
        panel.add(vpnBankLabel);

        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setBounds(20, 70, 120, 25);
        panel.add(accountNumberLabel);

        accountNumberField = new JTextField();
        accountNumberField.setBounds(150, 70, 200, 25);
        panel.add(accountNumberField);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(20, 110, 120, 25);
        panel.add(pinLabel);

        pinField = new JPasswordField();
        pinField.setBounds(150, 110, 200, 25);
        panel.add(pinField);

        JButton signInButton = new JButton("Sign In");
        signInButton.setBounds(150, 150, 100, 25);
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText();
                String pin = String.valueOf(pinField.getPassword());
                signIn(accountNumber, pin);
            }
        });
        panel.add(signInButton);

        transactionTextArea = new JTextArea();
        transactionTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transactionTextArea);
        scrollPane.setBounds(20, 200, 560, 150);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);
    }

    private void signIn(String accountNumber, String pin) {
        // Check if the account exists and the pin is correct
        // For simplicity, I'm assuming there's only one account in this example
        if (isValidAccount(accountNumber, pin)) {
            this.accountHolder = JOptionPane.showInputDialog("Enter Account Holder Name:");
            this.accountNumber = accountNumber;
            this.pin = Integer.parseInt(pin);
            transactionHistory = new ArrayList<>();
            updateTransactionHistory("Signed in");
            openBankAccountWindow();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid account number or PIN.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidAccount(String accountNumber, String pin) {
        // Check if the account number is 11 digits and the PIN is 4 digits
        if (accountNumber.length() == 11 && pin.length() == 4) {
            // Check if both account number and PIN contain only digits
            if (accountNumber.matches("\\d+") && pin.matches("\\d+")) {
                return true;
            }
        }
        return false;
    }

    private void openBankAccountWindow() {
        JFrame bankAccountFrame = new JFrame();
        bankAccountFrame.setTitle("Bank Account");
        bankAccountFrame.setSize(600, 400);
        bankAccountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK); // Set panel background color to black

        JLabel accountHolderLabel = new JLabel("Welcome, " + accountHolder);
        accountHolderLabel.setFont(new Font("Arial", Font.BOLD, 18));
        accountHolderLabel.setForeground(Color.WHITE);
        accountHolderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        accountHolderLabel.setBounds(150, 50, 300, 30);
        panel.add(accountHolderLabel);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(20, 100, 120, 25);
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountString = JOptionPane.showInputDialog("Enter deposit amount:");
                double amount = Double.parseDouble(amountString);
                deposit(amount);
            }
        });
        panel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(20, 150, 120, 25);
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountString = JOptionPane.showInputDialog("Enter withdrawal amount:");
                double amount = Double.parseDouble(amountString);
                withdraw(amount);
            }
        });
        panel.add(withdrawButton);

        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.setBounds(20, 200, 120, 25);
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Current balance: " + balance);
            }
        });
        panel.add(checkBalanceButton);

        JButton viewStatementButton = new JButton("View Statement");
        viewStatementButton.setBounds(20, 250, 120, 25);
        viewStatementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTransactionHistory();
            }
        });
        panel.add(viewStatementButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(20, 300, 120, 25);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bankAccountFrame.dispose();
            }
        });
        panel.add(exitButton);

        bankAccountFrame.add(panel);
        bankAccountFrame.setVisible(true);
    }

    protected void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            updateTransactionHistory("Withdrawal: $" + amount);
            JOptionPane.showMessageDialog(null, "Withdrawal successful. Current balance: " + balance);
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient funds. Current balance: " + balance);
        }
    }

    protected void deposit(double amount) {
        balance += amount;
        updateTransactionHistory("Deposit: $" + amount);
        JOptionPane.showMessageDialog(null, "Deposit successful. Current balance: " + balance);
    }

    private void updateTransactionHistory(String transaction) {
        transactionHistory.add(transaction);
        StringBuilder sb = new StringBuilder();
        for (String t : transactionHistory) {
            sb.append(t).append("\n");
        }
        transactionTextArea.setText(sb.toString());
    }

    private void showTransactionHistory() {
        JFrame statementFrame = new JFrame();
        statementFrame.setTitle("Transaction History");
        statementFrame.setSize(600, 400);

        JTextArea statementTextArea = new JTextArea();
        statementTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statementTextArea);
        scrollPane.setBounds(20, 20, 560, 320);

        StringBuilder sb = new StringBuilder();
        for (String t : transactionHistory) {
            sb.append(t).append("\n");
        }
        statementTextArea.setText(sb.toString());

        statementFrame.add(scrollPane);
        statementFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BankAccount();
            }
        });
    }
}
