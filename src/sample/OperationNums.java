package sample;

public class OperationNums {
    private double doubleResult;
    private int integerResult;
    private double uniResult;
    private boolean isFixed = false;

    public OperationNums(Object o) {
        if (o instanceof Double) {
            doubleResult = ((Double) o).doubleValue();
            uniResult = doubleResult;
            isFixed = true;
        } else if (o instanceof Integer) {
            integerResult = ((Integer) o).intValue();
            uniResult = integerResult;
        }
    }


    public void completePrevious(char sign, Object o) {
        if (sign == '+') {
            if (o instanceof Integer) {
                uniResult += ((Integer) o).intValue();
                integerResult = (int) uniResult;
            } else if (o instanceof Double) {
                uniResult += ((Double)o).doubleValue();
                isFixed = true;
            }
        } else if (sign == '-') {
            if (o instanceof Integer) {
                uniResult -= ((Integer) o).intValue();
                integerResult = (int) uniResult;
            } else if ( o instanceof Double) {
                uniResult -= ((Double)o).doubleValue();
                isFixed = true;
            }
        } else if (sign == '*') {
            if (o instanceof Integer) {
                uniResult *= ((Integer) o).intValue();
                integerResult = (int) uniResult;
            } else if (o instanceof Double) {
                uniResult *= ((Double)o).doubleValue();
                isFixed = true;
            }
        } else if (sign == '/') {
            if (o instanceof Integer) {
                uniResult /= ((Integer) o).intValue();
                integerResult = (int) uniResult;
            } else if (o instanceof Double) {
                uniResult /= ((Double)o).doubleValue();
                isFixed = true;
            }
        }
    }

    public Object getResult() {
        if(isFixed){
            String s = String.format("%.2f",uniResult);
            uniResult = Double.parseDouble(s);
            return uniResult;
        }
        else
            return integerResult;
    }
}
