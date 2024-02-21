package com.csed.mailServer.Utility;

import com.csed.mailServer.Email.Email;

import java.util.ArrayList;
import java.util.List;

public class CriteriaBody implements ICriteria {
    @Override
    public List meetCriteria(List<Email> emails, String criteria) {
        List<Email> FilteredEmails = new ArrayList<Email>();
        for (Email m:emails) {

            if((m.getBody().toLowerCase().contains( criteria.toLowerCase() ))) {
                FilteredEmails.add( m );
            }
        }
        return FilteredEmails;
    }
}
