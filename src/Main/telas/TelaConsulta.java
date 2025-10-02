package Main.telas;

import Main.conexaoBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TelaConsulta extends JFrame implements ActionListener {

    JTable table;
    DefaultTableModel model;
    JButton btn_voltar;

    public TelaConsulta(){

        btn_voltar = new JButton("Voltar");
        btn_voltar.addActionListener(this);

        String[] lista = {"Nome", "Preço", "Quantidade", "Data Cadastro"};
        model = new DefaultTableModel(lista, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);
        add(btn_voltar,BorderLayout.SOUTH);

        carregarDados();

        this.setSize(500,400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
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

                    String Nome_Prod = rs.getString("Nome_Prod");
                    double preco = rs.getDouble("Preco_prod");
                    int quantidade = rs.getInt("Quantidade");
                    Date data = rs.getDate("Data_Cadastro");

                    model.addRow(new Object[]{Nome_Prod,preco,quantidade,data});
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
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
