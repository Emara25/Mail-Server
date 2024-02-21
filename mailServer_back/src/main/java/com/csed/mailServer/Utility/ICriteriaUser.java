package com.csed.mailServer.Utility;

import com.csed.mailServer.User.User;

import java.util.List;

public interface ICriteriaUser {
    public List<User> meetCriteria(List<User> contacts, String criteria);
}
