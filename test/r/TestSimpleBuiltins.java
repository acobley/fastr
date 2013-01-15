package r;

import org.antlr.runtime.*;
import org.junit.*;

public class TestSimpleBuiltins extends TestBase {

    @Test
    public void testSequence() throws RecognitionException {
        assertEval("{ 5L:10L }", "5L, 6L, 7L, 8L, 9L, 10L");
        assertEval("{ 5L:(0L-5L) }", "5L, 4L, 3L, 2L, 1L, 0L, -1L, -2L, -3L, -4L, -5L");
        assertEval("{ 1:10 }", "1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L"); // note: yes, GNU R will convert to integers
        assertEval("{ 1:(0-10) }", "1L, 0L, -1L, -2L, -3L, -4L, -5L, -6L, -7L, -8L, -9L, -10L");
        assertEval("{ 1L:(0-10) }", "1L, 0L, -1L, -2L, -3L, -4L, -5L, -6L, -7L, -8L, -9L, -10L");
        assertEval("{ 1:(0L-10L) }", "1L, 0L, -1L, -2L, -3L, -4L, -5L, -6L, -7L, -8L, -9L, -10L");
        assertEval("{ (0-12):1.5 }", "-12L, -11L, -10L, -9L, -8L, -7L, -6L, -5L, -4L, -3L, -2L, -1L, 0L, 1L");
        assertEval("{ 1.5:(0-12) }", "1.5, 0.5, -0.5, -1.5, -2.5, -3.5, -4.5, -5.5, -6.5, -7.5, -8.5, -9.5, -10.5, -11.5");
        assertEval("{ (0-1.5):(0-12) }", "-1.5, -2.5, -3.5, -4.5, -5.5, -6.5, -7.5, -8.5, -9.5, -10.5, -11.5");
        assertEval("{ 10:1 }", "10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L");
        assertEval("{ (0-5):(0-9) }", "-5L, -6L, -7L, -8L, -9L");

        assertEval("{ seq(1,10) }", "1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L");
        assertEval("{ seq(10,1) }", "10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L");
        assertEval("{ seq(from=1,to=3) }", "1L, 2L, 3L");
        assertEval("{ seq(to=-1,from=-10) }", "-10L, -9L, -8L, -7L, -6L, -5L, -4L, -3L, -2L, -1L");
        assertEval("{ seq(length.out=13.4) }", "1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L");
        assertEval("{ seq(length.out=0) }", "integer(0)");
        assertEval("{ seq(length.out=1) }", "1L");
        assertEval("{ seq(along.with=10) }", "1L");
        assertEval("{ seq(along.with=NA) }", "1L");
        assertEval("{ seq(along.with=1:10) }", "1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L");
        assertEval("{ seq(along.with=-3:-5) }", "1L, 2L, 3L");
        assertEval("{ seq(from=1.4) }", "1L");
        assertEval("{ seq(from=1.7) }", "1L");
        assertEval("{ seq(from=10:12) }", "1L, 2L, 3L");
        assertEval("{ seq(from=c(TRUE, FALSE)) }", "1L, 2L");
        assertEval("{ seq(from=TRUE, to=TRUE, length.out=0) }", "integer(0)");
        assertEval("{ seq(from=10.5, to=15.4, length.out=4) }", "10.5, 12.133333333333333, 13.766666666666667, 15.4");
        assertEval("{ seq(from=11, to=12, length.out=2) }", "11.0, 12.0");
        assertEval("{ seq(from=1,to=3,by=1) }", "1.0, 2.0, 3.0");
        assertEval("{ seq(from=-10,to=-5,by=2) }", "-10.0, -8.0, -6.0");
        assertEval("{ seq(from=-10.4,to=-5.8,by=2.1) }", "-10.4, -8.3, -6.2");
        assertEval("{ seq(from=3L,to=-2L,by=-4.2) }", "3.0, -1.2000000000000002");

        assertEval("{ seq(along=c(10,11,12)) }", "1L, 2L, 3L");
    }

    @Test
    public void testArrayConstructors() throws RecognitionException {
        assertEval("{ integer() }", "integer(0)");
        assertEval("{ double() }", "numeric(0)");
        assertEval("{ logical() }", "logical(0)");
        assertEval("{ double(3) }", "0.0, 0.0, 0.0");
        assertEval("{ logical(3L) }", "FALSE, FALSE, FALSE");
        assertEval("{ character(1L) }", "\"\"");
    }

    @Test
    public void testMaximum() throws RecognitionException {
        assertEval("{ max((-1):100) }", "100L");
        assertEval("{ max(1:10, 100:200, c(4.0, 5.0)) }", "200.0");
        assertEval("{ max(1:10, 100:200, c(4.0, 5.0), c(TRUE,FALSE,NA)) }", "NA");
        assertEval("{ max(2L, 4L) }", "4L");
        assertEval("{ max() }", "-Infinity");
        assertEval("{ max(c(\"hi\",\"abbey\",\"hello\")) }", "\"hi\"");
        assertEval("{ max(\"hi\",\"abbey\",\"hello\") }", "\"hi\"");
    }

    @Test
    public void testMinimum() throws RecognitionException {
        assertEval("{ min((-1):100) }", "-1L");
        assertEval("{ min(1:10, 100:200, c(4.0, -5.0)) }", "-5.0");
        assertEval("{ min(1:10, 100:200, c(4.0, 5.0), c(TRUE,FALSE,NA)) }", "NA");
        assertEval("{ min(2L, 4L) }", "2L");
        assertEval("{ min() }", "Infinity");
        assertEval("{ min(c(\"hi\",\"abbey\",\"hello\")) }", "\"abbey\"");
        assertEval("{ min(\"hi\",\"abbey\",\"hello\") }", "\"abbey\"");
        assertEval("{ min(\"hi\",100) }", "\"100.0\"");
    }

