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

package net.caspervg.efxams.backend.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.caspervg.efxams.backend.ReportBackend;
import net.caspervg.efxams.backend.beans.Report;
import net.caspervg.efxams.backend.exception.ReportBackendException;

import java.io.*;

public class JsonReportBackend implements ReportBackend {

    @Override
    public void marshallExam(Report report, File file) throws ReportBackendException {
        try (Writer writer = new FileWriter(file)) {
            new Gson().toJson(report, writer);
        } catch (IOException ex) {
            throw new ReportBackendException("Failed to write report to JSON file", ex);
        }
    }

    @Override
    public Report unmarshallReport(File file) throws ReportBackendException {
        try (Reader reader = new FileReader(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, Report.class);
        } catch (IOException ex) {
            throw new ReportBackendException("Failed to read report from JSON file", ex);
        }
    }

}
