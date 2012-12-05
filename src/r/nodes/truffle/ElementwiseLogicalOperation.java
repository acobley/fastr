package r.nodes.truffle;

import r.*;
import r.data.*;
import r.data.internal.*;
import r.errors.*;
import r.nodes.*;

import com.oracle.truffle.nodes.*;
import com.oracle.truffle.runtime.*;


public abstract class ElementwiseLogicalOperation extends BaseR {

    @Stable RNode left;
    @Stable RNode right;
    final Operation op;

    public ElementwiseLogicalOperation(ASTNode ast, RNode left, Operation op, RNode right) {
        super(ast);
        this.left = updateParent(left);
        this.right = updateParent(right);
        this.op = op;
    }

    @Override
    public final  Object execute(RContext context, Frame frame) {
        RAny leftValue = (RAny) left.execute(context, frame);
        RAny rightValue = (RAny) right.execute(context, frame);
        return execute(context, leftValue, rightValue);
    }

    public abstract RAny execute(RContext context, RAny leftValue, RAny rightValue);

    public static ElementwiseLogicalOperation createUninitialized(ASTNode ast, RNode left, Operation op, RNode right) {
        return new ElementwiseLogicalOperation(ast, left, op, right) {

            @Override
            public RAny execute(RContext context, RAny leftValue, RAny rightValue) {
                try {
                    throw new UnexpectedResultException(null);
                } catch (UnexpectedResultException e) {
                    ElementwiseLogicalOperation sn = Specialized.create(ast, left, op, right, leftValue, rightValue);
                    replace(sn, "install LogicalOperation from Uninitialized");
                    return sn.execute(context, leftValue, rightValue);
                }
            }

        };
    }

    public static ElementwiseLogicalOperation createGeneric(ASTNode ast, RNode left, Operation op, RNode right) {
        return new ElementwiseLogicalOperation(ast, left, op, right) {

            @Override
            public RAny execute(RContext context, RAny leftValue, RAny rightValue) {
                if (leftValue instanceof RLogical && rightValue instanceof RLogical) {
                    return op.op((RLogical) leftValue, (RLogical) rightValue, context, ast);
                }
                if ((leftValue instanceof RInt || leftValue instanceof RDouble || leftValue instanceof RLogical) &&
                               (rightValue instanceof RInt || rightValue instanceof RDouble || rightValue instanceof RLogical)) {
                    return op.op(leftValue.asLogical(), rightValue.asLogical(), context, ast);
                }
                Utils.nyi("unsupported types");
                return null;
            }

        };
    }

    public static class Specialized extends ElementwiseLogicalOperation {
        private final Action action;

        Specialized(ASTNode ast, RNode left, Operation op, RNode right, Action action) {
            super(ast, left, op, right);
            this.action = action;
        }

        public abstract static class Action {
            abstract RLogical doFor(RAny leftValue, RAny rightValue, RContext context, ASTNode ast) throws UnexpectedResultException;
        }

        public static ElementwiseLogicalOperation create(final ASTNode ast, RNode left, final Operation op, RNode right, RAny leftTemplate, RAny rightTemplate) {
            if (leftTemplate instanceof ScalarLogicalImpl && rightTemplate instanceof ScalarLogicalImpl) {
                return new Specialized(ast, left, op, right, new Action() {
                    @Override
                    RLogical doFor(RAny leftValue, RAny rightValue, RContext context, ASTNode ast) throws UnexpectedResultException {
                        if ((leftValue instanceof ScalarLogicalImpl && rightValue instanceof ScalarLogicalImpl)) {
                            int l = op.op(((ScalarLogicalImpl) leftValue).getLogical(), ((ScalarLogicalImpl) rightValue).getLogical());
                            return RLogical.RLogicalFactory.getScalar(l);
                        }
                        return null;
                    }
                });
            }
            // TODO: more specialized nodes
            return createGeneric(ast, left, op, right);
        }

        @Override
        public RAny execute(RContext context, RAny leftValue, RAny rightValue) {
            try {
                return action.doFor(leftValue, rightValue, context, ast);
            } catch (UnexpectedResultException e) {
                ElementwiseLogicalOperation gn = createGeneric(ast, left, op, right);
                replace(gn, "install Generic from ElementwiseLogicalOperation.Specialized");
                return gn.execute(context, leftValue, rightValue);
            }
        }

    }

    public abstract static class Operation {
        public abstract int op(int a, int b);
        public RLogical op(RLogical a, RLogical b, RContext context, ASTNode ast) {
            int na = a.size();
            int nb = b.size();
            int[] dimensions = Arithmetic.resultDimensions(ast, a, b);
            if (na == 0 || nb == 0) {
                return RLogical.EMPTY;
            }

            int n = (na > nb) ? na : nb;
            int[] content = new int[n];
            int ai = 0;
            int bi = 0;

            for (int i = 0; i < n; i++) {
                int alog = a.getLogical(ai++);
                if (ai == na) {
                    ai = 0;
                }
                int blog = b.getLogical(bi++);
                if (bi == nb) {
                    bi = 0;
                }
                content[i] = op(alog, blog);
            }

            if (ai != 0 || bi != 0) {
                context.warning(ast, RError.LENGTH_NOT_MULTI);
            }
            return RLogical.RLogicalFactory.getFor(content, dimensions);
        }
    }

    public static final Operation AND = new Operation() {
        @Override
        public int op(int a, int b) {
            if (a == RLogical.TRUE) {
                return b;
            }
            if (a == RLogical.FALSE) {
                return RLogical.FALSE;
            }
            // a == RLogical.NA
            if (b == RLogical.TRUE) {
                return RLogical.NA;
            } else {
                return RLogical.FALSE;
            }
        }
    };

    public static final Operation OR = new Operation() {
        @Override
        public int op(int a, int b) {
            if (a == RLogical.TRUE) {
                return RLogical.TRUE;
            }
            if (a == RLogical.FALSE) {
                return b;
            }
            // a == RLogical.NA
            if (b == RLogical.TRUE) {
                return RLogical.TRUE;
            } else {
                return RLogical.NA;
            }
        }
    };
}