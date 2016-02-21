
public class codeIso {
	
	public enum codeISO {
		// correction outil
		G41, G42,
		
		// Lubrification
		M07, M08,
		
		// Systeme de mesure
		// G70 en pouce ou G71 m�trique
		G70, G71,
		
		// Avance rapide
		G0,
		
		// Changement d'outil
		M06,
		
		// Interpolation circulaire 
		// G02 (sens horaire)
		// G03 (sens antihoraire)
		G02, G03,
		
		// Cycle de per�age simple
		G81,
		
		// Cycle de per�age lamage (avec temporisation)
		G82,
		
		// Cycle de per�age d�bourrage
		G83,
		
		// D�placements en coordonn�es 
		// G90 absolues
		// G91 relatives
		G90,
		
		//Avances 
		// G94 milim�tres/minute
		// G95 Avances en milim�tres/tour
		G94, G95,
		
		// Vitesse de coupe constante en m�tres/minute
		G96,
		
		// Vitesse de rotation constante en tours/minute
		G97,
		
		// Compensation de la longueur d'outil (fraisage uniquement)
		// G43 dans le sens +
		// G44 dans le sens -
		G43, G44,
		
		// Annulation de la longueur d'outil (fraisage uniquement)
		G49,
		
		// Arr�t rotation broche
		M05,
	}
	
	/*
	public enum code {
		
		private String name = "";
		
		code(String name) {
			this.name = name;
		}
	}*/
}
