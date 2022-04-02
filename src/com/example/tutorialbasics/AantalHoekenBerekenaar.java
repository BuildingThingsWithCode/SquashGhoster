package com.example.tutorialbasics;

public class AantalHoekenBerekenaar {

	private int frontPercentage;
	private int sidesPercentage;
	private int backPercentage;
	private int aantalGhostings;
	private int front, sides, back;
	/*opmerking deze klasse gaat op gezette tijden aanroepen
	 * worden door chooseView en moet zelf bijhouden welk
	 * de volgende view moet zijn uit de lijst die moet worden
	 * aangeboden. Deze klasse wordt enkel gebruikt wanneer de 
	 * percentage verschillen van 33 33 34 (in eender welke volgorde
	 * dat deze percentages voorkomen)
	 */
	public AantalHoekenBerekenaar(int fp, int sp, int bp, int ghostings){
	    this.frontPercentage= fp;
		this.sidesPercentage = sp;
		this.backPercentage = bp;
		this.aantalGhostings = ghostings;
	}

	
	/*
	 * geeft een array van integers terug, waarbij de int op index[0]
	 * =aan het aantal keren dat er moet gekozen worden tussen de voorste
	 * hoeken, index [1] het aantal keren dat er gekozen moet worden tussen
	 * de sides en index[2] het aantal keren dat er gekozen moet worden tussen
	 * de back corners.
	 */
	public int[] aantallen(){
		int changedView;
		//percentages verschillen alledrie
		if (frontPercentage != sidesPercentage && sidesPercentage != backPercentage && frontPercentage != backPercentage){
			//front en sides zijn de twee die maar 1 verschillen
			if (frontPercentage - sidesPercentage == 1 || frontPercentage - sidesPercentage == -1){
				changedView = backPercentage;
				if ((aantalGhostings*changedView)%100 >= 50){
					back = ((aantalGhostings*changedView)/100)+1;
					setFrontSides();
				}
				else {
					back = ((aantalGhostings*changedView)/100);
					setFrontSides();
				}
			}
			//front en back zijn de twee die maar 1 verschillen
			else if (frontPercentage - backPercentage == 1 || frontPercentage - backPercentage == -1){
				changedView = sidesPercentage;
				if ((aantalGhostings*changedView)%100 >= 50){
					sides = ((aantalGhostings*changedView)/100)+1;
					setFrontBack();
				}
				else {
					sides = ((aantalGhostings*changedView)/100);
					setFrontBack();
				}
			}
			else {
				//back and sides zijn de twee die maar 1 verschillen
				changedView = frontPercentage;
				if ((aantalGhostings*changedView)%100 >= 50){
					front = ((aantalGhostings*changedView)/100)+1;
					setSidesBack();
				}
				else {
					front = ((aantalGhostings*changedView)/100);
					setSidesBack();
				}
			}
		}
		//ze verschillen niet alledrie
		else {
			//front and sides idem
			if (frontPercentage == sidesPercentage){
				changedView = backPercentage;
				if ((aantalGhostings*changedView)%100 >= 50){
					back = ((aantalGhostings*changedView)/100)+1;
					setFrontSides();
				}
				else {
					back = ((aantalGhostings*changedView)/100);
					setFrontSides();
				}
			}
			// front and back idem
			else if (frontPercentage == backPercentage){
				changedView = sidesPercentage;
				if ((aantalGhostings*changedView)%100 >= 50){
					sides = ((aantalGhostings*changedView)/100)+1;
					setFrontBack();
				}
				else {
					sides = ((aantalGhostings*changedView)/100);
					setFrontBack();
				}
			}
			else {
				//sides and back idem
				changedView = frontPercentage;
				if ((aantalGhostings*changedView)%100 >= 50){
					front = ((aantalGhostings*changedView)/100)+1;
					setSidesBack();
				}
				else {
					front = ((aantalGhostings*changedView)/100);
					setSidesBack();
				}
			}
		}
	/*Deze int een attribuut van maken
	*zodat niet elke keer wanneer aantallen()
	*wordt aanroepen er een nieuwe int[] wordt
	*aangemaakt
	*/
		int [] aantallen = new int[3];
		aantallen[0]=front;
		aantallen[1]=sides;
		aantallen[2]=back;
		return aantallen;
	}


	/*
	 * Hulpmethode
	 */
	public void setFrontBack() {
		if ((aantalGhostings - sides)%2 == 0){
			front = (aantalGhostings - sides)/2;
			back = (aantalGhostings - sides)/2;
		} else {
			front = ((aantalGhostings - sides)/2)+1;
			back = ((aantalGhostings - sides)/2);
		}
	}
	
	/*
	 * Hulpmethode
	 */
	public void setSidesBack() {
		if ((aantalGhostings - front)%2 == 0){
			sides = (aantalGhostings - front)/2;
			back = (aantalGhostings - front)/2;
		} else {
			sides = ((aantalGhostings - front)/2)+1;
			back = ((aantalGhostings - front)/2);
		}
	}

	

	/*
	 * Hulpmethode
	 */
	public void setFrontSides() {
		if ((aantalGhostings - back)%2 == 0){
			front = (aantalGhostings - back)/2;
			sides = (aantalGhostings - back)/2;
		} else {
			front = ((aantalGhostings - back)/2)+1;
			sides = ((aantalGhostings - back)/2);
		}
	}
}
