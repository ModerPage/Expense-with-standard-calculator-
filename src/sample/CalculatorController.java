package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;


public class CalculatorController {
    private StringBuilder nums;
    private StringBuilder upperString;
    private char sign = ' ' , lastSign = ' ';
    private boolean isFixed = false;
    private boolean isFirstOperation = true;
    private boolean gotResult = false;

    private OperationNums operationNums;

    @FXML
    private Button zero;
    @FXML
    private Button one;
    @FXML
    private Button two;
    @FXML
    private Button three;
    @FXML
    private Button four;
    @FXML
    private Button five;
    @FXML
    private Button six;
    @FXML
    private Button seven;
    @FXML
    private Button eight;
    @FXML
    private Button nine;
    @FXML
    private Button division;
    @FXML
    private Button difference;
    @FXML
    private Button multiply;
    @FXML
    private Button plus;
    @FXML
    private Button equal;
    @FXML
    private Button plusOrMinus;
    @FXML
    private Button remove;
    @FXML
    private Button clear;
    @FXML
    private Button dot;
    @FXML
    private Label screenDown;
    @FXML
    private Label screenUp;
    @FXML


    public void initialize() {
        nums = new StringBuilder();
        upperString = new StringBuilder();
        screenDown.setText("0");
        screenUp.setText("");
        screenDown.setAlignment(Pos.CENTER_RIGHT);
        screenUp.setAlignment(Pos.CENTER_RIGHT);


    }

    private String overloadUP(String label) {
        if(label.length() > 23) {
            String newLabel = label.substring((label.length() - 23));
            return newLabel;
        }
        return label;
    }

    private String overloadDown(String label) {
        if(label.length() > 15) {
            String newLabel = label.substring((label.length() - 15));
            return newLabel;
        }
        return label;
    }

    @FXML
    public void buttonOnAction(ActionEvent e) {
        if(gotResult) {
            isFirstOperation = true;
            isFixed = false;
            upperString.setLength(0);
            gotResult = false;
        }
        if(e.getSource().equals(one)) {
            nums.append(1);
            screenDown.setText(overloadDown(nums.toString()));
        }else if(e.getSource().equals(two)) {
            nums.append(2);
            screenDown.setText(overloadDown(nums.toString()));
        }else if(e.getSource().equals(three)) {
            nums.append(3);
            screenDown.setText(overloadDown(nums.toString()));
        }else if(e.getSource().equals(four)) {
            nums.append(4);
            screenDown.setText(overloadDown(nums.toString()));
        }else if(e.getSource().equals(five)) {
            nums.append(5);
            screenDown.setText(overloadDown(nums.toString()));
        }else if(e.getSource().equals(six)) {
            nums.append(6);
            screenDown.setText(overloadDown(nums.toString()));
        }else if(e.getSource().equals(seven)) {
            nums.append(7);
            screenDown.setText(overloadDown(nums.toString()));
        }else if(e.getSource().equals(eight)) {
            nums.append(8);
            screenDown.setText(overloadDown(nums.toString()));
        }else if(e.getSource().equals(nine)) {
            nums.append(9);
            screenDown.setText(overloadDown(nums.toString()));
        }else if(e.getSource().equals(zero)) {
            nums.append(0);
            screenDown.setText(overloadDown(nums.toString()));
        }else if(e.getSource().equals(clear)) {
            nums.setLength(0);
            screenUp.setText("");
            screenDown.setText("0");
            isFixed = false;
            isFirstOperation = true;
            upperString.setLength(0);
            sign = ' ';
            lastSign = ' ';
        }else if(e.getSource().equals(remove)) {
            if(nums.length()!= 0) {
                nums.setLength(nums.length()-1);
                if(nums.length() == 0) {
                    screenDown.setText("0");
                    isFixed = false;
                }
                else
                    screenDown.setText(overloadDown(nums.toString()));
                return;
            }
            screenDown.setText("0");
            isFixed = false;
        }else if(e.getSource().equals(dot)) {
            if(nums.length() == 0) {
                nums.append(0.);
                nums.setLength(nums.length() - 1);
                isFixed = true;
            } else {
                double d = Double.parseDouble(nums.toString());
                nums.setLength(0);
                nums.append(d);
                nums.setLength(nums.length() - 1);
                isFixed = true;
            }
            screenDown.setText(overloadDown(nums.toString()));
        } else if (e.getSource().equals(plusOrMinus)) {
            if(!isFixed) {
                int i = Integer.parseInt(nums.toString()) * (-1);
                nums.setLength(0);
                nums.append(i);
                screenDown.setText(overloadDown(nums.toString()));
            } else {
                double d = Double.parseDouble(nums.toString()) * (-1);
                nums.setLength(0);
                nums.append(d);
                screenDown.setText(overloadDown(nums.toString()));
            }
        }
    }