    @Test
    public void testRep() throws RecognitionException {
        assertEval("{ rep(1,3) }", "1.0, 1.0, 1.0");
        assertEval("{ rep(1:3,2) }", "1L, 2L, 3L, 1L, 2L, 3L");
        assertEval("{ rep(c(1,2),0) }", "numeric(0)");
        assertEval("{ rep(1:3, length.out=4) }", "1L, 2L, 3L, 1L");
        assertEval("{ rep(1:3, length.out=NA) }", "1L, 2L, 3L");
        assertEval("{ rep(as.raw(14), 4) }", "0e, 0e, 0e, 0e");

        assertEval("{ x <- as.raw(11) ; names(x) <- c(\"X\") ; rep(x, 3) }", " X  X  X\n0b 0b 0b");
        assertEval("{ x <- as.raw(c(11,12)) ; names(x) <- c(\"X\",\"Y\") ; rep(x, 2) }", " X  Y  X  Y\n0b 0c 0b 0c");
        assertEval("{ x <- c(TRUE,NA) ; names(x) <- c(\"X\",NA) ; rep(x, length.out=3) }", "   X <NA>    X\nTRUE   NA TRUE");
        assertEval("{ x <- 1L ; names(x) <- c(\"X\") ; rep(x, times=2) } ", " X  X\n1L 1L");
        assertEval("{ x <- 1 ; names(x) <- c(\"X\") ; rep(x, times=0) }", "named numeric(0)");
        assertEval("{ x <- 1+1i ; names(x) <- c(\"X\") ; rep(x, times=2) }", "       X        X\n1.0+1.0i 1.0+1.0i");
        assertEval("{ x <- c(1+1i,1+2i) ; names(x) <- c(\"X\") ; rep(x, times=2) }", "       X     <NA>        X     <NA>\n1.0+1.0i 1.0+2.0i 1.0+1.0i 1.0+2.0i");
        assertEval("{ x <- c(\"A\",\"B\") ; names(x) <- c(\"X\") ; rep(x, length.out=3) }", "  X <NA>   X\n\"A\"  \"B\" \"A\"");
    }

    @Test
    public void testCombine() throws RecognitionException {
        assertEval("{ c(1.0,1L) }", "1.0, 1.0");
        assertEval("{ c(1L,1.0) }", "1.0, 1.0");
        assertEval("{ c(TRUE,1L,1.0,list(3,4)) }", "[[1]]\nTRUE\n\n[[2]]\n1L\n\n[[3]]\n1.0\n\n[[4]]\n3.0\n\n[[5]]\n4.0");
        assertEval("{ c(TRUE,1L,1.0,list(3,list(4,5))) }", "[[1]]\nTRUE\n\n[[2]]\n1L\n\n[[3]]\n1.0\n\n[[4]]\n3.0\n\n[[5]]\n[[5]][[1]]\n4.0\n\n[[5]][[2]]\n5.0");
        assertEval("{ c() }", "NULL");
        assertEval("{ c(NULL,NULL) }", "NULL");
        assertEval("{ c(NULL,1,2,3) }", "1.0, 2.0, 3.0");
        assertEval("{ f <- function(x,y) { c(x,y) } ; f(1,1) ; f(1, TRUE) }", "1.0, 1.0");
        assertEval("{ f <- function(x,y) { c(x,y) } ; f(1,1) ; f(1, TRUE) ; f(NULL, NULL) }", "NULL");
        assertEval("{ c(\"hello\", \"hi\") }", "\"hello\", \"hi\"");
        assertEval("{ c(1+1i, as.raw(10)) }", "1.0+1.0i, 10.0+0.0i");
        assertEval("{ c(as.raw(10), as.raw(20)) }", "0a, 14");

        assertEval("{ c(x=1,y=2) }", "  x   y\n1.0 2.0");
        assertEval("{ c(x=1,2) }", "  x    \n1.0 2.0");
        assertEval("{ x <- 1:2 ; names(x) <- c(\"A\",NA) ; c(x,test=x) }", " A <NA> test.A test.NA\n1L   2L     1L      2L");
    }

    @Test
    public void testIsNA() throws RecognitionException {
        assertEval("{ is.na(c(1,2,3,4)) }", "FALSE, FALSE, FALSE, FALSE");
        assertEval("{ is.na(1[10]) }", "TRUE");
        assertEval("{ is.na(c(1[10],2[10],3)) }", "TRUE, TRUE, FALSE");
        assertEval("{ is.na(list(1[10],1L[10],list(),integer())) }", "TRUE, TRUE, FALSE, FALSE");
    }

    @Test
    public void testCasts() throws RecognitionException {
        assertEval("{ as.integer(c(1,2,3)) }", "1L, 2L, 3L");
        assertEval("{ as.integer(list(c(1),2,3)) }", "1L, 2L, 3L");
        assertEval("{ as.integer(list(integer(),2,3)) }", "NA, 2L, 3L");
        assertEval("{ as.integer(list(list(1),2,3)) }", "NA, 2L, 3L");
        assertEval("{ as.integer(list(1,2,3,list())) }", "1L, 2L, 3L, NA");

        assertEval("{ m<-matrix(1:6, nrow=3) ; as.integer(m) }", "1L, 2L, 3L, 4L, 5L, 6L");
        assertEval("{ m<-matrix(1:6, nrow=3) ; as.vector(m, \"any\") }", "1L, 2L, 3L, 4L, 5L, 6L");
        assertEval("{ m<-matrix(1:6, nrow=3) ; as.vector(mode = \"integer\", x=m) }", "1L, 2L, 3L, 4L, 5L, 6L");
        assertEval("{ as.vector(list(1,2,3), mode=\"integer\") }", "1L, 2L, 3L");

        assertEval("{ as.double(\"1.27\") }", "1.27");
        assertEval("{ as.double(1L) }", "1.0");
        assertEval("{ as.double(\"TRUE\") }", "NA");
        assertEval("{ as.double(c(\"1\",\"hello\")) }", "1.0, NA");

        assertEval("{ as.character(1L) }", "\"1L\"");
        assertEval("{ as.character(TRUE) }", "\"TRUE\"");
        assertEval("{ as.character(1:3) }", "\"1L\", \"2L\", \"3L\"");
        assertEval("{ as.character(NULL) }", "character(0)");
        assertEval("{ as.character(list(c(\"hello\", \"hi\"))) }", "\"c(\\\"hello\\\", \\\"hi\\\")\"");
        assertEval("{ as.character(list(list(c(\"hello\", \"hi\")))) }", "\"list(c(\\\"hello\\\", \\\"hi\\\"))\"");
        assertEval("{ as.character(list(1,2,3)) }", "\"1.0\", \"2.0\", \"3.0\"");
        assertEval("{ as.character(list(c(2L, 3L))) }", "\"2:3\"");
        assertEval("{ as.character(list(c(2L, 3L, 5L))) }", "\"c(2L, 3L, 5L)\"");

        assertEval("{ as.raw(list(1,2,3)) }", "01, 02, 03");
        assertEval("{ as.raw(list(\"1\", 2L, 3.4)) }", "01, 02, 03");
        assertEval("{ as.raw(c(1,1000,NA)) }", "01, 00, 00");

        assertEval("{ as.logical(1) }", "TRUE");
        assertEval("{ as.logical(\"false\") }", "FALSE");
        assertEval("{ as.logical(\"dummy\") }", "NA"); // no warning produced

        assertEval("{ as.complex(0) }", "0.0+0.0i");
        assertEval("{ as.complex(TRUE) }", "1.0+0.0i");
        assertEval("{ as.complex(\"1+5i\") }", "1.0+5.0i");
        assertEval("{ as.complex(\"1e10+5i\") }", "1.0E10+5.0i");
        assertEval("{ as.complex(\"-1+5i\") }", "-1.0+5.0i");
        assertEval("{ as.complex(\"-1-5i\") }", "-1.0-5.0i");
        assertEval("{ as.complex(\"-.1e10+5i\") }", "-1.0E9+5.0i");
        assertEval("{ as.complex(\"1e-2+3i\") }", "0.01+3.0i");
        assertEval("{ as.complex(\"+.1e+2-3i\") }", "10.0-3.0i");

        // shortcuts in views (only some combinations)
        assertEval("{ as.complex(as.character(c(1+1i,1+1i))) }", "1.0+1.0i, 1.0+1.0i");
        assertEval("{ as.complex(as.double(c(1+1i,1+1i))) }", "1.0+0.0i, 1.0+0.0i");
        assertEval("{ as.complex(as.integer(c(1+1i,1+1i))) }", "1.0+0.0i, 1.0+0.0i");
        assertEval("{ as.complex(as.logical(c(1+1i,1+1i))) }", "1.0+0.0i, 1.0+0.0i");
        assertEval("{ as.complex(as.raw(c(1+1i,1+1i))) }", "1.0+0.0i, 1.0+0.0i");

        assertEval("{ as.double(as.logical(c(10,10))) }", "1.0, 1.0");
        assertEval("{ as.integer(as.logical(-1:1)) }", "1L, 0L, 1L");
        assertEval("{ as.raw(as.logical(as.raw(c(1,2)))) }", "01, 01");
        assertEval("{ as.character(as.double(1:5)) }", "\"1.0\", \"2.0\", \"3.0\", \"4.0\", \"5.0\"");
        assertEval("{ as.character(as.complex(1:2)) }", "\"1.0+0.0i\", \"2.0+0.0i\"");

        // dropping dimensions
        assertEval("{ m <- matrix(1:6, nrow=2) ; as.double(m) }", "1.0, 2.0, 3.0, 4.0, 5.0, 6.0");
        assertEval("{ m <- matrix(c(1,2,3,4,5,6), nrow=2) ; as.integer(m) }", "1L, 2L, 3L, 4L, 5L, 6L");
        assertEval("{ m <- matrix(c(1,2,3,4,5,6), nrow=2) ; as.logical(m) }", "TRUE, TRUE, TRUE, TRUE, TRUE, TRUE");

        // dropping names
        assertEval("{ x <- 1:2; names(x) <- c(\"hello\",\"hi\") ; as.double(x) }", "1.0, 2.0");
        assertEval("{ x <- c(1,2); names(x) <- c(\"hello\",\"hi\") ; as.integer(x) }", "1L, 2L");
        assertEval("{ x <- c(0,2); names(x) <- c(\"hello\",\"hi\") ; as.logical(x) }", "FALSE, TRUE");

    }

