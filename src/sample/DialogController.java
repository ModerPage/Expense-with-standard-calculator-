package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.ExpenseData.Expense;

import java.time.LocalDate;

public class DialogController {

    @FXML
    private TextField foodField;

    @FXML
    private TextField otherField;

    @FXML
    private TextField incomeField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField totalField;

    @FXML
    private Button calc;

    public void initialize() {
        calc.setDisable(true);
    }

    public Expense processResult() {
        String food = foodField.getText().trim();
        String other = otherField.getText().trim();
        String income = incomeField.getText().trim();
        LocalDate date = datePicker.getValue();
        String total = totalField.getText().trim();

        Expense expense = new Expense(Double.parseDouble(food),Double.parseDouble(other),Double.parseDouble(income),Double.parseDouble(total),date);
        return expense;

    }

    public void openEditExpense(Expense expense) {
        foodField.setText("" + expense.getFood());
        otherField.setText("" + expense.getOther());
        incomeField.setText("" + expense.getIncome());
        datePicker.setValue(expense.getDate());
        //totalField.setText("" + expense.getTotal());
    }

    public void updateEditExpense(Expense expense) {
        expense.setFood(Double.parseDouble(foodField.getText().trim()));
        expense.setOther(Double.parseDouble(otherField.getText().trim()));
        expense.setIncome(Double.parseDouble(incomeField.getText().trim()));
        expense.setDate(datePicker.getValue());
        expense.setTotal(Double.parseDouble(totalField.getText().trim()));
    }

    @FXML
    private void getTotal() {
        double food = Double.parseDouble(foodField.getText().trim());
        double other = Double.parseDouble(otherField.getText().trim());
        totalField.setText("" + (food + other));
    }

    @FXML
    public void handleKeyReleased() {
        String food = foodField.getText();
        String other = otherField.getText();

        boolean disableButton = (food.isEmpty() || food.trim().isEmpty()) || (other.isEmpty() || other.trim().isEmpty());
        calc.setDisable(disableButton);
    }
}
