package data.csv_handling.transaction_handling;

import static data.csv_handling.transaction_handling.HeaderFields.*;

/**
 * Created by dcmeade on 4/18/2017.
 */
public enum TransactionSource
{
    WELLSFARGOCHECKING(DATE, AMOUNT, UNKNOWN, CHECKNUMBER, DESCRIPTION)
    ;

    private HeaderFields[] header;

    TransactionSource(HeaderFields... header)
    {
        this.header = header;
    }

    public HeaderFields[] getHeader()
    {
        return header;
    }

    // TODO handle if there are more than 1 unknown, would need to return a list
    public int getHeaderIndex(String target)
    {
        int ctr = 0;
        for (HeaderFields headerFields : getHeader())
        {
            if (headerFields.getName().equals(target))
            {
                return ctr;
            }

            ctr++;
        }

        return -1;
    }
}
