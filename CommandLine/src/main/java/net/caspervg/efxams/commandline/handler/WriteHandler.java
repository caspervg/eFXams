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
import net.caspervg.efxams.commandline.argument.CommandWrite;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WriteHandler implements CommandHandler {

    @Override
    public boolean handle(Command command) {
        CommandWrite write;

        if (command instanceof CommandWrite) {
            write = (CommandWrite) command;
        } else {
            System.err.println("Could not parse write command");
            return false;
        }

        String fileExtension = Files.getFileExtension(write.getFile().getName());
        ExamBackend backend = BackendFactory.getBackend(fileExtension);

        Scanner in = new Scanner(System.in);

        System.out.print("Please enter the name of the examination: ");
        String examName = in.nextLine();

        System.out.println("Please enter the name of the author of the examination: ");
        String examAuthor = in.nextLine();

        boolean stopped = false;
        int counter = 1;
        List<Question> questionList = new ArrayList<>();

        while(! stopped) {
            System.out.println(String.format("Enter the title for question %d. Write STOP to stop adding more questions", counter));
            String questionName = in.nextLine();

            if (! questionName.equals("STOP")) {
                System.out.println(String.format("Enter the query for question %d", counter));
                String questionQuery = in.nextLine();

                System.out.println(String.format("Enter the answer for question %d", counter));
                String questionAnswer = in.nextLine();

                System.out.println(String.format("Enter a comma-delimited list of keywords that the answer to question %d should contain to be counted valid", counter));
                String allowed = in.nextLine();
                HashSet<String> allowedList = Arrays.asList(allowed.split(","))
                        .stream()
                        .map(String::trim)
                        .collect(Collectors.toCollection(HashSet::new));

                System.out.println(String.format("Enter a comma-delimited list of keywords that the answer to question %d may not contain to be counted valid", counter));
                String banned = in.nextLine();
                HashSet<String> bannedList = Arrays.asList(banned.split(","))
                        .stream()
                        .map(String::trim)
                        .collect(Collectors.toCollection(HashSet::new));

                System.out.println(String.format("Enter a comma-delimited list of hints that the user can receive for question %d", counter));
                String hints = in.nextLine();
                LinkedList<String> hintList = Arrays.asList(hints.split(","))
                        .stream()
                        .map(String::trim)
                        .collect(Collectors.toCollection(LinkedList::new));

                questionList.add(new Question.QuestionBuilder(questionName, questionQuery, questionName)
                        .allowedWords(allowedList)
                        .bannedWords(bannedList)
                        .hints(hintList)
                        .build());
            } else {
                stopped = true;
            }
        }

        Exam exam = new Exam.ExamBuilder(examName)
                .author(examAuthor)
                .questions(questionList)
                .build();

        try {
            //noinspection ResultOfMethodCallIgnored
            write.getFile().createNewFile();

            backend.marshallExam(exam, write.getFile());
            return true;
        } catch (ExamBackendException | IOException e) {
            System.err.println("Could not write exam to file" + e.toString());
            return false;
        }
    }
}
