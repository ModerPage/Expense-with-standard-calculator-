package sample.ExpenseData;

import javafx.beans.property.SimpleDoubleProperty;

import java.time.LocalDate;

public class Expense {
    private SimpleDoubleProperty food ;
    private SimpleDoubleProperty other;
    private SimpleDoubleProperty income;
    private SimpleDoubleProperty total;
    private LocalDate date;

    public Expense() {
        food = new SimpleDoubleProperty(0.0);
        other = new SimpleDoubleProperty(0.0);
        income = new SimpleDoubleProperty(0.0);
        total = new SimpleDoubleProperty(0.0);
        date = LocalDate.now() ;
    }

    public Expense(Double food, Double other, Double income, Double total, LocalDate date) {
        this.food = new SimpleDoubleProperty(food);
        this.other = new SimpleDoubleProperty(other);
        this.income = new SimpleDoubleProperty(income);
        this.total = new SimpleDoubleProperty(total);
        this.date = date;
    }

    public double getFood() {
        return food.get();
    }

    public SimpleDoubleProperty foodProperty() {
        return food;
    }

    public void setFood(double food) {
        this.food.set(food);
    }

    public double getOther() {
        return other.get();
    }

    public SimpleDoubleProperty otherProperty() {
        return other;
    }

    public void setOther(double other) {
        this.other.set(other);
    }

    public double getIncome() {
        return income.get();
    }

    public SimpleDoubleProperty incomeProperty() {
        return income;
    }

    public void setIncome(double income) {
        this.income.set(income);
    }

    public double getTotal() {
        return total.get();
    }

    public SimpleDoubleProperty totalProperty() {
        return total;
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
