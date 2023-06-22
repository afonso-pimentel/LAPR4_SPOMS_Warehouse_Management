// Generated from D:/LAPR4_REPO/projects/eapli.base/base.core/src/main/java/eapli/base/surveysmanagement/grammar\Survey.g4 by ANTLR 4.10.1
package eapli.base.surveysmanagement.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SurveyParser}.
 */
public interface SurveyListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SurveyParser#survey}.
	 * @param ctx the parse tree
	 */
	void enterSurvey(SurveyParser.SurveyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#survey}.
	 * @param ctx the parse tree
	 */
	void exitSurvey(SurveyParser.SurveyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#welcome_Message}.
	 * @param ctx the parse tree
	 */
	void enterWelcome_Message(SurveyParser.Welcome_MessageContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#welcome_Message}.
	 * @param ctx the parse tree
	 */
	void exitWelcome_Message(SurveyParser.Welcome_MessageContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#final_Message}.
	 * @param ctx the parse tree
	 */
	void enterFinal_Message(SurveyParser.Final_MessageContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#final_Message}.
	 * @param ctx the parse tree
	 */
	void exitFinal_Message(SurveyParser.Final_MessageContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#section}.
	 * @param ctx the parse tree
	 */
	void enterSection(SurveyParser.SectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#section}.
	 * @param ctx the parse tree
	 */
	void exitSection(SurveyParser.SectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#section_ID}.
	 * @param ctx the parse tree
	 */
	void enterSection_ID(SurveyParser.Section_IDContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#section_ID}.
	 * @param ctx the parse tree
	 */
	void exitSection_ID(SurveyParser.Section_IDContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#section_Title}.
	 * @param ctx the parse tree
	 */
	void enterSection_Title(SurveyParser.Section_TitleContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#section_Title}.
	 * @param ctx the parse tree
	 */
	void exitSection_Title(SurveyParser.Section_TitleContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#category}.
	 * @param ctx the parse tree
	 */
	void enterCategory(SurveyParser.CategoryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#category}.
	 * @param ctx the parse tree
	 */
	void exitCategory(SurveyParser.CategoryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#question}.
	 * @param ctx the parse tree
	 */
	void enterQuestion(SurveyParser.QuestionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#question}.
	 * @param ctx the parse tree
	 */
	void exitQuestion(SurveyParser.QuestionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#question_ID}.
	 * @param ctx the parse tree
	 */
	void enterQuestion_ID(SurveyParser.Question_IDContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#question_ID}.
	 * @param ctx the parse tree
	 */
	void exitQuestion_ID(SurveyParser.Question_IDContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstruction(SurveyParser.InstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstruction(SurveyParser.InstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#why}.
	 * @param ctx the parse tree
	 */
	void enterWhy(SurveyParser.WhyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#why}.
	 * @param ctx the parse tree
	 */
	void exitWhy(SurveyParser.WhyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#extra_Info}.
	 * @param ctx the parse tree
	 */
	void enterExtra_Info(SurveyParser.Extra_InfoContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#extra_Info}.
	 * @param ctx the parse tree
	 */
	void exitExtra_Info(SurveyParser.Extra_InfoContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#answer}.
	 * @param ctx the parse tree
	 */
	void enterAnswer(SurveyParser.AnswerContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#answer}.
	 * @param ctx the parse tree
	 */
	void exitAnswer(SurveyParser.AnswerContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#single_Choice}.
	 * @param ctx the parse tree
	 */
	void enterSingle_Choice(SurveyParser.Single_ChoiceContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#single_Choice}.
	 * @param ctx the parse tree
	 */
	void exitSingle_Choice(SurveyParser.Single_ChoiceContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#single_Choice_With_Input}.
	 * @param ctx the parse tree
	 */
	void enterSingle_Choice_With_Input(SurveyParser.Single_Choice_With_InputContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#single_Choice_With_Input}.
	 * @param ctx the parse tree
	 */
	void exitSingle_Choice_With_Input(SurveyParser.Single_Choice_With_InputContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#multiple_Choice}.
	 * @param ctx the parse tree
	 */
	void enterMultiple_Choice(SurveyParser.Multiple_ChoiceContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#multiple_Choice}.
	 * @param ctx the parse tree
	 */
	void exitMultiple_Choice(SurveyParser.Multiple_ChoiceContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#multiple_Choice_With_Input}.
	 * @param ctx the parse tree
	 */
	void enterMultiple_Choice_With_Input(SurveyParser.Multiple_Choice_With_InputContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#multiple_Choice_With_Input}.
	 * @param ctx the parse tree
	 */
	void exitMultiple_Choice_With_Input(SurveyParser.Multiple_Choice_With_InputContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#sorting_Options}.
	 * @param ctx the parse tree
	 */
	void enterSorting_Options(SurveyParser.Sorting_OptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#sorting_Options}.
	 * @param ctx the parse tree
	 */
	void exitSorting_Options(SurveyParser.Sorting_OptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SurveyParser#scaling_Options}.
	 * @param ctx the parse tree
	 */
	void enterScaling_Options(SurveyParser.Scaling_OptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SurveyParser#scaling_Options}.
	 * @param ctx the parse tree
	 */
	void exitScaling_Options(SurveyParser.Scaling_OptionsContext ctx);
}