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
import net.caspervg.efxams.commandline.argument.Command;
import net.caspervg.efxams.commandline.argument.CommandWrite;

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
        System.out.println(backend);

        return true;
    }
}
