package com.csed.mailServer.Utility;

import com.csed.mailServer.Email.Email;

import java.util.List;

public class SearchDif  implements ICriteria{
    ICriteria criteria;
    ICriteria criteria2;
    ICriteria criteria3;
    ICriteria criteria4;
    ICriteria criteria5;
    ICriteria criteria6;
    ICriteria criteria7;
    public SearchDif(ICriteria criteria, ICriteria criteria2,ICriteria criteria3, ICriteria criteria4, ICriteria criteria5){
        this.criteria = criteria;
        this.criteria2 = criteria2;
        this.criteria3 = criteria3;
        this.criteria4 = criteria4;
        this.criteria5 = criteria5;
    }
    @Override
    public List<Email> meetCriteria(List<Email> mails, String criteria) {
        List<Email> first = this.criteria.meetCriteria( mails,criteria );
        List<Email> second = criteria2.meetCriteria( mails,criteria );
        List<Email> third = this.criteria3.meetCriteria( mails,criteria );
        List<Email> fourth = this.criteria4.meetCriteria( mails,criteria );
        List<Email> fifth = this.criteria5.meetCriteria( mails,criteria );

        for (Email m :second) {
            if(!first.contains( m )){
                first.add( m );
            }
        }
        for (Email m :third) {
            if(!first.contains( m )){
                first.add( m );
            }
        }
        for (Email m :fourth) {
            if(!first.contains( m )){
                first.add( m );
            }
        }
        for (Email m :fifth) {
            if(!first.contains( m )){
                first.add( m );
            }
        }
        return first;
    }
}
