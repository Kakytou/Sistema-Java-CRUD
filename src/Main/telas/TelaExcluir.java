package Main.telas;

import Main.conexaoBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TelaExcluir extends JFrame implements ActionListener {

    JTable table;
    DefaultTableModel model;
    JPanel panel;
    JTextField textField;
    JButton btn_excluir,btn_voltar;
    JLabel label;

    public TelaExcluir(){

        btn_voltar = new JButton("Voltar");
        label = new JLabel("Digite o ID que deseja excluir: ");
        btn_excluir = new JButton("Excluir");
        panel = new JPanel();
        textField = new JTextField();

        textField.setPreferredSize(new Dimension(30,20));
        textField.setToolTipText("Digite o ID que deseja Excluir: ");

        panel.add(btn_voltar);
        panel.add(label);
        panel.add(textField);
        panel.add(btn_excluir);

        btn_excluir.addActionListener(this);
        btn_voltar.addActionListener(this);

        String[] lista = {"Id", "Nome", "Preço", "Quantidade", "Data Cadastro"};
        model = new DefaultTableModel(lista, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);
        add(panel,BorderLayout.SOUTH);

        carregarDados();

        this.setSize(500,400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    public void excluirDadosByID() throws SQLException {

        try{
            if (textField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Opção Inválida, digite um ID");
                return;
            }

            Connection conn = conexaoBD.getConexao();
            if (conn != null){
                String sql = "DELETE FROM Produtos WHERE Id_Produtos = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setInt(1, Integer.parseInt(textField.getText()));
                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas > 0){
                    JOptionPane.showMessageDialog(null,"Excluido com sucesso!");
                    carregarDados();
                    textField.setText("");
                }else {
                    JOptionPane.showMessageDialog(null,"Id não encontrado!");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void carregarDados(){
        try {
            Connection conn = conexaoBD.getConexao();
            if (conn != null){
                System.out.println("BD conectado consultar");
                String sql = "SELECT * FROM Produtos";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                model.setRowCount(0);

                while (rs.next()){

                    int Id_Produtos = rs.getInt("Id_Produtos");
                    String Nome_Prod = rs.getString("Nome_Prod");
                    double preco = rs.getDouble("Preco_prod");
                    int quantidade = rs.getInt("Quantidade");
                    Date data = rs.getDate("Data_Cadastro");

                    model.addRow(new Object[]{Id_Produtos,Nome_Prod,preco,quantidade,data});
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_excluir){
            try {
                System.out.println("botao funcionando na teoria");
                excluirDadosByID();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }else {
            if (e.getSource() == btn_voltar) {
                this.dispose();
                new Estoque();
            }
        }
    }
}
