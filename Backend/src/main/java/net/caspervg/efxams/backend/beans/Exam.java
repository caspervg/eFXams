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

package net.caspervg.efxams.backend.beans;

import net.caspervg.efxams.backend.ExamBackend;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@XmlRootElement(name="exam")
public class Exam implements Serializable {

    private UUID id;
    private String name;
    private String author;
    private @NotNull List<Question> questions;

    public Exam() {
        questions = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    @XmlAttribute
    private void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    private void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    @XmlElement
    private void setAuthor(String author) {
        this.author = author;
    }

    @NotNull
    public List<Question> getQuestions() {
        return questions;
    }

    @XmlElementWrapper
    @XmlElement(name="question")
    private void setQuestions(List<Question> questions) {
        if (questions != null) {
            this.questions = questions;
        } else {
            this.questions = new ArrayList<>();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Exam)) {
            return false;
        } else {
            Exam exam = (Exam) o;
            if (! this.author.equals(exam.getAuthor())) return false;
            if (! this.id.equals(exam.getId())) return false;
            if (! this.name.equals(exam.getName())) return false;

            if (! this.questions.equals(exam.getQuestions())) return false;

            return true;
        }
    }

    public static class ExamBuilder {

        // Required
        private final UUID id;
        private final String name;

        // Optional
        private String author = "";
        private @NotNull List<Question> questions = new ArrayList<>();

        public ExamBuilder(String name) {
            this.id = UUID.randomUUID();
            this.name = name;
        }

        public ExamBuilder author(String value) {
            this.author = value;
            return this;
        }

        public ExamBuilder questions(@NotNull List<Question> value) {
            this.questions = value;
            return this;
        }

        public Exam build() {
            return new Exam(this);
        }
    }

    public Exam(ExamBuilder builder) {
        this();
        this.id = builder.id;
        this.name = builder.name;
        this.author = builder.author;
        this.questions = builder.questions;
    }
}
