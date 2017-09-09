package it.basiliocarrabbotta.timbra_cartellini;

import android.content.DialogInterface;

/**
 * Created by Kalos on 22/08/2017.
 */

public class CommandWrapper implements DialogInterface.OnClickListener {

    private ICommand command;
    public CommandWrapper(ICommand command) {
        this.command = command;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        command.execute();
    }
}