package com.csed.mailServer.Utility;

import com.csed.mailServer.Email.Email;

import java.util.ArrayList;
import java.util.List;

public class CriteriaSubject implements ICriteria {
    @Override
    public List meetCriteria(List<Email> mails, String subject) {
        List<Email> mailSub = new ArrayList<Email>();
        for (Email m:mails) {
            if(m.getSubject().toLowerCase().contains( subject.toLowerCase() )){
                mailSub.add( m );
            }
        }
        return mailSub;
    }
}
