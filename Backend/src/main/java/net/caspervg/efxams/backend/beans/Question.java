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

import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Question implements Serializable {

    private UUID id;
    private String title;
    private String query;
    private String answer;
    private List<String> hints;

    public Question() {
        // No-arg constructor
    }

    public Question(String title, String query, String answer, @Nullable List<String> hints) {
        this.title = title;
        this.query = query;
        this.answer = answer;
        this.hints = hints;

        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    /**
     * Do not set the UUID manually. This method is for binding use only
     * @param id UUID of the Question
     */
    @XmlAttribute
    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuery() {
        return query;
    }

    @XmlElement
    public void setQuery(String query) {
        this.query = query;
    }

    public String getAnswer() {
        return answer;
    }

    @XmlElement
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getHints() {
        return hints;
    }

    @XmlElementWrapper
    @XmlElement(name="hint")
    public void setHints(List<String> hints) {
        this.hints = hints;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof Question)) {
            return false;
        } else {
            Question question = (Question) o;
            if (! this.id.equals(question.getId())) return false;
            if (! this.query.equals(question.getQuery())) return false;
            if (! this.answer.equals(question.getAnswer())) return false;

            if (this.hints == null && question.getHints() != null) return false;
            if (this.hints != null && question.getHints() == null) return false;

            if (this.hints == null || this.getHints() == null) return true;

            if (! (this.hints.size() == question.getHints().size())) return false;
            for (int i = 0; i < this.hints.size(); i++) {
                if (! this.hints.get(i).equals(question.getHints().get(i))) return false;
            }

            return true;
        }
    }

}
