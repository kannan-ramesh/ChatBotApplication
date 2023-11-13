package com.kannanrameshrk;


	public class SelectedOptions {
		private int step;
		private int choice;
		
		public SelectedOptions(int step, int choice) {
			
			this.setStep(step);
			this.setChoice(choice);
		}

		public int getStep() {
			return step;
		}

		public void setStep(int step) {
			this.step = step;
		}

		public int getChoice() {
			return choice;
		}

		public void setChoice(int choice) {
			this.choice = choice;
		}
		
	}

