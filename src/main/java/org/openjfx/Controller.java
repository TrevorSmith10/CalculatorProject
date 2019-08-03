package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.math.BigDecimal;

public class Controller {


    @FXML
    private TextField textFieldDisplay;
    @FXML
    private Button sevenButton;
    @FXML
    private Button zeroButton;
    @FXML
    private Button oneButton;
    @FXML
    private Button twoButton;
    @FXML
    private Button threeButton;
    @FXML
    private Button fourButton;
    @FXML
    private Button fiveButton;
    @FXML
    private Button sixButton;
    @FXML
    private Button eightButton;
    @FXML
    private Button nineButton;
    @FXML
    private Button plusButton;
    @FXML
    private Button minusButton;
    @FXML
    private Button multiplyButton;
    @FXML
    private Button divideButton;
    @FXML
    private Button decimalButton;
    @FXML
    private Button equalsButton;

    private BigDecimal left;
    private String selectedOperator;
    private boolean numberInputting;

    public Controller() {
        this.left = BigDecimal.ZERO;
        this.selectedOperator = "";
        this.numberInputting = false;
    }

    @FXML
    public void handleClick(ActionEvent evt) {
        Button button = (Button) evt.getSource();
        String buttonText = button.getText();
        //Clears out calculator if user tried to previously divide by zero,
        if (textFieldDisplay.getText().contains("CANNOT DIVIDE BY ZERO")) {
            clearCalculator();
        }

        //checking to see if user clicked 0-9 and adding it to the display
        switch (buttonText) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case ".":
                appendIntToDisplay(buttonText);
                break;

            //Removes the last character from the string displayed on the calculator
            case "BACK":
                backOneChar();
                break;

            //IF user selects the CE button the calculator will be cleared out
            case "CE":
                clearCalculator();
                break;

            case "+-":
                positiveOrNegative();
                break;

            //If user selected an operator the selected operator will update in order to prepare for the next number entry (traditional step two in a calculation)
            case "-":
            case "+":
            case "/":
            case "*":
                selectOperatorForEquation(buttonText);
                break;

            //If user selected = then a calculation will be performed and displayed on screen
            case "=":
                calculateEquation();
                break;

            default:
                break;
        }
    }

    @FXML
    public void acceptKeyboardInput(KeyEvent keyEvt) {
        String buttonText = keyEvt.getText();
        //Clears out calculator if user tried to previously divide by zero,
        if (textFieldDisplay.getText().contains("CANNOT DIVIDE BY ZERO")) {
            clearCalculator();
        }
        switch (buttonText) {
            case "0":
                zeroButton.setStyle("-fx-background-color: black");
                appendIntToDisplay(buttonText);
                break;
            case "1":
                oneButton.setStyle("-fx-background-color: black");
                appendIntToDisplay(buttonText);
                break;
            case "2":
                twoButton.setStyle("-fx-background-color: black");
                appendIntToDisplay(buttonText);
                break;
            case "3":
                threeButton.setStyle("-fx-background-color: black");
                appendIntToDisplay(buttonText);
                break;
            case "4":
                fourButton.setStyle("-fx-background-color: black");
                appendIntToDisplay(buttonText);
                break;
            case "5":
                fiveButton.setStyle("-fx-background-color: black");
                appendIntToDisplay(buttonText);
                break;
            case "6":
                sixButton.setStyle("-fx-background-color: black");
                appendIntToDisplay(buttonText);
                break;
            case "7":
                sevenButton.setStyle("-fx-background-color: black");
                appendIntToDisplay(buttonText);
                break;
            case "8":
                eightButton.setStyle("-fx-background-color: black");
                appendIntToDisplay(buttonText);
                break;
            case "9":
                nineButton.setStyle("-fx-background-color: black");
                appendIntToDisplay(buttonText);
                break;
            case ".":
                decimalButton.setStyle("-fx-background-color: black");
                appendIntToDisplay(buttonText);
                break;

            //If user selected an operator the selected operator will update in order to prepare for the next number entry (traditional step two in a calculation)
            case "-":
                minusButton.setStyle("-fx-background-color: black");
                selectOperatorForEquation(buttonText);
                break;
            case "+":
                plusButton.setStyle("-fx-background-color: black");
                selectOperatorForEquation(buttonText);
                break;
            case "/":
                divideButton.setStyle("-fx-background-color: black");
                selectOperatorForEquation(buttonText);
                break;
            case "*":
                multiplyButton.setStyle("-fx-background-color: black");
                selectOperatorForEquation(buttonText);
                break;

            //If user selected = then a calculation will be performed and displayed on screen
            case "=":
                equalsButton.setStyle("-fx-background-color: black");
                calculateEquation();
                break;

            default:
                break;
        }
    }

    //Method to perform the calculation, then passes back to control to display result on GUI
    private static BigDecimal calculate(String operator, BigDecimal left, BigDecimal right) {
        switch (operator) {
            case "+":
                return left.add(right);
            case "-":
                return left.subtract(right);
            case "*":
                return left.multiply(right);
            case "/":
                //noinspection BigDecimalMethodWithoutRoundingCalled
                return left.divide(right);
            default:
        }
        return right;
    }

    //used when calculator is cleared when user presses CE or text is on screen before input
    private void clearCalculator() {
        left = BigDecimal.ZERO;
        selectedOperator = "";
        numberInputting = false;
        textFieldDisplay.setText("0");
    }

    private void positiveOrNegative() {
        String value = textFieldDisplay.getText();
        if (textFieldDisplay.getText().contains("-")) {
            textFieldDisplay.setText(value.substring(1));
        } else {
            textFieldDisplay.setText("-" + value);
        }
    }

    private void backOneChar() {
        //handles calculator not having anything to back out
        if (textFieldDisplay.getText().equals("")) {
            return;
        }
        String beforeBack = textFieldDisplay.getText();
        String afterBack = beforeBack.substring(0, beforeBack.length() - 1);
        textFieldDisplay.setText(afterBack);
    }

    private void calculateEquation() {
        //cannot perform calculation with a decimal or no value. This factors in the making digit a zero for calculation purposes.
        if (textFieldDisplay.getText().equals(".") || textFieldDisplay.getText().equals("")) {
            textFieldDisplay.appendText("0");
        }
        BigDecimal right = numberInputting ? new BigDecimal(textFieldDisplay.getText()) : left;
        //try catch informs user they cannot divide by zero.
        try {
            left = calculate(selectedOperator, left, right);
            //catch divide by zero error and inform user of issue
        } catch (ArithmeticException exception) {
            textFieldDisplay.setText("CANNOT DIVIDE BY ZERO");
            System.out.println("User attempted to divide by zero");
            return;
        }
        textFieldDisplay.setText(left.toString());
        numberInputting = false;
    }

    private void appendIntToDisplay(String buttonText) {
        if (!numberInputting) {
            numberInputting = true;
            textFieldDisplay.clear();
        }
        if ((textFieldDisplay.getText().endsWith(".")) && (buttonText.equals("."))) {
            System.out.println("Cannot use more than one decimal");
            return;
        }
        if (textFieldDisplay.getLength() == 16) {
            return;
        } else {
            textFieldDisplay.appendText(buttonText);
        }
    }

    private void selectOperatorForEquation(String buttonText) {
        left = new BigDecimal(textFieldDisplay.getText());
        selectedOperator = buttonText;
        numberInputting = false;
    }

    public void handleKeyPressed(KeyEvent keyEvent) {
        String buttonText = keyEvent.getText();
//        System.out.println("hello");
//        if (keyEvent.equals("7")){
//            System.out.println("hi again");
//        }
        switch (buttonText) {
            case "0":
                zeroButton.setStyle("-fx-background-color: green;");
                break;
            case "1":
                oneButton.setStyle("-fx-background-color: green;");
                break;
            case "2":
                twoButton.setStyle("-fx-background-color: green;");
                break;
            case "3":
                threeButton.setStyle("-fx-background-color: green;");
                break;
            case "4":
                fourButton.setStyle("-fx-background-color: green;");
                break;
            case "5":
                fiveButton.setStyle("-fx-background-color: green;");
                break;
            case "6":
                sixButton.setStyle("-fx-background-color: green;");
                break;
            case "7":
                sevenButton.setStyle("-fx-background-color: green;");
                break;
            case "8":
                eightButton.setStyle("-fx-background-color: green;");
                break;
            case "9":
                nineButton.setStyle("-fx-background-color: green;");
                break;
            case ".":
                decimalButton.setStyle("-fx-background-color: green;");
                break;
            case "+":
                plusButton.setStyle("-fx-background-color: green;");
                break;
            case "-":
                minusButton.setStyle("-fx-background-color: green;");
                break;
            case "*":
                multiplyButton.setStyle("-fx-background-color: green;");
                break;
            case "/":
                divideButton.setStyle("-fx-background-color: green;");
                break;
            case "=":
                equalsButton.setStyle("-fx-background-color: green;");
                break;
            default:
                break;
        }
    }
}






