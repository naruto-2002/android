package model;

public class MucBieuDoXHTC {
    private String lable;
    private float percent, totalAmount;
    private int color;
    private boolean selected = false;

    public MucBieuDoXHTC(String lable, float percent, float totalAmount, int color, boolean selected) {
        this.lable = lable;
        this.percent = percent;
        this.totalAmount = totalAmount;
        this.color = color;
        this.selected = selected;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