    @Test
    public void testSum() throws RecognitionException {
        assertEval("{ sum(1:6, 3, 4) }", "28.0");
        assertEval("{ sum(1:6, 3L, TRUE) }", "25L");
        assertEval("{ sum() }", "0L");
        assertEval("{ sum(0, 1[3]) }", "NA");
        assertEval("{ sum(na.rm=FALSE, 0, 1[3]) }", "NA");
        assertEval("{ sum(0, na.rm=FALSE, 1[3]) }", "NA");
        assertEval("{ sum(0, 1[3], na.rm=FALSE) }", "NA");
        assertEval("{ sum(0, 1[3], na.rm=TRUE) }", "0.0");
        assertEval("{ `sum`(1:10) }", "55L");
    }

    @Test
    public void testApply() throws RecognitionException {
        assertEval("{ lapply(1:3, function(x) { 2*x }) }", "[[1]]\n2.0\n\n[[2]]\n4.0\n\n[[3]]\n6.0");
        assertEval("{ lapply(1:3, function(x,y) { x*y }, 2) }", "[[1]]\n2.0\n\n[[2]]\n4.0\n\n[[3]]\n6.0");

        assertEval("{ sapply(1:3,function(x){x*2}) }", "2.0, 4.0, 6.0");
        assertEval("{ sapply(c(1,2,3),function(x){x*2}) }", "2.0, 4.0, 6.0");
        assertEval("{ sapply(list(1,2,3),function(x){x*2}) }", "2.0, 4.0, 6.0");
        assertEval("{ sapply(1:3, function(x) { if (x==1) { 1 } else if (x==2) { integer() } else { TRUE } }) }", "[[1]]\n1.0\n\n[[2]]\ninteger(0)\n\n[[3]]\nTRUE");
        assertEval("{ f<-function(g) { sapply(1:3, g) } ; f(function(x) { x*2 }) }", "2.0, 4.0, 6.0");
        assertEval("{ f<-function(g) { sapply(1:3, g) } ; f(function(x) { x*2 }) ; f(function(x) { TRUE }) }", "TRUE, TRUE, TRUE");
        assertEval("{ sapply(1:3, function(x) { if (x==1) { list(1) } else if (x==2) { list(NULL) } else { list(2) } }) }", "[[1]]\n1.0\n\n[[2]]\nNULL\n\n[[3]]\n2.0");
        assertEval("{ sapply(1:3, function(x) { if (x==1) { list(1) } else if (x==2) { list(NULL) } else { list() } }) }", "[[1]]\n[[1]][[1]]\n1.0\n\n[[2]]\n[[2]][[1]]\nNULL\n\n[[3]]\nlist()");
        assertEval("{ f<-function() { x<-2 ; sapply(1, function(i) { x }) } ; f() }", "2.0");

        assertEval("{ sapply(1:3, length) }", "1L, 1L, 1L");
        assertEval("{ f<-length; sapply(1:3, f) }", "1L, 1L, 1L");
        assertEval("{ sapply(1:3, `-`, 2) }", "-1.0, 0.0, 1.0");
        assertEval("{ sapply(1:3, \"-\", 2) }", "-1.0, 0.0, 1.0");

        assertEval("{ sapply(1:2, function(i) { if (i==1) { as.raw(0) } else { as.raw(10) } }) }", "00, 0a");
        assertEval("{ sapply(1:2, function(i) { if (i==1) { as.raw(0) } else { \"hello\" }} ) } ", "\"00\", \"hello\"");
        assertEval("{ sapply(1:2, function(i) { if (i==1) { as.raw(0) } else { 5+10i } }) }", "0.0+0.0i, 5.0+10.0i");

        // matrix support
        assertEval("{ sapply(1:3, function(i) { list(1,2) }) }", "     [,1] [,2] [,3]\n[1,]  1.0  1.0  1.0\n[2,]  2.0  2.0  2.0");
        assertEval("{ sapply(1:3, function(i) { if (i < 3) { list(1,2) } else { c(11,12) } }) }", "     [,1] [,2] [,3]\n[1,]  1.0  1.0 11.0\n[2,]  2.0  2.0 12.0");
        assertEval("{ sapply(1:3, function(i) { if (i < 3) { c(1+1i,2) } else { c(11,12) } }) }", "         [,1]     [,2]      [,3]\n[1,] 1.0+1.0i 1.0+1.0i 11.0+0.0i\n[2,] 2.0+0.0i 2.0+0.0i 12.0+0.0i");

        // names
        assertEval("{ ( sapply(1:3, function(i) { if (i < 3) { list(xxx=1) } else {list(zzz=2)} })) }", "$xxx\n1.0\n\n$xxx\n1.0\n\n$zzz\n2.0");
        assertEval("{ ( sapply(1:3, function(i) { list(xxx=1:i) } )) }", "$xxx\n1L\n\n$xxx\n1L, 2L\n\n$xxx\n1L, 2L, 3L");
        assertEval("{ sapply(1:3, function(i) { if (i < 3) { list(xxx=1) } else {list(2)} }) }", "$xxx\n1.0\n\n$xxx\n1.0\n\n[[3]]\n2.0");
        assertEval("{ ( sapply(1:3, function(i) { if (i < 3) { c(xxx=1) } else {c(2)} })) }", "xxx xxx    \n1.0 1.0 2.0");
        assertEval("{ f <- function() { lapply(c(X=\"a\",Y=\"b\"), function(x) { c(a=x) })  } ; f() }", "$X\n  a\n\"a\"\n\n$Y\n  a\n\"b\"");
        assertEval("{ f <- function() { sapply(c(1,2), function(x) { c(a=x) })  } ; f() }", "  a   a\n1.0 2.0");
        assertEval("{ f <- function() { sapply(c(X=1,Y=2), function(x) { c(a=x) })  } ; f() }", "X.a Y.a\n1.0 2.0");
        assertEval("{ f <- function() { sapply(c(\"a\",\"b\"), function(x) { c(a=x) })  } ; f() }", "a.a b.a\n\"a\" \"b\"");
        assertEval("{ f <- function() { sapply(c(X=\"a\",Y=\"b\"), function(x) { c(a=x) })  } ; f() }", "X.a Y.a\n\"a\" \"b\"");
        assertEval("{ sapply(c(\"a\",\"b\",\"c\"), function(x) { x }) }", "  a   b   c\n\"a\" \"b\" \"c\"");
    }

