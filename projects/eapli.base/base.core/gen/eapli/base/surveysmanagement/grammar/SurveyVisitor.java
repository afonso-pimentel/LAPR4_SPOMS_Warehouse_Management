// Generated from D:/LAPR4_REPO/projects/eapli.base/base.core/src/main/java/eapli/base/surveysmanagement/grammar\Survey.g4 by ANTLR 4.10.1
package eapli.base.surveysmanagement.grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SurveyParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SurveyVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SurveyParser#survey}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSurvey(SurveyParser.SurveyContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#welcome_Message}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWelcome_Message(SurveyParser.Welcome_MessageContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#final_Message}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFinal_Message(SurveyParser.Final_MessageContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#section}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSection(SurveyParser.SectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#section_ID}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSection_ID(SurveyParser.Section_IDContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#section_Title}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSection_Title(SurveyParser.Section_TitleContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#category}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCategory(SurveyParser.CategoryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion(SurveyParser.QuestionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#question_ID}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion_ID(SurveyParser.Question_IDContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruction(SurveyParser.InstructionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#why}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhy(SurveyParser.WhyContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#extra_Info}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtra_Info(SurveyParser.Extra_InfoContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#answer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnswer(SurveyParser.AnswerContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#single_Choice}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingle_Choice(SurveyParser.Single_ChoiceContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#single_Choice_With_Input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingle_Choice_With_Input(SurveyParser.Single_Choice_With_InputContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#multiple_Choice}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiple_Choice(SurveyParser.Multiple_ChoiceContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#multiple_Choice_With_Input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiple_Choice_With_Input(SurveyParser.Multiple_Choice_With_InputContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#sorting_Options}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSorting_Options(SurveyParser.Sorting_OptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SurveyParser#scaling_Options}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScaling_Options(SurveyParser.Scaling_OptionsContext ctx);
}