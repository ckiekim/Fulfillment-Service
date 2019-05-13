package closing;

public class ClosingDTO {
	private int income;
	private int logisExpense;
	private int purchaseExpense;
	private int grossMargin;
	
	public ClosingDTO() {
	}
	public ClosingDTO(int income, int logisExpense, int purchaseExpense, int grossMargin) {
		this.income = income;
		this.logisExpense = logisExpense;
		this.purchaseExpense = purchaseExpense;
		this.grossMargin = grossMargin;
	}
	public int getIncome() {
		return income;
	}
	public void setIncome(int income) {
		this.income = income;
	}
	public int getLogisExpense() {
		return logisExpense;
	}
	public void setLogisExpense(int logisExpense) {
		this.logisExpense = logisExpense;
	}
	public int getPurchaseExpense() {
		return purchaseExpense;
	}
	public void setPurchaseExpense(int purchaseExpense) {
		this.purchaseExpense = purchaseExpense;
	}
	public int getGrossMargin() {
		return grossMargin;
	}
	public void setGrossMargin(int grossMargin) {
		this.grossMargin = grossMargin;
	}
	@Override
	public String toString() {
		return "ClosingDTO [income=" + income + ", logisExpense=" + logisExpense + ", purchaseExpense="
				+ purchaseExpense + ", grossMargin=" + grossMargin + "]";
	}
}
