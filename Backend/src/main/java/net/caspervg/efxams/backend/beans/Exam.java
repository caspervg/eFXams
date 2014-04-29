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

import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@XmlRootElement(name="exam")
public class Exam implements Serializable {

    private UUID id;
    private String name;
    private String author;
    private List<Question> questions;

    public Exam() {
        // No-arg constructor
    }

    public Exam(String name, String author, @NotNull List<Question> questions) {
        this.name = name;
        this.author = author;
        this.questions = questions;

        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    /**
     * Do not set the UUID manually. This method is for binding use only
     * @param id UUID of the Exam
     */
    @XmlAttribute
    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    @XmlElement
    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    @XmlElementWrapper
    @XmlElement(name="question")
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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

            if (! (this.questions.size() == exam.getQuestions().size())) return false;
            for (int i = 0; i < this.questions.size(); i++) {
                if (! this.questions.get(i).equals(exam.getQuestions().get(i))) return false;
            }

            return true;
        }
    }
}
