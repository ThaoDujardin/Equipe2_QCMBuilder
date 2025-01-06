package src.ihm.question;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import src.Controleur;

public class FrameModifierQuestion extends JFrame implements ActionListener
{
	private Controleur ctrl;
	private PanelModifierQuestion panelM;

	private JMenuItem importerImage;
	private FileHandler fileHandler;

	public FrameModifierQuestion(Controleur ctrl, String nomQuestion, String notion, String ressource)
	{
		this.ctrl = ctrl;
		this.fileHandler = new FileHandler("fichier_question");

		this.setTitle("Modifier de question");
		this.setSize(730, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setBackground(Color.LIGHT_GRAY);

		JPanel titrePanel = new JPanel();
		titrePanel.setLayout(new BorderLayout());
		titrePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JLabel titreLabel = new JLabel("Mofification de question", JLabel.CENTER);
		titreLabel.setFont(new Font("Arial", Font.BOLD, 20));
		titrePanel.add(titreLabel, BorderLayout.CENTER);
		titrePanel.setBackground(Color.lightGray);
		this.add(titrePanel, BorderLayout.NORTH);

		JMenuBar menubMaBarre = new JMenuBar();
		JMenu menuImport = new JMenu("Importer");

		this.importerImage = new JMenuItem("Importer image");

		menuImport.add(this.importerImage);

		menubMaBarre.add(menuImport);

		this.setJMenuBar(menubMaBarre);


		this.panelM = new PanelModifierQuestion(this.ctrl, this, nomQuestion, notion, ressource);

		this.add(this.panelM);

		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{

		if (e.getSource().equals(this.importerImage))
		{
			try
			{
				File selectedFile = fileHandler.chooseFile();
				fileHandler.handleFile(selectedFile);
			} catch (IOException ex)
			{
				System.out.println("Erreur lors du traitement du fichier : " + ex.getMessage());
			}
		}
	}

	public PanelModifierQuestion getPanelModifierQuestion()
	{
		return this.panelM;
	}
}