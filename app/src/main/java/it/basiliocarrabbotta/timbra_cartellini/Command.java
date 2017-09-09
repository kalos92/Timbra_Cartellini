package it.basiliocarrabbotta.timbra_cartellini;

/**
 * Created by Kalos on 22/08/2017.
 */

public interface Command {
    public void execute();

    public static final Command NO_OP = new Command() { public void execute() {} };
}