    @FXML
    public void operationButtons(ActionEvent e) {
        gotResult = false;
        if (e.getSource().equals(difference)) {
            sign = '-';
            if (isFixed) {
                if (nums.length() == 0 && Double.parseDouble(screenDown.getText()) == (Double) operationNums.getResult()) {
                    lastSign = sign;
                    upperString.setLength(upperString.length() - 2);
                    upperString.append(" ");
                    upperString.append(sign);
                    screenUp.setText(overloadUP(upperString.toString()));
                    return;
                }

                if (isFirstOperation) {
                    operationNums = new OperationNums(Double.parseDouble(nums.toString()));
                    isFirstOperation = false;
                }
                else {
                    operationNums.completePrevious(lastSign,Double.parseDouble(nums.toString()));
                }
                upperString.append(" ");
                upperString.append(nums);
                upperString.append(" ");
                upperString.append(sign);
                screenUp.setText(overloadUP(upperString.toString()));
                screenDown.setText(overloadDown("" + (double)operationNums.getResult()));
                nums.setLength(0);
            } else {
                if (nums.length() == 0 && Integer.parseInt(screenDown.getText()) == (Integer) operationNums.getResult()) {
                    lastSign = sign;
                    upperString.setLength(upperString.length() - 2);
                    upperString.append(" ");
                    upperString.append(sign);
                    screenUp.setText(overloadUP(upperString.toString()));
                    return;
                }
                if (isFirstOperation) {
                    operationNums = new OperationNums(Integer.parseInt(nums.toString()));
                    isFirstOperation = false;
                } else {
                    operationNums.completePrevious(lastSign,Integer.parseInt(nums.toString()));
                }
                upperString.append(" ");
                upperString.append(nums);
                upperString.append(" ");
                upperString.append(sign);
                screenUp.setText(overloadUP(upperString.toString()));
                screenDown.setText(overloadDown("" + operationNums.getResult()));
                nums.setLength(0);
            }
            lastSign = sign;
        } else if (e.getSource().equals(plus)) {
            sign = '+';
            if (isFixed) {
                if (nums.length() == 0 && Double.parseDouble(screenDown.getText()) == (Double) operationNums.getResult()) {
                    lastSign = sign;
                    upperString.setLength(upperString.length() - 2);
                    upperString.append(" ");
                    upperString.append(sign);
                    screenUp.setText(overloadUP(upperString.toString()));
                    return;
                }

                if (isFirstOperation) {
                    operationNums = new OperationNums(Double.parseDouble(nums.toString()));
                    isFirstOperation = false;
                } else {
                    operationNums.completePrevious(lastSign,Double.parseDouble(nums.toString()));
                }
                upperString.append(" ");
                upperString.append(nums);
                upperString.append(" ");
                upperString.append(sign);
                screenUp.setText(overloadUP(upperString.toString()));
                screenDown.setText(overloadDown("" + (double)operationNums.getResult()));
                nums.setLength(0);
            } else {
                if (nums.length() == 0 && Integer.parseInt(screenDown.getText()) == (Integer) operationNums.getResult()) {
                    lastSign = sign;
                    upperString.setLength(upperString.length() - 2);
                    upperString.append(" ");
                    upperString.append(sign);
                    screenUp.setText(overloadUP(upperString.toString()));
                    return;
                }

                if (isFirstOperation) {
                    operationNums = new OperationNums(Integer.parseInt(nums.toString()));
                    isFirstOperation = false;
                } else {
                    operationNums.completePrevious(lastSign,Integer.parseInt(nums.toString()));
                }
                upperString.append(" ");
                upperString.append(nums);
                upperString.append(" ");
                upperString.append(sign);
                screenUp.setText(overloadUP(upperString.toString()));
                screenDown.setText(overloadDown("" + operationNums.getResult()));
                nums.setLength(0);
            }
            lastSign = sign;
        } else if (e.getSource().equals(multiply)) {
            sign = '*';
            if (isFixed) {
                if (nums.length() == 0 && Double.parseDouble(screenDown.getText()) == (Double) operationNums.getResult()) {
                    lastSign = sign;
                    upperString.setLength(upperString.length() - 2);
                    upperString.append(" ");
                    upperString.append(sign);
                    screenUp.setText(overloadUP(upperString.toString()));
                    return;
                }
                if (isFirstOperation) {
                    operationNums = new OperationNums(Double.parseDouble(nums.toString()));
                    isFirstOperation = false;
                } else {
                    operationNums.completePrevious(lastSign,Double.parseDouble(nums.toString()));
                }
                upperString.append(" ");
                upperString.append(nums);
                upperString.append(" ");
                upperString.append(sign);
                screenUp.setText(overloadUP(upperString.toString()));
                String s = String.format("%.1f",(double)operationNums.getResult());
                screenDown.setText(overloadDown(s));
                nums.setLength(0);
            } else {
                if (nums.length() == 0 && Integer.parseInt(screenDown.getText()) == (Integer) operationNums.getResult()) {
                    lastSign = sign;
                    upperString.setLength(upperString.length() - 2);
                    upperString.append(" ");
                    upperString.append(sign);
                    screenUp.setText(overloadUP(upperString.toString()));
                    return;
                }

                if (isFirstOperation) {
                    operationNums = new OperationNums(Integer.parseInt(nums.toString()));
                    isFirstOperation = false;
                } else {
                    operationNums.completePrevious(lastSign,Integer.parseInt(nums.toString()));
                }

                upperString.append(" ");
                upperString.append(nums);
                upperString.append(" ");
                upperString.append(sign);
                screenUp.setText(overloadUP(upperString.toString()));
                screenDown.setText(overloadDown("" + operationNums.getResult()));
                nums.setLength(0);
            }
            lastSign = sign;
        } else if (e.getSource().equals(division)) {
            sign = '/';
            if (isFixed) {
                if (nums.length() == 0 && Double.parseDouble(screenDown.getText()) == (Double) operationNums.getResult()) {
                    lastSign = sign;
                    upperString.setLength(upperString.length() - 2);
                    upperString.append(" ");
                    upperString.append(sign);
                    screenUp.setText(overloadUP(upperString.toString()));
                    return;
                }

                if (isFirstOperation) {
                    operationNums = new OperationNums(Double.parseDouble(nums.toString()));
                    isFirstOperation = false;
                } else {
                    operationNums.completePrevious(lastSign,Double.parseDouble(nums.toString()));
                }

                upperString.append(" ");
                upperString.append(nums);
                upperString.append(" ");
                upperString.append(sign);
                screenUp.setText(overloadUP(upperString.toString()));
                String s = String.format("%.1f",(double)operationNums.getResult());
                screenDown.setText(overloadDown(s));
                nums.setLength(0);
            } else {
                if (nums.length() == 0 && Integer.parseInt(screenDown.getText()) == (Integer) operationNums.getResult()) {
                    lastSign = sign;
                    upperString.setLength(upperString.length() - 2);
                    upperString.append(" ");
                    upperString.append(sign);
                    screenUp.setText(overloadUP(upperString.toString()));
                    return;
                }

                if (isFirstOperation) {
                    operationNums = new OperationNums(Integer.parseInt(nums.toString()));
                    isFirstOperation = false;
                } else {
                    operationNums.completePrevious(lastSign,Integer.parseInt(nums.toString()));
                }

                upperString.append(" ");
                upperString.append(nums);
                upperString.append(" ");
                upperString.append(sign);
                screenUp.setText(overloadUP(upperString.toString()));
                screenDown.setText(overloadDown("" + operationNums.getResult()));
                nums.setLength(0);
            }
            lastSign = sign;
        } else if (e.getSource().equals(equal)) {
            gotResult = true;
            if (isFixed) {
                if(nums.length() != 0)
                    operationNums.completePrevious(lastSign,Double.parseDouble(nums.toString()));
                screenDown.setText(overloadDown("" + (double)operationNums.getResult()));
                screenUp.setText("");
                nums.setLength(0);
                sign = ' ';
                upperString.setLength(0);
                upperString.append(operationNums.getResult());
                upperString.append(" ");
                upperString.append(lastSign);
                lastSign = ' ';
            } else {
                if(nums.length() != 0)
                    operationNums.completePrevious(lastSign,Integer.parseInt(nums.toString()));
                screenDown.setText(overloadDown("" + operationNums.getResult()));
                screenUp.setText("");
                nums.setLength(0);
                sign = ' ';
                upperString.setLength(0);
                upperString.append(operationNums.getResult());
                upperString.append(" ");
                upperString.append(lastSign);
                lastSign = ' ';
            }
        }
    }


    @FXML
    public void ctrlC() {
        if(isFixed) {
            double d = (Double) operationNums.getResult();
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable transferable = new StringSelection(String.valueOf(d));
            clipboard.setContents(transferable, null);
        } else {
            int i = (Integer) operationNums.getResult();
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable transferable = new StringSelection(String.valueOf(i));
            clipboard.setContents(transferable, null);
        }

    }
}