    @Test
    public void testCat() throws RecognitionException {
        assertEval("{ cat(\"hi\",1:3,\"hello\") }", "hi 1L 2L 3L hello", "NULL");
        assertEval("{ cat(\"hi\",NULL,\"hello\",sep=\"-\") }", "hi-hello", "NULL");
        assertEval("{ cat(\"hi\",integer(0),\"hello\",sep=\"-\") }", "hi--hello", "NULL");
        assertEval("{ cat(\"hi\",1[2],\"hello\",sep=\"-\") }", "hi-NA-hello", "NULL");
        assertEval("{ m <- matrix(as.character(1:6, nrow=2)) ; cat(m) }", "1L 2L 3L 4L 5L 6L", "NULL");
        assertEval("{ cat(sep=\" \", \"hello\") }", "hello", "NULL");
    }

    @Test
    public void testOuter() throws RecognitionException {
        assertEval("{ outer(1:3,1:2) }", "     [,1] [,2]\n[1,]  1.0  2.0\n[2,]  2.0  4.0\n[3,]  3.0  6.0");
        assertEval("{ outer(1:3,1:2,\"*\") }", "     [,1] [,2]\n[1,]  1.0  2.0\n[2,]  2.0  4.0\n[3,]  3.0  6.0");
        assertEval("{ outer(1, 3, \"-\") }", "     [,1]\n[1,] -2.0");
        assertEval("{ outer(1:3,1:2, function(x,y,z) { x*y*z }, 10) }", "     [,1] [,2]\n[1,] 10.0 20.0\n[2,] 20.0 40.0\n[3,] 30.0 60.0");
        assertEval("{ outer(1:2, 1:3, \"<\") }", "      [,1]  [,2] [,3]\n[1,] FALSE  TRUE TRUE\n[2,] FALSE FALSE TRUE");
        assertEval("{ outer(1:2, 1:3, '<') }", "      [,1]  [,2] [,3]\n[1,] FALSE  TRUE TRUE\n[2,] FALSE FALSE TRUE");
    }

    @Test
    public void testOperators() throws RecognitionException {
        assertEval("{ `+`(1,2) }", "3.0");
        assertEval("{ `-`(1,2) }", "-1.0");
        assertEval("{ `*`(1,2) }", "2.0");
        assertEval("{ `/`(1,2) }", "0.5");
        assertEval("{ `%/%`(1,2) }", "0.0");
        assertEval("{ `%%`(1,2) }", "1.0");
        assertEval("{ `^`(1,2) }", "1.0");
        assertEval("{ `!`(TRUE) }", "FALSE");
        assertEval("{ `||`(TRUE, FALSE) }", "TRUE");
        assertEval("{ `&&`(TRUE, FALSE) }", "FALSE");
        assertEval("{ `|`(TRUE, FALSE) }", "TRUE");
        assertEval("{ `&`(TRUE, FALSE) }", "FALSE");
        assertEval("{ `%o%`(3,5) }", "     [,1]\n[1,] 15.0");
        assertEval("{ `%*%`(3,5) }", "     [,1]\n[1,] 15.0");
        assertEval("{ x <- `+` ; x(2,3) }", "5.0");
        assertEval("{ x <- `+` ; f <- function() { x <- 1 ; x(2,3) } ; f() }", "5.0");
    }

