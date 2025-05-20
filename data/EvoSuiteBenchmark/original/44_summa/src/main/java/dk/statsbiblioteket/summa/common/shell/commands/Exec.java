/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package dk.statsbiblioteket.summa.common.shell.commands;

import dk.statsbiblioteket.summa.common.shell.Command;
import dk.statsbiblioteket.summa.common.shell.Script;
import dk.statsbiblioteket.summa.common.shell.ShellContext;

/**
 * <p>This command launches a script which is a series of commands delimited
 * by the semi-colon character ";". Semi colons can be escaped with back slahes
 * </p>
 */
public class Exec extends Command {

    public Exec() {
        super ("exec", "Execute a series of commands delimied by ';' or "
                       + " newline");
        setUsage ("exec <command> [;command...]");
    }
    
    @Override
    public void invoke(ShellContext ctx) throws Exception {
        Script script = new Script(getRawCommandLine());
        script.pushToShellContext(ctx);        
    }
}




