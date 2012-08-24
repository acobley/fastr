package r.nodes;

import com.oracle.truffle.runtime.*;

import r.*;
import r.Convert;
import r.data.*;

@Precedence(Precedence.MAX)
public class Constant extends ASTNode {

    final RAny value;

    Constant(RAny val) {
        value = val;
    }

    @Override
    public RAny execute(RContext global, Frame frame) {
        return getValue();
    }

    public String prettyValue() {
        return getValue().pretty();
    }

    public static ASTNode getNull() {
        return new Constant(RNull.getNull());
    }

    @Override
    public void visit_all(Visitor v) {
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public static Constant createDoubleConstant(String... values) {
        double[] val = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            val[i] = Convert.string2double(values[i]);
        }
        return createDoubleConstant(val);
    }

    public static Constant createDoubleConstant(double... values) {
        if (values.length == 1) {
            return new Constant(RDouble.RDoubleFactory.getArray(values));
        }
        throw new Error("Non scalar constants are not implemented.");
    }

    public static Constant createComplexConstant(String... values) {
        double[] val = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            val[i] = Convert.string2double(values[i]);
        }
        return createComplexConstant(val);
    }

    public static Constant createComplexConstant(double... values) {
        if (values.length == 1) {
// return new Constant(RComplex.RDoubleFactory.getArray(values));
            // Punt since I don't whant to create a class constant
        }
        throw new Error("Non scalar constants are not implemented.");
    }

    public static Constant createIntConstant(String... values) {
        int[] val = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            val[i] = Convert.string2int(values[i]);
        }
        return createIntConstant(val);
    }

    public static Constant createIntConstant(int... values) {
        if (values.length == 1) {
            return new Constant(RInt.RIntFactory.getArray(values));
        }
        throw new Error("Non scalar constants are not implemented.");
    }

    public static Constant createBoolConstant(String... values) {
        int[] val = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            val[i] = Convert.string2lgl(values[i]);
        }
        return createBoolConstant(values);
    }

    public static Constant createBoolConstant(int... values) {
        if (values.length == 1) {
            return new Constant(RLogical.RLogicalFactory.getArray(values));
        }
        throw new Error("Non scalar constants are not implemented.");
    }

    @Override
    public String toString() {
        return getValue().pretty();
    }

    public RAny getValue() {
        return value;
    }
}