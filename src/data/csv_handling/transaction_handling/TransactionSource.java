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
}
