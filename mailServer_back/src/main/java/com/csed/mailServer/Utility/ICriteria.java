package com.csed.mailServer.Utility;

import com.csed.mailServer.Email.Email;

import java.util.List;

public interface ICriteria {
    public List<Email> meetCriteria(List<Email> emails, String criteria);
}