package com.dbelgamembership.membersip.Helper.HelperPrintUniversal;

import android.content.Context;

import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;

public class AsyncBluetoothEscPosPrint extends com.dbelgamembership.membersip.Helper.HelperPrintUniversal.AsyncEscPosPrint {
    public AsyncBluetoothEscPosPrint(Context context) {
        super(context);
    }

    protected Integer doInBackground(AsyncEscPosPrinter... printersData) {
        if (printersData.length == 0) {
            return com.dbelgamembership.membersip.Helper.HelperPrintUniversal.AsyncEscPosPrint.FINISH_NO_PRINTER;
        }

        AsyncEscPosPrinter printerData = printersData[0];

        DeviceConnection deviceConnection = printerData.getPrinterConnection();

        if(deviceConnection == null) {
            this.publishProgress(com.dbelgamembership.membersip.Helper.HelperPrintUniversal.AsyncEscPosPrint.PROGRESS_CONNECTING);

            printersData[0] = new AsyncEscPosPrinter(
                    BluetoothPrintersConnections.selectFirstPaired(),
                    printerData.getPrinterDpi(),
                    printerData.getPrinterWidthMM(),
                    printerData.getPrinterNbrCharactersPerLine()
            );
            printersData[0].setTextToPrint(printerData.getTextToPrint());
        }

        return super.doInBackground(printersData);
    }

}
