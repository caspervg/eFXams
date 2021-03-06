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

import net.caspervg.efxams.backend.exception.ExamBackendException;
import net.caspervg.efxams.backend.json.JsonBackend;
import org.junit.Test;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.caspervg.efxams.backend.beans.Exam;
import net.caspervg.efxams.backend.beans.Question;

import static org.junit.Assert.assertEquals;

public class JsonMarshalling {

    @Test
    public void testManualJsonMarshalling() {
        Question q1 = new Question.QuestionBuilder("Question1Question", "Question1Answer").build();
        Question q2 = new Question.QuestionBuilder("Question2Question", "Question2Answer").build();

        List<Question> questionList = new ArrayList<>();
        questionList.addAll(Arrays.asList(q1, q2));

        Exam examBefore = new Exam.ExamBuilder("TestExam")
                .author("TestAuthorName")
                .questions(questionList)
                .build();

        String jsonRep = new Gson().toJson(examBefore);

        Exam examAfter = new Gson().fromJson(jsonRep, Exam.class);

        assertEquals(examBefore, examAfter);
    }

    @Test
    public void testAutomaticJsonMarshalling() throws ExamBackendException {
        Question q1 = new Question.QuestionBuilder("Question1Question", "Question1Answer").build();
        Question q2 = new Question.QuestionBuilder("Question2Question", "Question2Answer").build();

        List<Question> questionList = new ArrayList<>();
        questionList.addAll(Arrays.asList(q1, q2));

        Exam examBefore = new Exam.ExamBuilder("TestExam")
                .author("TestAuthorName")
                .questions(questionList)
                .build();
        File file = new File(getClass().getResource("/json_marshalling.json").getFile());

        new JsonBackend().marshallExam(examBefore, file);

        Exam examAfter = new JsonBackend().unmarshallExam(file);

        assertEquals(examBefore, examAfter);
    }
}
