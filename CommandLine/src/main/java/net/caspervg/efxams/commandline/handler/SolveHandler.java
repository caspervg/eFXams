/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Casper Van Gheluwe
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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.caspervg.efxams.commandline.handler;

import com.google.common.io.Files;
import net.caspervg.efxams.backend.BackendFactory;
import net.caspervg.efxams.backend.ExamBackend;
import net.caspervg.efxams.backend.beans.Exam;
import net.caspervg.efxams.backend.beans.Question;
import net.caspervg.efxams.backend.exception.ExamBackendException;
import net.caspervg.efxams.commandline.argument.Command;
import net.caspervg.efxams.commandline.argument.CommandRead;
import net.caspervg.efxams.commandline.argument.CommandSolve;

import java.util.List;
import java.util.Scanner;

public class SolveHandler implements CommandHandler {
    @Override
    public boolean handle(Command command) {
        CommandSolve solve;

        if (command instanceof CommandRead) {
            solve = (CommandSolve) command;
        } else {
            System.err.println("Could not parse read command");
            return false;
        }

        String fileExtension = Files.getFileExtension(solve.getFile().getName());
        ExamBackend backend = BackendFactory.getBackend(fileExtension);
        try {
            Exam exam = backend.unmarshallExam(solve.getFile());
            Scanner in = new Scanner(System.in);

            System.out.println(String.format("Welcome the \"%s\" exam, made by %s!", exam.getName(), exam.getAuthor()));
            System.out.println("Type HINT if you want a hint");
            List<Question> questionList = exam.getQuestions();

            int correct = 0, wrong = 0;
            for (int i = 0; i < questionList.size(); i++) {
                Question question = questionList.get(i);


                System.out.println("");
                System.out.println(String.format("Question %d", (i+1)));

                System.out.println(question.getQuery());

                System.out.println("Write your answer below: ");
                String userAnswer = in.nextLine();

                int numHints = question.getHints().size(), hintsGiven = 0;
                while (userAnswer.equals("HINT")) {
                    if (hintsGiven >= numHints) {
                        System.out.println("There are no hints left for this question.");
                    } else {
                        System.out.format("Hint %d for Question %d: %s", (hintsGiven + 1), (i + 1), question.getHints().get(hintsGiven));
                        hintsGiven += 1;

                        System.out.println("Write your answer below: ");
                        userAnswer = in.nextLine();
                    }
                }

                if (evaluateAnswer(userAnswer, question)) {
                    correct++;
                    System.out.println("Correct!");
                    System.out.println("Current score: ");
                } else {
                    wrong++;
                    System.out.println("Sorry, that's wrong! The correct answer was: ");
                    System.out.println(question.getAnswer());
                    System.out.println("Current score: ");
                }
                System.out.format("+%d | -%d | #%5f", correct, wrong, (double) correct / (double) wrong);
            }

        } catch (ExamBackendException e) {
            System.err.println("Could not read exam from file " + e.toString());
            return false;
        }

        return true;
    }

    private boolean evaluateAnswer(String answer, Question question) {
        if (answer.equalsIgnoreCase(question.getAnswer())) return true;

        for (String required : question.getAllowedWords()) {
            if (! answer.toLowerCase().contains(required.toLowerCase())) return false;
        }

        for (String banned : question.getBannedWords()) {
            if (answer.toLowerCase().contains(banned.toLowerCase())) return false;
        }

        return true;
    }
}
