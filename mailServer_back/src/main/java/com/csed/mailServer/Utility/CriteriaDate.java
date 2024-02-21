package com.csed.mailServer.Utility;

import com.csed.mailServer.Email.Email;

import java.util.ArrayList;
import java.util.List;

public class CriteriaDate implements ICriteria {
    @Override
    public List meetCriteria(List<Email> mails, String date) {
        List<Email> mailS = new ArrayList<Email>();
        for (Email m:mails) {

            if(m.getDate().toString().contains( date )) {
                mailS.add( m );
            }
        }
        return mailS;
    }
}
