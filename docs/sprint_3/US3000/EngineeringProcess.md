Firstly lets compact all of the information we have that might be important for the survey's grammar definition:

* A questionnaire is a set of questions organized into one or more sections (i.e., a
group of questions). Each question is of a certain type (e.g.: free text, multiple choices,
ordering/ranking choices) which constraints the information to be requested.

* A questionnaire might have mandatory and optional sections/questions and that some
sections/questions might become mandatory/optional.

* It must be allowed that questions of a given section are repeatedly made depending
on some condition (e.g.: questions of section X needs to be answered 3 times since “3” is an answer
provided previously on another section).

Since they are organized into different sections we should take a look at what is what.

The base of the survey is the following:

| Field              | Description/Example                                                                                                                                                                                       |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Id                 | Mandatory alphanumeric value to univocally identify a questionnaire.<br>E.g.: “COSM22-01”.                                                                                                                |
| Title              | The title of the questionnaire. It is a mandatory short sentence.<br>E.g.: “Cosmetics Questionnaire”.                                                                                                     |
| Welcome<br>Message | An optional message to be presented before any section/question.<br>E.g.: “Hello,<br>This questionnaire aims to…<br>It takes approximately 10 minutes.<br>Thank you very much for your time and support.” |
| List of Sections   | A non-empty list of sections. Answering to a section might be “mandatory”,<br>“optional” or “condition dependent”. At least one section needs to be<br>“mandatory”.                                       |
| Final Message      | E.g.: “You have successfully completed the questionnaire. \[…\]<br>Thank you for your help.”                                                                                                              |

Going into the groups, a section is defined as:

| Field                  | Description/Example                                                                                                                                                                                                           |
|------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Section ID             | Mandatory numeric value to univocally identify a section inside a questionnaire.<br>Usually, it follows the sequence. E.g.: 1, 2, 3, …                                                                                        |
| Section Title          | The title of the section. It is a mandatory short sentence.<br>E.g.: “Basic Personal Information”.                                                                                                                            |
| Section<br>Description | An optional message highlighting some concern/purpose of the section. Multiple<br>sentences need to be supported.                                                                                                             |
| Obligatoriness         | It might be one of “mandatory”, “optional” or “condition dependent”. If the latter<br>is selected, a condition needs to be set.                                                                                               |
| Repeatability          | Optional condition stating if the questions of this section are to be answered more<br>than once. At least two kinds of conditions need to be supported: (i) based on<br>numeric answers or (ii) on a set of selected values. |
| Content                | A non-empty set of questions forming the section content.                                                                                                                                                                     |

And last but not least, Questions are defined as:

| Field          | Description/Example                                                                                                                     |
|----------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| Question ID    | Mandatory numeric value to univocally identify a question inside a questionnaire.<br>Usually, it follows the sequence. E.g.: 1, 2, 3, … |
| Question       | A free text stating the question to be answered.<br>E.g.: “How willing are you to try different cosmetic products?”                     |
| Instruction    | Optionally a text with some answering instruction might be provided.                                                                    |
| Type           | Defines the kind of answer and available options (cf. 5.1.2).                                                                           |
| Obligatoriness | It might be one of “mandatory”, “optional” or “condition dependent”. If the latter<br>is selected, a condition needs to be set.         |
| Extra Info     | Additional structured information depending on the question type.                                                                       |

Inside Questions we also have various types:

| Type                              | Description/Example                                                                                                                                                                                                                                              |
|-----------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Free-Text:                        | the person answers the question by typing some text (cf. Figure 3). In some<br>case, it is necessary to limit the amount of text (e.g.: single line, multiple lines setting a maximum<br>number of chars per line).                                              |
| Numeric:                          | the person answers the question by typing a numeric value. By default, no<br>decimals are allowed. When no decimals are allowed, the question might be used to determine<br>the repeatability of a section.                                                      |
| Single-Choice:                    | the person answers the question by selection one (and just one) of the<br>provided options (cf. Figure 4). Each option consists of a value (not showed to the person) and a<br>description. At least to option must be specified                                 |
| Single-Choice with input value:   | very similar to the “single choice” but the last option, if selected,<br>implies that the person must type a numeric value or a free text.                                                                                                                       |
| Multiple-Choice:                  | very similar to the “single choice”, but instead of selection just one, the<br>answering person might select more than one (cf. Figure 5). Sometimes, it is necessary to set a<br>limit (e.g., maximum 3 option can be selected). By default, there is no limit. |
| Multiple-Choice with input value: | very similar to the “multiple choice”, but the last option, if<br>selected, implies that the person must type a numeric value or a free text (cf. Figure 6).                                                                                                     |
| Sorting Options:                  | given two or more option the person answers the question by sorting the options<br>as desired and in accordance with the instructions provided. By default, no order is assumed.                                                                                 |
| Scaling Options:                  | the person answers the question by selecting a value of a given scale<br>(e.g.: unimportant, neutral, important) to each of the specified options (cf. Figure 7). The adopted<br>scale is the same for all the options of a given question.                      |

Now we have the necessary information to define the grammar, obviously with a mix of common sense.

We will be using ANTLR as the main tool to define the parser grammar.

### What is ANTLR?

