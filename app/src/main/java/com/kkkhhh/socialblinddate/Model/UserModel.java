package com.kkkhhh.socialblinddate.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dev1 on 2016-10-28.
 */
@IgnoreExtraProperties
public class UserModel {
    public String _uID;
    public String _uEmail;
    public String  _uUpdateData;

    public UserModel(){

    }
    public UserModel(String _uID, String _uEmail, String _uUpdateData) {
        this._uID = _uID;
        this._uEmail = _uEmail;
        this._uUpdateData=_uUpdateData;
    }

}
