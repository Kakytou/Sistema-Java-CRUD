package Main.telas;

import Main.conexaoBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TelaCadastro extends JFrame implements ActionListener {

    JPanel panelTitulo,centro, panelSouth;
    JButton btn_cadastrar;
    JButton btn_voltar;
    JLabel titulo,textoId,nomeProd,precoProd,quantProd,categoriaProd,dataCadastro;
    JTextField idTextField,nomeTextField,precoTextField,quantTextField,dataTextField;
    JComboBox categoriaOpcoes;
    GridBagConstraints c = new GridBagConstraints();

    public TelaCadastro(){
        btn_voltar = new JButton("Voltar");
        btn_cadastrar = new JButton("Cadastrar");
        textoId = new JLabel("Id: ");
        panelTitulo = new JPanel(new GridBagLayout());
        centro = new JPanel(new GridBagLayout());
        titulo = new JLabel("Tela de Cadastro");
        idTextField = new JTextField();
        panelSouth = new JPanel();

        btn_cadastrar.addActionListener(this);
        btn_voltar.addActionListener(this);
        panelSouth.add(btn_voltar);
        panelSouth.add(btn_cadastrar);
        //id produto

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 10);
        textoId = new JLabel("Id do Produto:");
        centro.add(textoId, c);

        c.gridx = 1;
        c.gridy = 0;
        idTextField = new JTextField();
        idTextField.setPreferredSize(new Dimension(100, 20));
        centro.add(idTextField, c);

        //nome produto

        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 0, 0, 10);
        nomeProd = new JLabel("Nome do Produto:");
        centro.add(nomeProd, c);

        c.gridx = 1;
        c.gridy = 1;
        nomeTextField = new JTextField();
        nomeTextField.setPreferredSize(new Dimension(100, 20));
        centro.add(nomeTextField, c);

        //Preço

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(10, 0, 0, 10);
        precoProd = new JLabel("Preço do Produto:");
        centro.add(precoProd, c);

        c.gridx = 1;
        c.gridy = 2;
        precoTextField = new JTextField();
        precoTextField.setPreferredSize(new Dimension(100, 20));
        centro.add(precoTextField, c);

        //Quantidade

        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(10, 0, 0, 10);
        quantProd = new JLabel("Quantidade do Produto:");
        centro.add(quantProd, c);

        c.gridx = 1;
        c.gridy = 3;
        quantTextField = new JTextField();
        quantTextField.setPreferredSize(new Dimension(100, 20));
        centro.add(quantTextField, c);

        //Id_Categoria

        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(10, 0, 0, 10);
        categoriaProd = new JLabel("Categoria do Produto:");
        centro.add(categoriaProd, c);

        String[] opcoes = {"1-Periféricos", "2-Hardware", "3-Móveis", "4-Acessórios"};

        c.gridx = 1;
        c.gridy = 4;
        categoriaOpcoes = new JComboBox<>(opcoes);
        centro.add(categoriaOpcoes, c);

        //DATA

        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(10, 0, 0, 10);
        dataCadastro = new JLabel("Data de Cadastro:");
        centro.add(dataCadastro, c);

        c.gridx = 1;
        c.gridy = 5;
        dataTextField = new JTextField();
        dataTextField.setPreferredSize(new Dimension(100, 20));
        centro.add(dataTextField, c);

        titulo.setFont(new Font("Arial",Font.BOLD,18));
        c.insets = new Insets(50,0,0,0);
        panelTitulo.add(titulo, c);

        this.add(panelTitulo, BorderLayout.NORTH);
        this.add(centro, BorderLayout.CENTER);
        this.add(panelSouth,BorderLayout.SOUTH);

        this.setSize(500,400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public void CadastrarDados(){

        try{
            Connection conn = conexaoBD.getConexao();

            if (conn != null){
                String sql = "INSERT INTO Produtos VALUES (?,?,?,?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setInt(1, Integer.parseInt(idTextField.getText()));
                stmt.setString(2,nomeTextField.getText());
                stmt.setDouble(3, Double.parseDouble(precoTextField.getText()));
                stmt.setInt(4, Integer.parseInt(quantTextField.getText()));

                String opcao = (String) categoriaOpcoes.getSelectedItem();
                int categoria = 0;
                if (opcao != null && opcao.contains("-")){
                    categoria = Integer.parseInt(opcao.split("-")[0]);
                }

                stmt.setInt(5,categoria);
                stmt.setDate(6, Date.valueOf(dataTextField.getText()));

                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0){
                    JOptionPane.showMessageDialog(null,"Alterado com sucesso!");
                    this.dispose();
                    new Estoque();
                }else {
                    JOptionPane.showMessageDialog(null,"Dado fornecido errado.");
                }

            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062){
                JOptionPane.showMessageDialog(null,"Esse ID já existe no banco de dados!");
            }else {
                JOptionPane.showMessageDialog(null,"Erro no Banco: " + e.getMessage());
            }

        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_cadastrar){
            CadastrarDados();
        } else if (e.getSource() == btn_voltar) {
            this.dispose();
            new Estoque();
        }
    }
}