    @Test
    public void testTriangular() throws RecognitionException {
        assertEval("{ m <- matrix(1:6, nrow=2) ;  upper.tri(m, diag=TRUE) }", "      [,1] [,2] [,3]\n[1,]  TRUE TRUE TRUE\n[2,] FALSE TRUE TRUE");
        assertEval("{ m <- matrix(1:6, nrow=2) ;  upper.tri(m, diag=FALSE) }", "      [,1]  [,2] [,3]\n[1,] FALSE  TRUE TRUE\n[2,] FALSE FALSE TRUE");
        assertEval("{ m <- matrix(1:6, nrow=2) ;  lower.tri(m, diag=TRUE) }", "     [,1]  [,2]  [,3]\n[1,] TRUE FALSE FALSE\n[2,] TRUE  TRUE FALSE");
        assertEval("{ m <- matrix(1:6, nrow=2) ;  lower.tri(m, diag=FALSE) }", "      [,1]  [,2]  [,3]\n[1,] FALSE FALSE FALSE\n[2,]  TRUE FALSE FALSE");

        assertEval("{ upper.tri(1:3, diag=TRUE) }", "      [,1]\n[1,]  TRUE\n[2,] FALSE\n[3,] FALSE");
        assertEval("{ upper.tri(1:3, diag=FALSE) }", "      [,1]\n[1,] FALSE\n[2,] FALSE\n[3,] FALSE");
        assertEval("{ lower.tri(1:3, diag=TRUE) }", "     [,1]\n[1,] TRUE\n[2,] TRUE\n[3,] TRUE");
        assertEval("{ lower.tri(1:3, diag=FALSE) }", "      [,1]\n[1,] FALSE\n[2,]  TRUE\n[3,]  TRUE");

        assertEval("{ m <- { matrix( as.character(1:6), nrow=2 ) } ; diag(m) <- c(1,2) ; m }", "      [,1]  [,2] [,3]\n[1,] \"1.0\"  \"3L\" \"5L\"\n[2,]  \"2L\" \"2.0\" \"6L\"");
        assertEval("{ m <- { matrix( (1:6) * (1+3i), nrow=2 ) } ; diag(m) <- c(1,2) ; m }", "         [,1]     [,2]      [,3]\n[1,] 1.0+0.0i 3.0+9.0i 5.0+15.0i\n[2,] 2.0+6.0i 2.0+0.0i 6.0+18.0i");
        assertEval("{ m <- { matrix( as.raw(11:16), nrow=2 ) } ; diag(m) <- c(as.raw(1),as.raw(2)) ; m }", "     [,1] [,2] [,3]\n[1,]   01   0d   0f\n[2,]   0c   02   10");
    }

    @Test
    public void testDiagonal() throws RecognitionException {
        assertEval("{ m <- matrix(1:6, nrow=3) ; diag(m) <- c(1,2) ; m }", "     [,1] [,2]\n[1,]  1.0  4.0\n[2,]  2.0  2.0\n[3,]  3.0  6.0");
        assertEval("{ x <- (m <- matrix(1:6, nrow=3)) ; diag(m) <- c(1,2) ; x }", "     [,1] [,2]\n[1,]   1L   4L\n[2,]   2L   5L\n[3,]   3L   6L");
        assertEval("{ m <- matrix(1:6, nrow=3) ; f <- function() { diag(m) <- c(100,200) } ; f() ; m }", "     [,1] [,2]\n[1,]   1L   4L\n[2,]   2L   5L\n[3,]   3L   6L");
    }

    @Test
    public void testDimensions() throws RecognitionException {
        assertEval("{ dim(1) }", "NULL");
        assertEval("{ dim(1:3) }", "NULL");
        assertEval("{ m <- matrix(1:6, nrow=3) ; dim(m) }", "3L, 2L");

        assertEval("{ nrow(1) }", "NULL");
        assertEval("{ nrow(1:3) }", "NULL");
        assertEval("{ m <- matrix(1:6, nrow=3) ; nrow(m) }", "3L");

        assertEval("{ ncol(1) }", "NULL");
        assertEval("{ ncol(1:3) }", "NULL");
        assertEval("{ m <- matrix(1:6, nrow=3) ; ncol(m) }", "2L");
    }

    @Test
    public void testCumulativeSum() throws RecognitionException {
        assertEval("{ cumsum(1:10) }", "1L, 3L, 6L, 10L, 15L, 21L, 28L, 36L, 45L, 55L");
        assertEval("{ cumsum(c(1,2,3)) }", "1.0, 3.0, 6.0");
        assertEval("{ cumsum(rep(1e308, 3) ) }", "1.0E308, Infinity, Infinity");
        assertEval("{ cumsum(NA) }", "NA");
        assertEval("{ cumsum(c(1e308, 1e308, NA, 1, 2)) }", "1.0E308, Infinity, NA, NA, NA");
        assertEval("{ cumsum(c(2000000000L, 2000000000L)) }", "2000000000L, NA");
        assertEval("{ cumsum(c(2000000000L, NA, 2000000000L)) }", "2000000000L, NA, NA");
        assertEval("{ cumsum(as.logical(-2:2)) }", "1L, 2L, 2L, 3L, 4L");
        assertEval("{ cumsum((1:6)*(1+1i)) }", "1.0+1.0i, 3.0+3.0i, 6.0+6.0i, 10.0+10.0i, 15.0+15.0i, 21.0+21.0i");
        assertEval("{ cumsum(as.raw(1:6)) }", "1.0, 3.0, 6.0, 10.0, 15.0, 21.0");
    }

    @Test
    public void testWhich() throws RecognitionException {
        assertEval("{ which(c(TRUE, FALSE, NA, TRUE)) }", "1L, 4L");
        assertEval("{ which(logical()) }", "integer(0)");
        assertEval("{ which(c(a=TRUE,b=FALSE,c=TRUE)) }", " a  c\n1L 3L");
    }

