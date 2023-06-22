//Defining a grammar called Answers

grammar AnswerGrammar;

answers : answer_Struct+EOF{1};

answer_Struct : (question_ID SEPARATOR answer);

question_ID : POSITIVE_NUMERIC;

answer : (single_Choice | single_Choice_With_Input | multiple_Choice | multiple_Choice_With_Input | sorting_Options | scaling_Options | simple_Answer);

//Type of answers

multiple_Choice : '{' multiple_Options '}';

multiple_Choice_With_Input : (multiple_Choice) (input_Answer);

sorting_Options : CARDINAL (multiple_Options) CARDINAL;

scaling_Options : SCALING_SEP ((scaling_Option) COMMA)+(scaling_Option) SCALING_SEP;

single_Choice : option;

single_Choice_With_Input : (single_Choice) (input_Answer);

simple_Answer : FREE_TEXT;

//Basis Answer Elements

multiple_Options : ((option)COMMA)+(option);

scaling_Option :  option SCALING_OP scale_Answer SCALING_OP;

scale_Answer : SCALE_VALUE;

input_Answer : INPUT_SEP simple_Answer INPUT_SEP;

option : (POSITIVE_NUMERIC '(' simple_Answer ')');

//GENERIC
POSITIVE_NUMERIC : [1-9]|([1-9]([0-9]+));

//Separators

CARDINAL : '#';

INPUT_SEP : '%in%';

SCALING_SEP : '%s%';

SCALING_OP : '%so%';

DOT : '.'-> skip;

DOT_COMMA : ';'-> skip;

COMMA : ',';

//Other Elements
SEPARATOR : '::';

NEWLINE: '\n' -> skip;

WS : ' '+ -> skip ;

APOSTROPHE : '\'' -> skip;

EQUAL : '=';

SPECIAL : [$&+=@|<>^*!];

HIFEN : '-';

SCALE_VALUE :  ('Strongly Agree' | 'Agree' | 'Neutral' | 'Disagree' | 'Strongly Disagree');

FREE_TEXT : (WORD | UPPERCASE_WORD | NAME | POSITIVE_NUMERIC | ZERO | WS | SPECIAL |APOSTROPHE | COMMA | HIFEN)+;

ZERO : '0';

NUMERIC : (('0'[1-9]) | ([1-9]([0-9]+)))+;

WORD : [a-z]+;

NAME : [A-Z]{1}[a-z]+;

UPPERCASE_WORD : [A-Z]+;