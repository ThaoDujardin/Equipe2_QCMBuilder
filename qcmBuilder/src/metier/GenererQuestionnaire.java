package src.metier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import src.TypeQuestionnaire;
import src.metier.question.Association;
import src.metier.question.Elimination;
import src.metier.question.QCM;
import src.metier.question.Question;
import src.metier.reponse.ReponseAssociation;
import src.metier.reponse.ReponseElimination;
import src.metier.reponse.ReponseQCM;



public class GenererQuestionnaire 
{
	public static void main(String[] args)
	{
		List<TypeQuestionnaire> lstTypeQuestionnaires = List.of(
			new TypeQuestionnaire("Notion 1", 0, 1, 0, 1),
			new TypeQuestionnaire("Notion 2", 1, 0, 0, 0),
			new TypeQuestionnaire("Notion 3", 0, 0, 1, 0)
		);

		Ressource r = new Ressource("R1.01", "Apagnan");
		Notion n1 = new Notion("Notion 1", r);
		Notion n2 = new Notion("Notion 2", r);
		Notion n3 = new Notion("Notion 3", r);

		ReponseAssociation ra1 = new ReponseAssociation("GaucheA",null, true );
		ReponseAssociation ra2 = new ReponseAssociation("GaucheB",null,  true);
		ReponseAssociation ra3 = new ReponseAssociation("GaucheC",null, true);
		ReponseAssociation ra4 = new ReponseAssociation("DroiteA",ra1, false);
		ReponseAssociation ra5 = new ReponseAssociation("DroiteB",ra2, false);
		ReponseAssociation ra6 = new ReponseAssociation("DroiteC",ra3, false);

		ra1.setReponseAssocie(ra3);
		ra2.setReponseAssocie(ra4);
		ra3.setReponseAssocie(ra5);

		List<ReponseAssociation> lstReponseAsso = List.of(
			ra1, ra2, ra3, ra4, ra5, ra6
		);

		List<ReponseElimination> lstReponseEliminations = List.of(
			new ReponseElimination("Vrai","La bonne"        ,0,   0),
			new ReponseElimination("Faux","Pas ça"          ,0,   0),
			new ReponseElimination("Faux","non plus"        ,2,   6),
			new ReponseElimination("Faux","il me semble pas",1,   7)
		);


		List<Question> lstQuestions = List.of(
			new Association(n1, "Question 1", 50, 5, 2, lstReponseAsso, "Eh oui, c'est ça la réponse !"),
			new Elimination(n2, "Question 2", 50, 5, 1, lstReponseEliminations, 2, "Eh oui, c'est ça la réponse !"),
			new QCM        (n3, "Question 3", 50, 5, 3, List.of(new ReponseQCM("Bonne réponse 1", "Vrai"), new ReponseQCM("Bonne réponse 2", "Vrai"), new ReponseQCM("Pas bonne réponse 3", "Faux")), "Eh oui, c'est ça la réponse !"), 
			new QCM        (n1, "Question 4", 50, 5, 4, List.of(new ReponseQCM("Bonne réponse", "Vrai"), new ReponseQCM("poulet", "Faux")), "Eh oui, c'est ça la réponse !")
		);
		GenererQuestionnaire gq = new GenererQuestionnaire("R1.01_Apagnan", true,"ouioui",lstTypeQuestionnaires, lstQuestions);
	}

	private String nomRessource;
	private String script;
	private String scriptIndex;
	private String nomRepertoire;
	private List<TypeQuestionnaire> lstTypeQuestionnaire;
	private List<Question> lstQuestions;