    @Test
    public void testColumnsRowsStat() throws RecognitionException {
        assertEval("{ m <- matrix(1:6, nrow=2) ; colMeans(m) }", "1.5, 3.5, 5.5");
        assertEval("{ m <- matrix(1:6, nrow=2) ; colSums(na.rm = FALSE, x = m) }", "3.0, 7.0, 11.0");
        assertEval("{ m <- matrix(1:6, nrow=2) ; rowMeans(x = m, na.rm = TRUE) }", "3.0, 4.0");
        assertEval("{ m <- matrix(1:6, nrow=2) ; rowSums(x = m) }", "9.0, 12.0");

        assertEval("{ m <- matrix(c(1,2,3,4,5,6), nrow=2) ; colMeans(m) }", "1.5, 3.5, 5.5");
        assertEval("{ m <- matrix(c(1,2,3,4,5,6), nrow=2) ; colSums(m) }", "3.0, 7.0, 11.0");
        assertEval("{ m <- matrix(c(1,2,3,4,5,6), nrow=2) ; rowMeans(m) }", "3.0, 4.0");
        assertEval("{ m <- matrix(c(1,2,3,4,5,6), nrow=2) ; rowSums(m) }", "9.0, 12.0");

        assertEval("{ m <- matrix(c(NA,2,3,4,NA,6), nrow=2) ; rowSums(m) }", "NA, 12.0");
        assertEval("{ m <- matrix(c(NA,2,3,4,NA,6), nrow=2) ; rowSums(m, na.rm = TRUE) }", "3.0, 12.0");
        assertEval("{ m <- matrix(c(NA,2,3,4,NA,6), nrow=2) ; rowMeans(m, na.rm = TRUE) }", "3.0, 4.0");

        assertEval("{ m <- matrix(c(NA,2,3,4,NA,6), nrow=2) ; colSums(m) }", "NA, 7.0, NA");
        assertEval("{ m <- matrix(c(NA,2,3,4,NA,6), nrow=2) ; colSums(na.rm = TRUE, m) }", "2.0, 7.0, 6.0");
        assertEval("{ m <- matrix(c(NA,2,3,4,NA,6), nrow=2) ; colMeans(m) }", "NA, 3.5, NA");
        assertEval("{ m <- matrix(c(NA,2,3,4,NA,6), nrow=2) ; colMeans(m, na.rm = TRUE) }", "2.0, 3.5, 6.0");

        assertEval("{ colSums(matrix(as.complex(1:6), nrow=2)) }", "3.0+0.0i, 7.0+0.0i, 11.0+0.0i");
        assertEval("{ colSums(matrix((1:6)*(1+1i), nrow=2)) }", "3.0+3.0i, 7.0+7.0i, 11.0+11.0i");
        assertEval("{ colMeans(matrix(as.complex(1:6), nrow=2)) }", "1.5+0.0i, 3.5+0.0i, 5.5+0.0i");
        assertEval("{ colMeans(matrix((1:6)*(1+1i), nrow=2)) }", "1.5+1.5i, 3.5+3.5i, 5.5+5.5i");
        assertEval("{ rowSums(matrix(as.complex(1:6), nrow=2)) }", "9.0+0.0i, 12.0+0.0i");
        assertEval("{ rowSums(matrix((1:6)*(1+1i), nrow=2)) }", "9.0+9.0i, 12.0+12.0i");
        assertEval("{ rowMeans(matrix(as.complex(1:6), nrow=2)) }", "3.0+0.0i, 4.0+0.0i");
        assertEval("{ rowMeans(matrix((1:6)*(1+1i), nrow=2)) }", "3.0+3.0i, 4.0+4.0i");

        assertEval("{ o <- outer(1:3, 1:4, \"<\") ; colSums(o) }", "0.0, 1.0, 2.0, 3.0");
    }

    @Test
    public void testNChar() throws RecognitionException {
        assertEval("{ nchar(c(\"hello\", \"hi\")) }", "5L, 2L");
        assertEval("{ nchar(c(\"hello\", \"hi\", 10, 130)) }", "5L, 2L, 4L, 5L"); // incompatible with R because of different number printing
        assertEval("{ nchar(c(10,130)) }", "4L, 5L"); // incompatible with R because of different number printing
    }

    @Test
    public void testStrSplit() throws RecognitionException {
        assertEval("{ strsplit(\"helloh\", \"h\", fixed=TRUE) }", "[[1]]\n\"\", \"ello\"");
        assertEval("{ strsplit( c(\"helloh\", \"hi\"), c(\"h\",\"\"), fixed=TRUE) }", "[[1]]\n\"\", \"ello\"\n\n[[2]]\n\"h\", \"i\"");
        assertEval("{ strsplit(\"helloh\", \"\", fixed=TRUE) }", "[[1]]\n\"h\", \"e\", \"l\", \"l\", \"o\", \"h\"");
        assertEval("{ strsplit(\"helloh\", \"h\") }", "[[1]]\n\"\", \"ello\"");
        assertEval("{ strsplit( c(\"helloh\", \"hi\"), c(\"h\",\"\")) }", "[[1]]\n\"\", \"ello\"\n\n[[2]]\n\"h\", \"i\"");
        assertEval("{ strsplit(\"ahoj\", split=\"\") [[c(1,2)]] }", "\"h\"");
    }

    @Test
    public void testPaste() throws RecognitionException {
        assertEval("{ paste(1:2, 1:3, FALSE, collapse=NULL) }", "\"1L 1L FALSE\", \"2L 2L FALSE\", \"1L 3L FALSE\"");
        assertEval("{ paste(1:2, 1:3, FALSE, collapse=\"-\", sep=\"+\") }", "\"1L+1L+FALSE-2L+2L+FALSE-1L+3L+FALSE\"");
        assertEval("{ paste() }", "character(0)");
        assertEval("{ paste(sep=\"\") }", "character(0)");
        assertEval("{ a <- as.raw(200) ; b <- as.raw(255) ; paste(a, b) }", "\"c8 ff\"");
    }

    @Test
    public void testSubstring() throws RecognitionException {
        assertEval("{ substr(\"123456\", start=2, stop=4) }", "\"234\"");
        assertEval("{ substr(\"123456\", start=2L, stop=4L) }", "\"234\"");
        assertEval("{ substr(\"123456\", start=2.8, stop=4) }", "\"234\"");
        assertEval("{ substr(c(\"hello\", \"bye\"), start=c(1,2,3), stop=4) }", "\"hell\", \"ye\"");
        assertEval("{ substr(\"fastr\", start=NA, stop=2) }", "NA");

        assertEval("{ substring(\"123456\", first=2, last=4) }", "\"234\"");
        assertEval("{ substring(\"123456\", first=2.8, last=4) }", "\"234\"");
        assertEval("{ substring(c(\"hello\", \"bye\"), first=c(1,2,3), last=4) }", "\"hell\", \"ye\", \"ll\"");
        assertEval("{ substring(\"fastr\", first=NA, last=2) }", "NA");
    }

    @Test
    public void testOrder() throws RecognitionException {
        assertEval("{ order(1:3) }", "1L, 2L, 3L");
        assertEval("{ order(3:1) }", "3L, 2L, 1L");
        assertEval("{ order(c(1,1,1), 3:1) }", "3L, 2L, 1L");
        assertEval("{ order(c(1,1,1), 3:1, decreasing=FALSE) }", "3L, 2L, 1L");
        assertEval("{ order(c(1,1,1), 3:1, decreasing=TRUE, na.last=TRUE) }", "1L, 2L, 3L");
        assertEval("{ order(c(1,1,1), 3:1, decreasing=TRUE, na.last=NA) }", "1L, 2L, 3L");
        assertEval("{ order(c(1,1,1), 3:1, decreasing=TRUE, na.last=FALSE) }", "1L, 2L, 3L");
        assertEval("{ order() }", "NULL");
        assertEval("{ order(c(NA,NA,1), c(2,1,3)) }", "3L, 2L, 1L");
        assertEval("{ order(c(NA,NA,1), c(1,2,3)) }", "3L, 1L, 2L");
        assertEval("{ order(c(1,2,3,NA)) }", "1L, 2L, 3L, 4L");
        assertEval("{ order(c(1,2,3,NA), na.last=FALSE) }", "4L, 1L, 2L, 3L");
        assertEval("{ order(c(1,2,3,NA), na.last=FALSE, decreasing=TRUE) }", "4L, 3L, 2L, 1L");
        assertEval("{ order(c(0/0, -1/0, 2)) }", "2L, 3L, 1L");
        assertEval("{ order(c(0/0, -1/0, 2), na.last=NA) }", "2L, 3L");
    }

