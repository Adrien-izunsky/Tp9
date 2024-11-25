package bowling;

public class Tour {
	private int[] lancers;
	private int numeroLancer;
	private boolean strike;

	public Tour() {
		lancers = new int[2];  // Un tour peut avoir jusqu'à 2 lancers (sauf si strike)
		numeroLancer = 0;
		strike = false;
	}

	public boolean enregistreLancer(int quillesTombées) {
		if (strike) {
			// Si c'est un strike, on n'a pas besoin de faire un autre lancer
			return false;  // Tour terminé après un strike
		}

		// Enregistrer les quilles tombées
		lancers[numeroLancer] = quillesTombées;
		numeroLancer++;

		// Si c'est un strike, on termine immédiatement le tour
		if (quillesTombées == 10 && numeroLancer == 1) {
			strike = true;
			return false;  // Pas de deuxième lancer nécessaire
		}

		return numeroLancer < 2;  // Retourne true si le tour continue
	}

	public boolean isStrike() {
		return strike;
	}

	public int getNumeroLancer() {
		return numeroLancer;
	}
}
