package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import src.Controleur;

public class PanelCreerRessource extends JPanel implements ActionListener
{
	private Controleur 			ctrl						;
	private PanelRessource 		panelR						;
	private PanelNotion 		panelN						;
	private JButton				btnAjouterR, btnAjouterN	;
	private JTextField 			textNumR, textNomR, textNomN;

	public PanelCreerRessource (Controleur ctrl)
	{
		this.ctrl = ctrl;
		this. setLayout (new BorderLayout(30,30));

		JPanel panelHaut 	= new JPanel(new GridLayout(1,2  ));
		JPanel panelMilieu 	= new JPanel(new GridLayout(1,2  ));
		JPanel panelBas 	= new JPanel(new GridLayout(1,2	 ));
		JPanel panelBasG	= new JPanel(new FlowLayout(5,5,5));
		JPanel panelBasD	= new JPanel(new FlowLayout(5,5,5));

		panelHaut.add(new JLabel("Choix de la Ressource"));
		panelHaut.add(new JLabel("Choix de la notion"	));

		this.add(panelHaut, BorderLayout.NORTH);

		this.panelR = new PanelRessource (ctrl, this);
		this.panelN = new PanelNotion 	 (ctrl);

		JScrollPane scrollPaneR = new JScrollPane (this.panelR);
		scrollPaneR.setVerticalScrollBarPolicy	(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneR.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER	 );

		JScrollPane scrollPaneN = new JScrollPane (this.panelN);
		scrollPaneN.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		panelMilieu.add (scrollPaneR);
		panelMilieu.add (scrollPaneN);

		this.add(panelMilieu, BorderLayout.CENTER);

		this.btnAjouterR = new JButton("ajouter");
		this.btnAjouterN = new JButton("ajouter");

		this.textNumR = new TextFieldPerso("Code"				);
		this.textNomR = new TextFieldPerso("nom de la ressource");
		this.textNomN = new TextFieldPerso("nom de la notion"	);

		this.textNumR.setColumns(5 );
		this.textNomR.setColumns(10);
		this.textNomN.setColumns(10);

		this.btnAjouterR.addActionListener(this);
		this.btnAjouterN.addActionListener(this);

		panelBasG.add(this.btnAjouterR);
		panelBasG.add(this.textNumR   );
		panelBasG.add(this.textNomR   );

		panelBasD.add(this.btnAjouterN);
		panelBasD.add(this.textNomN   );


		panelBas.add(panelBasG);
		panelBas.add(panelBasD);

		this.add(panelBas, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().equals(this.btnAjouterN))
		{
			if (this.panelR.getSelecionner() == null)
			{
				JOptionPane.showMessageDialog(null, "Veuillez selectionner une Ressource", "Attention", JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (this.textNomN.getText().equals(""))
				JOptionPane.showMessageDialog(null, "Rentrer tous les champs avant de créer une notion", "Attention", JOptionPane.WARNING_MESSAGE);
			else 
				if (!this.ctrl.ajouterNotion( this.panelN.getRessource(),this.textNomN.getText()))
					JOptionPane.showMessageDialog(null, "la notion existe deja", "Attention", JOptionPane.WARNING_MESSAGE);
		}

		if (e.getSource().equals(this.btnAjouterR))
		{
			if (this.textNumR.getText().equals("") || this.textNumR.getText().equals(""))
				JOptionPane.showMessageDialog(null, "Rentrer tous les champs avant de créer une ressource", "Attention", JOptionPane.WARNING_MESSAGE);
			else 
				if (!this.ctrl.ajouterRessource(this.textNumR.getText(), this.textNomR.getText()))
					JOptionPane.showMessageDialog(null, "le code de la ressource exite deja", "Attention", JOptionPane.WARNING_MESSAGE);	
		}
		this.panelR.Update();
		this.panelN.Update(panelN.getRessource());
		this.repaint();
	}

	public PanelRessource getPanelRessource	() {return this.panelR;}
	public PanelNotion    getPanelNotion	() {return this.panelN;}
}