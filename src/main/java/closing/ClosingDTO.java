package closing;

public class ClosingDTO {
	private int income;
	private int logisExpense;
	private int purchaseExpense;
	private int grossMargin;
	private String incomeComma;
	private String logisExpenseComma;
	private String purchaseExpenseComma;
	private String grossMarginComma;
	
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
	public String getIncomeComma() {
		return incomeComma;
	}
	public void setIncomeComma(String incomeComma) {
		this.incomeComma = incomeComma;
	}
	public String getLogisExpenseComma() {
		return logisExpenseComma;
	}
	public void setLogisExpenseComma(String logisExpenseComma) {
		this.logisExpenseComma = logisExpenseComma;
	}
	public String getPurchaseExpenseComma() {
		return purchaseExpenseComma;
	}
	public void setPurchaseExpenseComma(String purchaseExpenseComma) {
		this.purchaseExpenseComma = purchaseExpenseComma;
	}
	public String getGrossMarginComma() {
		return grossMarginComma;
	}
	public void setGrossMarginComma(String grossMarginComma) {
		this.grossMarginComma = grossMarginComma;
	}
	@Override
	public String toString() {
		return "ClosingDTO [incomeComma=" + incomeComma + ", logisExpenseComma=" + logisExpenseComma
				+ ", purchaseExpenseComma=" + purchaseExpenseComma + ", grossMarginComma=" + grossMarginComma + "]";
	}
}