    @Test
    public void testMathFunctions() throws RecognitionException {
        assertEval("{ log(1) } ", "0.0");
        assertEval("{ m <- matrix(1:4, nrow=2) ; log10(m) }", "                   [,1]                [,2]\n[1,]                0.0 0.47712125471966244\n[2,] 0.3010299956639812  0.6020599913279624");

        assertEval("{ x <- c(a=1, b=10) ; c(log(x), log10(x), log2(x)) }", "  a                 b   a   b   a                  b\n0.0 2.302585092994046 0.0 1.0 0.0 3.3219280948873626");

        assertEval("{ sqrt(c(a=9,b=81)) }", "  a   b\n3.0 9.0");
    }

    @Test
    public void testCharUtils() throws RecognitionException {
        assertEval("{ toupper(c(\"hello\",\"bye\")) }", "\"HELLO\", \"BYE\"");
        assertEval("{ tolower(c(\"Hello\",\"ByE\")) }", "\"hello\", \"bye\"");
        assertEval("{ tolower(1E100) }", "\"1.0e100\"");
        assertEval("{ toupper(1E100) }", "\"1.0E100\"");
        assertEval("{ tolower(c()) }", "character(0)");
        assertEval("{ tolower(NA) }", "NA");
        assertEval("{ m <- matrix(\"hi\") ; toupper(m) }", "     [,1]\n[1,] \"HI\"");
        assertEval("{ toupper(c(a=\"hi\", \"hello\")) }", "   a        \n\"HI\" \"HELLO\"");
        assertEval("{ tolower(c(a=\"HI\", \"HELlo\")) }", "   a        \n\"hi\" \"hello\"");
    }

    @Test
    public void testTypeOf() throws RecognitionException {
        assertEval("{ typeof(1) }", "\"double\"");
        assertEval("{ typeof(1L) }", "\"integer\"");
        assertEval("{ typeof(sum) }", "\"builtin\"");
        assertEval("{ typeof(function(){}) }", "\"closure\"");
        assertEval("{ typeof(\"hi\") }", "\"character\"");
    }

    @Test
    public void testSub() throws RecognitionException {
        assertEval("{ gsub(\"a\",\"aa\", \"prague alley\", fixed=TRUE) }", "\"praague aalley\"");
        // TODO: re-enable (now pidigits screws up the builtin binding)
//        assertEval("{ sub(\"a\",\"aa\", \"prague alley\", fixed=TRUE) }", "\"praague alley\"");
        assertEval("{ gsub(\"a\",\"aa\", \"prAgue alley\", fixed=TRUE) }", "\"prAgue aalley\"");
        assertEval("{ gsub(\"a\",\"aa\", \"prAgue alley\", fixed=TRUE, ignore.case=TRUE) }", "\"praague aalley\"");
        assertEval("{ gsub(\"h\",\"\", c(\"hello\", \"hi\", \"bye\"), fixed=TRUE) }", "\"ello\", \"i\", \"bye\"");

        assertEval("{ gsub(\"a\",\"aa\", \"prague alley\") }", "\"praague aalley\"");
        // TODO: re-enable (now pidigits screws up the builtin binding)
//        assertEval("{ sub(\"a\",\"aa\", \"prague alley\") }", "\"praague alley\"");
        assertEval("{ gsub(\"a\",\"aa\", \"prAgue alley\") }", "\"prAgue aalley\"");
        assertEval("{ gsub(\"a\",\"aa\", \"prAgue alley\", ignore.case=TRUE) }", "\"praague aalley\"");
        assertEval("{ gsub(\"h\",\"\", c(\"hello\", \"hi\", \"bye\") }", "\"ello\", \"i\", \"bye\"");

        assertEval("{ gsub(\"([a-e])\",\"\\\\1\\\\1\", \"prague alley\") }", "\"praaguee aalleey\"");
    }

    @Test
    public void testRegExpr() throws RecognitionException {
        assertEval("gregexpr(\"(a)[^a]\\\\1\", c(\"andrea apart\", \"amadeus\", NA))", "[[1]]\n6L\n\n[[2]]\n1L\n\n[[3]]\nNA"); // NOTE: this is without attributes
        assertEval("regexpr(\"(a)[^a]\\\\1\", c(\"andrea apart\", \"amadeus\", NA))", "6L, 1L, NA"); // NOTE: this is without attributes
    }

    @Test
    public void testLength() throws RecognitionException {
        assertEval("{ x <- 1:4 ; length(x) <- 2 ; x }", "1L, 2L");
        assertEval("{ x <- 1:2 ; length(x) <- 4 ; x }", "1L, 2L, NA, NA");
        assertEval("{ x <- 1:2 ; z <- (length(x) <- 4) ; z }", "4.0");
        assertEval("{ length(c(z=1:4)) }", "4L");
    }

    @Test
    public void testNames() throws RecognitionException {
        assertEval("{ x <- 1:2 ; names(x) <- c(\"hello\", \"hi\"); names(x) } ", "\"hello\", \"hi\"");
        assertEval("{ x <- 1:2 ; names(x) <- c(\"hello\"); names(x) }", "\"hello\", NA");
        assertEval("{ x <- 1:2; names(x) <- c(\"hello\", \"hi\") ; x }", "hello hi\n   1L 2L");

        assertEval("{ x <- c(1,9); names(x) <- c(\"hello\",\"hi\") ; sqrt(x) }", "hello  hi\n  1.0 3.0");
        assertEval("{ x <- c(1,9); names(x) <- c(\"hello\",\"hi\") ; is.na(x) }", "hello    hi\nFALSE FALSE");
        assertEval("{ x <- c(1,NA); names(x) <- c(\"hello\",\"hi\") ; cumsum(x) }", "hello hi\n  1.0 NA");
        assertEval("{ x <- c(1,NA); names(x) <- c(NA,\"hi\") ; cumsum(x) }", "<NA> hi\n 1.0 NA");
        assertEval("{ x <- c(1,2); names(x) <- c(\"A\", \"B\") ; x + 1 }", "  A   B\n2.0 3.0");
        assertEval("{ x <- 1:2; names(x) <- c(\"A\", \"B\") ; y <- c(1,2,3,4) ; names(y) <- c(\"X\", \"Y\", \"Z\") ; x + y }", "  X   Y   Z <NA>\n2.0 4.0 4.0  6.0");
        assertEval("{ x <- 1:2; names(x) <- c(\"A\", \"B\") ; abs(x) }", " A  B\n1L 2L");
    }

