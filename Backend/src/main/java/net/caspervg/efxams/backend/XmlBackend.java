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

package net.caspervg.efxams.backend;

import net.caspervg.efxams.backend.beans.Exam;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlBackend {

    /**
     * Writes the exam data to an XML file, using the default setting for formatting (pretty print)
     * @param exam Exam to write
     * @param file File to write in
     * @throws JAXBException
     */
    public static void marshallXml(Exam exam, File file) throws JAXBException {
        marshallXml(exam, file, true);
    }

    /**
     * Writes the exam data to an XML file
     * @param exam Exam to write
     * @param file File to write in
     * @param formatOutput Should the output be formatted (pretty print) or not
     * @throws JAXBException
     */
    public static void marshallXml(Exam exam, File file, boolean formatOutput) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Exam.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatOutput);

        jaxbMarshaller.marshal(exam, file);
    }

    /**
     * Reads exam data from an XML file
     * @param file File to read from
     * @return Exam
     * @throws JAXBException
     */
    public static Exam unmarshallXml(File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Exam.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        return (Exam) jaxbUnmarshaller.unmarshal(file);
    }
}
