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
import net.caspervg.efxams.backend.xml.XmlBackend;
import net.caspervg.efxams.backend.beans.Exam;
import net.caspervg.efxams.backend.beans.Question;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class XmlMarshalling {

    @Test
    public void testXmlManualMarshalling() throws JAXBException {
        Question q1 = new Question.QuestionBuilder("Question1Question", "Question1Answer")
                .allowedWords(Arrays.asList("Allowed1", "Allowed2").stream().collect(Collectors.toCollection(HashSet::new)))
                .bannedWords(Arrays.asList("Banned1", "Banned2").stream().collect(Collectors.toCollection(HashSet::new)))
                .build();
        Question q2 = new Question.QuestionBuilder("Question2Question", "Question2Answer").build();

        List<Question> questionList = new ArrayList<>();
        questionList.addAll(Arrays.asList(q1, q2));

        Exam examBefore = new Exam.ExamBuilder("TestExam")
                .author("TestAuthorName")
                .questions(questionList)
                .build();

        File file = new File(getClass().getResource("/xml_marshalling.xml").getFile());


        JAXBContext jaxbContext = JAXBContext.newInstance(Exam.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(examBefore, file);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Exam examAfter = (Exam) jaxbUnmarshaller.unmarshal(file);

        assertEquals(examBefore, examAfter);
    }

    @Test
    public void testXmlAutomaticMarshalling() throws ExamBackendException {
        Question q1 = new Question.QuestionBuilder("Question1Question", "Question1Answer").build();
        Question q2 = new Question.QuestionBuilder("Question2Question", "Question2Answer").build();

        List<Question> questionList = new ArrayList<>();
        questionList.addAll(Arrays.asList(q1, q2));

        Exam examBefore = new Exam.ExamBuilder("TestExam")
                .author("TestAuthorName")
                .questions(questionList)
                .build();

        File file = new File(getClass().getResource("/xml_marshalling.xml").getFile());
        new XmlBackend().marshallExam(examBefore, file);

        Exam examAfter = new XmlBackend().unmarshallExam(file);
        assertEquals(examBefore, examAfter);
    }

}
