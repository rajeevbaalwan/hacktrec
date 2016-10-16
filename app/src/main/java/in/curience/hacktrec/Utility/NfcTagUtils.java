package in.curience.hacktrec.Utility;

import android.content.Context;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Brekkishhh on 13-08-2016.
 */
public class NfcTagUtils {

    private static final String TAG = "NfcTagUtils";


    public static JSONObject readNfcTag(Tag tag){

        JSONObject tagComponents = new JSONObject();
        String [] techList = tag.getTechList();

        return tagComponents;
    }

    public static String readTag(Tag tag) {
        MifareClassic mfc = MifareClassic.get(tag);

        byte[] data;
        byte[] cardData = null;

        try {       //  5.1) Connect to card
            mfc.connect();
            boolean auth = false;

            // 5.2) and get the number of sectors this card has..and loop thru these sectors
            int secCount = mfc.getSectorCount();
            int bCount = 0;
            int bIndex = 0;
            for (int j = 0; j < secCount; j++) {
                // 6.1) authenticate the sector
                auth = mfc.authenticateSectorWithKeyA(j, MifareClassic.KEY_DEFAULT);
                auth = mfc.authenticateSectorWithKeyB(j,MifareClassic.KEY_DEFAULT);

                if (auth) {


                    // 6.2) In each sector - get the block count
                    bCount = mfc.getBlockCountInSector(j);
                    bIndex = 0;
                    for (int i = 0; i < bCount; i++) {
                        bIndex = mfc.sectorToBlock(j);
                        // 6.3) Read the block
                        data = mfc.readBlock(bIndex);

                        if (bIndex==4){
                            cardData = data;
                        }
                        // 7) Convert the data into a string from Hex format.


                      //  Log.i(TAG, "the Bindex is" + bIndex + "   " + new String(data, Charset.forName("UTF-8")));
                    }

                } else { // Authentication failed - Handle it
                    Log.d(TAG, "Unable to authenticate....");
                }


            }
        } catch (IOException e) {
            Log.e(TAG, ""+e.getLocalizedMessage());

        }

        if (cardData==null){
            return "           No DATA On Card";
        }

        return new String(cardData);
    }// End of method


}

