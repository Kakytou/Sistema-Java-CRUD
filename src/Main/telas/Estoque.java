package Main.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Estoque implements ActionListener {

    JPanel tituloPanel = new JPanel(new GridBagLayout());
    JPanel centro = new JPanel(new GridBagLayout());
    JLabel titulo = new JLabel("GERENCIADOR DE ESTOQUE");
    JButton cadastrar = new JButton("Cadastrar");
    JButton alterar = new JButton("Alterar");
    JButton excluir = new JButton("Excluir");
    JButton consultar = new JButton("Consultar");
    GridBagConstraints c = new GridBagConstraints();
    private JFrame tela;

    public Estoque() {

        tela = new JFrame("Gerenciador de Estoque");
        tela.setSize(600,500);

        titulo.setFont(new Font("Arial",Font.BOLD,24));
        c.gridy = 0;
        c.insets = new Insets(50,0,0,0);

        tituloPanel.add(titulo,c);

        cadastrar.setPreferredSize(new Dimension(200,50));
        cadastrar.setFocusable(false);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0,0,0,0);
        cadastrar.addActionListener(this);
        centro.add(cadastrar,c);

        alterar.setPreferredSize(new Dimension(200,50));
        alterar.setFocusable(false);
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(20,0,0,0);
        alterar.addActionListener(this);
        centro.add(alterar,c);

        consultar.setPreferredSize(new Dimension(200,50));
        consultar.setFocusable(false);
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(20,0,0,0);
        consultar.addActionListener(this);
        centro.add(consultar,c);

        excluir.setPreferredSize(new Dimension(200,50));
        excluir.setFocusable(false);
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(20,0,0,0);
        excluir.addActionListener(this);
        centro.add(excluir,c);

        tela.add(tituloPanel, BorderLayout.NORTH);
        tela.add(centro, BorderLayout.CENTER);
        tela.setLocationRelativeTo(null);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cadastrar){
            TelaCadastro telaCadastro = new TelaCadastro();
            tela.dispose();
            telaCadastro.setVisible(true);

        } else if (e.getSource() == alterar) {
            TelaAlterar telaAlterar = new TelaAlterar();
            tela.dispose();
            telaAlterar.setVisible(true);

        } else if (e.getSource() == consultar) {
            TelaConsulta consulta = new TelaConsulta();
            tela.dispose();
            consulta.setVisible(true);

        } else if (e.getSource() == excluir) {
            TelaExcluir telaExcluir = new TelaExcluir();
            tela.dispose();
            telaExcluir.setVisible(true);

        }
    }
}