    @Test
    public void testRev() throws RecognitionException {
        assertEval("{ rev(c(1+1i, 2+2i)) }", "2.0+2.0i, 1.0+1.0i");
        assertEval("{ rev(1:3) }", "3L, 2L, 1L");
    }

    @Test
    public void testEnvironment() throws RecognitionException {
        assertEval("{ f <- function() { assign(\"x\", 1) ; x } ; f() }", "1.0");
        assertEval("{ f <- function() { x <- 2 ; g <- function() { x <- 3 ; assign(\"x\", 1, inherits=FALSE) ; x } ; g() } ; f() }", "1.0");
        assertEval("{ f <- function() { x <- 2 ; g <- function() { assign(\"x\", 1, inherits=FALSE) } ; g() ; x } ; f() }", "2.0");
        assertEval("{ f <- function() { x <- 2 ; g <- function() { assign(\"x\", 1, inherits=TRUE) } ; g() ; x } ; f() }", "1.0");
        assertEval("{ f <- function() {  g <- function() { assign(\"x\", 1, inherits=TRUE) } ; g() } ; f() ; x }", "1.0");
        assertEval("{ x <- 3 ; g <- function() { x } ; f <- function() { assign(\"x\", 2) ; g() } ; f() }", "3.0");
        assertEval("{ x <- 3 ; f <- function() { assign(\"x\", 2) ; g <- function() { x } ; g() } ; f() }", "2.0");
        assertEval("{ h <- function() { x <- 3 ; g <- function() { x } ; f <- function() { assign(\"x\", 2) ; g() } ; f() }  ; h() }", "3.0");
        assertEval("{ h <- function() { x <- 3  ; f <- function() { assign(\"x\", 2) ; g <- function() { x } ; g() } ; f() }  ; h() }", "2.0");
        assertEval("{ x <- 3 ; h <- function() { g <- function() { x } ; f <- function() { assign(\"x\", 2, inherits=TRUE) } ; f() ; g() }  ; h() }", "2.0");
        assertEval("{ x <- 3 ; h <- function(s) { if (s == 2) { assign(\"x\", 2) } ; x }  ; h(1) ; h(2) }", "2.0");
        assertEval("{ x <- 3 ; h <- function(s) { y <- x ; if (s == 2) { assign(\"x\", 2) } ; c(y,x) }  ; c(h(1),h(2)) }", "3.0, 3.0, 3.0, 2.0");

        assertEval("{ f <- function()  { as.environment(-1) } ; f() }", "<environment: R_GlobalEnv>");
        assertEval("{ emptyenv() }", "<environment: R_EmptyEnv>");
        assertEval("{ x <- 3 ; f <- function() { exists(\"x\") } ; f() }", "TRUE");
        assertEval("{ x <- 3 ; f <- function() { exists(\"x\", inherits=FALSE) } ; f() }", "FALSE");
        assertEval("{ h <- new.env(parent=emptyenv()) ; assign(\"x\", 1, h) ; assign(\"y\", 2, h) ; ls(h) }", "\"x\", \"y\"");

        // lookup
        assertEval("{ f <- function() { x <- 2 ; get(\"x\") } ; f() }", "2.0");
        assertEval("{ x <- 3 ; f <- function() { get(\"x\") } ; f() }", "3.0");
        assertEval("{ x <- 3 ; f <- function() { x <- 2 ; get(\"x\") } ; f() }", "2.0");
        assertEval("{ x <- 3 ; f <- function() { x <- 2; h <- function() {  get(\"x\") }  ; h() } ; f() }", "2.0");
        assertEval("{ x <- 3 ; f <- function() { assign(\"x\", 4) ; h <- function(s=1) { if (s==2) { x <- 5 } ; x } ; h() } ; f() }", "4.0");
        assertEval("{ f <- function() { assign(\"x\", 2, inherits=TRUE) ; assign(\"x\", 1) ; h <- function() { x } ; h() } ; f() }", "1.0");
        assertEval("{ x <- 3 ; g <- function() { if (FALSE) { x <- 2 } ; f <- function() { h <- function() { x } ; h() } ; f() } ; g() }", "3.0");
        assertEval("{ x <- 3 ; gg <- function() {  g <- function() { if (FALSE) { x <- 2 } ; f <- function() { h <- function() { x } ; h() } ; f() } ; g() } ; gg() }", "3.0");

        // lookup with function matching
        assertEval("{ x <- function(){3} ; f <- function() { assign(\"x\", function(){4}) ; h <- function(s=1) { if (s==2) { x <- 5 } ; x() } ; h() } ; f() }", "4.0");
        assertEval("{ f <- function() { assign(\"x\", function(){2}, inherits=TRUE) ; assign(\"x\", function(){1}) ; h <- function() { x() } ; h() } ; f() }", "1.0");
        assertEval("{ x <- function(){3} ; g <- function() { if (FALSE) { x <- 2 } ; f <- function() { h <- function() { x() } ; h() } ; f() } ; g() }", "3.0");
        assertEval("{ x <- function(){3} ; gg <- function() {  g <- function() { if (FALSE) { x <- 2 } ; f <- function() { h <- function() { x() } ; h() } ; f() } ; g() } ; gg() }", "3.0");

        // lookup with super assignment
        assertEval("{ x <- 3 ; f <- function() { assign(\"x\", 4) ; h <- function(s=1) { if (s==2) { x <- 5 } ; x <<- 6 } ; h() ; get(\"x\") } ; f() }", "6.0");
        assertEval("{ x <- 3 ; f <- function() { assign(\"x\", 4) ; hh <- function() { if (FALSE) { x <- 100 } ; h <- function() { x <<- 6 } ; h() } ; hh() ; get(\"x\") } ; f() }", "6.0");
        assertEval("{ x <- 3 ; g <- function() { if (FALSE) { x <- 2 } ; f <- function() { h <- function() { x ; hh <- function() { x <<- 4 } ; hh() } ; h() } ; f() } ; g() ; x }", "4.0");

        // hashmaps
        assertEval("{ h <- new.env(parent=emptyenv()) ; assign(\"x\", 1, h) ; exists(\"x\", h) }", "TRUE");
        assertEval("{ h <- new.env(parent=emptyenv()) ; assign(\"x\", 1, h) ; exists(\"xx\", h) }", "FALSE");

        // top-level lookups
        assertEval("{ exists(\"sum\") }", "TRUE");
        assertEval("{ exists(\"sum\", inherits = FALSE) }", "FALSE");
        assertEval("{ x <- 1; exists(\"x\", inherits = FALSE) }", "TRUE");
    }

    @Test
    public void testOther() throws RecognitionException {
        assertEval("{ rev.mine <- function(x) { if (length(x)) x[length(x):1L] else x } ; rev.mine(1:3) }", "3L, 2L, 1L");
    }


}
