package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import src.Controleur;
import src.ihm.question.FrameCreerQuestion;

public class FrameMenu extends JFrame implements ActionListener
{
	private JButton creerQuestion, genererQuestionnaire, creerResNot, modifierUneQuestion;
	private JLabel title;
	private Controleur ctrl;
	private JPanel panel;

	public FrameMenu(Controleur ctrl) 
	{
		this.ctrl = ctrl;

		this.setLayout          (new BorderLayout()                                );
		this.panel = new JPanel (new GridLayout(4, 1, 10, 10)                      );
		this.panel.setBorder    (BorderFactory.createEmptyBorder(20, 20, 20, 20)   );
		this.panel.setBackground(Color.LIGHT_GRAY                                  );

		this.setTitle                (    "Menu principal");
		this.setSize                 (      400,       400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo   (null                );

		this.title                = new JLabel ("QCMBuilder"            );
		this.creerQuestion        = new JButton("Créer Question"        );
		this.genererQuestionnaire = new JButton("Générer Questionnaire" );
		this.creerResNot          = new JButton("Créer Ressource/Notion");
		this.modifierUneQuestion  = new JButton("Editer les questions"  );

		this.title               .setFont(new Font("Arial", Font.BOLD , 20));
		this.creerQuestion       .setFont(new Font("Arial", Font.PLAIN, 14));
		this.genererQuestionnaire.setFont(new Font("Arial", Font.PLAIN, 14));
		this.creerResNot         .setFont(new Font("Arial", Font.PLAIN, 14));
		this.modifierUneQuestion .setFont(new Font("Arial", Font.PLAIN, 14));

		this.creerQuestion.setOpaque           (false                      );
		this.creerQuestion.setContentAreaFilled(false                      );
		this.creerQuestion.setBorder           (new LineBorder(Color.BLACK));
		this.creerQuestion.setBackground(Color.WHITE);
		this.creerQuestion.setFocusPainted     (false                      );
		this.creerQuestion.setForeground       (Color.BLACK                );

		this.genererQuestionnaire.setOpaque           (false                      );
		this.genererQuestionnaire.setContentAreaFilled(false                      );
		this.genererQuestionnaire.setBorder           (new LineBorder(Color.BLACK));
		this.genererQuestionnaire.setBackground(Color.WHITE);
		this.genererQuestionnaire.setFocusPainted     (false                      );
		this.genererQuestionnaire.setForeground       (Color.BLACK                );

		this.creerResNot.setOpaque           (false                      );
		this.creerResNot.setContentAreaFilled(false                      );
		this.creerResNot.setBorder           (new LineBorder(Color.BLACK));
		this.creerResNot.setBackground(Color.WHITE);
		this.creerResNot.setFocusPainted     (false                      );
		this.creerResNot.setForeground       (Color.BLACK                );

		this.modifierUneQuestion.setOpaque           (false                      );
		this.modifierUneQuestion.setContentAreaFilled(false                      );
		this.modifierUneQuestion.setBorder           (new LineBorder(Color.BLACK));
		this.modifierUneQuestion.setBackground(Color.WHITE);
		this.modifierUneQuestion.setFocusPainted     (false                      );
		this.modifierUneQuestion.setForeground       (Color.BLACK                );

		this.creerQuestion       .addActionListener(this);
		this.genererQuestionnaire.addActionListener(this);
		this.creerResNot         .addActionListener(this);
		this.modifierUneQuestion .addActionListener(this);

		this.ajouterPassageSouris(creerQuestion       );
		this.ajouterPassageSouris(genererQuestionnaire);
		this.ajouterPassageSouris(creerResNot         );
		this.ajouterPassageSouris(modifierUneQuestion );

		
		this.panel.add(this.creerResNot         );
		this.panel.add(this.creerQuestion       );
		this.panel.add(this.genererQuestionnaire);
		this.panel.add(this.modifierUneQuestion	);

		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.LIGHT_GRAY);
		northPanel.add(this.title);


		this.add (northPanel, BorderLayout.NORTH);
		this.add (this.panel, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.creerQuestion))
		{
			new FrameCreerQuestion(this.ctrl);
			this.dispose();
		}

		if (e.getSource().equals(this.genererQuestionnaire))
		{
			new FrameGenererQuestionnaire(this.ctrl);
			this.dispose();
		}

		if (e.getSource().equals(this.creerResNot))
		{
			new FrameCreerRessource(this.ctrl);
			this.dispose();
		}

		if (e.getSource().equals(this.modifierUneQuestion))
		{
			new FrameListerQuestion(this.ctrl);
			this.dispose();
		}
	}

	private void ajouterPassageSouris(JButton button)
	{
		button.setOpaque(true);
		button.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				button.setBackground(button.getBackground().darker());
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				button.setBackground(Color.WHITE);
			}
		});
	}
}
