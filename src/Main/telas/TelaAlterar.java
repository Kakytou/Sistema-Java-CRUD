package Main.telas;

import Main.conexaoBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class TelaAlterar extends JFrame implements ActionListener{

    JTable table;
    JPanel panel;
    DefaultTableModel model;
    JScrollPane scrollPane;
    JButton btn_voltar;
    ArrayList<Object> list = new ArrayList<>();

    public TelaAlterar(){

        btn_voltar = new JButton("Voltar");
        btn_voltar.addActionListener(this);

        panel = new JPanel();
        panel.add(btn_voltar);

        String[] colunas = {"Id_Produtos","Nome_Prod","Preco_Prod","Quantidade","Id_Categoria","Data_Cadastro"};
        model = new DefaultTableModel(colunas,0);
        table = new JTable(model);

        scrollPane = new JScrollPane(table);

        add(scrollPane,BorderLayout.CENTER);
        add(panel,BorderLayout.SOUTH);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = table.getSelectedRow();
                Object valorDaCelula;

                list.clear();
                for (int i = 0; i < colunas.length; i++) {
                    valorDaCelula = model.getValueAt(linha,i);
                    list.add(valorDaCelula);
                }
                new popUpAlterar(list);
            }
        });

        carregarDados();

        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void carregarDados(){
        try {
            Connection conn = conexaoBD.getConexao();
            if (conn != null){
                String sql = "SELECT * FROM Produtos";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                model.setRowCount(0);

                while (rs.next()){

                    int Id_Produtos = rs.getInt("Id_Produtos");
                    String nome_prod = rs.getString("Nome_Prod");
                    double precoProd = rs.getDouble("Preco_prod");
                    int Quantidade = rs.getInt("Quantidade");
                    int Id_Categoria = rs.getInt("Id_Categoria");
                    Date Data_Cadastro = rs.getDate("Data_Cadastro");

                    model.addRow(new Object[]{Id_Produtos,nome_prod, precoProd, Quantidade, Id_Categoria, Data_Cadastro});
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_voltar){
            this.dispose();
            new Estoque();
        }
    }

}