ANTLR is a parser generator, a tool that helps you to create parsers. **A parser takes a piece of text
and transforms it into an organized structure**, a parse tree, also known as an Abstract Syntax Tree
(AST). You can think of the AST as a story describing the content of the code, or also as its logical
representation, created by putting together the various pieces.

The parser grammar is, in a way, universal. We can then define a parser per programming language. One parser for Java, one for C#, etc.

## What is a Lexer?

A lexer, also known as tokenizer, is the first stepping stone towards a parser. A lexer is a scanner that takes the individual characters from a given file or text and transforms them in tokens.

For example:

![lexer](US2004/lexer.png)

## Creating a Grammar

We have two possible approaches regarding the definition of a Grammar: Top-down and bottom-up.

### Top-Down Approach

This approach consists in starting from the general organization of a file written in our language.
What is the main section of a file? What is its order? What is contained in each section?
For example, a Java file can be divided in three sections:

* package declaration
* imports
* type definitions

This approach works best when you already know the language or format that you are designing a
grammar for. It is probably the strategy preferred by people with a good theoretical background or
people who prefer to start with “the big plan”.
When using this approach, you start by defining the rule representing the whole file. It will
probably include other rules, to represent the main sections. You then define those rules, and you
move from the most general, abstract rules to the low-level, practical ones.

### Bottom-Up Approach

The bottom-up approach consists in focusing on the small elements first: defining how the tokens
are captured, how the basic expressions are defined and so on. Then we move to higher level
constructs until we define the rule representing the whole file.

We could prefer to start from the bottom, the basic items, that are analyzed with the lexer. And
then we grow naturally from there to the structure, that is dealt with the parser. This approach
allows to focus on a small piece of the grammar, build tests for that, ensure it works as expected
and then move on to the next bit.

This approach mimics the way we learn. Furthermore, there is the advantage of starting with real
code that is actually quite common among many languages. In fact, most languages have things
like identifiers, comments, whitespace, etc.

The disadvantage of a bottom-up approach rests on the fact that the parser is the thing you
actually care about. You were not asked to build a lexer, you were asked to build a parser, that
could provide a specific functionality. So by starting on the last part, the lexer, you might end up
doing some refactoring, if you do not already know how the rest of the program will work.

### What approach should we choose?

We have two sides to this, on one hand we have really solid information about the structure of a Survey. 
On another hand, we are being requested to create a tool for surveys of all types, create a generic tool.

We should opt for the Top-Down approach. As said above, in the Bottom-Up approach we would most likely have
to refactor some code, this might save us some time and work.

## Steps to follow

### Defining field by field:

#### Base Structure:

 * ID : SURVEY_CODE (e.g.: "COSM22-01")
 * Title : FREE_TEXT 'Questionnaire' (e.g.: "Cosmetics Questionnaire")
 * Welcome Message : Optional  GREETING_MESSAGE OBJECTIVE DURATION_MESSAGE THANK_YOU_MESSAGE (e.g.: "Hello, NEWLINE This questionnaire aims to OBJECTIVE NEW LINE It takes approximately 10 minutes. NEWLINE Thank you very much for your time and support.”)
 * List Of Sections : Section+
 * Final Message : SUCCESS_MESSAGE THANK_YOU_MESSAGE EOF

#### Section Structure

 * Section ID : POSITIVE_NUMERIC (e.g.: 1,2,3,...)
 * Section Title : category
 * Section Description : Optional OBJECTIVE
 * Obligatoriness : OBLIGATORINESS (e.g.: mandatory | optional | condition dependent)
 * Repeatability : Optional CONDITION (e.g.: based on numeric answers | on a set of selected values)
 * Content : question+

#### Question Structure

* Question ID : POSITIVE_NUMERIC (e.g.: 1,2,3,...)
* Question : QUESTION_TEXT (e.g.: "How willing are you to try different cosmetic products?")
* Instruction : Optional FREE_TEXT
* Scale Value Header : Optional SCALE_VALUE_HEADER
* Options : OPTION*
* Input Value Label : QUESTION_TEXT
* Obligatoriness : OBLIGATORINESS (e.g.: mandatory | optional | condition dependent)
* Extra Info : Optional FREE_TEXT

#### Question Types / Answer Kind

* Free Text : (WORD | NAME | POSITIVE_NUMERIC | WS | SPECIAL |APOSTROPHE | COMMA)+;
* Numeric : POSITIVE_NUMERIC | ZERO;
* Single-Choice : OPTION
* Single-Choice with Input Value : (single_Choice INPUT_ANSWER);
* Multiple-Choice : (OPTION COMMA)+(OPTION);
* Multiple-Choice with input value : (multiple_Choice INPUT_ANSWER);
* Sorting Options : multiple_Choice;
* Scaling Options : (SCALE_VALUE WS OPTION)(scaling_Options)+;

#### Other Values

 * Option : POSITIVE_NUMERIC ' - ' (FREE_TEXT | NAME | UPPERCASE_WORD);
 * Input Answer : INPUT_LABEL FREE_TEXT;
 * Input Label : "Answer:";
 * Scale Value : ("Strongly Agree" | "Agree" | "Neutral" | "Disagree" | "Strongly Disagree")
 * SCALE_VALUE_HEADER : 'Strongly Agree | Agree | Neutral | Disagree | Strongly Disagree';

Check the grammar for any other elements that may be relevant.