	public GenererQuestionnaire (String nomRessource, boolean chrono, String nomQuestionnaire, List<TypeQuestionnaire> lstTypeQuestionnaires, List<Question> lstQuestions)
	{
		this.nomRessource = nomRessource;
		this.script = "";
		this.scriptIndex = "";
		this.nomRepertoire = nomQuestionnaire;
		this.lstTypeQuestionnaire = lstTypeQuestionnaires;
		this.lstQuestions = lstQuestions;

		try {
			String tempNomQuestionnaire = nomQuestionnaire;
			boolean exist = Files.exists(Paths.get("../" + tempNomQuestionnaire));
			int i=1;
			while(exist)
			{
				tempNomQuestionnaire = nomQuestionnaire;
				tempNomQuestionnaire += "("+ i++ +")";
				exist = Files.exists(Paths.get("../" + tempNomQuestionnaire));
			}
			

			Files.createDirectories(Paths.get("../"+tempNomQuestionnaire                       ));
			Files.createDirectories(Paths.get("../"+tempNomQuestionnaire+"/css"                   ));
			Files.createDirectories(Paths.get("../"+tempNomQuestionnaire+"/fichier_complementaire"));
			Files.createDirectories(Paths.get("../"+tempNomQuestionnaire+"/pages"                 ));
			Files.createDirectories(Paths.get("../"+tempNomQuestionnaire+"/script"                ));

			Files.copy(Paths.get("../data/web/css/index.css"       ),
					   Paths.get("../"+tempNomQuestionnaire+"/css/index.css"  ),
					   StandardCopyOption.REPLACE_EXISTING);

			Files.copy(Paths.get("../data/web/css/qcm_multiple.css"        ),
					   Paths.get("../"+tempNomQuestionnaire+"/css/qcm_multiple.css"   ),
					   StandardCopyOption.REPLACE_EXISTING);

			Files.copy(Paths.get("../data/web/css/qcm_unique.css"     ),
					   Paths.get("../"+tempNomQuestionnaire+"/css/qcm_unique.css"),
					   StandardCopyOption.REPLACE_EXISTING);

			Files.copy(Paths.get("../data/web/css/question_association.css"     ),
					   Paths.get("../"+tempNomQuestionnaire+"/css/question_association.css"),
					   StandardCopyOption.REPLACE_EXISTING);

			Files.copy(Paths.get("../data/web/css/question_elimination.css"         ),
					   Paths.get("../"+tempNomQuestionnaire+"/css/question_elimination.css"),
					   StandardCopyOption.REPLACE_EXISTING);

			Files.copy(Paths.get("../data/web/script/popup.js"       ),
					   Paths.get("../"+tempNomQuestionnaire+"/script/popup.js"  ),
					   StandardCopyOption.REPLACE_EXISTING);

			if (chrono) {
				this.script      = "<script src=\"../script/indexTimed.js\"></script>";
				this.scriptIndex = "<script src=\"./script/indexTimed.js\"></script>";
				Files.copy(Paths.get("../data/web/script/indexTimed.js"       ),
						   Paths.get("../"+tempNomQuestionnaire+"/script/indexTimed.js"  ),
						   StandardCopyOption.REPLACE_EXISTING);
			} else {
				this.script = "<script src=\"../script/indexNoTimed.js\"></script>";
				this.scriptIndex = "<script src=\"./script/indexNoTimed.js\"></script>";
				Files.copy(Paths.get("../data/web/script/indexNoTimed.js"       ),
						   Paths.get("../"+tempNomQuestionnaire+"/script/indexNoTimed.js"  ),
						   StandardCopyOption.REPLACE_EXISTING);
			}

			Files.write(Paths.get("../"+tempNomQuestionnaire+"/index.html"  ), getIndexHtml().getBytes());
			Files.write(Paths.get("../"+tempNomQuestionnaire+"/fin.html"    ), getFinHtml().getBytes());
			

			for (Question q : lstQuestions)
			{
				Files.write(Paths.get("../"+tempNomQuestionnaire+"/pages/question"+(lstQuestions.indexOf(q)+1)+".html"), getQuestionHtml(q).getBytes());
			}

			Files.write(Paths.get("../" + tempNomQuestionnaire + "/script/data.js"), getDataJs().getBytes());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getQuestionHeader(Question q)
	{
		String difficulte = "";

		switch (q.getDifficulte()) {
			case 1:
				difficulte = "Très Facile";
				break;
			case 2:
				difficulte = "Facile";
				break;
			case 3:
				difficulte = "Moyenne";
				break;
			case 4:
				difficulte = "Difficile";
				break;
		}

		String css  = "";
		String type = "";
		String id= "";
		if (q instanceof QCM)
		{
			QCM qcm = (QCM) q;
			if (qcm.estUnique())
			{
				type = "Unique";
				css = "qcm_unique.css";
				id = "question-unique";
			}
			else
			{
				type = "Multiple";
				css = "qcm_multiple.css";
				id = "question-multiple";
			}
		}
		if (q instanceof Elimination)
		{
			type = "Elimination";
			css = "question_elimination.css";
			id = "eliminate-question";
		}
		if (q instanceof Association)
		{
			type = "Association";
			css = "question_association.css";
			id = "association-question";
		}


		String sRet = String.format("""
		<!DOCTYPE html>
		<html lang="fr">
			<head>
				<meta charset="UTF-8">
				<meta name="viewport" content="width=device-width, initial-scale=1.0">
				<link rel="stylesheet" href="../css/%s">
				<title>Question %s</title>
			</head>
			<!-- Ici value correpond au numéro de la question -->
			<!-- Ici point correpond au nombre de point de la question -->
			<body value="%d" point="%5.3f" >
				<!-- Précise que c'est une question de type %s -->
				<main class="container question %s">
					<!-- Informations de la question-->
					<section class="informations">
						<p class="question-number">Question numéro : <span class="question-number-data">%d</span></p>
						<p class="notion"         >Notion :          <span class="notion-data"         >%s</span></p>
						<p class="difficulty"     >Difficulté :      <span class="difficulty-data"     >%s</span></p>
						<p class="estimated-time" >Temps estimé :    <span class="estimated-time-data" >%dsec</span></p>
						<p class="points"         >Points :          <span class="points"              >%5.3f</span></p>
					</section>

					<!-- Barre de progression des questions -->
					<section class="progress-bar">
						<div class="progress-bar-fill">
							<p class="progress-percentage"></p>
						</div>
					</section>

					<!-- Compteur de temps -->
					<section class="countdown">
						<p class="countdown-title">Temps restant :</p>
						<p class="countdown-data">%d</p>
					</section>

					<figure>
						<img src="%s" alt="%s" class="question-image">
					</figure>

						""",css, type, lstQuestions.indexOf(q)+1, q.getNbPoint(), type, id, lstQuestions.indexOf(q)+1, q.getNotions().getNom(), difficulte, q.getTimer(), q.getNbPoint(), q.getTimer(), q.getUrlImage(), q.getUrlImage());

		return sRet;
	}

	private String getQuestionFooter(Question q)
	{
		String sRet = """

		\t\t\t<!-- Navigation entre les questions -->
					<nav class="nav">
						<button type="button" class="nav-button previous-button">Précédent</button>
						<button type="button" class="nav-button validate-button">Valider</button>
						<button type="button" class="nav-button feedback-button">Feedback</button>""";

				if (q instanceof Association) {
					sRet += """
					<button type="button" class="nav-button delete-line-button">Supprimer les lignes</button>
					""";

				}
				
				sRet += String.format("""
								<button type="button" class="nav-button next-button">Suivant</button>
							</nav>
						</main>

						<!-- Ligne d'association -->
						<div class="line-container"></div>

						<!-- PopUp pour le feedback -->
						<section class="popup">
							<div class="popup-content">
								<button type="button" class="close-button">Fermer</button>
								<p class="feedback-title">Réponse</p>

								<p class="feedback-text">%s</p>
								<p class="feedback-point"></p>
							</div>
						</section>

						<!-- Footer -->
						<footer>
							<p>&copy; 2024 Groupe 2. Tous droits réservés.</p>
						</footer>

						<!-- Script -->
						<script src="../script/popup.js"></script>
						<script src="../script/data.js"></script>
						%s
					</body>
				</html>
				""",q.getExplication(),this.script);

		return sRet;
	}

	private String getQuestionHtml(Question q)
	{
		String sRet = getQuestionHeader(q);
		if (q instanceof QCM)
		{
			QCM qcm = (QCM) q;
			qcm.melanger();
			List<ReponseQCM> lstReponses = qcm.getLstReponses();
			sRet += String.format("""
					<!-- Question et réponses -->
					<article class="question">
						<p class="question-text">%s</p>
						<section class="answers">
		""", qcm.getText());
			if (qcm.estUnique())
			{
				
				for (ReponseQCM r : lstReponses)
				{
					sRet += String.format("""
							<div class="answer">
								<input type="radio" name="answer" id="answer%d" value="answer%d">
								<label for="answer%d">%s</label>
							</div>
		""", lstReponses.indexOf(r)+1, lstReponses.indexOf(r)+1, lstReponses.indexOf(r)+1, r.getText());
				}

				
			}
			else
			{
				for (ReponseQCM r : lstReponses)
				{
					sRet += String.format("""
							<div class="answer">
								<input type="checkbox" name="answer" id="answer%d" value="answer%d">
								<label for="answer%d">%s</label>
							</div>
		""", lstReponses.indexOf(r)+1, lstReponses.indexOf(r)+1, lstReponses.indexOf(r)+1, r.getText());
				}
			}

			sRet += """
						</section>
					</article>
		""";
		}

		if (q instanceof Elimination)
		{
			Elimination elim = (Elimination) q;
			elim.melanger();
			List<ReponseElimination> lstReponses = elim.getLstReponses();
			sRet += String.format("""
					<!-- Question et réponses -->
					<article class="question">
						<p class="question-text">%s</p>
						<section class="answers">
		""", elim.getText());

			for (ReponseElimination r : lstReponses)
				{
					sRet += String.format("""
							<div class="answer">
								<input type="radio" name="answer" id="answer%d" value="answer%d">
								<label for="answer%d">%s</label>
							</div>
		""", lstReponses.indexOf(r)+1, lstReponses.indexOf(r)+1, lstReponses.indexOf(r)+1, r.getText());
				}

			sRet += String.format("""
						</section>
					</article>

					<!-- Bouton indice -->
					<section class="hint">
						<button type="button" class="hint-button">Indice</button>
						<div class="hint-count">
							<p>Indice : <span class="hint-current-data">0</span>/<span class="hint-total-data">%d</span></p>
						</div>
					</section>
		""", elim.getNbIndice());


		}

		if (q instanceof Association)
		{
			Association asso = (Association) q;
			
			for (int i = 0; i<50; i++)
				asso.melanger();

			List<ReponseAssociation> lstReponses = asso.getLstReponses();
			sRet += String.format("""
					<!-- Question et réponses -->
					<section class="question">
						<p class="question-text">%s</p>
						<div class="association-container">
							<article class="association-column left-column">
		""", asso.getText());

			for (ReponseAssociation r : lstReponses)
			{
				if (r.estAGauche())
				{
					if (!r.getUrlImage().equals(""))
					{
						sRet += String.format("""
								<p class="association-item" data-id="%d"><img src="%s" alt="%s"></p>
		""", lstReponses.indexOf(r), r.getUrlImage(), r.getText());
					}
					else
					{
						sRet += String.format("""
								<p class="association-item" data-id="%d">%s</p>
		""", lstReponses.indexOf(r), r.getText());
					}
				}
			}

			sRet += """
							</article>
							<article class="association-column right-column"> 
		""";

			for (ReponseAssociation r : lstReponses)
			{
				if (!r.estAGauche())
				{
					sRet += String.format("""
								<p class="association-item" data-id="%d">%s</p>
		""", lstReponses.indexOf(r), r.getText());
				}
			}

								
					
			sRet += """
							</article>
						</div>
					</section>
		""";
		}

		sRet += getQuestionFooter(q);

		return sRet;
	}
		

	private String getDataJs()
	{
		String sRet = String.format("""
				// ...existing code...
				// Nombre total de questions dans le questionnaire
				const totalQuestions = %d;
				
				// Index de la question actuelle
				let currentQuestion = 0;
				
				// Identifiant de l'intervalle de temps
				let intervalId = 0;
				
				// Nombre d'indices utilisés
				let usedHint  = 0;
				
				// Nombre de points perdus en utilisant des indices
				let hintPoint = 0;
				
				// Tableau des éléments associés
				let associateItems = [];
				
				// Points accumulés par l'élève
				let point = 0;
				
				// Somme des points de chaque question
				let totalPoint = 0;
				
				// Tableau pour stocker les réponses de chaques questions
				const correctAnswers = [

				// Exemple question à choix multiple
				//["Bonne réponse 1", "Bonne réponse 2", "Bonne réponse 3"],

				// Exemple question à élimination
				//[["Bonne réponse"],[
				//	[["indice1"], point-perdu],
				//	[["indice2"], point-perdu]]
				//],

				// Exemple question à association
				//[
				//	["Gauche1","Sa Réponse"],
				//	["Gauche2","Sa Réponse"],
				//	["Gauche3","Sa Réponse"],
				//	["Gauche4","Sa Réponse"]
				//],

				// Exemple question à choix unique
				//["Bonne réponse 1"],


				""", lstQuestions.size());

				for (Question q : lstQuestions)
				{
					if (q instanceof QCM) 
					{
						QCM qcm = (QCM) q;
						List<ReponseQCM> lstReponses = qcm.getLstReponses();
						int i = 0;
						sRet += "// Question " + (int)(lstQuestions.indexOf(q)+1) + " de type QCM\n";
						sRet += "\t[";
						for (ReponseQCM r : lstReponses)
						{

							if (r.estVrai())
							{
								sRet += String.format("""
										"answer%d" """, lstReponses.indexOf(r)+1); 
								i++;
								if (i != qcm.getNbReponseVrai())
									sRet += ", ";

							}
						}
						if (lstQuestions.indexOf(q) != lstQuestions.size() - 1)
							sRet += "],\n\n\t";
						else
							sRet += "]\n";
					}

					if (q instanceof Elimination) 
					{
						Elimination e = (Elimination) q;
						List<ReponseElimination> lstReponses = e.getLstReponses();
						sRet += "// Question " + (int)(lstQuestions.indexOf(q)+1) + " de type Elimination\n";
						sRet += "\t[";
						for (ReponseElimination r : lstReponses)
						{
							System.out.println(r.estVrai());
							if (r.estVrai())
							{
								sRet += String.format("""
										["answer%d"], """, lstReponses.indexOf(r)+1);

								sRet += "[\n\t";
								
								List<ReponseElimination> lstIndices = new ArrayList<>();
								for (ReponseElimination re : lstReponses)
								{
									if (re.getNbPointPerdu() != 0)
									{
										lstIndices.add(re);
									}
								}

								lstIndices.sort((re1, re2) -> Double.compare(re1.getOrdreIndice(), re2.getOrdreIndice()));

								for (ReponseElimination re : lstIndices)
								{
									sRet += String.format(java.util.Locale.US, """
											\t["answer%d",%3.1f] """, lstReponses.indexOf(re)+1, re.getNbPointPerdu());
									if (lstIndices.indexOf(re) != lstIndices.size() - 1)
										sRet += ",\n\t";
									else
										sRet += "]\n\t";
								}
							}
						}
						if (lstQuestions.indexOf(q) != lstQuestions.size() - 1)
							sRet += "],\n\n\t";
						else
							sRet += "]\n";
					}

					if (q instanceof Association) 
					{
						Association a = (Association) q;
						List<ReponseAssociation> lstReponses = a.getLstReponses();
						sRet += "// Question " + (int)(lstQuestions.indexOf(q)+1) + " de type Association\n";
						sRet += "\t[\n\t";
						for (int i = 0; i < lstReponses.size(); i++)
						{
							ReponseAssociation r = lstReponses.get(i);
							if (r.estAGauche())
							{
								sRet += String.format("""
										\t["%d","%d"]""", lstReponses.indexOf(r), lstReponses.indexOf(r.getReponseAssocie()));
								if ( i < lstReponses.size() ) {
									sRet += ", ";
								}
								sRet += "\n\t";
							}
						}
						if (lstQuestions.indexOf(q) != lstQuestions.size())
							sRet += "],\n\n\t";
						else
							sRet += "]\n";
					}
				
				}

				sRet += "];";

				return sRet;
	}

	private String getIndexHtml()
	{
		int nbQuestion = 0;
		int nbTf       = 0;
		int nbF        = 0;
		int nbM        = 0;
		int nbD        = 0;

		int tempsEstime = 0;

		for (Question q : lstQuestions)
		{
			tempsEstime += q.getTimer();
		}

		String sRet = String.format("""
				<!DOCTYPE html>
				<html lang="fr">
					<head>
						<meta charset="UTF-8">
						<meta name="viewport" content="width=device-width, initial-scale=1.0">
						<link rel="stylesheet" href="./css/index.css">
						<title>%s</title>
					</head>
					<!-- Précise que c'est l'index -->
					<body class="index" value="0">

						<!-- Titre du questionnaire -->
						<h1 class="evaluation-title">%s</h1>
						<h2 class="app-name">QCMBuilder</h2>

						<!-- Informations du quesionnaire -->
						<section class="informations">
							<p class="estimated-time">Temps estimé : <span class="estimated-time-data">%s sec</span></p>
							<p class="resource">Ressources concernée : <span class="resource-data">%s</span></p>
							<p class="chrono">Chronomètre : <span class="chrono-data"></span></p>
							<!-- Tableau du nombre de question par difficulté et notions -->
							<table class="difficulty-table">
								<thead>
									<tr>
										<!-- L'entête du tableau -->
										<th>Notions / Difficultés</th>
										<th class="very-easy">Très facile</th>
										<th class="easy">Facile</th>
										<th class="medium">Moyenne</th>
										<th class="hard">Difficile</th>
										<th>Total</th>
									</tr>
								</thead>
								<tbody> <!-- Les données en fonction des notions -->
				""", nomRepertoire,nomRepertoire, tempsEstime, nomRessource);

				for(TypeQuestionnaire tq : lstTypeQuestionnaire)
				{
					String notion = tq.getNotion();
					int nbTfNotion = tq.getNbTf();
					int nbFNotion = tq.getNbF();
					int nbMNotion = tq.getNbM();
					int nbDNotion = tq.getNbD();
					int total = nbTfNotion + nbFNotion + nbMNotion + nbDNotion;
					sRet += String.format("""
										<tr>
											<td>%s</td>
											<td class="difficulty-item-data very-easy">%d</td>
											<td class="difficulty-item-data easy">%d</td>
											<td class="difficulty-item-data medium">%d</td>
											<td class="difficulty-item-data hard">%d</td>
											<td class="notion-total">%d</td>
										</tr>
					""", notion, nbTfNotion, nbFNotion, nbMNotion, nbDNotion, total);

					nbQuestion += total;
					nbTf       += nbTfNotion;
					nbF        += nbFNotion;
					nbM        += nbMNotion;
					nbD        += nbDNotion;
				}
				sRet += String.format("""
									<!-- Les totaux en fonction des difficultés et global -->
									<tr>
										<td>Total</td>
										<td class="difficulty-total">%d</td>
										<td class="difficulty-total">%d</td>
										<td class="difficulty-total">%d</td>
										<td class="difficulty-total">%d</td>
										<td class="grand-total">%d</td>
									</tr>
								</tbody>
							</table>
							<!-- Boutton pour commencer le questionnaire -->
							<button type="button" class="start-button">Commencer</button>
						</section>
						<!-- Footer -->
						<footer>
							<p>&copy; 2024 Groupe 2. Tous droits réservés.</p>
						</footer>
						<!-- Script -->
						<script src="./script/data.js"></script>
						%s
					</body>
				</html>
				""", nbTf, nbF, nbM, nbD, nbQuestion, this.scriptIndex);

		return sRet;
	}

	private String getFinHtml()
	{
		int nbQuestion = 0;
		int nbTf       = 0;
		int nbF        = 0;
		int nbM        = 0;
		int nbD        = 0;

		int tempsEstime = 0;

		for (Question q : lstQuestions)
		{
			tempsEstime += q.getTimer();
		}

		String sRet = String.format("""
				<!DOCTYPE html>
				<html lang="fr">
					<head>
						<meta charset="UTF-8">
						<meta name="viewport" content="width=device-width, initial-scale=1.0">
						<link rel="stylesheet" href="./css/index.css">
						<title>Fin du Questionnaire</title>
					</head>
					<body>
						<!--Titre de fin-->
						<h1 class="evaluation-title">Fin du questionnaire</h1>
						
						<!-- Informations du questionnaire -->
						<section class="informations">
							<p class="estimated-time">Temps estimé : <span class="estimated-time-data">%d sec</span></p>
							<p class="resource">Ressources concernée : <span class="resource-data">%s</span></p>
						
							<!-- Tableau du nombre de question par difficulté et notions -->
							<table class="difficulty-table">
								<thead>
									<tr>
										<!-- L'entête du tableau -->
										<th>Notions / Difficultés</th>
										<th class="very-easy">Très facile</th>
										<th class="easy">Facile</th>
										<th class="medium">Moyenne</th>
										<th class="hard">Difficile</th>
										<th>Total</th>
									</tr>
								</thead>
								<tbody> <!-- Les données en fonction des notions -->
				""", tempsEstime, nomRessource);

				for(TypeQuestionnaire tq : lstTypeQuestionnaire)
				{
					String notion = tq.getNotion();
					int nbTfNotion = tq.getNbTf();
					int nbFNotion = tq.getNbF();
					int nbMNotion = tq.getNbM();
					int nbDNotion = tq.getNbD();
					int total = nbTfNotion + nbFNotion + nbMNotion + nbDNotion;
					sRet += String.format("""
										<tr>
											<td>%s</td>
											<td class="difficulty-item-data very-easy">%d</td>
											<td class="difficulty-item-data easy">%d</td>
											<td class="difficulty-item-data medium">%d</td>
											<td class="difficulty-item-data hard">%d</td>
											<td class="notion-total">%d</td>
										</tr>
					""", notion, nbTfNotion, nbFNotion, nbMNotion, nbDNotion, total);

					nbQuestion += total;
					nbTf       += nbTfNotion;
					nbF        += nbFNotion;
					nbM        += nbMNotion;
					nbD        += nbDNotion;
				}
				sRet += String.format("""
									<!-- Les totaux en fonction des difficultés et global -->
									<tr>
										<td>Total</td>
										<td class="difficulty-total">%d</td>
										<td class="difficulty-total">%d</td>
										<td class="difficulty-total">%d</td>
										<td class="difficulty-total">%d</td>
										<td class="grand-total">%d</td>
									</tr>
								</tbody>
							</table>
						
							<!-- Score -->
							<p class="score">Votre score : <span class="score-data"></span></p>
						
							<!-- Bouton recommencer -->
							<button type="button" class="restart-button" onclick="window.location.href='index.html'">Recommencer</button>
						
							<!-- Footer -->
							<footer>
								<p>&copy; 2024 Groupe 2. Tous droits réservés.</p>
							</footer>
							
							<!-- Script -->
							<script src="./script/data.js"></script>
							%s
						</section>
					</body>
				</html>
				""", nbTf, nbF, nbM, nbD, nbQuestion, this.scriptIndex);

		return sRet;
	}



	
}
