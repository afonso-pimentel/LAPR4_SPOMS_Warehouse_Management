//Defining a grammar called Survey

grammar Survey;

//Main Rule
survey : (SURVEY_CODE)
            (TITLE)
            (welcome_Message)?
            (section)+
            (final_Message);

welcome_Message : GREETING_MESSAGE OBJECTIVE DURATION_MESSAGE THANK_YOU_MESSAGE;

final_Message : SUCCESS_MESSAGE THANK_YOU_MESSAGE EOF;

//Second
section : (section_ID)
          (section_Title)
          (OBJECTIVE)?
          (OBLIGATORINESS)
          (CONDITION)?
          (question+);

section_ID : POSITIVE_NUMERIC;

section_Title : category;

category : NAME | FREE_TEXT;

//Fifth
question : (question_ID)
           (QUESTION_TEXT) //Every question needs this
           (instruction)?
           (SCALE_VALUE_HEADER)? //For scaling questions
           (OPTION)* //For single, multiple, sorting and scaling options
           (why)? //For choices with input
           (OBLIGATORINESS)
           (CONDITION)?
           (extra_Info)?;

question_ID : POSITIVE_NUMERIC;

instruction : FREE_TEXT;

why : QUESTION_TEXT;

extra_Info : FREE_TEXT;

//answer types - for the next sprint
answer : FREE_TEXT | ZERO | single_Choice | single_Choice_With_Input | multiple_Choice | multiple_Choice_With_Input | sorting_Options | scaling_Options;

single_Choice : OPTION;

single_Choice_With_Input : (single_Choice INPUT_ANSWER);

multiple_Choice : (OPTION COMMA)+(OPTION);

multiple_Choice_With_Input : (multiple_Choice INPUT_ANSWER);

sorting_Options : multiple_Choice;

scaling_Options : (SCALE_VALUE WS OPTION)(scaling_Options)+;

//Lexer
//GENERIC

INPUT_LABEL : 'Answer:';

OBLIGATORINESS : 'mandatory' | 'optional' | 'condition dependent';

CONDITION : 'based on numeric answers' | 'on a set of selected values' | 'based on a previous answered question';

SCALE_VALUE_HEADER : 'Strongly Agree | Agree | Neutral | Disagree | Strongly Disagree';

SCALE_VALUE :  ('Strongly Agree' | 'Agree' | 'Neutral' | 'Disagree' | 'Strongly Disagree');

SUCCESS_MESSAGE : 'You have successfully completed the survey.';

TIME_UNIT : ('hours'|'minutes'|'seconds');

INPUT_ANSWER : INPUT_LABEL FREE_TEXT;

POSITIVE_NUMERIC : [1-9]|([1-9]([0-9]+));

ZERO : '0';

NUMERIC : (('0'[1-9]) | ([1-9]([0-9]+)))+;

WORD : [a-z]+;

NAME : [A-Z]{1}[a-z]+;

UPPERCASE_WORD : [A-Z]+;

DOT : '.'-> skip;

DOT_COMMA : ';'-> skip;

COMMA : ','-> skip;

NEWLINE: '\n' -> skip;

WS : ' '+ -> skip ;

APOSTROPHE : '\'' -> skip;

SPECIAL : [$&+=@#|<>^*()%!];

HIFEN : '-' -> skip;

//SPECIFIC
SURVEY_CODE : 'COSM22-' NUMERIC;

TITLE : FREE_TEXT 'Questionnaire';

GREETING_MESSAGE : 'Hello' WS NAME DOT;

OBJECTIVE : 'This aims to' WS FREE_TEXT DOT;

DURATION_MESSAGE : 'It will take you up to' WS POSITIVE_NUMERIC WS TIME_UNIT DOT;

THANK_YOU_MESSAGE : 'Thank You' WS FREE_TEXT DOT;

QUESTION_TEXT : NAME (WS (FREE_TEXT))* ('?'|':');

OPTION : POSITIVE_NUMERIC ' - ' (FREE_TEXT | NAME | UPPERCASE_WORD);

FREE_TEXT : (WORD | UPPERCASE_WORD | NAME | POSITIVE_NUMERIC | WS | SPECIAL |APOSTROPHE | COMMA | HIFEN)+;
