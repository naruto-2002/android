package model;

public class MucBieuDoSSTC {
    private String time;
    private float income, expense, profit, loss;
    private boolean selected = false;

    public MucBieuDoSSTC(String time, float income, float expense, float profit, float loss, boolean selected) {
        this.time = time;
        this.income = income;
        this.expense = expense;
        this.profit = profit;
        this.loss = loss;
        this.selected = selected;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public float getExpense() {
        return expense;
    }

    public void setExpense(float expense) {
        this.expense = expense;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public float getLoss() {
        return loss;
    }

    public void setLoss(float loss) {
        this.loss = loss;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
