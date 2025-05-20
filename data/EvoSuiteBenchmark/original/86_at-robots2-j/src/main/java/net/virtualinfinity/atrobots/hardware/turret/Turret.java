package net.virtualinfinity.atrobots.hardware.turret;

import net.virtualinfinity.atrobots.arena.Heading;
import net.virtualinfinity.atrobots.computer.Resettable;
import net.virtualinfinity.atrobots.hardware.HasHeading;
import net.virtualinfinity.atrobots.hardware.missiles.MissileLauncher;
import net.virtualinfinity.atrobots.hardware.scanning.scanner.Scanner;

/**
 * @author Daniel Pitts
 */
public class Turret implements Resettable, HasHeading {
    private final Heading heading = new Heading();
    private boolean keepshift;
    private Scanner scanner;
    private MissileLauncher missileLauncher;

    public void setKeepshift(boolean keepshift) {
        heading.setAbsolute(keepshift);
        this.keepshift = keepshift;
    }

    public Heading getHeading() {
        return heading;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public MissileLauncher getMissileLauncher() {
        return missileLauncher;
    }

    public void setMissileLauncher(MissileLauncher missileLauncher) {
        this.missileLauncher = missileLauncher;
    }

    public void reset() {
        setKeepshift(false);

    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
