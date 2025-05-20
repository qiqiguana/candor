package net.virtualinfinity.atrobots.computer;

/**
 * A special register is a memory location which isn't actually connected to memory, but instead connected to a special
 * hardware input.
 *
 * @author Daniel Pitts
 */
public interface SpecialRegister {
    short get();
}
