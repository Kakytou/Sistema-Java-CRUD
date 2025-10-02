package Main.telas;

import Main.conexaoBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class popUpAlterar extends JFrame implements ActionListener {

    private ArrayList<Object> valorDaCelula;
    JLabel titulo, IdProd, nomeProd, precoProd, quantProd, categoriaProd, dataMod;
    JPanel panelTitulo, panelCentro,PanelSouth;
    JTextField IdTextField, nomeTextField, precoTextField, quantTextField, dataTextField;
    JButton btnAlterar;
    JComboBox categoriaOpcoes;
    GridBagConstraints c = new GridBagConstraints();

    public popUpAlterar(ArrayList<Object> valorDaCelula) {
        this.valorDaCelula = valorDaCelula;

        panelTitulo = new JPanel(new GridBagLayout());

        c.insets = new Insets(50, 0, 0, 0);
        titulo = new JLabel("Tela de Alteração");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitulo.add(titulo, c);

        panelCentro = new JPanel(new GridBagLayout());

        //id produto

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 10);
        IdProd = new JLabel("Id do Produto:");
        panelCentro.add(IdProd, c);

        c.gridx = 1;
        c.gridy = 0;
        IdTextField = new JTextField();
        IdTextField.setPreferredSize(new Dimension(100, 20));
        panelCentro.add(IdTextField, c);

        //nome produto

        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 0, 0, 10);
        nomeProd = new JLabel("Nome do Produto:");
        panelCentro.add(nomeProd, c);

        c.gridx = 1;
        c.gridy = 1;
        nomeTextField = new JTextField();
        nomeTextField.setPreferredSize(new Dimension(100, 20));
        panelCentro.add(nomeTextField, c);

        //Preço

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(10, 0, 0, 10);
        precoProd = new JLabel("Preço do Produto:");
        panelCentro.add(precoProd, c);

        c.gridx = 1;
        c.gridy = 2;
        precoTextField = new JTextField();
        precoTextField.setPreferredSize(new Dimension(100, 20));
        panelCentro.add(precoTextField, c);

        //Quantidade

        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(10, 0, 0, 10);
        quantProd = new JLabel("Quantidade do Produto:");
        panelCentro.add(quantProd, c);

        c.gridx = 1;
        c.gridy = 3;
        quantTextField = new JTextField();
        quantTextField.setPreferredSize(new Dimension(100, 20));
        panelCentro.add(quantTextField, c);

        //Id_Categoria

        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(10, 0, 0, 10);
        categoriaProd = new JLabel("Categoria do Produto:");
        panelCentro.add(categoriaProd, c);

        String[] opcoes = {"1-Periféricos", "2-Hardware", "3-Móveis", "4-Acessórios"};

        c.gridx = 1;
        c.gridy = 4;
        categoriaOpcoes = new JComboBox<>(opcoes);
        panelCentro.add(categoriaOpcoes, c);

        //DATA

        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(10, 0, 0, 10);
        dataMod = new JLabel("Data de Modificação:");
        panelCentro.add(dataMod, c);

        c.gridx = 1;
        c.gridy = 5;
        dataTextField = new JTextField();
        dataTextField.setPreferredSize(new Dimension(100, 20));
        panelCentro.add(dataTextField, c);

        btnAlterar = new JButton("Alterar");
        btnAlterar.addActionListener(this);
        PanelSouth = new JPanel();
        PanelSouth.add(btnAlterar);

        System.out.println(valorDaCelula);
        System.out.println(valorDaCelula.getFirst());
        System.out.println(nomeProd.getText());

        CamposComValor();

        add(panelTitulo, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(PanelSouth,BorderLayout.SOUTH);

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void CamposComValor() {

        IdTextField.setText(String.valueOf(valorDaCelula.getFirst()));
        nomeTextField.setText(String.valueOf(valorDaCelula.get(1)));
        precoTextField.setText(String.valueOf(valorDaCelula.get(2)));
        quantTextField.setText(String.valueOf(valorDaCelula.get(3)));
        dataTextField.setText(String.valueOf(valorDaCelula.get(5)));
        Object idCatObj = valorDaCelula.get(4);
        if (idCatObj != null) {
            String idCatStr = idCatObj.toString();
            for (int i = 0; i < categoriaOpcoes.getItemCount(); i++) {
                String item = (String) categoriaOpcoes.getItemAt(i);
                if (item.startsWith(idCatStr + "-")) {
                    categoriaOpcoes.setSelectedIndex(i);
                    break;
                }

            }
        }
    }

    public void AlterarDados() throws SQLException {

        Connection conn = conexaoBD.getConexao();

        String sql = "UPDATE Produtos SET Nome_Prod = ?, Preco_Prod = ?, Quantidade = ?, Id_Categoria = ?, Data_Cadastro = ? WHERE Id_Produtos = ?";
        try{
            if (conn != null){
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nomeTextField.getText());

                stmt.setDouble(2,Double.parseDouble(precoTextField.getText()));

                stmt.setInt(3,Integer.parseInt(quantTextField.getText()));

                String opcao = (String) categoriaOpcoes.getSelectedItem();
                int categoria = 0;
                if (opcao != null && opcao.contains("-")){
                    categoria = Integer.parseInt(opcao.split("-")[0]);
                }
                stmt.setInt(4,categoria);

                stmt.setDate(5,Date.valueOf(dataTextField.getText()));

                stmt.setInt(6, Integer.parseInt(IdTextField.getText()));

                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(this, "Alterado com sucesso!");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Nenhuma linha alterada. Verifique o id.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAlterar){
            try {
                AlterarDados();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}



