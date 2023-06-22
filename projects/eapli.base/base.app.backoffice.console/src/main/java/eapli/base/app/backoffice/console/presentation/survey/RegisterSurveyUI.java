/*
 * Copyright (c) 2021-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package eapli.base.app.backoffice.console.presentation.survey;

import eapli.base.surveysmanagement.application.RegisterSurveyController;
import eapli.base.surveysmanagement.application.RegisterSurveyControllerImpl;
import eapli.base.surveysmanagement.application.SurveyRuleDTO;
import eapli.base.surveysmanagement.domain.Survey;
import eapli.base.surveysmanagement.domain.SurveyRules;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import org.apache.commons.io.IOUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Extends AbstractUI
 */
public class RegisterSurveyUI extends AbstractUI {
    private final RegisterSurveyController registerSurveyController;

    /**
     * Initializes a new instance of RegisterSurveyUI
     */
    public RegisterSurveyUI(){
        this.registerSurveyController = new RegisterSurveyControllerImpl();
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    protected boolean doShow() {
        try {
            final String surveyCode = Console.readNonEmptyLine("Survey Code: ", "Survey Code cannot be empty!");

            if(surveyCode.length() > 10){
                System.out.println("SurveyCode cannot be bigger than 10 characters");
                return false;
            }

            final String surveyDescription =  Console.readNonEmptyLine("Survey description: ", "Survey description cannot be empty!");

            if(surveyDescription.length() < 10){
                System.out.println("SurveyDescription cannot be less than 10 characters");
            }

            final Date startDate = Console.readDate("Start date(e.g 01/01/2022): ", "dd/MM/yyyy");

            final Date endDate = Console.readDate("End date(e.g 12/12/2022: ", "dd/MM/yyyy");

            final String pathToQuestionaireFile = Console.readNonEmptyLine("Path to questionaire file: ", "Path to questionaire file cannot be empty!");

            final SelectWidget<SurveyRules> surveyRulesSelector = new SelectWidget<>("Available survey rules:", SurveyRules.availableRules(), new SurveyRulesPrinter());
            surveyRulesSelector.show();

            var surveyRulesList = new ArrayList<SurveyRuleDTO>();

            while(surveyRulesSelector.selectedOption() != 0){
                SurveyRuleDTO surveyRule = getSurveyRule(surveyRulesSelector.selectedOption());
                surveyRulesList.add(surveyRule);

                surveyRulesSelector.show();
            }

            final InputStream questionaireFileStream =  new FileInputStream(pathToQuestionaireFile);
            final byte[] questionaireFile = IOUtils.toByteArray(questionaireFileStream);

            if(questionaireFile.length > 60000){
                System.out.println("The uploaded file questioner is to big. The maximum amount of a questioner file is 60000");
            }

            Survey insertedSurvey = registerSurveyController.addSurvey(surveyCode, surveyDescription, startDate, endDate, questionaireFile, surveyRulesList);

            System.out.println("Survey added successfully!");

            var printer = new SurveyPrinter();
            printer.visit(insertedSurvey);

        } catch (ParseException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("File not found exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        } catch(IntegrityViolationException e){
            System.out.println("A Survey with the same survey code already exists");
        }catch(IllegalArgumentException e){
            System.out.println("Invalid argument provided: " + e.getMessage());
        }
        catch(Exception e){
            System.out.println("A general occur has occurred: " + e.getMessage());
        }

        return false;
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public String headline() {
        return "Register Survey";
    }

    private SurveyRuleDTO getSurveyRule(int selectOption) throws ParseException {
        var surveyRule = new SurveyRuleDTO();

        switch(selectOption){
            case 1:
                String productCode = Console.readNonEmptyLine("Insert a product code: ", "Product code cannot be empty!");
                var startDate = String.valueOf(Console.readDate("Insert initial bought interval: ", "dd/MM/yyyy"));
                var endDate = String.valueOf(Console.readDate("Insert final bought interval: ", "dd/MM/yyyy"));

                surveyRule.value = productCode;
                surveyRule.startPeriod = startDate;
                surveyRule.endPeriod = endDate;
                surveyRule.rule = SurveyRules.PERIOD_PRODUCT;
                break;
            case 2:
                String brand = Console.readNonEmptyLine("Insert a brand: ", "Brand cannot be empty!");
                startDate = String.valueOf(Console.readDate("Insert initial bought interval: ", "dd/MM/yyyy"));
                endDate = String.valueOf(Console.readDate("Insert final bought interval: ", "dd/MM/yyyy"));

                surveyRule.value = brand;
                surveyRule.startPeriod = startDate;
                surveyRule.endPeriod = endDate;
                surveyRule.rule = SurveyRules.PERIOD_BRAND;
                break;
            case 3:
                String category = Console.readNonEmptyLine("Insert a category: ", "Category cannot be empty!");
                startDate = String.valueOf(Console.readDate("Insert initial bought interval: ", "dd/MM/yyyy"));
                endDate = String.valueOf(Console.readDate("Insert final bought interval: ", "dd/MM/yyyy"));

                surveyRule.value = category;
                surveyRule.startPeriod = startDate;
                surveyRule.endPeriod = endDate;
                surveyRule.rule = SurveyRules.PERIOD_CATEGORY;
                break;
            case 4:
                int ageRange1 = Console.readInteger("Insert age range 1: ");
                int ageRange2 = Console.readInteger("Insert age range 2: ");

                surveyRule.value = String.valueOf(ageRange1);
                surveyRule.value2 = String.valueOf(ageRange2);
                surveyRule.rule = SurveyRules.PERIOD_AGE;
                break;
            case 5:
                String gender = Console.readNonEmptyLine("Insert gender: ", "Gender cannot be empty!");

                surveyRule.value = gender;
                surveyRule.rule = SurveyRules.SCALAR_GENDER;
                break;
            case 6:
                surveyRule.value = "ALL CUSTOMERS";
                surveyRule.rule = SurveyRules.SCALAR_ALL_CUSTOMERS;
                break;
            default:
                return null;
        }

        return surveyRule;
    }
}

