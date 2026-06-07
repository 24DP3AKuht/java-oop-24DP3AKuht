package rvt;

public class IoweYou {

    private String name;
    private double amount;

    public IoweYou() {
        this.name = "";
        this.amount = 0;
    }

    public void setSum(String toWhom, double amount) {
        this.name = toWhom;
        this.amount = amount;
    }

    public double howMuchDoIOweTo(String toWhom) {
        if (this.name.equals(toWhom)) {
            return this.amount;
        }
        return 0;
    }
}
