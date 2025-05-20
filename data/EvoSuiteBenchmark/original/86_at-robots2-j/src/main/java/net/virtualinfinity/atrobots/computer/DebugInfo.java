package net.virtualinfinity.atrobots.computer;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps instruction pointer to a source line.
 *
 * @author Daniel Pitts
 */
public class DebugInfo {
    private final Map<Integer, String> lineByInstructionPointer = new HashMap<Integer, String>();
    private final Map<Integer, String> variableNamesByAddress = new HashMap<Integer, String>();
    private final Map<String, Integer> addressesByVariableName = new HashMap<String, Integer>();

    public String getLineForInstructionPointer(int instructionPointer) {
        return lineByInstructionPointer.get(instructionPointer);
    }

    public void setLineForInstructionPointer(int instructionPointer, String line) {
        lineByInstructionPointer.put(instructionPointer, line);
    }

    public void addVariable(int value, String variableName) {
        variableNamesByAddress.put(value, variableName);
        addressesByVariableName.put(variableName.toLowerCase(), value);
    }

    public String getVariableName(int address) {
        if (variableNamesByAddress.get(address) != null) {
            return variableNamesByAddress.get(address) + "@" + address;
        } else {
            return "@" + address;
        }
    }

    public Integer getVariableAddress(String name) {
        return addressesByVariableName.get(name.toLowerCase());
    }


}
