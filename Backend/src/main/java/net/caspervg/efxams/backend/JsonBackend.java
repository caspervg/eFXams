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

import com.google.gson.Gson;
import net.caspervg.efxams.backend.beans.Exam;

import java.io.*;

public class JsonBackend {

    /**
     * Writes the exam data to a JSON file
     * @param exam Exam to write
     * @param file File to write in
     * @throws IOException
     */
    public static void marshallJson(Exam exam, File file) throws IOException {
        Writer writer = new FileWriter(file);
        new Gson().toJson(exam, writer);
        writer.close();
    }

    /**
     * Reads exam data from a JSON file
     * @param file File to read from
     * @return Exam
     * @throws IOException
     */
    public static Exam unmarshallJson(File file) throws IOException {
        Reader reader = new FileReader(file);
        return new Gson().fromJson(reader, Exam.class);
    }
}
