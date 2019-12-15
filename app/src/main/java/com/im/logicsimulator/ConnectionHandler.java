package com.im.logicsimulator;
public final class ConnectionHandler {

    /*
     * As this class is a utility class, the default constructor is made private inorder to avoid
     * attempts to instantiate this class.
     */
    private ConnectionHandler() {
    }

    //The check tool1 != tool2 is used to ensure that a gates output cannot be connected to its
    //inputs.
    public static boolean connect(Tool tool1, Tool tool2, Wire wire) {

        if (!(tool1 instanceof Output) && tool2 instanceof Output) {
            return toolToOutput((Node) tool1, (Light) tool2, wire);
        } else if (tool2 instanceof NotGate && tool1 != tool2) {
            return toolToNotGate((Node) tool1, (NotGate) tool2, wire);
        } else if (tool2 instanceof TwoInputTool && tool1 != tool2) {
            return toolToTwoInputTool((Node) tool1, (TwoInputTool) tool2, wire);
        }
        return false;
    }

    private static boolean toolToTwoInputTool(Node tool1, TwoInputTool tool2, Wire wire) {
        if (tool2.getSourceA() == null) {
            tool2.setSourceA(tool1);
            wire.setEnd(tool2.getAInputPositionX(), tool2.getAInputPositionY());
            tool2.setInputWire1(wire);
            addOutputWire(tool1, wire);
            return true;

        } else if (tool2.getSourceB() == null) {
            tool2.setSourceB(tool1);
            wire.setEnd(tool2.getBInputPositionX(), tool2.getBInputPositionY());
            tool2.setInputWire2(wire);
            addOutputWire(tool1, wire);
            return true;
        }
        return false;
    }

    private static void addOutputWire(Node tool1, Wire wire) {
        if (tool1 instanceof Switch) {
            ((Switch) tool1).addOutputWire(wire);
        } else if (tool1 instanceof NotGate) {
            ((NotGate) tool1).addOutputWire(wire);
        } else if (tool1 instanceof TwoInputTool) {
            ((TwoInputTool) tool1).addOutputWire(wire);
        }
    }

    private static boolean toolToNotGate(Node tool1, NotGate tool2, Wire wire) {
        if (tool2.getSource() == null) {
            tool2.setSource(tool1);
            wire.setEnd(tool2.getInputPositionX(), tool2.getInputPositionY());
            tool2.setInputWire(wire);
            addOutputWire(tool1, wire);
            return true;
        }
        return false;
    }

    private static boolean toolToOutput(Node tool1, Output output, Wire wire) {
        if (output.getSource() == null) {
            output.setSource(tool1);
            wire.setEnd(output.getOutputPositionX(), output.getOutputPositionY());
            output.setOutputWire(wire);
            addOutputWire(tool1, wire);
            return true;
        }
        return false;
    }
}