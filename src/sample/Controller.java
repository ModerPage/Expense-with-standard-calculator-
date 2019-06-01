package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.ExpenseData.Expense;
import sample.ExpenseData.ExpenseData;

import java.io.IOException;
import java.util.Optional;


public class Controller {
    private ExpenseData data;

    @FXML
    private TableView<Expense> expenseTable;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void initialize() {
        data = new ExpenseData();
        data.loadExpenses();
        expenseTable.setItems(data.getExpenses());
    }

    @FXML
    public void addExpense() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add a new Expense");
        dialog.setHeaderText("Create a new expense using dialog");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dialogController.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("dialog can't be loaded.");
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            DialogController dialogController = fxmlLoader.getController();
            Expense newExpense = dialogController.processResult();
            expenseTable.getSelectionModel().select(newExpense);
            data.addExpense(newExpense);
            data.saveExpenses();
        }
    }

    @FXML
    public void editExpense() {
        Expense selectedExpense = expenseTable.getSelectionModel().getSelectedItem();

        if(selectedExpense == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Expense Selected");
            alert.setHeaderText(null);
            alert.setContentText("Select the expense that you want to edit");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit Expense");
        dialog.setHeaderText("Edit the expense using dialog");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dialogController.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        DialogController dialogController = fxmlLoader.getController();
        dialogController.openEditExpense(selectedExpense);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            dialogController.updateEditExpense(selectedExpense);
            data.saveExpenses();
        }
    }

    @FXML
    public void deleteExpense() {
        Expense selectedExpense = expenseTable.getSelectionModel().getSelectedItem();

        if(selectedExpense == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Expense Selected");
            alert.setHeaderText(null);
            alert.setContentText("Select expense that you want to edit");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Content");
        alert.setHeaderText("Date of the expense: " + selectedExpense.getDate());
        alert.setContentText("Are you sure to delete the expense ?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            try {
                data.removeExpense(selectedExpense);
                data.saveExpenses();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void showCalculator() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Calculator");
        dialog.setHeaderText("Calculate expenses");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("calculator.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Calculator dialog can't be loaded");
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }
}
