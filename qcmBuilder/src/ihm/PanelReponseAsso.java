package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PanelReponseAsso extends JPanel
{
	private PanelCreerQuestionAsso 	panelQ;
	private ArrayList<JPanel>		reponsesPossibles;
	private PanelReponseGaucheAsso 	panelGauche;
	private PanelReponseDroiteAsso 	panelDroite;

	private static PanelReponseGaucheAsso 	lierGauche;
	private static PanelReponseDroiteAsso	lierDroite;


	public PanelReponseAsso (PanelCreerQuestionAsso panelQ)
	{
		this.panelQ = panelQ;
		this.setLayout(new GridLayout(1,2));

		//Initialisation
		this.panelGauche = new PanelReponseGaucheAsso(this);
		this.panelDroite = new PanelReponseDroiteAsso(this);

		//Insertion
		this.add (this.panelGauche 	);
		this.add (this.panelDroite	);

		this.setVisible(true);

	}

	public void lier(PanelReponseGaucheAsso gauche)
	{
		
		PanelReponseAsso.lierGauche=gauche;

		String str1, str2;
		if (PanelReponseAsso.lierGauche==null)
			str1=null;
		else 
			str1 = PanelReponseAsso.lierGauche.getNom();

		if (PanelReponseAsso.lierDroite==null)
			str2=null;
		else 
			str2 = PanelReponseAsso.lierDroite.getNom();
		
		System.out.println("etape 1 : "+str1 +" : "+str2);

		if (PanelReponseAsso.lierDroite!=null)
		{
			PanelReponseAsso.lierGauche.ajouterBonneRep(PanelReponseAsso.lierDroite);
			PanelReponseAsso.lierDroite=null;
			PanelReponseAsso.lierGauche=null;
		}
		System.out.println("etape 2 : "+str1 +" : "+str2);
	}

	public void lier(PanelReponseDroiteAsso droite)
	{
		PanelReponseAsso.lierDroite=droite;

		String str1, str2;
		if (PanelReponseAsso.lierGauche==null)
			str1=null;
		else 
			str1 = PanelReponseAsso.lierGauche.getNom();

		if (PanelReponseAsso.lierDroite==null)
			str2=null;
		else 
			str2 = this.lierDroite.getNom();
		
		System.out.println("etape 1 : "+str1 +" : "+str2);

		if (PanelReponseAsso.lierGauche!=null)
		{	
			System.out.println("liaison");
			PanelReponseAsso.lierGauche.ajouterBonneRep(PanelReponseAsso.lierDroite);
			PanelReponseAsso.lierDroite=null;
			PanelReponseAsso.lierGauche=null;
		}

		System.out.println("etape 2 : "+str1 +" : "+str2);
	}

	public void supprimer(PanelReponseGaucheAsso p)
	{
		if (this.panelDroite==null)
			this.panelQ.supprimer(this);
		else 
		{
			this.removeAll();
			this.add(new JPanel());
			this.add(this.panelDroite );
			this.panelGauche=null;
			this.revalidate();
			this.repaint();
		}
	}

	public void supprimer(PanelReponseDroiteAsso p)
	{
		if (this.panelGauche==null)
			this.panelQ.supprimer(this);
		else 
		{
			this.removeAll();
			this.panelGauche.supprimerLiaison(p);
			this.add(this.panelGauche);
			this.panelDroite=null;
			this.add(new JPanel());
			this.revalidate();
			this.repaint();
		}
		
	}

	

	public PanelCreerQuestionAsso getPanelDeBase() {return this.panelQ;}

	public String getString()
	{
		String str = "";
		
		
		return str;
	}

	public PanelReponseGaucheAsso getPanelGauche (){return this.panelGauche;}
	public PanelReponseDroiteAsso getPanelDroit  (){return this.panelDroite;}
}