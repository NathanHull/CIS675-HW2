# CIS675-HW2
DOT Lexer

My attempt at a Java lexer for the graphviz graph visualization software suite language, DOT. The language's documentation can be found here: http://www.graphviz.org/pdf/dotguide.pdf.

## Input
        digraph G {
            main [shape=box]; /* this is a comment */
            main -> parse [weight=8];
            parse -> execute;
            main -> init [style=dotted];
            main -> cleanup;
            execute -> make_string;
            init -> make_string;
            main -> printf [style=bold,label="100 times"];
            make_string [label="make a\nstring"];
            node [shape=box,style=filled,color=".7 .3 1.0"];
            execute -> compare;
         }
