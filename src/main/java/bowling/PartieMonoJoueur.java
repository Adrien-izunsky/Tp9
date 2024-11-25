package bowling;

import java.util.ArrayList;
import java.util.List;

public class PartieMonoJoueur {
	private List<Integer> lancers = new ArrayList<>();
	private int score = 0;
	private int numeroTourCourant = 0;
	private int numeroProchainLancer = 1;

	// Enregistre un lancer dans la partie
	public void enregistreLancer(int quilles) {
		if (estTerminee()) {
			throw new IllegalStateException("La partie est terminée");
		}

		// Ajouter le lancer à la liste des lancers
		lancers.add(quilles);

		// Si c'est un strike (10 quilles)
		if (quilles == 10 && numeroTourCourant < 9) {
			// Si c'est un strike, on ajoute les 2 prochains lancers au score
			score += 10 + obtenirBonus(2); // Strike, ajoute le bonus des 2 prochains lancers
			numeroTourCourant++;          // Passer au tour suivant
			numeroProchainLancer = 1;     // Reset du nombre de lancers pour le tour suivant
		} else if (numeroProchainLancer == 2 && lancers.get(lancers.size() - 2) + quilles == 10) {
			// Gérer le cas des spares
			score += 10 + obtenirBonus(1); // Spare, ajoute le bonus du prochain lancer
			numeroTourCourant++;
			numeroProchainLancer = 1; // Reset pour le tour suivant
		} else {
			// Sinon, c'est un lancer normal
			if (numeroProchainLancer == 2) {
				numeroTourCourant++;
				numeroProchainLancer = 1;
			} else {
				numeroProchainLancer = 2; // Passe au second lancer du tour
			}
		}
	}

	// Calcul du score total
	public int score() {
		int totalScore = 0;
		int lanceIndex = 0;
		for (int tour = 0; tour < 10; tour++) {
			if (lancers.get(lanceIndex) == 10) { // Strike
				totalScore += 10 + lancers.get(lanceIndex + 1) + lancers.get(lanceIndex + 2);
				lanceIndex++; // Passe au tour suivant
			} else if (lancers.get(lanceIndex) + lancers.get(lanceIndex + 1) == 10) { // Spare
				totalScore += 10 + lancers.get(lanceIndex + 2);
				lanceIndex += 2; // Passe au tour suivant
			} else { // Normal
				totalScore += lancers.get(lanceIndex) + lancers.get(lanceIndex + 1);
				lanceIndex += 2;
			}
		}
		return totalScore;
	}

	// Vérifie si la partie est terminée
	public boolean estTerminee() {
		return numeroTourCourant >= 10;
	}

	// Retourne le numéro du tour courant
	public int numeroTourCourant() {
		return numeroTourCourant + 1; // Les tours sont numérotés à partir de 1
	}

	// Retourne le numéro du prochain lancer
	public int numeroProchainLancer() {
		return numeroProchainLancer;
	}

	// Obtient les N prochains lancers comme bonus (pour strike ou spare)
	private int obtenirBonus(int nombreLancers) {
		int bonus = 0;
		int index = lancers.size() - 1;
		for (int i = 0; i < nombreLancers; i++) {
			if (index + 1 < lancers.size()) {
				bonus += lancers.get(index + 1);
				index++;
			}
		}
		return bonus;
	}

	// Pour réinitialiser les données pour un nouveau test (utilisé dans les tests)
	public void reset() {
		lancers.clear();
		score = 0;
		numeroTourCourant = 0;
		numeroProchainLancer = 1;
	}
}
